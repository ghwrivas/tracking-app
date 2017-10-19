/**
 * COPYRIGHT (C) 2014 Alcaldía de Iribarren. Todos los derechos reservados.
 */
package ve.com.tracking.uidgen;

/**
 * 
 * @author Williams Rivas Created 08/12/2014 11:03:57
 * 
 */
public interface UIDSequencerManager extends UIDSequencer {

	/**
	 * Retorna el siguiente codigo autogenerado para el siguimiento de cada una
	 * de las piezas que conforma el producto.
	 * 
	 * El codigo esta conformado por el numero del sistema, numero del recibo y
	 * numero del producto
	 * 
	 * @param reciboAlmacenId
	 * @param guiaId
	 * @return
	 */
	public abstract String getNextCodigoGuia(Long reciboAlmacenId, Long guiaId);

	/**
	 * Retorna el siguiente codigo autogenerado para el contenedor
	 * 
	 * @return
	 */
	public abstract String getNextCodigoContenedor();

}
