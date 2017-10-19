package ve.com.tracking.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ve.com.tracking.model.CodigoGuia;
import ve.com.tracking.model.EstatusGuia;

@Repository
public interface EstatusGuiaRepository extends JpaSpecificationExecutor<EstatusGuia>, JpaRepository<EstatusGuia, Long> {
	List<EstatusGuia> findByCodigoGuiaId(CodigoGuia codigoGuia);
}
