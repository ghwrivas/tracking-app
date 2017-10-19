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
public class CiRifPassportValidator implements ConstraintValidator<CiRifPassport, Object> {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private UsersRepository usersRepository;

	private String message;

	@Override
	public void initialize(final CiRifPassport constraintAnnotation) {
		message = constraintAnnotation.message();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext cvc) {
		boolean valid = true;
		try {
			final String ciRifPassport = (String) value;
			if (StringUtils.isBlank(ciRifPassport)) {
				message = "CI, RIF, Passpor inválido";
				valid = false;
			}
			if (!ciRifPassport.matches(RegexConstantes.PATTERN_CEDULA_RIF_COMPLETO)) {
				message = RegexConstantes.MESSAGE_ERROR_CI_RIF_COMPLETO;
				valid = false;
			}
			Users user = usersRepository.findByCiRifPassport(ciRifPassport);
			if (user != null) {
				message = "Ya existe un usuario con el CI, RIF, Passport: " + ciRifPassport;
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
