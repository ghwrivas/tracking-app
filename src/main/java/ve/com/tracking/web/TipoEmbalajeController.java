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

import ve.com.tracking.model.TipoEmbalaje;
import ve.com.tracking.services.TipoEmbalajeService;

@RequestMapping("/admin/tipoembalaje")
@Controller
@RooWebScaffold(path = "admin/tipoembalaje", formBackingObject = TipoEmbalaje.class)
public class TipoEmbalajeController {

	void populateEditForm(Model uiModel, TipoEmbalaje tipoEmbalaje) {
		uiModel.addAttribute("tipoEmbalaje", tipoEmbalaje);
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
	TipoEmbalajeService tipoEmbalajeService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid TipoEmbalaje tipoEmbalaje,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, tipoEmbalaje);
			return "admin/tipoembalaje/create";
		}
		uiModel.asMap().clear();
		tipoEmbalajeService.saveTipoEmbalaje(tipoEmbalaje);
		return "redirect:/admin/tipoembalaje/"
				+ encodeUrlPathSegment(tipoEmbalaje.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new TipoEmbalaje());
		return "admin/tipoembalaje/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		uiModel.addAttribute("tipoembalaje",
				tipoEmbalajeService.findTipoEmbalaje(id));
		uiModel.addAttribute("itemId", id);
		return "admin/tipoembalaje/show";
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
			uiModel.addAttribute("tipoembalajes", tipoEmbalajeService
					.findTipoEmbalajeEntries(firstResult, sizeNo));
			float nrOfPages = (float) tipoEmbalajeService
					.countAllTipoEmbalajes() / sizeNo;
			uiModel.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			uiModel.addAttribute("tipoembalajes",
					tipoEmbalajeService.findAllTipoEmbalajes());
		}
		return "admin/tipoembalaje/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid TipoEmbalaje tipoEmbalaje,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, tipoEmbalaje);
			return "admin/tipoembalaje/update";
		}
		uiModel.asMap().clear();
		tipoEmbalajeService.updateTipoEmbalaje(tipoEmbalaje);
		return "redirect:/admin/tipoembalaje/"
				+ encodeUrlPathSegment(tipoEmbalaje.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel, tipoEmbalajeService.findTipoEmbalaje(id));
		return "admin/tipoembalaje/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Long id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel, final RedirectAttributes redirectAttributes) {
		TipoEmbalaje tipoEmbalaje = tipoEmbalajeService.findTipoEmbalaje(id);
		try {
			tipoEmbalajeService.deleteTipoEmbalaje(tipoEmbalaje);
		} catch (DataAccessException e) {
			redirectAttributes.addFlashAttribute("messageError",
					"Error al intentar eliminar el registro: "
							+ e.getMessage());
		}
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/admin/tipoembalaje";
	}
}
