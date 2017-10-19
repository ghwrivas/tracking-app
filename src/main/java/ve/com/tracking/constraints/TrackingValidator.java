/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import ve.com.tracking.core.RegexConstantes;

public class TrackingValidator implements ConstraintValidator<Tracking, Object> {

	@Autowired
	private MessageSource messageSource;

	private String fieldHasTracking;
	private String fieldTracking;
	private String message;

	@Override
	public void initialize(final Tracking constraintAnnotation) {
		fieldHasTracking = constraintAnnotation.fieldHasTracking();
		fieldTracking = constraintAnnotation.fieldTracking();
		message = constraintAnnotation.message();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext cvc) {
		boolean valid = true;
		try {
			final String hasTracking = BeanUtils.getProperty(value,
					fieldHasTracking);
			final String tracking = BeanUtils.getProperty(value, fieldTracking);

			if (hasTracking.equals("s")) { // si tiene tracking
				if (tracking == null || tracking.equals("")) {
					message = "El tracking es requerido";
					valid = false;
				} else if (tracking.length() < 1) {
					message = "La longitud del tracking debe ser mayor que 1 y menor o igual que 100";
					valid = false;
				} else if (tracking.length() > 100) {
					message = "La longitud del tracking debe ser mayor que 1 y menor o igual que 100";
					valid = false;
				} else if (!tracking
						.matches(RegexConstantes.CUALQUIER_CARACTER_MENOS_ESPACIOS_EN_BLANCO)) {
					message = "El código del tracking no puede contener espacios en blanco";
					valid = false;
				}
			} else { // no tiene tracking

			}
		} catch (final Exception e) {
			System.out.println(e.toString());
		}
		// If the validation failed
		if (!valid) {
			cvc.disableDefaultConstraintViolation();
			cvc.buildConstraintViolationWithTemplate(message)
					.addNode(fieldTracking).addConstraintViolation();
		}
		return valid;
	}
}
