package ve.com.tracking.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ve.com.tracking.core.TipoEstatusRecibo;
import ve.com.tracking.model.ReciboAlmacen;
import ve.com.tracking.model.Users;

public class ReciboAlmacenRepositoryImpl implements
		ReciboAlmacenRepositoryCustom {

	@PersistenceContext
	EntityManager em;

	private static final String SELECT_SEARCH = "SELECT r";

	private static final String SELECT_COUNT = "SELECT count(r)";

	private static final String FROM_RECIBO_ALMACEN = " FROM ReciboAlmacen r JOIN r.cliente c";

	private static final String FROM_RECIBO_ALMACEN_ONLY = " FROM ReciboAlmacen r";

	private static final String WHERE_RECIBO_ALMACEN = " WHERE c.firstName LIKE :nombre or c.lastName LIKE :apellido or c.username LIKE :username or c.ciRifPassport LIKE :ciRif";

	private static final String WHERE_RECIBO_ALMACEN_ESTATUS = " WHERE (c.firstName LIKE :nombre or c.lastName LIKE :apellido or c.username LIKE :username or c.ciRifPassport LIKE :ciRif) and r.estatus = :estatus";

	private static final String WHERE_RECIBO_ALMACEN_ONLY_ESTATUS = " WHERE r.estatus = :estatus";

	private static final String WHERE_RECIBO_ALMACEN_CLIENTE_ONLY = " WHERE r.cliente = :cliente";

	private static final String SELECT_SEARCH_RECIBO_CLIENTE = "SELECT r FROM ReciboAlmacen r JOIN r.cliente c WHERE r.cliente = :cliente and r.id = :id";

	private static final String SELECT_SEARCH_RECIBO_COUNT = "SELECT count(r) FROM ReciboAlmacen r JOIN r.cliente c WHERE r.cliente = :cliente and r.id = :id";

	private static final String[] PARAMS_SELECT_SEARCH_RECIBO_ALMACEN = {
			"nombre", "apellido", "username", "ciRif" };

	private static final String RECIBO_ALMACEN_SQL_SEARCH = SELECT_SEARCH
			+ FROM_RECIBO_ALMACEN + WHERE_RECIBO_ALMACEN;

	private static final String RECIBO_ALMACEN_SQL_SEARCH_COUNT = SELECT_COUNT
			+ FROM_RECIBO_ALMACEN + WHERE_RECIBO_ALMACEN;

	private static final String RECIBO_ALMACEN_SQL_SEARCH_CLIENTE_ONLY = SELECT_SEARCH
			+ FROM_RECIBO_ALMACEN + WHERE_RECIBO_ALMACEN_CLIENTE_ONLY;

	private static final String RECIBO_ALMACEN_SQL_SEARCH_COUNT_CLIENTE_ONLY = SELECT_COUNT
			+ FROM_RECIBO_ALMACEN + WHERE_RECIBO_ALMACEN_CLIENTE_ONLY;

	private static final String RECIBO_ALMACEN_SQL_SEARCH_ESTATUS = SELECT_SEARCH
			+ FROM_RECIBO_ALMACEN + WHERE_RECIBO_ALMACEN_ESTATUS;

	private static final String RECIBO_ALMACEN_SQL_SEARCH_COUNT_ESTATUS = SELECT_COUNT
			+ FROM_RECIBO_ALMACEN + WHERE_RECIBO_ALMACEN_ESTATUS;

	private static final String RECIBO_ALMACEN_SQL_SEARCH_ONLY_ESTATUS = SELECT_SEARCH
			+ FROM_RECIBO_ALMACEN + WHERE_RECIBO_ALMACEN_ONLY_ESTATUS;

	private static final String RECIBO_ALMACEN_SQL_SEARCH_COUNT_ONLY_ESTATUS = SELECT_COUNT
			+ FROM_RECIBO_ALMACEN + WHERE_RECIBO_ALMACEN_ONLY_ESTATUS;

	@Override
	public List<ReciboAlmacen> searchReciboAlmacen(String searchString,
			int firstResult, int maxResults) {
		String sql = RECIBO_ALMACEN_SQL_SEARCH;
		Long posibleId = parseLong(searchString);
		TypedQuery<ReciboAlmacen> q = em.createQuery(sql, ReciboAlmacen.class);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		if (posibleId != null) {
			sql = SELECT_SEARCH + FROM_RECIBO_ALMACEN_ONLY
					+ " WHERE r.id = :id";
			q = em.createQuery(sql, ReciboAlmacen.class);
			q.setParameter("id", posibleId);
		} else {
			setQuerySearchParams(searchString,
					PARAMS_SELECT_SEARCH_RECIBO_ALMACEN, q);
		}
		return q.getResultList();
	}

	@Override
	public List<ReciboAlmacen> searchReciboAlmacen(String searchString) {
		Long posibleId = parseLong(searchString);
		String sql = RECIBO_ALMACEN_SQL_SEARCH;
		TypedQuery<ReciboAlmacen> q = em.createQuery(sql, ReciboAlmacen.class);
		if (posibleId != null) {
			sql = SELECT_SEARCH + FROM_RECIBO_ALMACEN_ONLY
					+ " WHERE r.id = :id";
			q = em.createQuery(sql, ReciboAlmacen.class);
			q.setParameter("id", posibleId);
		} else {
			setQuerySearchParams(searchString,
					PARAMS_SELECT_SEARCH_RECIBO_ALMACEN, q);
		}
		return q.getResultList();
	}

	@Override
	public Long countSearchReciboAlmacen(String searchString) {
		Long posibleId = parseLong(searchString);
		String sql = RECIBO_ALMACEN_SQL_SEARCH_COUNT;
		TypedQuery<Long> q = em.createQuery(sql, Long.class);
		if (posibleId != null) {
			sql = SELECT_COUNT + FROM_RECIBO_ALMACEN_ONLY + " WHERE r.id = :id";
			q = em.createQuery(sql, Long.class);
			q.setParameter("id", posibleId);
		} else {
			setQuerySearchParams(searchString,
					PARAMS_SELECT_SEARCH_RECIBO_ALMACEN, q);
		}
		return q.getSingleResult();
	}

	public Long parseLong(String str) {
		Long number = null;
		try {
			number = new Long(Long.parseLong(str));
		} catch (Exception e) {

		}
		return number;
	}

	private <T> void setQuerySearchParams(String searchString, String[] params,
			TypedQuery<T> query) {
		for (int i = 0; i < params.length; i++) {
			query.setParameter(params[i], "%" + searchString + "%");
		}
	}

	@Override
	public List<ReciboAlmacen> searchReciboAlmacenByCliente(
			String numeroRecibo, Users cliente, int firstResult, int maxResults) {
		String sql = SELECT_SEARCH_RECIBO_CLIENTE;
		TypedQuery<ReciboAlmacen> q = em.createQuery(sql, ReciboAlmacen.class);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		q.setParameter("id", parseLong(numeroRecibo));
		q.setParameter("cliente", cliente);
		return q.getResultList();
	}

	@Override
	public List<ReciboAlmacen> searchReciboAlmacenByCliente(
			String numeroRecibo, Users cliente) {
		String sql = SELECT_SEARCH_RECIBO_CLIENTE;
		TypedQuery<ReciboAlmacen> q = em.createQuery(sql, ReciboAlmacen.class);
		q.setParameter("id", parseLong(numeroRecibo));
		q.setParameter("cliente", cliente);
		return q.getResultList();
	}

	@Override
	public Long countSearchReciboAlmacenByCliente(String numeroRecibo,
			Users cliente) {
		String sql = SELECT_SEARCH_RECIBO_COUNT;
		TypedQuery<Long> q = em.createQuery(sql, Long.class);
		q.setParameter("id", parseLong(numeroRecibo));
		q.setParameter("cliente", cliente);
		return q.getSingleResult();
	}

	@Override
	public List<ReciboAlmacen> searchReciboAlmacenByCliente(Users cliente,
			int firstResult, int maxResults) {
		String sql = RECIBO_ALMACEN_SQL_SEARCH_CLIENTE_ONLY;
		TypedQuery<ReciboAlmacen> q = em.createQuery(sql, ReciboAlmacen.class);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		q.setParameter("cliente", cliente);
		return q.getResultList();
	}

	@Override
	public List<ReciboAlmacen> searchReciboAlmacenByCliente(Users cliente) {
		String sql = RECIBO_ALMACEN_SQL_SEARCH_CLIENTE_ONLY;
		TypedQuery<ReciboAlmacen> q = em.createQuery(sql, ReciboAlmacen.class);
		q.setParameter("cliente", cliente);
		return q.getResultList();
	}

	@Override
	public Long countSearchReciboAlmacenByCliente(Users cliente) {
		String sql = RECIBO_ALMACEN_SQL_SEARCH_COUNT_CLIENTE_ONLY;
		TypedQuery<Long> q = em.createQuery(sql, Long.class);
		q.setParameter("cliente", cliente);
		return q.getSingleResult();
	}

	@Override
	public List<ReciboAlmacen> searchReciboAlmacen(String searchString,
			TipoEstatusRecibo estatus, int firstResult, int maxResults) {
		String sql = RECIBO_ALMACEN_SQL_SEARCH_ESTATUS;
		Long posibleId = parseLong(searchString);
		TypedQuery<ReciboAlmacen> q = em.createQuery(sql, ReciboAlmacen.class);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		if (posibleId != null) {
			sql = SELECT_SEARCH + FROM_RECIBO_ALMACEN_ONLY
					+ " WHERE r.id = :id";
			q = em.createQuery(sql, ReciboAlmacen.class);
			q.setParameter("id", posibleId);
		} else {
			setQuerySearchParams(searchString,
					PARAMS_SELECT_SEARCH_RECIBO_ALMACEN, q);
			q.setParameter("estatus", estatus);
		}
		return q.getResultList();
	}

	@Override
	public List<ReciboAlmacen> searchReciboAlmacen(String searchString,
			TipoEstatusRecibo estatus) {
		String sql = RECIBO_ALMACEN_SQL_SEARCH_ESTATUS;
		Long posibleId = parseLong(searchString);
		TypedQuery<ReciboAlmacen> q = em.createQuery(sql, ReciboAlmacen.class);
		if (posibleId != null) {
			sql = SELECT_SEARCH + FROM_RECIBO_ALMACEN_ONLY
					+ " WHERE r.id = :id";
			q = em.createQuery(sql, ReciboAlmacen.class);
			q.setParameter("id", posibleId);
		} else {
			setQuerySearchParams(searchString,
					PARAMS_SELECT_SEARCH_RECIBO_ALMACEN, q);
			q.setParameter("estatus", estatus);
		}
		return q.getResultList();
	}

	@Override
	public Long countSearchReciboAlmacen(String searchString,
			TipoEstatusRecibo estatus) {
		Long posibleId = parseLong(searchString);
		String sql = RECIBO_ALMACEN_SQL_SEARCH_COUNT_ESTATUS;
		TypedQuery<Long> q = em.createQuery(sql, Long.class);
		if (posibleId != null) {
			sql = SELECT_COUNT + FROM_RECIBO_ALMACEN_ONLY + " WHERE r.id = :id";
			q = em.createQuery(sql, Long.class);
			q.setParameter("id", posibleId);
		} else {
			setQuerySearchParams(searchString,
					PARAMS_SELECT_SEARCH_RECIBO_ALMACEN, q);
			q.setParameter("estatus", estatus);
		}
		return q.getSingleResult();
	}

	@Override
	public List<ReciboAlmacen> searchReciboAlmacen(TipoEstatusRecibo estatus,
			int firstResult, int maxResults) {
		String sql = RECIBO_ALMACEN_SQL_SEARCH_ONLY_ESTATUS;
		TypedQuery<ReciboAlmacen> q = em.createQuery(sql, ReciboAlmacen.class);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		q.setParameter("estatus", estatus);
		return q.getResultList();
	}

	@Override
	public List<ReciboAlmacen> searchReciboAlmacen(TipoEstatusRecibo estatus) {
		String sql = RECIBO_ALMACEN_SQL_SEARCH_ONLY_ESTATUS;
		TypedQuery<ReciboAlmacen> q = em.createQuery(sql, ReciboAlmacen.class);
		q.setParameter("estatus", estatus);
		return q.getResultList();
	}

	@Override
	public Long countSearchReciboAlmacen(TipoEstatusRecibo estatus) {
		String sql = RECIBO_ALMACEN_SQL_SEARCH_COUNT_ONLY_ESTATUS;
		TypedQuery<Long> q = em.createQuery(sql, Long.class);
		q.setParameter("estatus", estatus);
		return q.getSingleResult();
	}

}
