package ve.com.tracking.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ve.com.tracking.model.EstatusPaquete;
import ve.com.tracking.model.Paquete;

@Repository
public interface EstatusPaqueteRepository extends JpaRepository<EstatusPaquete, Long>, JpaSpecificationExecutor<EstatusPaquete> {
	List<EstatusPaquete> findByPaqueteId(Paquete paquete);
}
