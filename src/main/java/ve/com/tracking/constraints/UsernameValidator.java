/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import ve.com.tracking.core.RegexConstantes;
import ve.com.tracking.model.Users;
import ve.com.tracking.repository.UsersRepository;

/**
 * Validador de username. No se permiten espacios en blanco, solo letras y/o
 * números y que no exista el username en la base de datos.
 * 
 * @author Williams Rivas
 * 
 *         Created 04/05/2014 14:22:38
 */
public class UsernameValidator implements ConstraintValidator<Username, Object> {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private UsersRepository usersRepository;

	private String message;

	@Override
	public void initialize(final Username constraintAnnotation) {
		message = constraintAnnotation.message();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext cvc) {
		boolean valid = true;
		try {
			final String username = (String) value;
			if (StringUtils.isBlank(username)) {
				message = "Username inválido";
				valid = false;
			}
			if (!username.matches(RegexConstantes.LETRAS_NUMEROS_SOLAMENTE)) {
				message = "Username inválido. Solo letras y/o números";
				valid = false;
			}
			Users user = usersRepository.findByUsername(username);
			if (user != null) {
				message = "Ya existe un usuario con el username: " + username;
				valid = false;
			}
		} catch (final Exception e) {
			System.out.println(e.toString());
		}
		// If the validation failed
		if (!valid) {
			cvc.disableDefaultConstraintViolation();
			cvc.buildConstraintViolationWithTemplate(message)
					.addConstraintViolation();
		}
		return valid;
	}
}
