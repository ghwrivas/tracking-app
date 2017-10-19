/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.forms;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import ve.com.tracking.constraints.Tracking;
import ve.com.tracking.model.EmpresaEnvio;
import ve.com.tracking.model.Paquete;

/**
 * Formulario utlizado para la notificación de un paquete por parte del cliente.
 * 
 * @author Williams Rivas Created 12/03/2014 14:28:23
 * 
 */
@Tracking(fieldHasTracking = "hasTracking", fieldTracking = "tracking", errorMessage = "Error de validación")
public class PackageNotifyForm {

	private Long id;

	@NotNull
	private String hasTracking = "s";

	private String tracking;

	@NotNull
	private EmpresaEnvio empresaEnvioId;

	private Paquete paqueteId;

	@Length(min = 1, max = 255)
	@NotNull
	private String descripcion;

	@NotNull
	private boolean asegurar;

	public boolean isAsegurar() {
		return asegurar;
	}

	public void setAsegurar(boolean asegurar) {
		this.asegurar = asegurar;
	}

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

	public String getHasTracking() {
		return hasTracking;
	}

	public void setHasTracking(String hasTracking) {
		this.hasTracking = hasTracking;
	}

}
