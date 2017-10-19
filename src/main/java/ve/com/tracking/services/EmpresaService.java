package ve.com.tracking.services;
import java.util.List;
import ve.com.tracking.model.Empresa;

public interface EmpresaService {

	public abstract long countAllEmpresas();


	public abstract void deleteEmpresa(Empresa empresa);


	public abstract Empresa findEmpresa(Short id);


	public abstract List<Empresa> findAllEmpresas();


	public abstract List<Empresa> findEmpresaEntries(int firstResult, int maxResults);


	public abstract void saveEmpresa(Empresa empresa);


	public abstract Empresa updateEmpresa(Empresa empresa);

}
