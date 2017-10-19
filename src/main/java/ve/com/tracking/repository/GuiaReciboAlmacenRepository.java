package ve.com.tracking.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ve.com.tracking.model.GuiaReciboAlmacen;
import ve.com.tracking.model.ReciboAlmacen;
import java.util.List;

@Repository
public interface GuiaReciboAlmacenRepository extends JpaSpecificationExecutor<GuiaReciboAlmacen>, JpaRepository<GuiaReciboAlmacen, Long> {
	List<GuiaReciboAlmacen> findByReciboAlmacenId(ReciboAlmacen reciboalmacenid);

}
