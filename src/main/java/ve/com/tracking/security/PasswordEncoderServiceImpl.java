/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Williams Rivas
 * 
 *         Created 26/03/2014 15:11:22
 */
@Service
public class PasswordEncoderServiceImpl implements PasswordEncoderSha256 {

	@Autowired
	private MessageDigestPasswordEncoder passwordEncoder;

	@Override
	public String encodeSha256Password(String password)
			throws IllegalArgumentException {
		if (password == null) {
			throw new IllegalArgumentException("Password nulo");
		}
		if (password.equals("")) {
			throw new IllegalArgumentException("Password inválido");
		}
		return passwordEncoder.encodePassword(password, null);
	}

}
