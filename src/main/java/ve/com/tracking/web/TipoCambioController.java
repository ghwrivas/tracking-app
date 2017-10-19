package ve.com.tracking.web;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import ve.com.tracking.model.Countries;
import ve.com.tracking.model.TipoCambio;
import ve.com.tracking.repository.CountriesRepository;
import ve.com.tracking.services.TipoCambioService;

@RequestMapping("/admin/tipocambio")
@Controller
@RooWebScaffold(path = "admin/tipocambio", formBackingObject = TipoCambio.class)
public class TipoCambioController {

	@Autowired
	CountriesRepository countriesRepository;

	void populateEditForm(Model uiModel, TipoCambio tipoCambio) {
		uiModel.addAttribute("countrieses", countriesRepository.findAll());
		uiModel.addAttribute("tipoCambio", tipoCambio);
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

	@Autowired
	TipoCambioService tipoCambioService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid TipoCambio newTipoCambio,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, newTipoCambio);
			return "admin/tipocambio/create";
		}
		Countries country = newTipoCambio.getCountry();
		TipoCambio tipoCambio = tipoCambioService
				.findTipoCambioByCountry(country.getIsoAlpha2());
		String id = null;
		if (tipoCambio != null) {
			tipoCambio.setCountry(country);
			tipoCambio.setValorDolar(newTipoCambio.getValorDolar());
			tipoCambioService.updateTipoCambio(tipoCambio);
			id = tipoCambio.getId().toString();
		} else {
			tipoCambioService.saveTipoCambio(newTipoCambio);
			id = newTipoCambio.getId().toString();
		}
		uiModel.asMap().clear();
		return "redirect:/admin/tipocambio/"
				+ encodeUrlPathSegment(id, httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new TipoCambio());
		return "admin/tipocambio/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		uiModel.addAttribute("tipocambio", tipoCambioService.findTipoCambio(id));
		uiModel.addAttribute("itemId", id);
		return "admin/tipocambio/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1)
					* sizeNo;
			uiModel.addAttribute("tipocambios", tipoCambioService
					.findTipoCambioEntries(firstResult, sizeNo));
			float nrOfPages = (float) tipoCambioService.countAllTipoCambios()
					/ sizeNo;
			uiModel.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			uiModel.addAttribute("tipocambios",
					tipoCambioService.findAllTipoCambios());
		}
		return "admin/tipocambio/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid TipoCambio tipoCambio,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, tipoCambio);
			return "admin/tipocambio/update";
		}
		Countries country = tipoCambio.getCountry();
		TipoCambio existTipoCambio = tipoCambioService
				.findTipoCambioByCountry(country.getIsoAlpha2());
		if (existTipoCambio != null) {
			tipoCambio.setId(existTipoCambio.getId());
		}
		uiModel.asMap().clear();
		tipoCambioService.updateTipoCambio(tipoCambio);
		return "redirect:/admin/tipocambio/"
				+ encodeUrlPathSegment(tipoCambio.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel, tipoCambioService.findTipoCambio(id));
		return "admin/tipocambio/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Long id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel, final RedirectAttributes redirectAttributes) {
		TipoCambio tipoCambio = tipoCambioService.findTipoCambio(id);
		try {
			tipoCambioService.deleteTipoCambio(tipoCambio);
		} catch (DataAccessException e) {
			redirectAttributes
					.addFlashAttribute(
							"messageError",
							"Error al intentar eliminar el registro: "
									+ e.getMessage());
		}
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/admin/tipocambio";
	}
}
