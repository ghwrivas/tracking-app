/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.core;

import java.io.Serializable;

/**
 * Define los tipo de transportación en los cuales se pueda enviar una guía.
 * 
 * @author Williams Rivas
 * 
 *         Created 01/04/2014 13:44:45
 */
public enum TipoTransportacion implements Serializable {
	AEREO("Aéreo"), MARITIMO("Marítimo"), TERRESTRE("Terrestre");

	private String nombre;

	public String getNombre() {
		return this.nombre;
	}

	private TipoTransportacion(String nombre) {
		this.nombre = nombre;
	}
}
