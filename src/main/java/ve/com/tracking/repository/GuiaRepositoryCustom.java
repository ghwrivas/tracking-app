package ve.com.tracking.repository;

import java.util.List;

import ve.com.tracking.core.TipoEstatusGuia;
import ve.com.tracking.model.DetalleItem;
import ve.com.tracking.model.Guia;
import ve.com.tracking.model.Guias;

public interface GuiaRepositoryCustom {
	List<DetalleItem> findDetalleItems(Guia guia);

	public List<Guias> searchGuiasView(String searchString, int firstResult,
			int maxResults);

	public List<Guias> searchGuiasView(String searchString);

	public Long countSearchGuiasView(String searchString);

	public List<Guias> findAllGuiasView();

	public Long countAllGuiasView();
	
	public List<Guias> findGuiasEntries(int firstResult, int maxResults);
	
	public List<Guias> searchGuiasView(String searchString, TipoEstatusGuia estatus, int firstResult,
			int maxResults);

	public List<Guias> searchGuiasView(String searchString, TipoEstatusGuia estatus);

	public Long countSearchGuiasView(String searchString,TipoEstatusGuia estatus);
	
	public List<Guias> searchGuiasView(TipoEstatusGuia estatus, int firstResult,
			int maxResults);

	public List<Guias> searchGuiasView(TipoEstatusGuia estatus);

	public Long countSearchGuiasView(TipoEstatusGuia estatus);
}
