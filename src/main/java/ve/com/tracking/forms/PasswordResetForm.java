/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.forms;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

/**
 * Formulario utilizado para resetear la contraseña.
 * 
 * @author Williams Rivas
 * 
 *         Created 08/03/2014 18:38:04
 */
public class PasswordResetForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@Email
	@Length(max = 100)
	@NotNull
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
