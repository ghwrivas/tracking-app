package ve.com.tracking.services;
import java.util.List;
import ve.com.tracking.model.Destinos;

public interface DestinosService {

	public abstract long countAllDestinoses();


	public abstract void deleteDestinos(Destinos destinos);


	public abstract Destinos findDestinos(Long id);


	public abstract List<Destinos> findAllDestinoses();


	public abstract List<Destinos> findDestinosEntries(int firstResult, int maxResults);


	public abstract void saveDestinos(Destinos destinos);


	public abstract Destinos updateDestinos(Destinos destinos);

}
