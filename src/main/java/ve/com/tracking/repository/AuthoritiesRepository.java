package ve.com.tracking.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ve.com.tracking.model.Authorities;
import ve.com.tracking.model.Users;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities, Long>, JpaSpecificationExecutor<Authorities> {
	List<Authorities> findByUsername(Users username);
	
}
