package ve.com.tracking.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ve.com.tracking.core.TipoEstatusGuia;
import ve.com.tracking.model.DetalleItem;
import ve.com.tracking.model.Guia;
import ve.com.tracking.model.Guias;

public class GuiaRepositoryImpl implements GuiaRepositoryCustom {

	@PersistenceContext
	EntityManager em;

	private static final String SELECT_SEARCH = "select g";

	private static final String SELECT_COUNT = "select count(g)";

	private static final String FROM_GUIAS_VIEW = " from Guias g";

	private static final String WHERE_GUIAS_VIEW = " where g.id.codigo LIKE :codigo or g.id.nombreCliente LIKE :cliente or g.id.detalle LIKE :detalle or g.id.trackings LIKE :trackings";

	private static final String WHERE_GUIAS_VIEW_ESTATUS = " where (g.id.codigo LIKE :codigo or g.id.nombreCliente LIKE :cliente or g.id.detalle LIKE :detalle or g.id.trackings LIKE :trackings) and g.id.estatus =:estatus";

	private static final String WHERE_GUIAS_VIEW_ONLY_ESTATUS = " where g.id.estatus =:estatus";

	private static final String GUIAS_VIEW_SQL_SEARCH = SELECT_SEARCH
			+ FROM_GUIAS_VIEW + WHERE_GUIAS_VIEW;

	private static final String GUIAS_VIEW_SQL_SEARCH_COUNT = SELECT_COUNT
			+ FROM_GUIAS_VIEW + WHERE_GUIAS_VIEW;

	private static final String GUIAS_VIEW_SQL_SEARCH_ESTATUS = SELECT_SEARCH
			+ FROM_GUIAS_VIEW + WHERE_GUIAS_VIEW_ESTATUS;

	private static final String GUIAS_VIEW_SQL_SEARCH_COUNT_ESTATUS = SELECT_COUNT
			+ FROM_GUIAS_VIEW + WHERE_GUIAS_VIEW_ESTATUS;
	
	private static final String GUIAS_VIEW_SQL_SEARCH_ONLY_ESTATUS = SELECT_SEARCH
			+ FROM_GUIAS_VIEW + WHERE_GUIAS_VIEW_ONLY_ESTATUS;

	private static final String GUIAS_VIEW_SQL_SEARCH_COUNT_ONLY_ESTATUS = SELECT_COUNT
			+ FROM_GUIAS_VIEW + WHERE_GUIAS_VIEW_ONLY_ESTATUS;
	
	private static final String[] PARAMS_SELECT_SEARCH_GUIAS_VIEW = {
			"codigo", "detalle", "trackings", "cliente" };

	@Override
	public List<DetalleItem> findDetalleItems(Guia guia) {
		TypedQuery<DetalleItem> q = em
				.createQuery(
						"select di from DetalleItem di join di.detalleGuiaId dg where dg.guiaId = :guia",
						DetalleItem.class);
		q.setParameter("guia", guia);
		return q.getResultList();
	}

	@Override
	public List<Guias> searchGuiasView(String searchString, int firstResult,
			int maxResults) {
		Long posibleId = parseLong(searchString);
		String sql = GUIAS_VIEW_SQL_SEARCH;
		if (posibleId != null) {
			sql += " or g.id.id = :id";
		}
		TypedQuery<Guias> q = em.createQuery(sql, Guias.class);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		if (posibleId != null)
			q.setParameter("id", posibleId);
		setQuerySearchParams(searchString, PARAMS_SELECT_SEARCH_GUIAS_VIEW, q);
		return q.getResultList();
	}

	@Override
	public List<Guias> searchGuiasView(String searchString) {
		Long posibleId = parseLong(searchString);
		String sql = GUIAS_VIEW_SQL_SEARCH;
		if (posibleId != null) {
			sql += " or g.id.id = :id";
		}
		TypedQuery<Guias> q = em.createQuery(sql, Guias.class);
		setQuerySearchParams(searchString, PARAMS_SELECT_SEARCH_GUIAS_VIEW, q);
		if (posibleId != null)
			q.setParameter("id", posibleId);
		return q.getResultList();
	}

	@Override
	public Long countSearchGuiasView(String searchString) {
		Long posibleId = parseLong(searchString);
		String sql = GUIAS_VIEW_SQL_SEARCH_COUNT;
		if (posibleId != null) {
			sql += " or g.id.id = :id";
		}
		TypedQuery<Long> q = em.createQuery(sql, Long.class);
		setQuerySearchParams(searchString, PARAMS_SELECT_SEARCH_GUIAS_VIEW, q);
		if (posibleId != null)
			q.setParameter("id", posibleId);
		return q.getSingleResult();
	}

	@Override
	public List<Guias> findAllGuiasView() {
		TypedQuery<Guias> q = em.createQuery(SELECT_SEARCH + FROM_GUIAS_VIEW,
				Guias.class);
		return q.getResultList();
	}

	@Override
	public Long countAllGuiasView() {
		TypedQuery<Long> q = em.createQuery(SELECT_COUNT + FROM_GUIAS_VIEW,
				Long.class);
		return q.getSingleResult();
	}

	private <T> void setQuerySearchParams(String searchString, String[] params,
			TypedQuery<T> query) {
		for (int i = 0; i < params.length; i++) {
			query.setParameter(params[i], "%" + searchString + "%");
		}
	}

	@Override
	public List<Guias> findGuiasEntries(int firstResult, int maxResults) {
		TypedQuery<Guias> q = em.createQuery(SELECT_SEARCH + FROM_GUIAS_VIEW,
				Guias.class);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		return q.getResultList();
	}

	public Long parseLong(String str) {
		Long number = null;
		try {
			number = Long.parseLong(str);
		} catch (Exception e) {

		}
		return number;
	}

	@Override
	public List<Guias> searchGuiasView(String searchString,
			TipoEstatusGuia estatus, int firstResult, int maxResults) {
		String sql = GUIAS_VIEW_SQL_SEARCH_ESTATUS;
		TypedQuery<Guias> q = em.createQuery(sql, Guias.class);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		setQuerySearchParams(searchString, PARAMS_SELECT_SEARCH_GUIAS_VIEW, q);
		q.setParameter("estatus", estatus);
		return q.getResultList();
	}

	@Override
	public List<Guias> searchGuiasView(String searchString,
			TipoEstatusGuia estatus) {
		String sql = GUIAS_VIEW_SQL_SEARCH;
		TypedQuery<Guias> q = em.createQuery(sql, Guias.class);
		setQuerySearchParams(searchString, PARAMS_SELECT_SEARCH_GUIAS_VIEW, q);
		q.setParameter("estatus", estatus);
		return q.getResultList();
	}

	@Override
	public Long countSearchGuiasView(String searchString,
			TipoEstatusGuia estatus) {
		String sql = GUIAS_VIEW_SQL_SEARCH_COUNT_ESTATUS;
		TypedQuery<Long> q = em.createQuery(sql, Long.class);
		setQuerySearchParams(searchString, PARAMS_SELECT_SEARCH_GUIAS_VIEW, q);
		q.setParameter("estatus", estatus);
		return q.getSingleResult();
	}

	@Override
	public List<Guias> searchGuiasView(TipoEstatusGuia estatus,
			int firstResult, int maxResults) {
		String sql = GUIAS_VIEW_SQL_SEARCH_ONLY_ESTATUS;
		TypedQuery<Guias> q = em.createQuery(sql, Guias.class);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		q.setParameter("estatus", estatus);
		return q.getResultList();
	}

	@Override
	public List<Guias> searchGuiasView(TipoEstatusGuia estatus) {
		String sql = GUIAS_VIEW_SQL_SEARCH_ONLY_ESTATUS;
		TypedQuery<Guias> q = em.createQuery(sql, Guias.class);
		q.setParameter("estatus", estatus);
		return q.getResultList();
	}

	@Override
	public Long countSearchGuiasView(TipoEstatusGuia estatus) {
		String sql = GUIAS_VIEW_SQL_SEARCH_COUNT_ONLY_ESTATUS;
		TypedQuery<Long> q = em.createQuery(sql, Long.class);
		q.setParameter("estatus", estatus);
		return q.getSingleResult();
	}
}
