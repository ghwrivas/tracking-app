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

import ve.com.tracking.model.Destinos;
import ve.com.tracking.repository.CountriesRepository;
import ve.com.tracking.services.DestinosService;

@RequestMapping("/admin/destinos")
@Controller
@RooWebScaffold(path = "admin/destinos", formBackingObject = Destinos.class)
public class DestinosController {

	@Autowired
	CountriesRepository countriesRepository;

	void populateEditForm(Model uiModel, Destinos destinos) {
		uiModel.addAttribute("countrieses", countriesRepository.findAll());
		uiModel.addAttribute("destinos", destinos);
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
	DestinosService destinosService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid Destinos destinos, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, destinos);
			return "admin/destinos/create";
		}
		uiModel.asMap().clear();
		destinosService.saveDestinos(destinos);
		return "redirect:/admin/destinos/"
				+ encodeUrlPathSegment(destinos.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new Destinos());
		return "admin/destinos/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		uiModel.addAttribute("destinos", destinosService.findDestinos(id));
		uiModel.addAttribute("itemId", id);
		return "admin/destinos/show";
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
			uiModel.addAttribute("destinoses",
					destinosService.findDestinosEntries(firstResult, sizeNo));
			float nrOfPages = (float) destinosService.countAllDestinoses()
					/ sizeNo;
			uiModel.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			uiModel.addAttribute("destinoses",
					destinosService.findAllDestinoses());
		}
		return "admin/destinos/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid Destinos destinos, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, destinos);
			return "admin/destinos/update";
		}
		uiModel.asMap().clear();
		destinosService.updateDestinos(destinos);
		return "redirect:/admin/destinos/"
				+ encodeUrlPathSegment(destinos.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel, destinosService.findDestinos(id));
		return "admin/destinos/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Long id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel, final RedirectAttributes redirectAttributes) {
		Destinos destinos = destinosService.findDestinos(id);
		try {
			destinosService.deleteDestinos(destinos);
		} catch (DataAccessException e) {
			redirectAttributes.addFlashAttribute(
					"messageError",
					"Error al intentar eliminar el registro: "
							+ e.getMessage());
		}

		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/admin/destinos";
	}
}
