package ve.com.tracking.services;

import java.util.List;

import ve.com.tracking.core.TipoEstatusPaquete;
import ve.com.tracking.exceptions.ClientException;
import ve.com.tracking.forms.PackageNotifyForm;
import ve.com.tracking.forms.PackageRecepcionarForm;
import ve.com.tracking.model.DetalleGuiasPaquete;
import ve.com.tracking.model.DetalleNotificacion;
import ve.com.tracking.model.DetalleRecepcion;
import ve.com.tracking.model.Paquete;
import ve.com.tracking.model.PaquetesNotificadoConfirmado;
import ve.com.tracking.model.Users;

/**
 * 
 * @author Williams Rivas
 * 
 *         Created 01/04/2014 13:46:21
 */
public interface PaqueteService {

	public abstract long countAllPaquetes();

	public abstract void deletePaquete(Paquete paquete);

	public abstract Paquete findPaquete(Long id);

	public abstract List<Paquete> findAllPaquetes();

	public abstract List<Paquete> findPaqueteEntries(int firstResult,
			int maxResults);

	public abstract void savePaquete(Paquete paquete);

	public abstract Paquete updatePaquete(Paquete paquete)
			throws ClientException;

	/**
	 * Realiza la notificacion de un paquete que el cliente haya enviado a la
	 * empresa
	 * 
	 * @param notifyForm
	 * @return
	 */
	public Paquete notifyPaquete(PackageNotifyForm notifyForm)
			throws ClientException;

	/**
	 * Realiza la recepcion de un paquete enviado por el cliente a la empresa.
	 * 
	 * @param recepcionarForm
	 * @return
	 */
	public Paquete recepcionarPaquete(PackageRecepcionarForm recepcionarForm)
			throws ClientException;

	/**
	 * Realiza la actualizacion de un paquete notificado.
	 * 
	 * @param notifyForm
	 * @return
	 */
	public Paquete updateNotificationPaquete(PackageNotifyForm notifyForm)
			throws ClientException;

	/**
	 * Realiza la actualizacion de un paquete recepcionado.
	 * 
	 * @param notifyForm
	 * @return
	 */
	public Paquete updateRecepcionPaquete(PackageRecepcionarForm recepcionarForm)
			throws ClientException;

	/**
	 * Obtiene todos los detalles de paquetes notificados por el usuario pasado
	 * como parametro. Soporta tambien paginacion.
	 * 
	 * @param user
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public List<DetalleNotificacion> findAllDetalleNotificationByUsers(
			Users user, int firstResult, int maxResults);

	/**
	 * Obtiene todos los detalles de paquetes notificados por el usuario pasado
	 * como parametro.
	 * 
	 * @param user
	 * @return
	 */
	public List<DetalleNotificacion> findAllDetalleNotificationByUsers(
			Users user);

	/**
	 * Obtiene la cantidad de paquetes notificados por el usuario pasado como
	 * parametro
	 * 
	 * @param user
	 * @return
	 */
	public Long countAllDetalleNotificationsByUser(Users user);

	/**
	 * Obtiene todos los detalles de paquetes recepcionados
	 * 
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public List<DetalleRecepcion> findAllDetalleRecepcionEntries(
			int firstResult, int maxResults);

	/**
	 * Obtiene todos los detalles de paquetes recepcionados
	 * 
	 * @return
	 */
	public List<DetalleRecepcion> findAllDetalleRecepcions();

	/**
	 * Obtiene la cantidad de paquetes recepcionados
	 * 
	 * @return
	 */
	public Long countAllDetalleRecepcions();

	public List<Paquete> searchPaquetes(String searchString, int firstResult,
			int maxResults);

	public List<Paquete> searchPaquetes(String searchString);

	public Long countSearchPaquetes(String searchString);

	public List<PaquetesNotificadoConfirmado> findPaquetesNotificadoConfirmado();

	public List<PaquetesNotificadoConfirmado> findEntries(int firstResult,
			int maxResults);

	public List<PaquetesNotificadoConfirmado> searchPaquetesNotificadoConfirmado(
			String searchString, int firstResult, int maxResults);

	public List<PaquetesNotificadoConfirmado> searchPaquetesNotificadoConfirmado(
			String searchString);

	public Long countSearchPaquetesNotificadoConfirmado(String searchString);

	public List<Paquete> findPaquetesByCliente(Users cliente,
			TipoEstatusPaquete estatus);

	public List<Paquete> findPaquetesByCliente(Users cliente);

	public List<Paquete> findPaquetesByCliente(Users cliente, int firstResult,
			int maxResults);

	public Long countPaquetesByCliente(Users cliente);

	public Paquete findPaqueteByTracking(String tracking);

	public List<DetalleGuiasPaquete> findAllDetallesGuiasPaquete(Paquete paquete);

	public List<Paquete> searchPaquetesByCliente(String searchString,
			Users cliente, int firstResult, int maxResults);

	public List<Paquete> searchPaquetesByCliente(String searchString,
			Users cliente);

	public Long countSearchPaquetesByCliente(String searchString, Users cliente);

	public Long countPaquetesNotificadoConfirmado();

	public List<Paquete> searchPaquetes(String searchString,
			TipoEstatusPaquete estatus, int firstResult, int maxResults);

	public List<Paquete> searchPaquetes(String searchString,
			TipoEstatusPaquete estatus);

	public Long countSearchPaquetes(String searchString,
			TipoEstatusPaquete estatus);

	public List<Paquete> searchPaquetes(TipoEstatusPaquete estatus,
			int firstResult, int maxResults);

	public List<Paquete> searchPaquetes(TipoEstatusPaquete estatus);

	public Long countSearchPaquetes(TipoEstatusPaquete estatus);

	public abstract List<Paquete> searchPaquetes(boolean trackingGenerado,
			String searchString, TipoEstatusPaquete enumEstatus,
			int firstResult, int sizeNo);

	public abstract Long countSearchPaquetes(boolean trackingGenerado,
			String searchString, TipoEstatusPaquete enumEstatus);

	public abstract List<Paquete> searchPaquetes(boolean trackingGenerado,
			String searchString, int firstResult, int sizeNo);

	public abstract Long countSearchPaquetes(boolean trackingGenerado,
			String searchString);

	public abstract List<Paquete> searchPaquetes(boolean trackingGenerado,
			TipoEstatusPaquete enumEstatus, int firstResult, int sizeNo);

	public abstract Long countSearchPaquetes(boolean trackingGenerado,
			TipoEstatusPaquete enumEstatus);

	public abstract List<Paquete> searchPaquetes(boolean trackingGenerado,
			int firstResult, int sizeNo);

	public abstract Long countSearchPaquetes(boolean trackingGenerado);
}
