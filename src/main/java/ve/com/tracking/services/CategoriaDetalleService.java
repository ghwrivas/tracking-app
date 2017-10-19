package ve.com.tracking.services;

import java.util.List;

import ve.com.tracking.model.CategoriaDetalle;

public interface CategoriaDetalleService {
	public abstract long countAll();

	public abstract void delete(CategoriaDetalle categoriaDetalle);

	public abstract CategoriaDetalle find(Long id);

	public abstract List<CategoriaDetalle> findAll();

	public abstract List<CategoriaDetalle> findEntries(int firstResult,
			int maxResults);

	public abstract void save(CategoriaDetalle categoriaDetalle);

	public abstract CategoriaDetalle update(CategoriaDetalle categoriaDetalle);

}
