/**
 * COPYRIGHT (C) 2014 Alcaldía de Iribarren. Todos los derechos reservados.
 */
package ve.com.tracking.uidgen;

/**
 * UIDSequencer define un metodo que retorna el siguiente id basado en una clave
 * dada.
 * 
 * @author Williams Rivas Created 17/06/2014 10:56:03
 * 
 */
public interface UIDSequencer {
	Integer getNext(String key);
}
