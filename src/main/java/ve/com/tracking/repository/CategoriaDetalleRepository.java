package ve.com.tracking.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ve.com.tracking.model.CategoriaDetalle;

@Repository
public interface CategoriaDetalleRepository extends JpaRepository<CategoriaDetalle, Long>, JpaSpecificationExecutor<CategoriaDetalle> {
}
