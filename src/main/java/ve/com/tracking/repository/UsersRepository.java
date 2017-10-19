package ve.com.tracking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ve.com.tracking.model.Users;

@Repository
public interface UsersRepository
		extends			
			JpaSpecificationExecutor<Users>,
			JpaRepository<Users, Long>, UsersRepositoryCustom {

	Users findByUsername(String username);

	Users findByEmail(String email);

	Users findByCiRifPassport(String ciRifPassport);

	Users findByUsernameOrCiRifPassportOrEmail(String username,
			String ciRifPassport, String email);
}
