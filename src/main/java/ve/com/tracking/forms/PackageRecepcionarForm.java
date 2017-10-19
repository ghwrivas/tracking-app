/**

 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import ve.com.tracking.core.RegexConstantes;
import ve.com.tracking.model.EmpresaEnvio;
import ve.com.tracking.model.Paquete;

/**
 * Formulario utilizado para recepcionar un paquete por parte de la empresa.
 * 
 * @author Williams Rivas
 * 
 *         Created 20/03/2014 21:30:19
 */
public class PackageRecepcionarForm {

	private Long id;

	@Pattern(regexp = RegexConstantes.CUALQUIER_CARACTER_MENOS_ESPACIOS_EN_BLANCO, message = "El código del tracking no puede contener espacios en blanco")
	@Length(min = 1, max = 100)
	@NotNull
	@NotBlank
	@NotEmpty
	private String tracking;

	@NotNull
	private EmpresaEnvio empresaEnvioId;

	private Paquete paqueteId;

	private String descripcion;

	public String getTracking() {
		return tracking;
	}

	public void setTracking(String tracking) {
		this.tracking = tracking;
	}

	public EmpresaEnvio getEmpresaEnvioId() {
		return empresaEnvioId;
	}

	public void setEmpresaEnvioId(EmpresaEnvio empresaEnvioId) {
		this.empresaEnvioId = empresaEnvioId;
	}

	public Paquete getPaqueteId() {
		return paqueteId;
	}

	public void setPaqueteId(Paquete paqueteId) {
		this.paqueteId = paqueteId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
