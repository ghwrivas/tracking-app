package ve.com.tracking.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
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

import ve.com.tracking.core.TipoEstatusPaquete;
import ve.com.tracking.exceptions.ClientException;
import ve.com.tracking.forms.PackageRecepcionarForm;
import ve.com.tracking.model.DetalleGuiasPaquete;
import ve.com.tracking.model.EstatusPaquete;
import ve.com.tracking.model.Paquete;
import ve.com.tracking.repository.EmpresaEnvioRepository;
import ve.com.tracking.services.EstatusService;
import ve.com.tracking.services.PaqueteService;
import ve.com.tracking.services.UsersService;

@RequestMapping("/admin/package/**")
@Controller
public class AdminPackageController {

	@Autowired
	private PaqueteService paqueteService;

	@Autowired
	private UsersService userService;

	@Autowired
	private EstatusService estatusService;

	@Autowired
	private EmpresaEnvioRepository empresaEnvioRepository;

	@RequestMapping(value = "/recepcionar", method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid PackageRecepcionarForm formRecepcionar,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest,
			final RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			populateRecepcionarForm(uiModel, formRecepcionar);
			return "admin/package/recepcionar";
		}
		try {
			paqueteService.recepcionarPaquete(formRecepcionar);
			redirectAttributes.addFlashAttribute(
					"messageSuccess",
					"La recepción del tracking: "
							+ formRecepcionar.getTracking()
							+ " fue registrada en nuestro sistema");
		} catch (ClientException e) {
			redirectAttributes.addFlashAttribute(
					"messageError",
					"Error al intentar realizar la recepción: "
							+ e.getMessage());
		}
		return "redirect:/admin/package/recepcionar";
	}

	@RequestMapping(value = "/recepcionar", method = RequestMethod.GET, produces = "text/html")
	public String recepcionarForm(Model uiModel,
			@RequestParam(required = false) Long id,
			final RedirectAttributes redirectAttributes) {
		if (id != null) { // viene del listado para recepcionar
			Paquete paquete = paqueteService.findPaquete(id);
			if (paquete != null) {
				if (paquete.getDetalleRecepcion() != null) {
					redirectAttributes.addFlashAttribute("messageError",
							"Paquete ya recepcionado");
					return "redirect:/admin/package/list?page=1&size=10";
				}
				if (paquete.getEstatus() == TipoEstatusPaquete.NOTIFICADO) {
					PackageRecepcionarForm paqueteForm = new PackageRecepcionarForm();
					paqueteForm.setDescripcion(paquete.getDetalleNotificacion()
							.getDescripcion());
					paqueteForm.setEmpresaEnvioId(paquete
							.getDetalleNotificacion().getEmpresaEnvioId());
					paqueteForm.setTracking(paquete.getTracking());
					populateRecepcionarForm(uiModel, paqueteForm);
					return "admin/package/recepcionar";
				} else {
					redirectAttributes
							.addFlashAttribute(
									"messageError",
									"El paquete de id: "
											+ id
											+ ", para poder ser recepcionado debe tener estatus: "
											+ TipoEstatusPaquete.NOTIFICADO);
					return "redirect:/admin/package/list?page=1&size=10";
				}

			} else {
				redirectAttributes.addFlashAttribute("messageError",
						"El paquete de id: " + id + " no fue encontrado");
				return "redirect:/admin/package/list?page=1&size=10";
			}
		} else {
			populateRecepcionarForm(uiModel, new PackageRecepcionarForm());
			return "admin/package/recepcionar";
		}
	}

	@RequestMapping(produces = "text/html")
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "searchString", required = false) String searchString,
			@RequestParam(value = "estatus", required = false) String estatus,
			@RequestParam(value = "trackingGenerado", required = false) String trackingGenerado,
			Model uiModel) {
		TipoEstatusPaquete enumEstatus = null;
		String paramTrackingGenerado = "";
		try {
			if (StringUtils.isNotBlank(estatus))
				enumEstatus = TipoEstatusPaquete.valueOf(estatus);
			if (StringUtils.equalsIgnoreCase("si", trackingGenerado)
					|| StringUtils.equalsIgnoreCase("no", trackingGenerado))
				paramTrackingGenerado = StringUtils.lowerCase(trackingGenerado);
		} catch (Exception e) {
			return "redirect:/admin/package/list?page=1&size=10";
		}

		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1)
					* sizeNo;
			float nrOfPages;
			if (StringUtils.isNotBlank(searchString)
					&& StringUtils.isBlank(paramTrackingGenerado)
					&& enumEstatus == null) {
				uiModel.addAttribute("paquetes", paqueteService.searchPaquetes(
						searchString, firstResult, sizeNo));
				nrOfPages = (float) paqueteService
						.countSearchPaquetes(searchString) / sizeNo;
			} else if (StringUtils.isNotBlank(searchString)
					&& StringUtils.isBlank(paramTrackingGenerado)
					&& enumEstatus != null) {
				uiModel.addAttribute("paquetes", paqueteService.searchPaquetes(
						searchString, enumEstatus, firstResult, sizeNo));
				nrOfPages = (float) paqueteService.countSearchPaquetes(
						searchString, enumEstatus) / sizeNo;
			} else if (StringUtils.isBlank(searchString)
					&& StringUtils.isBlank(paramTrackingGenerado)
					&& enumEstatus != null) {
				uiModel.addAttribute("paquetes", paqueteService.searchPaquetes(
						enumEstatus, firstResult, sizeNo));
				nrOfPages = (float) paqueteService
						.countSearchPaquetes(enumEstatus) / sizeNo;
			} else if (StringUtils.isNotBlank(paramTrackingGenerado)
					&& StringUtils.isNotBlank(searchString)
					&& enumEstatus != null) {// a
				uiModel.addAttribute("paquetes", paqueteService.searchPaquetes(
						getBoolean(paramTrackingGenerado), searchString,
						enumEstatus, firstResult, sizeNo));
				nrOfPages = (float) paqueteService.countSearchPaquetes(
						getBoolean(paramTrackingGenerado), searchString,
						enumEstatus)
						/ sizeNo;
			} else if (StringUtils.isNotBlank(paramTrackingGenerado)
					&& StringUtils.isNotBlank(searchString)
					&& enumEstatus == null) {
				uiModel.addAttribute("paquetes", paqueteService.searchPaquetes(
						getBoolean(paramTrackingGenerado), searchString,
						firstResult, sizeNo));
				nrOfPages = (float) paqueteService.countSearchPaquetes(
						getBoolean(paramTrackingGenerado), searchString)
						/ sizeNo;
			} else if (StringUtils.isNotBlank(paramTrackingGenerado)
					&& StringUtils.isBlank(searchString) && enumEstatus != null) {
				uiModel.addAttribute("paquetes", paqueteService.searchPaquetes(
						getBoolean(paramTrackingGenerado), enumEstatus,
						firstResult, sizeNo));
				nrOfPages = (float) paqueteService.countSearchPaquetes(
						getBoolean(paramTrackingGenerado), enumEstatus)
						/ sizeNo;
			} else if (StringUtils.isNotBlank(paramTrackingGenerado)
					&& StringUtils.isBlank(searchString) && enumEstatus == null) {
				uiModel.addAttribute("paquetes", paqueteService.searchPaquetes(
						getBoolean(paramTrackingGenerado), firstResult, sizeNo));
				nrOfPages = (float) paqueteService
						.countSearchPaquetes(getBoolean(paramTrackingGenerado))
						/ sizeNo;
			} else {
				uiModel.addAttribute("paquetes",
						paqueteService.findPaqueteEntries(firstResult, sizeNo));
				nrOfPages = (float) paqueteService.countAllPaquetes() / sizeNo;
			}
			uiModel.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			uiModel.addAttribute("paquetes", paqueteService.findAllPaquetes());
		}
		addDateTimeFormatPatterns(uiModel);
		return "admin/package/list";
	}

	private boolean getBoolean(String value) {
		return value.equals("si");
	}

	@RequestMapping(value = "/generate/guias", produces = "text/html")
	public String listNotificadosConfirmadosGenerateGuias(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "searchString", required = false) String searchString,
			Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1)
					* sizeNo;
			float nrOfPages;
			if (searchString != null && !searchString.equals("")) {
				uiModel.addAttribute("users", paqueteService
						.searchPaquetesNotificadoConfirmado(searchString,
								firstResult, sizeNo));
				nrOfPages = (float) paqueteService
						.countSearchPaquetesNotificadoConfirmado(searchString)
						/ sizeNo;
			} else {
				uiModel.addAttribute("users",
						paqueteService.findEntries(firstResult, sizeNo));
				nrOfPages = (float) paqueteService.countAllPaquetes() / sizeNo;
			}
			uiModel.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			uiModel.addAttribute("users",
					paqueteService.findPaquetesNotificadoConfirmado());
		}
		addDateTimeFormatPatterns(uiModel);
		return "admin/package/generate/guias";
	}

	@RequestMapping(value = "/generate/recibos", produces = "text/html")
	public String listNotificadosConfirmadosGenerateRecibos(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "searchString", required = false) String searchString,
			Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1)
					* sizeNo;
			float nrOfPages;
			if (searchString != null && !searchString.equals("")) {
				uiModel.addAttribute("users", paqueteService
						.searchPaquetesNotificadoConfirmado(searchString,
								firstResult, sizeNo));
				nrOfPages = (float) paqueteService
						.countSearchPaquetesNotificadoConfirmado(searchString)
						/ sizeNo;
			} else {
				uiModel.addAttribute("users",
						paqueteService.findEntries(firstResult, sizeNo));
				nrOfPages = (float) paqueteService
						.countPaquetesNotificadoConfirmado() / sizeNo;
			}
			uiModel.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			uiModel.addAttribute("users",
					paqueteService.findPaquetesNotificadoConfirmado());
		}
		addDateTimeFormatPatterns(uiModel);
		return "admin/package/generate/recibos";
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET, produces = "text/html")
	public String show(Model uiModel, @RequestParam(required = true) Long id,
			final RedirectAttributes redirectAttributes) {
		Paquete paquete = paqueteService.findPaquete(id);
		if (paquete == null) {
			redirectAttributes.addFlashAttribute("messageError",
					"El paquete no fue encontrado");
			return "redirect:/client/package/list";
		}
		uiModel.addAttribute("paquete", paquete);
		List<EstatusPaquete> estatuses = estatusService
				.findAllEstatusPaquete(paquete);
		uiModel.addAttribute("estatuses", estatuses);

		uiModel.addAttribute("allEstatus",
				estatusService.getAllEstatusPaquete());

		List<DetalleGuiasPaquete> detalleGuias = paqueteService
				.findAllDetallesGuiasPaquete(paquete);
		if (detalleGuias != null) {
			uiModel.addAttribute("guias", detalleGuias);
		} else {
			uiModel.addAttribute("guias", new ArrayList<DetalleGuiasPaquete>());
		}
		addDateTimeFormatPatterns(uiModel);
		return "admin/package/show";
	}

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(
				"detallerecepcions_created_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
		uiModel.addAttribute(
				"detallerecepcions_updated_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
		uiModel.addAttribute("tipoEstatus",
				Arrays.asList(TipoEstatusPaquete.values()));
	}

	void populateRecepcionarForm(Model uiModel, PackageRecepcionarForm form) {
		uiModel.addAttribute("packageRecepcionarForm", form);
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
