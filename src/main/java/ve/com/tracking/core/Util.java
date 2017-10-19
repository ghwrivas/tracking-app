/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.core;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Métodos útiles a nivel global de la aplicación.
 * 
 * @author Williams Rivas
 * 
 *         Created 01/05/2014 10:34:19
 */
public class Util {

	public static final String APP_NAME = "tracking-app";
	
	/**
	 * Retorna la base url de la aplicacion. Solo debe usarse este metodo en el
	 * ambito del request (peticiones) que se hagan al servidor.
	 * 
	 * @return la base url
	 * @throws IllegalStateException
	 *             si ningun request attribute es atado al actual proceso
	 */
	public static String getBaseUrl() throws IllegalStateException {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		String baseUrl = String.format("%s://%s:%d/%s/", request.getScheme(),
				request.getServerName(), request.getServerPort(), APP_NAME);
		return baseUrl;
	}
}
