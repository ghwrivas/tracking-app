/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.forms;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * Formulario utilizado para buscar la guía a ser agregada a un contenedor.
 * 
 * @author Williams Rivas
 * 
 *         Created 01/05/2014 10:45:42
 */
public class CodigoGuiaContenedorForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	private String codigo;

	public CodigoGuiaContenedorForm() {

	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

}
