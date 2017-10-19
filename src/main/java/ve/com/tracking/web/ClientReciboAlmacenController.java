package ve.com.tracking.web;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import ve.com.tracking.model.Users;
import ve.com.tracking.services.ReciboService;
import ve.com.tracking.services.UsersService;

@RequestMapping("/client/recibo")
@Controller
public class ClientReciboAlmacenController {

	@Autowired
	private ReciboService reciboService;
	
	@Autowired
	private UsersService usersService;

	@RequestMapping(produces = "text/html", value = "/list")
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "numeroRecibo", required = false) String numeroRecibo,
			Model uiModel) {
		Users cliente = usersService.getPrincipal();
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1)
					* sizeNo;
			float nrOfPages;
			if (numeroRecibo != null && !numeroRecibo.equals("")) {
				uiModel.addAttribute("recibos", reciboService
						.searchReciboAlmacenByCliente(numeroRecibo, cliente, firstResult, sizeNo));
				nrOfPages = (float) reciboService
						.countSearchReciboAlmacenByCliente(numeroRecibo, cliente) / sizeNo;
			} else {
				uiModel.addAttribute("recibos", reciboService.searchReciboAlmacenByCliente(cliente, firstResult, sizeNo));
				nrOfPages = (float) reciboService.countSearchReciboAlmacenByCliente(cliente)
						/ sizeNo;
			}
			uiModel.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			uiModel.addAttribute("recibos",
					reciboService.findRecibosAlmacenByCliente(cliente));
		}
		return "client/recibo/list";
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
