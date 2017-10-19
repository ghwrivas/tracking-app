package ve.com.tracking.web;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;
import ve.com.tracking.model.GuiaReciboAlmacen;

@RequestMapping("/admin/guiasrecibo")
@Controller
@RooWebScaffold(path = "admin/guiasrecibo", formBackingObject = GuiaReciboAlmacen.class)
public class GuiaReciboAlmacenController {

	void populateEditForm(Model uiModel, GuiaReciboAlmacen guiaReciboAlmacen) {
        uiModel.addAttribute("guiaReciboAlmacen", guiaReciboAlmacen);
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
