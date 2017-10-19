/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.core;

import java.io.Serializable;

/**
 * Define los tipos de estatus que pueda tener un contendor en momento dado.
 * 
 * @author Williams Rivas
 * 
 *         Created 01/04/2014 13:44:27
 */
public enum TipoEstatusContenedor implements Serializable {
	CREADO("Creado"), EN_TRANSITO_PAIS_DESTINO("En transito a país destino"), CONFIRMADO_PAIS_DESTINO(
			"Confirmado en país destino"), CONFIRMADO_ALMACEN_DESTINO(
			"Confirmado en almacén destino");

	private String nombre;

	public String getNombre() {
		return this.nombre;
	}

	private TipoEstatusContenedor(String nombre) {
		this.nombre = nombre;
	}
}
