package ve.com.tracking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ve.com.tracking.model.CodigoGuia;
import ve.com.tracking.model.Guia;
import java.util.List;
import java.lang.String;

@Repository
public interface CodigoGuiaRepository extends
		JpaSpecificationExecutor<CodigoGuia>, JpaRepository<CodigoGuia, Long> {

	List<CodigoGuia> findByGuiaId(Guia guiaid);

	CodigoGuia findByCodigo(String codigo);
}