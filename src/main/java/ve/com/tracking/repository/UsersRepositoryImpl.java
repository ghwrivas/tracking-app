package ve.com.tracking.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ve.com.tracking.model.Users;

/**
 * 
 * @author Williams Rivas
 * 
 *         Created 26/03/2014 15:53:55
 */
public class UsersRepositoryImpl implements UsersRepositoryCustom {

	@PersistenceContext
	EntityManager em;

	private static final String SELECT_SEARCH = "select u ";

	private static final String SELECT_COUNT = "select count(u) ";

	private static final String FROM = "from Users u ";

	private static final String WHERE = "where u.telefono LIKE :telefono or u.bbPin LIKE :bbpin or u.ciRifPassport LIKE :cirifpassport or u.username LIKE :username or u.firstName LIKE :firstname or u.lastName LIKE :lastname or u.email LIKE :email";

	private static final String SQL_SEARCH = SELECT_SEARCH + FROM + WHERE;

	private static final String SQL_SEARCH_COUNT = SELECT_COUNT + FROM + WHERE;

	private static final String[] PARAMS_SELECT_SEARCH = {"username",
			"firstname", "lastname", "email", "cirifpassport", "telefono",
			"bbpin"};

	@Override
	public List<Users> searchUsers(String searchString, int firstResult,
			int maxResults) {
		TypedQuery<Users> q = em.createQuery(SQL_SEARCH, Users.class);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		setQuerySearchParams(searchString, q);
		return q.getResultList();
	}

	@Override
	public List<Users> searchUsers(String searchString) {
		TypedQuery<Users> q = em.createQuery(SQL_SEARCH, Users.class);
		setQuerySearchParams(searchString, q);
		return q.getResultList();
	}

	@Override
	public Long countSearchUsers(String searchString) {
		TypedQuery<Long> q = em.createQuery(SQL_SEARCH_COUNT, Long.class);
		setQuerySearchParams(searchString, q);
		return q.getSingleResult();
	}

	private <T> void setQuerySearchParams(String searchString,
			TypedQuery<T> query) {
		for (int i = 0; i < PARAMS_SELECT_SEARCH.length; i++) {
			query.setParameter(PARAMS_SELECT_SEARCH[i], "%" + searchString
					+ "%");
		}
	}
}
