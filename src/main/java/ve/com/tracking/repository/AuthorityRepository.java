package ve.com.tracking.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ve.com.tracking.model.Authoritys;

@Repository
public interface AuthorityRepository extends JpaSpecificationExecutor<Authoritys>, JpaRepository<Authoritys, Long> {
	Authoritys findByAuthority(String name);
	
	List<Authoritys> findByNameNotLike(String name);
}
