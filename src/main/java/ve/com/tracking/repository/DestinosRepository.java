package ve.com.tracking.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ve.com.tracking.model.Destinos;

@Repository
public interface DestinosRepository extends JpaSpecificationExecutor<Destinos>, JpaRepository<Destinos, Long> {
}
