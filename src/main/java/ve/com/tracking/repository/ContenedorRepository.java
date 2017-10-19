package ve.com.tracking.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ve.com.tracking.model.Contenedor;

@Repository
public interface ContenedorRepository
		extends
			ContenedorRepositoryCustom,
			JpaSpecificationExecutor<Contenedor>,
			JpaRepository<Contenedor, Long> {
}
