package ve.com.tracking.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ve.com.tracking.model.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Short>, JpaSpecificationExecutor<Empresa> {

}
