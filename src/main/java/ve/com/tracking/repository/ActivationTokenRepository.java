package ve.com.tracking.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ve.com.tracking.model.ActivationToken;
import ve.com.tracking.model.Users;

@Repository
public interface ActivationTokenRepository extends JpaSpecificationExecutor<ActivationToken>, JpaRepository<ActivationToken, Long> {
	ActivationToken findByToken(String token);
	
	ActivationToken findByUsersId(Users user);
}
