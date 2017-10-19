/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class FieldMatchValidator
		implements
			ConstraintValidator<FieldMatch, Object> {

	@Autowired
	private MessageSource messageSource;

	private String firstFieldName;
	private String secondFieldName;
	private String message;

	@Override
	public void initialize(final FieldMatch constraintAnnotation) {
		firstFieldName = constraintAnnotation.first();
		secondFieldName = constraintAnnotation.second();
		message = constraintAnnotation.message();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext cvc) {
		boolean toReturn = false;

		try {
			final Object firstObj = BeanUtils
					.getProperty(value, firstFieldName);
			final Object secondObj = BeanUtils.getProperty(value,
					secondFieldName);
			toReturn = firstObj == null && secondObj == null
					|| firstObj != null && firstObj.equals(secondObj);
		} catch (final Exception e) {
			System.out.println(e.toString());
		}
		// If the validation failed
		if (!toReturn) {
			String i18Message = messageSource.getMessage(message, null,
					LocaleContextHolder.getLocale());
			cvc.disableDefaultConstraintViolation();
			cvc.buildConstraintViolationWithTemplate(i18Message)
					.addNode(firstFieldName).addConstraintViolation();
		}
		return toReturn;
	}
}
