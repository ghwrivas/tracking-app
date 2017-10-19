/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.forms;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/**
 * Formulario en modo edición para actualizar la cuenta de un usuario
 * 
 * @author Williams Rivas Created 07/03/2014 11:25:40
 * 
 */
public class AccountUpdateForm implements Serializable {

	private static final long serialVersionUID = 1L;

	public AccountUpdateForm() {
		super();
	}

	@NotNull
	private Long id;

	@Length(max = 45)
	@NotNull
	private String firstName;

	@Length(max = 45)
	@NotNull
	private String lastName;

	@Length(min = 1, max = 200)
	@NotNull
	private String direccion;

	@Length(max = 45)
	@NotNull
	private String telefono;

	@Length(max = 45)
	private String bbPin;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
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
