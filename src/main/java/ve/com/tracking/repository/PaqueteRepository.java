package ve.com.tracking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ve.com.tracking.model.Paquete;

@Repository
public interface PaqueteRepository extends JpaSpecificationExecutor<Paquete>,
	JpaRepository<Paquete, Long>, PaqueteRepositoryCustom {
    Paquete findPaqueteByTracking(String tracking);

}
