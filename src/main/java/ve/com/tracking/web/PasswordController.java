package ve.com.tracking.web;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.MailException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import ve.com.tracking.exceptions.ClientException;
import ve.com.tracking.exceptions.ResourceNotFoundException;
import ve.com.tracking.forms.PasswordChangeForm;
import ve.com.tracking.forms.PasswordResetForm;
import ve.com.tracking.model.Users;
import ve.com.tracking.services.UsersService;

@RequestMapping("/password")
@Controller
public class PasswordController {

	private static final Logger log = Logger
			.getLogger(PasswordController.class);

	@Autowired
	private UsersService usersService;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/reset", method = RequestMethod.POST, produces = "text/html")
	public String reset(@Valid PasswordResetForm passwordResetForm,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest,
			final RedirectAttributes redirectAttributes) {
		if (!CaptchaController.validateCaptcha(httpServletRequest)) {
			uiModel.addAttribute("captchaError", messageSource.getMessage(
					"field_invalid_captcha", null,
					LocaleContextHolder.getLocale()));
			populateResetForm(uiModel, passwordResetForm);
			return "password/reset";
		}
		if (bindingResult.hasErrors()) {
			populateResetForm(uiModel, passwordResetForm);
			return "password/reset";
		}
		try {
			usersService.resetPassword(passwordResetForm.getEmail());
			redirectAttributes.addFlashAttribute("messageSuccess",
					"Se le ha enviado un nuevo password");
		} catch (ResourceNotFoundException re) {
			redirectAttributes.addFlashAttribute("messageError",
					re.getMessage());
		} catch (MailException ee) {
			uiModel.addAttribute("messageError", messageSource.getMessage(
					"error_send_email", null, LocaleContextHolder.getLocale()));
			log.error("Error al intentar resetear el password: "
					+ ee.getMessage());
			populateResetForm(uiModel, passwordResetForm);
			return "password/reset";			
		}
		return "redirect:/password/reset";
	}

	@RequestMapping(value = "/reset", method = RequestMethod.GET, produces = "text/html")
	public String resetForm(Model uiModel) {
		populateResetForm(uiModel, new PasswordResetForm());
		return "password/reset";
	}

	@RequestMapping(value = "/change", method = RequestMethod.POST, produces = "text/html")
	public String change(@Valid PasswordChangeForm passwordChangeForm,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest,
			final RedirectAttributes redirectAttributes) {
		if (!CaptchaController.validateCaptcha(httpServletRequest)) {
			uiModel.addAttribute("captchaError", messageSource.getMessage(
					"field_invalid_captcha", null,
					LocaleContextHolder.getLocale()));
			populateChangeForm(uiModel, passwordChangeForm);
			return "password/change";
		}
		if (bindingResult.hasErrors()) {
			populateChangeForm(uiModel, passwordChangeForm);
			return "password/change";
		}
		uiModel.asMap().clear();
		try {
			usersService.changePassword(passwordChangeForm);
			redirectAttributes.addFlashAttribute("messageSuccess",
					"El password fue cambiado");
		} catch (ClientException e) {
			redirectAttributes
					.addFlashAttribute("messageError", e.getMessage());
		}
		return "redirect:/password/change";
	}

	@RequestMapping(value = "/change", method = RequestMethod.GET, produces = "text/html")
	public String changeForm(Model uiModel) {
		Users user = getUserAuthenticated();
		PasswordChangeForm pf = new PasswordChangeForm();
		pf.setId(user.getId());
		populateChangeForm(uiModel, pf);
		return "password/change";
	}

	private Users getUserAuthenticated() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();

		return usersService.findUsersFromAuthentication(auth.getName());
	}

	private void populateChangeForm(Model uiModel,
			PasswordChangeForm passwordChangeForm) {
		uiModel.addAttribute("passwordChangeForm", passwordChangeForm);
	}

	void populateResetForm(Model uiModel, PasswordResetForm passwordResetForm) {
		uiModel.addAttribute("passwordResetForm", passwordResetForm);
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
