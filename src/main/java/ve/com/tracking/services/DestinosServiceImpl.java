package ve.com.tracking.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ve.com.tracking.model.Destinos;
import ve.com.tracking.repository.DestinosRepository;

@Service
@Transactional
public class DestinosServiceImpl implements DestinosService {

	@Autowired
	DestinosRepository destinosRepository;
	
	public long countAllDestinoses() {
        return destinosRepository.count();
    }

	public void deleteDestinos(Destinos destinos) {
        destinosRepository.delete(destinos);
    }

	public Destinos findDestinos(Long id) {
		return destinosRepository.findOne(id);
    }

	public List<Destinos> findAllDestinoses() {
        return destinosRepository.findAll();
    }

	public List<Destinos> findDestinosEntries(int firstResult, int maxResults) {
		return destinosRepository.findAll(
				new org.springframework.data.domain.PageRequest(firstResult
						/ maxResults, maxResults)).getContent();
    }

	public void saveDestinos(Destinos destinos) {
		destinosRepository.save(destinos);
    }

	public Destinos updateDestinos(Destinos destinos) {
        return destinosRepository.save(destinos);
    }
}
