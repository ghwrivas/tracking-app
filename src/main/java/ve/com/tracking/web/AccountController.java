package ve.com.tracking.web;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import ve.com.tracking.exceptions.AccountActivationException;
import ve.com.tracking.exceptions.ResourceNotFoundException;
import ve.com.tracking.forms.AccountCreateForm;
import ve.com.tracking.forms.AccountUpdateForm;
import ve.com.tracking.forms.PasswordResetForm;
import ve.com.tracking.model.Users;
import ve.com.tracking.services.AccountManager;
import ve.com.tracking.services.UsersService;

@RequestMapping("/account")
@Controller
public class AccountController {

	private static final Logger log = Logger.getLogger(AccountController.class);

	@Autowired
	AccountManager accountManager;

	@Autowired
	UsersService usersService;

	@Autowired
	MessageSource messageSource;

	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid AccountCreateForm account,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest,
			final RedirectAttributes redirectAttributes) {
		if (!CaptchaController.validateCaptcha(httpServletRequest)) {
			uiModel.addAttribute("captchaError", messageSource.getMessage(
					"field_invalid_captcha", null,
					LocaleContextHolder.getLocale()));
			populateAccountCreateForm(uiModel, account);
			return "account/create";
		}
		if (bindingResult.hasErrors()) {
			populateAccountCreateForm(uiModel, account);
			return "account/create";
		}
		try {
			accountManager.createAccount(account);
			redirectAttributes.addFlashAttribute("messageSuccess",
					"Su cuenta ha sido creada");
		} catch (CannotCreateTransactionException e) {
			uiModel.addAttribute("messageError", messageSource.getMessage(
					"error_dataaccessfailure_title", null,
					LocaleContextHolder.getLocale()));
			log.error("Error al intentar crear la cuenta: " + e.getMessage());
			populateAccountCreateForm(uiModel, account);
			return "account/create";
		} catch (MailException e) {
			uiModel.addAttribute("messageError", messageSource.getMessage(
					"error_send_email", null, LocaleContextHolder.getLocale()));
			log.error("Error al intentar crear la cuenta: " + e.getMessage());
			populateAccountCreateForm(uiModel, account);
			return "account/create";
		}
		return "redirect:/account/create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET, produces = "text/html")
	public String createForm(Model uiModel) {
		populateAccountCreateForm(uiModel, new AccountCreateForm());
		return "account/create";
	}

	@RequestMapping(value = "/activate", method = RequestMethod.GET, produces = "text/html")
	public String activate(ModelMap model, @RequestParam() String email,
			@RequestParam() String token,
			final RedirectAttributes redirectAttributes) {
		try {
			accountManager.activateAccount(email, token);
			model.addAttribute("messageSuccess",
					"La cuenta para el usuario con cuenta de correo: " + email
							+ " ha sido activada");
		} catch (CannotCreateTransactionException e) {
			model.addAttribute("messageError", messageSource.getMessage(
					"error_dataaccessfailure_title", null,
					LocaleContextHolder.getLocale()));
			log.error("Error al intentar activar la cuenta: " + e.getMessage());
		} catch (AccountActivationException ae) {
			model.addAttribute("messageError", ae.getMessage());
		} catch (ResourceNotFoundException re) {
			model.addAttribute("messageError", re.getMessage());
		}
		return "account/activate";
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid AccountUpdateForm account,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest,
			final RedirectAttributes redirectAttributes) {
		if (!CaptchaController.validateCaptcha(httpServletRequest)) {
			uiModel.addAttribute("captchaError", messageSource.getMessage(
					"field_invalid_captcha", null,
					LocaleContextHolder.getLocale()));
			populateAccountUpdateFormAsErrors(uiModel, account);
			return "account/update";
		}
		if (bindingResult.hasErrors()) {
			populateAccountUpdateFormAsErrors(uiModel, account);
			return "account/update";
		}
		accountManager.updateAccount(account);
		redirectAttributes.addFlashAttribute("messageSuccess",
				"Sus datos se han actualizado");
		return "redirect:/account/update";
	}

	private void populateAccountUpdateFormAsErrors(Model uiModel,
			AccountUpdateForm account) {
		uiModel.addAttribute("accountCreateForm", account);
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET, produces = "text/html")
	public String updateForm(Model uiModel) {
		Users user = usersService.getPrincipal();
		populateAccountUpdateForm(uiModel, user);
		return "account/update";
	}

	@RequestMapping(value = "/resend", method = RequestMethod.POST, produces = "text/html")
	public String resend(@Valid PasswordResetForm passwordResetForm,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest,
			final RedirectAttributes redirectAttributes) {
		if (!CaptchaController.validateCaptcha(httpServletRequest)) {
			uiModel.addAttribute("captchaError", messageSource.getMessage(
					"field_invalid_captcha", null,
					LocaleContextHolder.getLocale()));
			populateResendForm(uiModel, passwordResetForm);
			return "account/resend";
		}
		if (bindingResult.hasErrors()) {
			populateResendForm(uiModel, passwordResetForm);
			return "account/resend";
		}
		uiModel.asMap().clear();
		try {
			accountManager.resendActivationLink(passwordResetForm.getEmail());
			redirectAttributes.addFlashAttribute("messageSuccess",
					"Se le ha reenviado el enlace de activación");
		} catch (CannotCreateTransactionException e) {
			redirectAttributes.addFlashAttribute("messageError", messageSource
					.getMessage("error_dataaccessfailure_title", null,
							LocaleContextHolder.getLocale()));
			log.error("Error al intentar reactivar la cuenta: " + e.getMessage());
		} catch (ResourceNotFoundException re) {
			redirectAttributes.addFlashAttribute("messageError",
					re.getMessage());
		} catch (MailException ee) {
			redirectAttributes.addFlashAttribute("messageError",
					ee.getMessage());
			log.error("Error al intentar reenviar el token de activacion: "
					+ ee.getMessage());
		} catch (AccountActivationException ee) {
			redirectAttributes.addFlashAttribute("messageError",
					ee.getMessage());
		}
		return "redirect:/account/resend";
	}

	@RequestMapping(value = "/resend", method = RequestMethod.GET, produces = "text/html")
	public String resendForm(Model uiModel) {
		populateResendForm(uiModel, new PasswordResetForm());
		return "account/resend";
	}

	void populateResendForm(Model uiModel, PasswordResetForm passwordResetForm) {
		uiModel.addAttribute("passwordResetForm", passwordResetForm);
	}

	void populateAccountUpdateForm(Model uiModel, Users user) {
		AccountUpdateForm account = new AccountUpdateForm();
		account.setId(user.getId());
		account.setBbPin(user.getBbPin());
		account.setDireccion(user.getDireccion());
		account.setFirstName(user.getFirstName());
		account.setLastName(user.getLastName());
		account.setTelefono(user.getTelefono());
		uiModel.addAttribute("accountUpdateForm", account);

	}

	void populateAccountCreateForm(Model uiModel, AccountCreateForm account) {
		uiModel.addAttribute("accountCreateForm", account);
	}

	String encodeUrlPathSegment(String pathSegment,
			HttpServletRequest httpServletRequest) {
		String enc = httpServletRequest.getCharacterEncoding();
		if (enc == null) {
			enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
		}
		try {
			pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
		} catch (UnsupportedEncodingException uee) {
		}
		return pathSegment;
	}
}
