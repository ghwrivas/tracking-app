package ve.com.tracking.services;
import java.util.List;
import ve.com.tracking.model.TipoEmbalaje;

public interface TipoEmbalajeService {

	public abstract long countAllTipoEmbalajes();


	public abstract void deleteTipoEmbalaje(TipoEmbalaje tipoEmbalaje);


	public abstract TipoEmbalaje findTipoEmbalaje(Long id);


	public abstract List<TipoEmbalaje> findAllTipoEmbalajes();


	public abstract List<TipoEmbalaje> findTipoEmbalajeEntries(int firstResult, int maxResults);


	public abstract void saveTipoEmbalaje(TipoEmbalaje tipoEmbalaje);


	public abstract TipoEmbalaje updateTipoEmbalaje(TipoEmbalaje tipoEmbalaje);

}
