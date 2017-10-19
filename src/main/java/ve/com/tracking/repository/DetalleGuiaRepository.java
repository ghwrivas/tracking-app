package ve.com.tracking.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ve.com.tracking.model.DetalleGuia;
import ve.com.tracking.model.DetalleItem;
import ve.com.tracking.model.Guia;

@Repository
public interface DetalleGuiaRepository extends JpaSpecificationExecutor<DetalleGuia>, JpaRepository<DetalleGuia, Long> {
	List<DetalleGuia> findByGuiaId(Guia guia);
	
	DetalleItem findByDetalleItemId(DetalleItem itemId);
}
