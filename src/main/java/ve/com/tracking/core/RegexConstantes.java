/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.core;

/**
 * Regex strings globales utilizadas a nivel de la aplicación y como JSR 303
 * para validaciónes a nivel de formularios
 * 
 * @author Williams Rivas
 * 
 *         Created 01/05/2014 10:22:48
 */
public class RegexConstantes {

	public final static String LETRAS_NUMEROS_SOLAMENTE = "^[a-zA-Z0-9]+$";
	public final static String CUALQUIER_CARACTER_MENOS_ESPACIOS_EN_BLANCO = "\\S+";

	public static final String PATTERN_CEDULA_RIF_COMPLETO = "^[VEPJG]\\d{3,10}$";

	public static final String MESSAGE_ERROR_CI_RIF_COMPLETO = "La Cédula, Pasaporte y/o RIF escrito no es válido; identificadores P,J,V,G en mayúsculas. Ej: P15885874, V15885874, E25875458, J158956892, G0158956892.";
}
