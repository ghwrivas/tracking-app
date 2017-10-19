package ve.com.tracking.web;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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

import ve.com.tracking.model.Users;
import ve.com.tracking.repository.AuthoritiesRepository;
import ve.com.tracking.services.UsersService;

@RequestMapping("/admin/users")
@Controller
@RooWebScaffold(path = "admin/users", formBackingObject = Users.class)
public class UsersController {

	@Autowired
	UsersService usersService;
	
	@Autowired
	AuthoritiesRepository authoritiesRepository;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid Users users, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, users);
			return "admin/users/create";
		}
		Users user = usersService.findUsers(users.getUsername(),
				users.getCiRifPassport(), users.getEmail());
		if (user != null) {
			uiModel.addAttribute(
					"messageError",
					"Error al intentar crear la cuenta: Ya existe un usuario con al menos un username, CI, RIF, Passport o email introducidos en el formulario");
			populateEditForm(uiModel, users);
			return "admin/users/create";
		}
		uiModel.asMap().clear();
		usersService.saveUsers(users);
		return "redirect:/admin/users/"
				+ encodeUrlPathSegment(users.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new Users());
		return "admin/users/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		Users user = usersService.findUsers(id);
		uiModel.addAttribute("users", user);
		uiModel.addAttribute("itemId", id);
		return "admin/users/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(
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
				uiModel.addAttribute("userses", usersService.searchUsers(
						searchString, firstResult, sizeNo));
				nrOfPages = (float) usersService.countSearchUsers(searchString)
						/ sizeNo;
			} else {
				uiModel.addAttribute("userses",
						usersService.findUsersEntries(firstResult, sizeNo));
				nrOfPages = (float) usersService.countAllUserses() / sizeNo;
			}
			uiModel.addAttribute("maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0)
							? nrOfPages + 1
							: nrOfPages));
		} else {
			uiModel.addAttribute("userses", usersService.findAllUserses());
		}
		addDateTimeFormatPatterns(uiModel);
		return "admin/users/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid Users users, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, users);
			return "admin/users/update";
		}
		uiModel.asMap().clear();
		try {
			usersService.updateUsers(users);
		} catch (DataAccessException e) {
			uiModel.addAttribute("messageError", e.getRootCause().getMessage());
			populateEditForm(uiModel, users);
			return "admin/users/update";
		}
		return "redirect:/admin/users/"
				+ encodeUrlPathSegment(users.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel, usersService.findUsers(id));
		return "admin/users/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Long id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel, final RedirectAttributes redirectAttributes) {
		Users users = usersService.findUsers(id);
		try {
			usersService.deleteUsers(users);
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
		return "redirect:/admin/users";
	}

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(
				"users_created_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
		uiModel.addAttribute(
				"users_updated_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
	}

	void populateEditForm(Model uiModel, Users users) {
		if (users.getId() != null) { // update do password null
			users.setPassword(null);
		}
		uiModel.addAttribute("users", users);
		addDateTimeFormatPatterns(uiModel);
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
