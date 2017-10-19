/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.core;

import java.io.Serializable;

/**
 * Define los tipos de estatus que pueda tener un paquete en un momento dado.
 * 
 * @author Williams Rivas
 * 
 *         Created 01/04/2014 13:44:27
 */
public enum TipoEstatusPaquete implements Serializable {
	NOTIFICADO("Notificado"), CONFIRMADO("Confirmado"), NOTIFICADO_CONFIRMADO(
			"Notificado y confirmado"), PROCESADO("Procesado");

	private String nombre;

	public String getNombre() {
		return this.nombre;
	}

	private TipoEstatusPaquete(String nombre) {
		this.nombre = nombre;
	}
}
