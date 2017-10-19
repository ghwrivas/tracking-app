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

import ve.com.tracking.model.EmpresaEnvio;
import ve.com.tracking.repository.EmpresaEnvioRepository;

@RequestMapping("/admin/empresaenvio")
@Controller
@RooWebScaffold(path = "admin/empresaenvio", formBackingObject = EmpresaEnvio.class)
public class EmpresaEnvioController {

	@Autowired
	EmpresaEnvioRepository empresaEnvioRepository;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid EmpresaEnvio empresaEnvio,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, empresaEnvio);
			return "admin/empresaenvio/create";
		}
		uiModel.asMap().clear();
		empresaEnvioRepository.save(empresaEnvio);
		return "redirect:/admin/empresaenvio/"
				+ encodeUrlPathSegment(empresaEnvio.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new EmpresaEnvio());
		return "admin/empresaenvio/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		uiModel.addAttribute("empresaenvio", empresaEnvioRepository.findOne(id));
		uiModel.addAttribute("itemId", id);
		return "admin/empresaenvio/show";
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
			uiModel.addAttribute(
					"empresaenvios",
					empresaEnvioRepository.findAll(
							new org.springframework.data.domain.PageRequest(
									firstResult / sizeNo, sizeNo)).getContent());
			float nrOfPages = (float) empresaEnvioRepository.count() / sizeNo;
			uiModel.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			uiModel.addAttribute("empresaenvios",
					empresaEnvioRepository.findAll());
		}
		return "admin/empresaenvio/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid EmpresaEnvio empresaEnvio,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, empresaEnvio);
			return "admin/empresaenvio/update";
		}
		uiModel.asMap().clear();
		empresaEnvioRepository.save(empresaEnvio);
		return "redirect:/admin/empresaenvio/"
				+ encodeUrlPathSegment(empresaEnvio.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel, empresaEnvioRepository.findOne(id));
		return "admin/empresaenvio/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Long id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel, RedirectAttributes redirectAttributes) {
		EmpresaEnvio empresaEnvio = empresaEnvioRepository.findOne(id);
		try {
			empresaEnvioRepository.delete(empresaEnvio);
		} catch (DataAccessException e) {
			redirectAttributes.addFlashAttribute(
					"messageError",
					"Error al intentar eliminar el registro: "
							+ e.getMessage());
		}
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/admin/empresaenvio";
	}

	void populateEditForm(Model uiModel, EmpresaEnvio empresaEnvio) {
		uiModel.addAttribute("empresaEnvio", empresaEnvio);
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
