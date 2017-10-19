package ve.com.tracking.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ve.com.tracking.model.Authoritys;
import ve.com.tracking.repository.AuthorityRepository;

/**
 * 
 * @author Williams Rivas
 * 
 *         Created 26/03/2014 15:14:00
 */
@Service
@Transactional
public class AuthorityServiceImpl implements AuthorityService {

	@Autowired
	AuthorityRepository authorityRepository;

	public long countAllAuthorityses() {
		return authorityRepository.count();
	}

	public void deleteAuthoritys(Authoritys authoritys) {
		authorityRepository.delete(authoritys);
	}

	public Authoritys findAuthoritys(Long id) {
		return authorityRepository.findOne(id);
	}

	public List<Authoritys> findAllAuthorityses() {
		return authorityRepository.findAll();
	}

	public List<Authoritys> findAuthoritysEntries(int firstResult,
			int maxResults) {
		return authorityRepository.findAll(
				new org.springframework.data.domain.PageRequest(firstResult
						/ maxResults, maxResults)).getContent();
	}

	public void saveAuthoritys(Authoritys authoritys) {
		authorityRepository.save(authoritys);
	}

	public Authoritys updateAuthoritys(Authoritys authoritys) {
		return authorityRepository.save(authoritys);
	}

	@Override
	public List<Authoritys> findAuthoritysNotLike(String name) {
		return authorityRepository.findByNameNotLike(name);
	}
}
