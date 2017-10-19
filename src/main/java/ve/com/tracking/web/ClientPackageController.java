package ve.com.tracking.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import ve.com.tracking.exceptions.ClientException;
import ve.com.tracking.forms.PackageNotifyForm;
import ve.com.tracking.model.DetalleGuiasPaquete;
import ve.com.tracking.model.EstatusPaquete;
import ve.com.tracking.model.Paquete;
import ve.com.tracking.model.Users;
import ve.com.tracking.repository.EmpresaEnvioRepository;
import ve.com.tracking.repository.TipoCambioRepository;
import ve.com.tracking.services.EstatusService;
import ve.com.tracking.services.GuiaService;
import ve.com.tracking.services.PaqueteService;
import ve.com.tracking.services.UsersService;

/**
 * 
 * @author Williams Rivas
 * 
 *         Created 08/04/2014 14:52:13
 */

@RequestMapping("/client/package/**")
@Controller
public class ClientPackageController {

	@Autowired
	private PaqueteService packageService;

	@Autowired
	private UsersService userService;

	@Autowired
	private EstatusService estatusService;

	@Autowired
	private EmpresaEnvioRepository empresaEnvioRepository;

	@Autowired
	private GuiaService guiaService;

	@Autowired
	private TipoCambioRepository tipoCambioRepository;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/notify", method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid PackageNotifyForm notify,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest,
			final RedirectAttributes redirectAttributes) {
		if (!CaptchaController.validateCaptcha(httpServletRequest)) {
			uiModel.addAttribute("captchaError", messageSource.getMessage(
					"field_invalid_captcha", null,
					LocaleContextHolder.getLocale()));
			populateNotifyForm(uiModel, notify);
			return "client/package/notify";
		}
		if (bindingResult.hasErrors()) {
			populateNotifyForm(uiModel, notify);
			return "client/package/notify";
		}
		try {
			packageService.notifyPaquete(notify);
			if (notify.getHasTracking().equals("n")) {
				redirectAttributes
						.addFlashAttribute(
								"messageSuccess",
								"La notificación del paquete con tracking: "
										+ notify.getTracking()
										+ " autogenerado por nuestro sistema fue registrada satisfactoriamente");
			} else {
				redirectAttributes.addFlashAttribute(
						"messageSuccess",
						"La notificación del paquete con tracking: "
								+ notify.getTracking()
								+ " fue registrada en nuestro sistema");
			}
		} catch (ClientException e) {
			redirectAttributes.addFlashAttribute(
					"messageError",
					"Error al intentar realizar la notificación: "
							+ e.getMessage());
		}
		return "redirect:/client/package/notify";
	}

	@RequestMapping(value = "/notify", method = RequestMethod.GET, produces = "text/html")
	public String notifyForm(Model uiModel) {
		populateNotifyForm(uiModel, new PackageNotifyForm());
		return "client/package/notify";
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET, produces = "text/html")
	public String show(Model uiModel, @RequestParam(required = true) Long id,
			final RedirectAttributes redirectAttributes) {
		Paquete paquete = packageService.findPaquete(id);
		if (paquete == null) {
			redirectAttributes.addFlashAttribute("messageError",
					"Recurso no encontrado");
			return "redirect:/client/package/list?page=1&size=10";
		}
		String username = paquete.getDetalleNotificacion().getUsersId()
				.getUsername();
		String principal = userService.getPrincipal().getUsername();
		if (!username.equals(principal)) {
			redirectAttributes.addFlashAttribute("messageError",
					"Recurso no encontrado");
			return "redirect:/client/package/list";
		}
		uiModel.addAttribute("paquete", paquete);
		List<EstatusPaquete> estatuses = estatusService
				.findAllEstatusPaquete(paquete);
		uiModel.addAttribute("estatuses", estatuses);
		uiModel.addAttribute("allEstatus",
				estatusService.getAllEstatusPaquete());
		List<DetalleGuiasPaquete> detalleGuias = packageService
				.findAllDetallesGuiasPaquete(paquete);
		if (detalleGuias != null) {
			uiModel.addAttribute("guias", detalleGuias);
		} else {
			uiModel.addAttribute("guias", new ArrayList<DetalleGuiasPaquete>());
		}
		addDateTimeFormatPatterns(uiModel);
		return "client/package/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "searchString", required = false) String searchString,
			Model uiModel, HttpServletRequest request) {
		Users cliente = userService.getPrincipal();
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1)
					* sizeNo;
			float nrOfPages;
			if (searchString != null && !searchString.equals("")) {
				uiModel.addAttribute("paquetes", packageService
						.searchPaquetesByCliente(searchString, cliente,
								firstResult, sizeNo));
				nrOfPages = (float) packageService
						.countSearchPaquetesByCliente(searchString, cliente)
						/ sizeNo;
			} else {
				uiModel.addAttribute("paquetes", packageService
						.findPaquetesByCliente(cliente, firstResult, sizeNo));
				nrOfPages = (float) packageService
						.countPaquetesByCliente(cliente) / sizeNo;
			}
			uiModel.addAttribute("maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0)
							? nrOfPages + 1
							: nrOfPages));
		} else {
			return "redirect:/client/package/list?page=1&size=10";
		}
		addDateTimeFormatPatterns(uiModel);
		return "client/package/list";
	}

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(
				"detallenotifications_created_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
		uiModel.addAttribute(
				"detallenotifications_updated_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
	}

	void populateNotifyForm(Model uiModel, PackageNotifyForm notify) {
		uiModel.addAttribute("packageNotifyForm", notify);
		uiModel.addAttribute("empresaenvioes", empresaEnvioRepository.findAll());
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
