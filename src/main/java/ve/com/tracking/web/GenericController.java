/**
 * COPYRIGHT (C) 2014 Alcaldía de Iribarren. Todos los derechos reservados.
 */
package ve.com.tracking.web;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ve.com.tracking.model.Users;
import ve.com.tracking.services.UsersService;

/**
 * Controlador central para le mapeo de peticiones comunes tales como captura de
 * errores y excepciones.
 * 
 * @author Williams Rivas Created 13/08/2014 08:29:42
 * 
 */
@Controller
public class GenericController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private UsersService usuariosManager;

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accesssDenied(Principal user, ModelMap model) {
		String message = messageSource.getMessage("error_access_denied_message_unauthenticated",
				null, LocaleContextHolder.getLocale());
		if (user != null) {
			Users usuario = usuariosManager.getPrincipal();
			if(usuario != null){
				Object[] args = new Object[1];
				String nombreCompleto = "";
				nombreCompleto = usuario.getFirstName() + " " + usuario.getLastName();
				args[0] = nombreCompleto;
				message = messageSource.getMessage("error_access_denied_message_authenticated", args,
						LocaleContextHolder.getLocale());				
			}
		}
		model.addAttribute("msg", message);
		return "accessDenied";

	}
}
