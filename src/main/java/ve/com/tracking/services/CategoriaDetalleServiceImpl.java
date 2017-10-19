package ve.com.tracking.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ve.com.tracking.model.CategoriaDetalle;
import ve.com.tracking.repository.CategoriaDetalleRepository;

@Service
@Transactional
public class CategoriaDetalleServiceImpl implements CategoriaDetalleService {

	@Autowired
	CategoriaDetalleRepository categoriaDetalleRepository;

	@Override
	public long countAll() {
		return categoriaDetalleRepository.count();
	}

	@Override
	public void delete(CategoriaDetalle categoriaDetalle) {
		categoriaDetalleRepository.delete(categoriaDetalle);
	}

	@Override
	public CategoriaDetalle find(Long id) {
		return categoriaDetalleRepository.findOne(id);
	}

	@Override
	public List<CategoriaDetalle> findAll() {
		return categoriaDetalleRepository.findAll();
	}

	@Override
	public List<CategoriaDetalle> findEntries(int firstResult, int maxResults) {
		return categoriaDetalleRepository.findAll(
				new org.springframework.data.domain.PageRequest(firstResult
						/ maxResults, maxResults)).getContent();

	}

	@Override
	public void save(CategoriaDetalle categoriaDetalle) {
		categoriaDetalleRepository.save(categoriaDetalle);
	}

	@Override
	public CategoriaDetalle update(CategoriaDetalle categoriaDetalle) {
		return categoriaDetalleRepository.save(categoriaDetalle);
	}

}
