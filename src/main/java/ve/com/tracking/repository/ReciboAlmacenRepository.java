package ve.com.tracking.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ve.com.tracking.model.ReciboAlmacen;
import ve.com.tracking.model.Users;

@Repository
public interface ReciboAlmacenRepository extends ReciboAlmacenRepositoryCustom, JpaRepository<ReciboAlmacen, Long>, JpaSpecificationExecutor<ReciboAlmacen> {

	List<ReciboAlmacen> findReciboAlmacensByCliente(Users cliente);
}
