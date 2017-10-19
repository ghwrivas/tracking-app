/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.forms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Formulario utilizado para manejar el listado de Guías seleccionadas y
 * posteriormente procesar su estatus de si son confirmadas en el almacen
 * destino.
 * 
 * @author Williams Rivas
 * 
 *         Created 01/05/2014 10:43:46
 */
public class ConfirmGuiasAlmacenForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Long> guiasIds = new ArrayList<Long>();

	public ConfirmGuiasAlmacenForm() {

	}

	public List<Long> getGuiasIds() {
		return guiasIds;
	}

	public void setGuiasIds(List<Long> guiasIds) {
		this.guiasIds = guiasIds;
	}

}
