/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.forms;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import ve.com.tracking.constraints.FieldMatch;

/**
 * Formulario utilizado para cambiar la contraseña del usuario en sesión.
 * 
 * @author Williams Rivas
 * 
 *         Created 01/05/2014 11:11:32
 */
@FieldMatch(message = "field_password_not_matching", first = "newPassword", second = "confirmNewPassword")
public class PasswordChangeForm {

	@NotNull
	private Long id;

	@NotNull
	@Length(min = 5, max = 10)
	private String currentPassword;

	@NotNull
	@Length(min = 5, max = 10)
	private String newPassword;

	@NotNull
	@Length(min = 5, max = 10)
	private String confirmNewPassword;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

}
