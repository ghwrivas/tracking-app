/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.core;

/**
 * Define los tipos de mensajes informativos utilizados para informar al
 * usuario.
 * 
 * @author Williams Rivas
 * 
 *         Created 01/04/2014 13:44:36
 */
public enum TipoFlashMessage {
	ERROR("messageError"), WARNING("messageWarning"), INFO("messageInfo"), SUCCESS(
			"messageSuccess");

	private String nombre;

	public String getNombre() {
		return this.nombre;
	}

	private TipoFlashMessage(String nombre) {
		this.nombre = nombre;
	}
}
