package ve.com.tracking.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ve.com.tracking.model.CodigoGuia;
import ve.com.tracking.model.CodigoGuiaContenedor;
import ve.com.tracking.model.Contenedor;

@Repository
public interface GuiaContenedorRepository
		extends
			JpaRepository<CodigoGuiaContenedor, Long>,
			JpaSpecificationExecutor<CodigoGuiaContenedor> {
	
	CodigoGuiaContenedor findByCodigoGuiaId(CodigoGuia guia);

	List<CodigoGuiaContenedor> findByContenedorId(Contenedor contenedor);

}
