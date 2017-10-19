package ve.com.tracking.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ve.com.tracking.model.EmpresaEnvio;

@Repository
public interface EmpresaEnvioRepository extends JpaRepository<EmpresaEnvio, Long>, JpaSpecificationExecutor<EmpresaEnvio> {
}
