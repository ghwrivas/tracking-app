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

import ve.com.tracking.model.CategoriaDetalle;
import ve.com.tracking.services.CategoriaDetalleService;

@RequestMapping("/admin/categorias")
@Controller
@RooWebScaffold(path = "admin/categorias", formBackingObject = CategoriaDetalle.class)
public class CategoriaDetalleController {

	@Autowired
	CategoriaDetalleService categoriaDetalleService;

	void populateEditForm(Model uiModel, CategoriaDetalle categoriaDetalle) {
		uiModel.addAttribute("categoriaDetalle", categoriaDetalle);
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

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid CategoriaDetalle categoriaDetalle,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, categoriaDetalle);
			return "admin/categorias/create";
		}
		uiModel.asMap().clear();
		categoriaDetalleService.save(categoriaDetalle);
		return "redirect:/admin/categorias/"
				+ encodeUrlPathSegment(categoriaDetalle.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new CategoriaDetalle());
		return "admin/categorias/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		uiModel.addAttribute("categoriaDetalle",
				categoriaDetalleService.find(id));
		uiModel.addAttribute("itemId", id);
		return "admin/categorias/show";
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
			uiModel.addAttribute("categorias",
					categoriaDetalleService.findEntries(firstResult, sizeNo));
			float nrOfPages = (float) categoriaDetalleService.countAll()
					/ sizeNo;
			uiModel.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			uiModel.addAttribute("categorias",
					categoriaDetalleService.findAll());
		}
		return "admin/categorias/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid CategoriaDetalle categoriaDetalle,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, categoriaDetalle);
			return "admin/categorias/update";
		}
		uiModel.asMap().clear();
		categoriaDetalleService.update(categoriaDetalle);
		return "redirect:/admin/categorias/"
				+ encodeUrlPathSegment(categoriaDetalle.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel, categoriaDetalleService.find(id));
		return "admin/categorias/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Long id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel, final RedirectAttributes redirectAttributes) {
		CategoriaDetalle categoriaDetalle = categoriaDetalleService.find(id);
		try {
			categoriaDetalleService.delete(categoriaDetalle);
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
		return "redirect:/admin/categorias";
	}
}
