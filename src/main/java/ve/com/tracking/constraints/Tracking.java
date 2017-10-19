/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Validador utilizado solo en el formulario de notificacion por parte del
 * cliente. Se valida si el paquete tiene tracking o no tiene. Si el paquete no
 * tiene tracking, el campo tracking puede pasar nulo o vacio pero si posee se
 * realizan las validaciones respectivas de que no sea nulo, no este vacio, no
 * tengan caracteres de espacios en blanco, longitud.
 * 
 * @author Williams Rivas
 * 
 *         Created 29/04/2014 10:44:59
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TrackingValidator.class)
@Documented
public @interface Tracking {
	String message() default "{constraints.fieldmatch}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	/**
	 * @return el campo que indicara si el paquete tiene tracking o no tiene al
	 *         momento de notificacion. Los valores posibles: "s" o "n"
	 */
	String fieldHasTracking();

	/**
	 * @return el nombre del campo que almacena el tracking del paquete
	 */
	String fieldTracking();

	/**
	 * @return el mensaje de error
	 */
	String errorMessage();

}
