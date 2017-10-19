package ve.com.tracking.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ve.com.tracking.model.Empresa;
import ve.com.tracking.repository.EmpresaRepository;

@Service
@Transactional
public class EmpresaServiceImpl implements EmpresaService {

	@Autowired
	EmpresaRepository empresaRepository;
	
	public long countAllEmpresas() {
		return empresaRepository.count();
    }

	public void deleteEmpresa(Empresa empresa) {
		empresaRepository.delete(empresa);
    }

	public Empresa findEmpresa(Short id) {
        return empresaRepository.findOne(id);
    }

	public List<Empresa> findAllEmpresas() {
        return empresaRepository.findAll();
    }

	public List<Empresa> findEmpresaEntries(int firstResult, int maxResults) {
		return empresaRepository.findAll(
				new org.springframework.data.domain.PageRequest(firstResult
						/ maxResults, maxResults)).getContent();
    }

	public void saveEmpresa(Empresa empresa) {
		empresaRepository.save(empresa);
    }

	public Empresa updateEmpresa(Empresa empresa) {
        return empresaRepository.save(empresa);
    }
}
