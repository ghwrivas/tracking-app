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

import ve.com.tracking.model.Authorities;
import ve.com.tracking.repository.AuthoritiesRepository;
import ve.com.tracking.services.AuthorityService;
import ve.com.tracking.services.UsersService;

@RequestMapping("/admin/authorities")
@Controller
@RooWebScaffold(path = "admin/authorities", formBackingObject = Authorities.class)
public class AuthoritiesController {

	@Autowired
	AuthoritiesRepository authoritiesRepository;

	@Autowired
	AuthorityService authorityService;

	@Autowired
	UsersService usersService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid Authorities authorities,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, authorities);
			return "admin/authorities/create";
		}
		uiModel.asMap().clear();
		authoritiesRepository.save(authorities);
		return "redirect:/admin/authorities/"
				+ encodeUrlPathSegment(authorities.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new Authorities());
		return "admin/authorities/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		uiModel.addAttribute("authorities", authoritiesRepository.findOne(id));
		uiModel.addAttribute("itemId", id);
		return "admin/authorities/show";
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
					"authoritieses",
					authoritiesRepository.findAll(
							new org.springframework.data.domain.PageRequest(
									firstResult / sizeNo, sizeNo)).getContent());
			float nrOfPages = (float) authoritiesRepository.count() / sizeNo;
			uiModel.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			uiModel.addAttribute("authoritieses",
					authoritiesRepository.findAll());
		}
		return "admin/authorities/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid Authorities authorities,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, authorities);
			return "admin/authorities/update";
		}
		uiModel.asMap().clear();
		authoritiesRepository.save(authorities);
		return "redirect:/admin/authorities/"
				+ encodeUrlPathSegment(authorities.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel, authoritiesRepository.findOne(id));
		return "admin/authorities/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Long id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel, final RedirectAttributes redirectAttributes) {
		Authorities authorities = authoritiesRepository.findOne(id);
		try {
			authoritiesRepository.delete(authorities);
		} catch (DataAccessException e) {
			redirectAttributes.addFlashAttribute(
					"messageError",
					"Error al intentar eliminar el registro: "
							+ e.getMessage());
		}		
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/admin/authorities";
	}

	void populateEditForm(Model uiModel, Authorities authorities) {
		uiModel.addAttribute("authorities", authorities);
		uiModel.addAttribute("authorityses",
				authorityService.findAuthoritysNotLike("ROLE_USER"));
		uiModel.addAttribute("userses", usersService.findAllUserses());
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
