package ve.com.tracking.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ve.com.tracking.model.DetalleRecepcion;
import ve.com.tracking.model.Paquete;

@Repository
public interface DetalleRecepcionRepository extends JpaRepository<DetalleRecepcion, Long>, JpaSpecificationExecutor<DetalleRecepcion> {

	DetalleRecepcion findByPaqueteId(Paquete paquete);
}
