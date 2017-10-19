package ve.com.tracking.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ve.com.tracking.core.TipoEstatusContenedor;
import ve.com.tracking.model.Contenedor;

/**
 * 
 * @author Williams Rivas
 * 
 *         Created 21/07/2014 05:19:29
 */
public class ContenedorRepositoryImpl implements ContenedorRepositoryCustom {

	@PersistenceContext
	EntityManager em;

	private static final String SELECT_SEARCH = "select u ";

	private static final String SELECT_COUNT = "select count(u) ";

	private static final String FROM = "from Contenedor u ";

	private static final String WHERE = "where u.codigo LIKE :codigo or u.descripcion LIKE :descripcion";

	private static final String WHERE_ESTATUS = "where (u.codigo LIKE :codigo or u.descripcion LIKE :descripcion) and u.estatus = :estatus";

	private static final String WHERE_ESTATUS_ONLY = "where u.estatus = :estatus";

	private static final String SQL_SEARCH = SELECT_SEARCH + FROM + WHERE;

	private static final String SQL_SEARCH_COUNT = SELECT_COUNT + FROM + WHERE;

	private static final String SQL_SEARCH_ESTATUS = SELECT_SEARCH + FROM
			+ WHERE_ESTATUS;

	private static final String SQL_SEARCH_COUNT_ESTATUS = SELECT_COUNT + FROM
			+ WHERE_ESTATUS;

	private static final String SQL_SEARCH_ESTATUS_ONLY = SELECT_SEARCH + FROM
			+ WHERE_ESTATUS_ONLY;

	private static final String SQL_SEARCH_COUNT_ESTATUS_ONLY = SELECT_COUNT
			+ FROM + WHERE_ESTATUS_ONLY;

	private static final String[] PARAMS_SELECT_SEARCH = { "codigo",
			"descripcion" };

	@Override
	public List<Contenedor> searchContenedores(String searchString,
			int firstResult, int maxResults) {
		TypedQuery<Contenedor> q = em.createQuery(SQL_SEARCH, Contenedor.class);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		setQuerySearchParams(searchString, q);
		return q.getResultList();
	}

	@Override
	public List<Contenedor> searchContenedores(String searchString) {
		TypedQuery<Contenedor> q = em.createQuery(SQL_SEARCH, Contenedor.class);
		setQuerySearchParams(searchString, q);
		return q.getResultList();
	}

	@Override
	public Long countSearchContenedores(String searchString) {
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

	@Override
	public List<Contenedor> searchContenedores(String searchString,
			TipoEstatusContenedor estatus, int firstResult, int maxResults) {
		TypedQuery<Contenedor> q = em.createQuery(SQL_SEARCH_ESTATUS,
				Contenedor.class);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		setQuerySearchParams(searchString, q);
		q.setParameter("estatus", estatus);
		return q.getResultList();
	}

	@Override
	public List<Contenedor> searchContenedores(String searchString,
			TipoEstatusContenedor estatus) {
		TypedQuery<Contenedor> q = em.createQuery(SQL_SEARCH_ESTATUS,
				Contenedor.class);
		setQuerySearchParams(searchString, q);
		q.setParameter("estatus", estatus);
		return q.getResultList();
	}

	@Override
	public Long countSearchContenedores(String searchString,
			TipoEstatusContenedor estatus) {
		TypedQuery<Long> q = em.createQuery(SQL_SEARCH_COUNT_ESTATUS,
				Long.class);
		setQuerySearchParams(searchString, q);
		q.setParameter("estatus", estatus);
		return q.getSingleResult();
	}

	@Override
	public List<Contenedor> searchContenedores(TipoEstatusContenedor estatus,
			int firstResult, int maxResults) {
		TypedQuery<Contenedor> q = em.createQuery(SQL_SEARCH_ESTATUS_ONLY,
				Contenedor.class);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		q.setParameter("estatus", estatus);
		return q.getResultList();
	}

	@Override
	public List<Contenedor> searchContenedores(TipoEstatusContenedor estatus) {
		TypedQuery<Contenedor> q = em.createQuery(SQL_SEARCH_ESTATUS_ONLY,
				Contenedor.class);
		q.setParameter("estatus", estatus);
		return q.getResultList();
	}

	@Override
	public Long countSearchContenedores(TipoEstatusContenedor estatus) {
		TypedQuery<Long> q = em.createQuery(SQL_SEARCH_COUNT_ESTATUS_ONLY,
				Long.class);
		q.setParameter("estatus", estatus);
		return q.getSingleResult();
	}
}
