/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.security;

/**
 * Servicio para codificar password con algoritmo SHA-256
 * 
 * @author Williams Rivas Created 07/03/2014 09:21:50
 * 
 */
public interface PasswordEncoderSha256 {

	/**
	 * Codifica el password pasado como parametro mediante el algoritmo SHA-256
	 * 
	 * @param password
	 * @return
	 */
	public String encodeSha256Password(String password)
			throws IllegalArgumentException;

}
