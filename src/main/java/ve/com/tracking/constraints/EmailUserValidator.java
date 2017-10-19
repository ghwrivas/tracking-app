/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import ve.com.tracking.model.Users;
import ve.com.tracking.repository.UsersRepository;

/**
 * Validador de username. Se valida que un usuario con el email asociado no
 * exista en la base de datos
 * 
 * @author Williams Rivas
 * 
 *         Created 04/05/2014 14:22:38
 */
public class EmailUserValidator
		implements
			ConstraintValidator<EmailUser, Object> {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private UsersRepository usersRepository;

	private String message;

	@Override
	public void initialize(final EmailUser constraintAnnotation) {
		message = constraintAnnotation.message();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext cvc) {
		boolean valid = true;
		try {
			final String email = (String) value;
			if (StringUtils.isBlank(email)) {
				message = "Email inválido";
				valid = false;
			}
			Users user = usersRepository.findByEmail(email);
			if (user != null) {
				message = "Ya existe un usuario con el email: " + email;
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
