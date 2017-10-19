/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.core;

import java.io.Serializable;

/**
 * Define los tipos de cargo utilizados para las guías generadas
 * 
 * @author Williams Rivas
 * 
 *         Created 01/04/2014 13:44:27
 */
public enum TipoCargo implements Serializable {
	PESO("Peso"), PESO_VOLUMETRICO("Peso Volumétrico"), VOLUMEN("Volumen"), MANUAL(
			"Manual");

	private String nombre;

	public String getNombre() {
		return this.nombre;
	}

	private TipoCargo(String nombre) {
		this.nombre = nombre;
	}
}
