package ve.com.tracking.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ve.com.tracking.model.DetalleNotificacion;
import ve.com.tracking.model.Paquete;
import ve.com.tracking.model.Users;

@Repository
public interface DetalleNotificacionRepository extends
		JpaSpecificationExecutor<DetalleNotificacion>,
		JpaRepository<DetalleNotificacion, Long> {

	DetalleNotificacion findByUsersIdAndPaqueteId(Users users, Paquete paqueteId);

	DetalleNotificacion findByPaqueteId(Paquete paqueteId);

	Page<DetalleNotificacion> findByUsersId(Users user, Pageable page);

	List<DetalleNotificacion> findByUsersId(Users user);
	
	@Query("select count(o) from DetalleNotificacion o where o.usersId = ?1")
	Long countByUsersId(Users user);
}
