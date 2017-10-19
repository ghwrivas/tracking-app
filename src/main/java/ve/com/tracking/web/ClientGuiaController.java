package ve.com.tracking.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

@RequestMapping("/client/guia/**")
@Controller
public class ClientGuiaController {

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

	@RequestMapping(value = "/show", method = RequestMethod.GET, produces = "text/html")
	public String show(Model uiModel, @RequestParam(required = true) String codigo,
			final RedirectAttributes redirectAttributes) {

		CodigoGuia codigoGuia = codigoGuiaRepository.findByCodigo(codigo);
		
		String username = codigoGuia.getGuiaId().getCliente().getUsername();
		
		String principal = userService.getPrincipal().getUsername();
		if (!username.equals(principal)) {
			redirectAttributes.addFlashAttribute("messageError",
					"El producto no fue encontrado");
			return "redirect:/client/guia/list";
		}
		List<DetalleItem> detalleItems = new ArrayList<DetalleItem>();
		try {
			detalleItems = guiaService.findAllDetalleItems(codigoGuia
					.getGuiaId());
		} catch (ResourceNotFoundException e) {
			redirectAttributes
					.addFlashAttribute("messageError", e.getMessage());
			return "redirect:/client/guia/list";
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
		return "client/guia/show";
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
				DateTimeFormat.patternForStyle("MM",
						LocaleContextHolder.getLocale()));
	}
}
