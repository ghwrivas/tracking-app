package ve.com.tracking.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ve.com.tracking.model.Guia;

@Repository
public interface GuiaRepository extends GuiaRepositoryCustom, JpaRepository<Guia, Long>, JpaSpecificationExecutor<Guia> {
}
