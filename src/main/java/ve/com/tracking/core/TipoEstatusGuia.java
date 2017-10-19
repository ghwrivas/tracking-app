/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.core;

import java.io.Serializable;

/**
 * Define los tipos de estatus que puede tener una guía en un momento dado.
 * 
 * @author Williams Rivas
 * 
 *         Created 01/04/2014 13:44:27
 */
public enum TipoEstatusGuia implements Serializable {
	CREADO("Creado"), AGREGADA_RECIBO_ALMACEN("Agregada al recibo de almacén"), AGREGADO_AL_CONTENEDOR(
			"Agregado al contenedor"), EN_TRANSITO_PAIS_DESTINO(
			"En transito a país destino"), CONFIRMADO_ALMACEN_DESTINO(
			"Confirmado en almacén destino"), ENTREGADO_CLIENTE(
			"Entregado al cliente");

	private String nombre;

	public String getNombre() {
		return this.nombre;
	}

	private TipoEstatusGuia(String nombre) {
		this.nombre = nombre;
	}
}
