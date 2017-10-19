/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.services;

import java.util.List;

import ve.com.tracking.core.TipoEstatusContenedor;
import ve.com.tracking.core.TipoEstatusGuia;
import ve.com.tracking.core.TipoEstatusPaquete;
import ve.com.tracking.exceptions.EstatusServiceException;
import ve.com.tracking.model.CodigoGuia;
import ve.com.tracking.model.Contenedor;
import ve.com.tracking.model.EstatusGuia;
import ve.com.tracking.model.EstatusPaquete;
import ve.com.tracking.model.Paquete;

/**
 * 
 * @author Williams Rivas
 * 
 *         Created 26/03/2014 15:15:53
 */
public interface EstatusService {
	/**
	 * Asigna un nuevo estatus al paquete segun la logica de negocio. La logica
	 * para la asignacion de los estatus es la siguiente: Notificado o
	 * Confirmado => Notificado_Confirmado => Procesado
	 * 
	 * @see ve.com.tracking.core.EstatusPaquete
	 * @param paquete
	 * @return el paquete pasado como parametro con su nuevo estatus
	 */
	void setNextEstatusPaquete(Paquete paquete) throws EstatusServiceException;

	/**
	 * Asigna un nuevo estatus al contenedor segun la logica de negocio. La
	 * logica para la asignacion de los estatus es la siguiente:
	 * 
	 * 
	 * @param contenedor
	 * @return
	 */
	void setNextEstatusContenedor(Contenedor contenedor)
			throws EstatusServiceException;

	/**
	 * Asigna un nuevo estatus a la guia segun la logica de negocio. La logica
	 * de asignacion de los estatus a la guia es la siguiente:
	 * 
	 * @param guia
	 * @return
	 */
	void setNextEstatusGuia(CodigoGuia guia) throws EstatusServiceException;

	void registerLogEstatusPaquete(Paquete paquete)
			throws EstatusServiceException;

	void registerLogEstatusContenedor(Contenedor contenedor)
			throws EstatusServiceException;

	void registerLogEstatusGuia(CodigoGuia guia) throws EstatusServiceException;

	TipoEstatusContenedor getNextEstatusContenedor(Contenedor contenedor)
			throws EstatusServiceException;

	TipoEstatusGuia getNextEstatusGuia(CodigoGuia guia)
			throws EstatusServiceException;

	TipoEstatusPaquete getNextEstatusPaquete(Paquete paquete)
			throws EstatusServiceException;

	boolean hasNextEstatusPaquete(TipoEstatusPaquete tipoEstatus)
			throws EstatusServiceException;

	boolean hasNextEstatusContenedor(TipoEstatusContenedor tipoEstatus)
			throws EstatusServiceException;

	boolean hasNextEstatusGuia(TipoEstatusGuia tipoEstatus)
			throws EstatusServiceException;

	TipoEstatusGuia getPreviousEstatusGuia(CodigoGuia guia);

	TipoEstatusContenedor getPreviousEstatusContenedor(Contenedor contenedor);
	
	List<EstatusPaquete> findAllEstatusPaquete(Paquete paquete);
	
	List<EstatusGuia> findAllEstatusGuia(CodigoGuia guia);	
	
	List<String> getAllEstatusPaquete();
	
	List<String> getAllEstatusGuia();
	
	List<String> getAllEstatusContenedor();

}
