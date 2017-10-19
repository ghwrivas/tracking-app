package ve.com.tracking.web;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import ve.com.tracking.core.TipoCargo;
import ve.com.tracking.core.TipoEstatusRecibo;
import ve.com.tracking.core.TipoTransportacion;
import ve.com.tracking.model.ReciboAlmacen;
import ve.com.tracking.services.ReciboService;

@RequestMapping("/admin/recibo")
@Controller
@RooWebScaffold(path = "admin/recibo", formBackingObject = ReciboAlmacen.class)
public class ReciboAlmacenController {

	@Autowired
	private ReciboService reciboService;

	@RequestMapping(produces = "text/html", value = "/list")
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "searchString", required = false) String searchString,
			@RequestParam(value = "estatus", required = false) String estatus,
			Model uiModel) {
		TipoEstatusRecibo enumEstatus = null;
		try {
			if (StringUtils.isNotBlank(estatus))
				enumEstatus = TipoEstatusRecibo.valueOf(estatus);
		} catch (Exception e) {
			return "redirect:/admin/recibo/list?page=1&size=10";
		}
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1)
					* sizeNo;
			float nrOfPages;
			if (StringUtils.isNotBlank(searchString) && enumEstatus == null) {
				uiModel.addAttribute("recibos", reciboService
						.searchReciboAlmacen(searchString, firstResult, sizeNo));
				nrOfPages = (float) reciboService
						.countSearchReciboAlmacen(searchString) / sizeNo;
			} else if (StringUtils.isNotBlank(searchString)
					&& enumEstatus != null) {
				uiModel.addAttribute("recibos", reciboService
						.searchReciboAlmacen(searchString, enumEstatus,
								firstResult, sizeNo));
				nrOfPages = (float) reciboService.countSearchReciboAlmacen(
						searchString, enumEstatus) / sizeNo;
			} else if (StringUtils.isBlank(searchString) && enumEstatus != null) {
				uiModel.addAttribute("recibos", reciboService
						.searchReciboAlmacen(enumEstatus, firstResult, sizeNo));
				nrOfPages = (float) reciboService
						.countSearchReciboAlmacen(enumEstatus) / sizeNo;
			} else {
				uiModel.addAttribute("recibos", reciboService
						.findReciboAlmacenEntries(firstResult, sizeNo));
				nrOfPages = (float) reciboService.countAllReciboAlmacens()
						/ sizeNo;
			}
			uiModel.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			uiModel.addAttribute("recibos",
					reciboService.findAllReciboAlmacens());
		}
		addDateTimeFormatPatterns(uiModel);
		return "admin/recibo/list";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("reciboalmacen",
				reciboService.findReciboAlmacen(id));
		uiModel.addAttribute("itemId", id);
		return "admin/recibo/show";
	}

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(
				"reciboAlmacen_created_date_format",
				DateTimeFormat.patternForStyle("MM",
						LocaleContextHolder.getLocale()));
		uiModel.addAttribute("tipoEstatus",
				Arrays.asList(TipoEstatusRecibo.values()));
	}

	void populateEditForm(Model uiModel, ReciboAlmacen reciboAlmacen) {
		uiModel.addAttribute("reciboAlmacen", reciboAlmacen);
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("tipocargoes", Arrays.asList(TipoCargo.values()));
		uiModel.addAttribute("tipoestatusreciboes",
				Arrays.asList(TipoEstatusRecibo.values()));
		uiModel.addAttribute("tipotransportacions",
				Arrays.asList(TipoTransportacion.values()));
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
