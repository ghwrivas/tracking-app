package ve.com.tracking.web;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;
import ve.com.tracking.model.Empresa;
import ve.com.tracking.services.EmpresaService;

@RequestMapping("/admin/empresa")
@Controller
@RooWebScaffold(path = "admin/empresa", formBackingObject = Empresa.class)
public class EmpresaController {

	@Autowired
    EmpresaService empresaService;

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Short id, Model uiModel) {
        uiModel.addAttribute("empresa", empresaService.findEmpresa(id));
        uiModel.addAttribute("itemId", id);
        return "admin/empresa/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        /*if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("empresas", empresaService.findEmpresaEntries(firstResult, sizeNo));
            float nrOfPages = (float) empresaService.countAllEmpresas() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("empresas", empresaService.findAllEmpresas());
        }*/
        return "redirect:/admin/empresa/1";
        //return "admin/empresa/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Empresa empresa, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, empresa);
            return "admin/empresa/update";
        }
        uiModel.asMap().clear();
        empresaService.updateEmpresa(empresa);
        return "redirect:/admin/empresa/" + encodeUrlPathSegment(empresa.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Short id, Model uiModel) {
        populateEditForm(uiModel, empresaService.findEmpresa(id));
        return "admin/empresa/update";
    }

	void populateEditForm(Model uiModel, Empresa empresa) {
        uiModel.addAttribute("empresa", empresa);
    }

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }	
}
