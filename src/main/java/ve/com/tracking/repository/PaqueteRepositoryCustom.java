package ve.com.tracking.repository;

import java.util.List;

import ve.com.tracking.core.TipoEstatusPaquete;
import ve.com.tracking.model.DetalleGuiasPaquete;
import ve.com.tracking.model.Paquete;
import ve.com.tracking.model.PaquetesNotificadoConfirmado;
import ve.com.tracking.model.Users;

public interface PaqueteRepositoryCustom {
	public List<Paquete> searchPaquetes(String searchString, int firstResult,
			int maxResults);

	public List<Paquete> searchPaquetes(String searchString);

	public Long countSearchPaquetes(String searchString);

	public List<Paquete> searchPaquetesByCliente(String searchString,
			Users cliente, int firstResult, int maxResults);

	public List<Paquete> searchPaquetesByCliente(String searchString,
			Users cliente);

	public Long countSearchPaquetesByCliente(String searchString, Users cliente);

	public List<PaquetesNotificadoConfirmado> findAllPaquetesNotificadosConfirmados();

	public List<PaquetesNotificadoConfirmado> searchPaquetesNotificadosConfirmados(
			String searchString, int firstResult, int maxResults);

	public List<PaquetesNotificadoConfirmado> searchPaquetesNotificadosConfirmados(
			String searchString);

	public Long countSearchPaquetesNotificadosConfirmados(String searchString);

	public List<PaquetesNotificadoConfirmado> findEntries(int firstResult,
			int maxResults);

	public List<PaquetesNotificadoConfirmado> findPaquetesNotificadoConfirmadoByUser(
			Long userId);

	/**
	 * Encuentra paquetes notificados por el cliente y el estatus pasados como
	 * parametro
	 * 
	 * @param user
	 *            cliente que realizo la notificacion
	 * @param estatus
	 * @return lista de paquetes
	 */
	public List<Paquete> findPaquetesByCliente(Users user,
			TipoEstatusPaquete estatus);

	public List<Paquete> findPaquetesByCliente(Users user);

	public List<Paquete> findPaquetesByCliente(Users user, int firstResult,
			int maxResults);

	public List<DetalleGuiasPaquete> findDetalleGuiasByPaquete(Long paqueteId);

	public Long countPaquetesByCliente(Users cliente);

	public Long countPaquetesNotificadosConfirmado();

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

	public List<Paquete> searchPaquetes(boolean trackingGenerado,
			String searchString, TipoEstatusPaquete enumEstatus,
			int firstResult, int maxResults);

	public List<Paquete> searchPaquetes(boolean trackingGenerado,
			String searchString, TipoEstatusPaquete enumEstatus);

	public Long countSearchPaquetes(boolean trackingGenerado,
			String searchString, TipoEstatusPaquete enumEstatus);

	public List<Paquete> searchPaquetes(boolean trackingGenerado,
			String searchString, int firstResult, int maxResults);

	public List<Paquete> searchPaquetes(boolean trackingGenerado,
			String searchString);

	public Long countSearchPaquetes(boolean trackingGenerado,
			String searchString);

	public List<Paquete> searchPaquetes(boolean trackingGenerado,
			TipoEstatusPaquete enumEstatus, int firstResult, int maxResults);

	public List<Paquete> searchPaquetes(boolean trackingGenerado,
			TipoEstatusPaquete enumEstatus);

	public Long countSearchPaquetes(boolean trackingGenerado,
			TipoEstatusPaquete enumEstatus);

	public List<Paquete> searchPaquetes(boolean trackingGenerado,
			int firstResult, int maxResults);

	public List<Paquete> searchPaquetes(boolean trackingGenerado);

	public Long countSearchPaquetes(boolean trackingGenerado);
}
