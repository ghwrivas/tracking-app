/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.security;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ve.com.tracking.services.UsersService;
import ve.com.tracking.web.CaptchaController;

/**
 * Custom authentication component para poder authenticar contra username,
 * ci_rif_passport o email.
 * 
 * @author Williams Rivas Created 07/03/2014 09:03:00
 * 
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private Logger log = Logger.getLogger(CustomAuthenticationProvider.class);

	@Autowired
	private UsersService usersService;

	@Autowired
	private PasswordEncoderSha256 passwordEncoder;

	@Autowired
	private MessageSource messageSource;

	private static final String CREDENCIALES_INCORRECTAS = "security_bad_credentials";

	private static final String CUENTA_IS_NOT_ENABLED = "security_account_not_actived";

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes()).getRequest();
		if (!CaptchaController.validateCaptcha(request)) {
			throw new BadCredentialsException("field_invalid_captcha");
		}
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		try {
			User user = usersService.findUserForAuthentication(username);
			if (user == null) {
				throw new BadCredentialsException(CREDENCIALES_INCORRECTAS);
			}
			if (!user.isEnabled()) {
				throw new BadCredentialsException(CUENTA_IS_NOT_ENABLED);
			}
			password = passwordEncoder.encodeSha256Password(password);
			if (!password.equals(user.getPassword())) {
				throw new BadCredentialsException(CREDENCIALES_INCORRECTAS);
			}
			Collection<? extends GrantedAuthority> authorities = user
					.getAuthorities();
			return new UsernamePasswordAuthenticationToken(user, password,
					authorities);
		} catch (CannotCreateTransactionException e) {
			log.error("Error de acceso a datos en autenticacion: "
					+ e.getLocalizedMessage());
			throw new BadCredentialsException("error_dataaccessfailure_title");
		}
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}
