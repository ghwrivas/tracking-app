package ve.com.tracking.repository;

import java.util.List;

import ve.com.tracking.core.TipoEstatusRecibo;
import ve.com.tracking.model.ReciboAlmacen;
import ve.com.tracking.model.Users;

public interface ReciboAlmacenRepositoryCustom {
	public List<ReciboAlmacen> searchReciboAlmacen(String searchString,
			int firstResult, int maxResults);

	public List<ReciboAlmacen> searchReciboAlmacen(String searchString);

	public Long countSearchReciboAlmacen(String searchString);

	public List<ReciboAlmacen> searchReciboAlmacenByCliente(
			String numeroRecibo, Users cliente, int firstResult, int maxResults);

	public List<ReciboAlmacen> searchReciboAlmacenByCliente(
			String numeroRecibo, Users cliente);

	public Long countSearchReciboAlmacenByCliente(String numeroRecibo,
			Users cliente);

	public List<ReciboAlmacen> searchReciboAlmacenByCliente(Users cliente,
			int firstResult, int maxResults);

	public List<ReciboAlmacen> searchReciboAlmacenByCliente(Users cliente);

	public Long countSearchReciboAlmacenByCliente(Users cliente);

	public List<ReciboAlmacen> searchReciboAlmacen(String searchString,
			TipoEstatusRecibo estatus, int firstResult, int maxResults);

	public List<ReciboAlmacen> searchReciboAlmacen(String searchString,
			TipoEstatusRecibo estatus);

	public Long countSearchReciboAlmacen(String searchString,
			TipoEstatusRecibo estatus);

	public List<ReciboAlmacen> searchReciboAlmacen(TipoEstatusRecibo estatus,
			int firstResult, int maxResults);

	public List<ReciboAlmacen> searchReciboAlmacen(TipoEstatusRecibo estatus);

	public Long countSearchReciboAlmacen(TipoEstatusRecibo estatus);
}
