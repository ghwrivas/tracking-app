package ve.com.tracking.services;
import java.util.List;

import ve.com.tracking.core.TipoEstatusGuia;
import ve.com.tracking.exceptions.ResourceNotFoundException;
import ve.com.tracking.forms.GuiaForm;
import ve.com.tracking.forms.PaquetesSelectedForm;
import ve.com.tracking.model.CodigoGuia;
import ve.com.tracking.model.DetalleGuia;
import ve.com.tracking.model.DetalleItem;
import ve.com.tracking.model.Guia;
import ve.com.tracking.model.Guias;
import ve.com.tracking.model.ReciboAlmacen;

public interface GuiaService {

	public abstract long countAllGuias();

	public abstract void deleteGuia(Guia guia);

	public abstract Guia findGuia(Long id);
	
	public abstract List<Guia> findAllGuias();

	public abstract List<Guia> findGuiaEntries(int firstResult, int maxResults);

	public abstract void saveGuia(Guia guia);

	public abstract Guia updateGuia(Guia guia);

	public GuiaForm saveGuiaBusiness(GuiaForm guia, ReciboAlmacen reciboModel);
	
	public void processPaquetes(PaquetesSelectedForm paquetesSelected);

	public GuiaForm updateGuiaBusiness(GuiaForm guia);

	public List<DetalleItem> findAllDetalleItems(Guia guia)
			throws ResourceNotFoundException;

	public abstract List<Guias> searchGuias(String searchString, int firstResult,
			int sizeNo);

	public abstract long countSearchGuias(String searchString);

	public abstract List<Guias> findGuiasEntries(int firstResult, int sizeNo);

	public abstract List<Guias> findAllGuiasView();

	public abstract long countAllGuiasView();
	
	public abstract List<DetalleGuia> findAllDetalleGuias(Guia guia);

	public abstract void entregarGuiaCliente(CodigoGuia guia);
	
	public abstract void deleteItem(Long itemId);
	
	public abstract void deleteGuia(Long guiaId);

	public abstract List<Guias> searchGuias(String searchString,
			TipoEstatusGuia enumEstatus, int firstResult, int sizeNo);

	public abstract long countSearchGuias(String searchString,
			TipoEstatusGuia enumEstatus);

	public abstract List<Guias> searchGuias(TipoEstatusGuia enumEstatus,
			int firstResult, int sizeNo);

	public abstract long countSearchGuias(TipoEstatusGuia enumEstatus);
	

}
