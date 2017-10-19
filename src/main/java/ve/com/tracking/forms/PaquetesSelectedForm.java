/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.forms;

import java.io.Serializable;
import java.util.List;

/**
 * Formulario utilizado para procesar paquetes que ya no tengan artículos para
 * generar otras guías.
 * 
 * @author Williams Rivas
 * 
 *         Created 01/05/2014 11:10:33
 */
public class PaquetesSelectedForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Long> paquetesId;

	public PaquetesSelectedForm() {

	}

	public List<Long> getPaquetesId() {
		return paquetesId;
	}

	public void setPaquetesId(List<Long> paquetesId) {
		this.paquetesId = paquetesId;
	}
}
