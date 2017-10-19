package ve.com.tracking.services;
import java.util.List;

import ve.com.tracking.core.TipoEstatusRecibo;
import ve.com.tracking.forms.ReciboAlmacenForm;
import ve.com.tracking.model.Guia;
import ve.com.tracking.model.ReciboAlmacen;
import ve.com.tracking.model.Users;

public interface ReciboService {

	public abstract long countAllReciboAlmacens();


	public abstract void deleteReciboAlmacen(ReciboAlmacen reciboAlmacen);


	public abstract ReciboAlmacen findReciboAlmacen(Long id);


	public abstract List<ReciboAlmacen> findAllReciboAlmacens();


	public abstract List<ReciboAlmacen> findReciboAlmacenEntries(int firstResult, int maxResults);


	public abstract void saveReciboAlmacen(ReciboAlmacen reciboAlmacen);


	public abstract ReciboAlmacen updateReciboAlmacen(ReciboAlmacen reciboAlmacen);
	
	public List<Guia> findAllGuiasRecibo(ReciboAlmacen reciboAlmacen);
	
	public ReciboAlmacenForm findReciboAlmacenForm(Long id);
	
	
	public abstract List<ReciboAlmacen> searchReciboAlmacen(String searchString, int firstResult,
			int sizeNo);

	public abstract long countSearchReciboAlmacen(String searchString);	
	
	public abstract List<ReciboAlmacen> findRecibosAlmacenByCliente(Users cliente);
	
	public abstract List<ReciboAlmacen> searchReciboAlmacenByCliente(String numeroRecibo, Users cliente, int firstResult,
			int sizeNo);

	public abstract long countSearchReciboAlmacenByCliente(String numeroRecibo, Users cliente);
	
	public abstract List<ReciboAlmacen> searchReciboAlmacenByCliente(Users cliente, int firstResult,
			int sizeNo);

	public abstract long countSearchReciboAlmacenByCliente(Users cliente);


	public abstract List<ReciboAlmacen> searchReciboAlmacen(String searchString,
			TipoEstatusRecibo enumEstatus, int firstResult, int sizeNo);


	public abstract long countSearchReciboAlmacen(String searchString,
			TipoEstatusRecibo enumEstatus);


	public abstract List<ReciboAlmacen> searchReciboAlmacen(TipoEstatusRecibo enumEstatus,
			int firstResult, int sizeNo);


	public abstract long countSearchReciboAlmacen(TipoEstatusRecibo enumEstatus);

}
