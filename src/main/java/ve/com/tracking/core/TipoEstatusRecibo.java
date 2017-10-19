/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.core;

import java.io.Serializable;

/**
 * Define los tipos de estatus que pueda tener un recibo de almacén.
 * 
 * @author Williams Rivas
 *
 * Created 08/06/2014 08:40:05
 */
public enum TipoEstatusRecibo implements Serializable {
	GENERADO("Generado"), CONFIRMADO("Confirmado");

	private String nombre;

	public String getNombre() {
		return this.nombre;
	}

	private TipoEstatusRecibo(String nombre) {
		this.nombre = nombre;
	}
}
