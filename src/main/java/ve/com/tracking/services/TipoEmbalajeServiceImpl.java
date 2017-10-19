package ve.com.tracking.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ve.com.tracking.model.TipoEmbalaje;
import ve.com.tracking.repository.TipoEmbalajeRepository;

@Service
@Transactional
public class TipoEmbalajeServiceImpl implements TipoEmbalajeService {

	@Autowired
	TipoEmbalajeRepository tipoEmbalajeRepository;
	
	public long countAllTipoEmbalajes() {
		return tipoEmbalajeRepository.count();
    }

	public void deleteTipoEmbalaje(TipoEmbalaje tipoEmbalaje) {
		tipoEmbalajeRepository.delete(tipoEmbalaje);
    }

	public TipoEmbalaje findTipoEmbalaje(Long id) {
		return tipoEmbalajeRepository.findOne(id);
    }

	public List<TipoEmbalaje> findAllTipoEmbalajes() {
        return tipoEmbalajeRepository.findAll();
    }

	public List<TipoEmbalaje> findTipoEmbalajeEntries(int firstResult, int maxResults) {
		return tipoEmbalajeRepository.findAll(
				new org.springframework.data.domain.PageRequest(firstResult
						/ maxResults, maxResults)).getContent();
    }

	public void saveTipoEmbalaje(TipoEmbalaje tipoEmbalaje) {
		tipoEmbalajeRepository.save(tipoEmbalaje);
    }

	public TipoEmbalaje updateTipoEmbalaje(TipoEmbalaje tipoEmbalaje) {
        return tipoEmbalajeRepository.save(tipoEmbalaje);
    }
}
