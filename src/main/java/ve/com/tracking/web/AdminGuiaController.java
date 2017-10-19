package ve.com.tracking.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import ve.com.tracking.core.TipoEstatusGuia;
import ve.com.tracking.exceptions.EntregarGuiaClienteException;
import ve.com.tracking.exceptions.ResourceNotFoundException;
import ve.com.tracking.model.CodigoGuia;
import ve.com.tracking.model.DetalleItem;
import ve.com.tracking.model.EstatusGuia;
import ve.com.tracking.repository.CodigoGuiaRepository;
import ve.com.tracking.repository.EmpresaEnvioRepository;
import ve.com.tracking.repository.TipoCambioRepository;
import ve.com.tracking.services.EstatusService;
import ve.com.tracking.services.GuiaService;
import ve.com.tracking.services.PaqueteService;
import ve.com.tracking.services.UsersService;

@RequestMapping("/admin/guia")
@Controller
public class AdminGuiaController {

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
	private CodigoGuiaRepository codigoGuiaRepository;

	@Autowired
	private TipoCambioRepository tipoCambioRepository;

	@RequestMapping(method = RequestMethod.POST, value = "{id}")
	public void post(@PathVariable Long id, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = "text/html")
	public String searchForm(Model uiModel) {
		return "admin/guia/search";
	}

	@RequestMapping(value = "/entregar", method = RequestMethod.POST, produces = "text/html")
	public String entregar(Model uiModel,
			final RedirectAttributes redirectAttributes,
			HttpServletRequest httpServletRequest) {
		String codigo = httpServletRequest.getParameter("codigo");
		CodigoGuia codigoGuia = codigoGuiaRepository.findByCodigo(codigo);
		try {
			guiaService.entregarGuiaCliente(codigoGuia);
			redirectAttributes.addFlashAttribute("messageSuccess",
					"Producto con nuevo estatus: " + codigoGuia.getEstatus());
		} catch (EntregarGuiaClienteException e) {
			redirectAttributes
					.addFlashAttribute("messageError", e.getMessage());
			return "redirect:/admin/guia/show?codigo=" + codigo;
		}
		return "redirect:/admin/guia/show?codigo=" + codigo;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET, produces = "text/html")
	public String show(Model uiModel,
			@RequestParam(required = true) String codigo,
			final RedirectAttributes redirectAttributes) {
		CodigoGuia codigoGuia = codigoGuiaRepository.findByCodigo(codigo);
		if (codigoGuia == null) {
			redirectAttributes.addFlashAttribute("messageError",
					"Producto no encontrado");
			return "redirect:/admin/guia/list?page=1&size=10";

		}
		List<DetalleItem> detalleItems = new ArrayList<DetalleItem>();
		try {
			detalleItems = guiaService.findAllDetalleItems(codigoGuia
					.getGuiaId());
		} catch (ResourceNotFoundException e) {
			redirectAttributes
					.addFlashAttribute("messageError", e.getMessage());
			return "redirect:/admin/package/list";
		}
		if (detalleItems == null) {
			detalleItems = new ArrayList<DetalleItem>();
		}
		List<EstatusGuia> estatuses = estatusService
				.findAllEstatusGuia(codigoGuia);
		uiModel.addAttribute("estatuses", estatuses);
		uiModel.addAttribute("allEstatus", estatusService.getAllEstatusGuia());
		uiModel.addAttribute("detalleItems", detalleItems);
		uiModel.addAttribute("codigoGuia", codigoGuia);
		addDateTimeFormatPatterns(uiModel);
		return "admin/guia/show";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "text/html")
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "searchString", required = false) String searchString,
			@RequestParam(value = "estatus", required = false) String estatus,
			Model uiModel) {
		TipoEstatusGuia enumEstatus = null;
		try {
			if (StringUtils.isNotBlank(estatus))
				enumEstatus = TipoEstatusGuia.valueOf(estatus);
		} catch (Exception e) {
			return "redirect:/admin/guia/list?page=1&size=10";
		}
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1)
					* sizeNo;
			float nrOfPages;
			if (StringUtils.isNotBlank(searchString) && enumEstatus == null) {
				uiModel.addAttribute("guias", guiaService.searchGuias(
						searchString, firstResult, sizeNo));
				nrOfPages = (float) guiaService.countSearchGuias(searchString)
						/ sizeNo;
			} else if (StringUtils.isNotBlank(searchString)
					&& enumEstatus != null) {
				uiModel.addAttribute("guias", guiaService.searchGuias(
						searchString, enumEstatus, firstResult, sizeNo));
				nrOfPages = (float) guiaService.countSearchGuias(searchString,
						enumEstatus) / sizeNo;
			} else if (StringUtils.isBlank(searchString) && enumEstatus != null) {
				uiModel.addAttribute("guias", guiaService.searchGuias(
						enumEstatus, firstResult, sizeNo));
				nrOfPages = (float) guiaService.countSearchGuias(enumEstatus)
						/ sizeNo;
			} else {
				uiModel.addAttribute("guias",
						guiaService.findGuiasEntries(firstResult, sizeNo));
				nrOfPages = (float) guiaService.countAllGuiasView() / sizeNo;
			}
			uiModel.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			uiModel.addAttribute("guias", guiaService.findAllGuiasView());

		}
		addDateTimeFormatPatterns(uiModel);
		return "admin/guia/list";
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

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(
				"date_pattern",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
		uiModel.addAttribute("tipoEstatus",
				Arrays.asList(TipoEstatusGuia.values()));
	}
}
