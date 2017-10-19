/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.forms;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import ve.com.tracking.constraints.CiRifPassport;
import ve.com.tracking.constraints.EmailUser;
import ve.com.tracking.constraints.FieldMatch;
import ve.com.tracking.constraints.Username;

/**
 * Formulario en modo creación para crear una cuenta de usuario
 * 
 * @author Williams Rivas Created 07/03/2014 11:25:40
 * 
 */
@FieldMatch.List(value = {
		@FieldMatch(message = "field_password_not_matching", first = "password", second = "confirmPassword"),
		@FieldMatch(message = "field_email_not_matching", first = "email", second = "confirmEmail")})
public class AccountCreateForm implements Serializable {

	private static final long serialVersionUID = 1L;

	public AccountCreateForm() {
		super();
	}

	@Username
	@Length(min = 5, max = 10)
	@NotNull
	private String username;

	@CiRifPassport
	@NotNull
	private String ciRifPassport;

	@Length(max = 45)
	@NotNull
	private String firstName;

	@Length(max = 45)
	@NotNull
	private String lastName;

	@NotNull
	@Length(min = 5, max = 10)
	private String password;

	@NotNull
	@Length(min = 5, max = 10)
	private String confirmPassword;

	@Length(min = 1, max = 200)
	@NotNull
	private String direccion;

	@EmailUser
	@Email
	@Length(max = 100)
	@NotNull
	private String email;

	@Length(max = 100)
	@NotNull
	private String confirmEmail;

	@Length(max = 45)
	@NotNull
	private String telefono;

	@Length(max = 45)
	private String bbPin;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCiRifPassport() {
		return ciRifPassport;
	}

	public void setCiRifPassport(String ciRifPassport) {
		this.ciRifPassport = ciRifPassport;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getConfirmEmail() {
		return confirmEmail;
	}

	public void setConfirmEmail(String confirmEmail) {
		this.confirmEmail = confirmEmail;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getBbPin() {
		return bbPin;
	}

	public void setBbPin(String bbPin) {
		this.bbPin = bbPin;
	}

}
