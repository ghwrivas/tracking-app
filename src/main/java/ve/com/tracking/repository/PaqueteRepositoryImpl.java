package ve.com.tracking.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import ve.com.tracking.core.TipoEstatusPaquete;
import ve.com.tracking.model.DetalleGuiasPaquete;
import ve.com.tracking.model.Paquete;
import ve.com.tracking.model.PaquetesNotificadoConfirmado;
import ve.com.tracking.model.Users;

public class PaqueteRepositoryImpl implements PaqueteRepositoryCustom {

	@PersistenceContext
	EntityManager em;

	private static final String SELECT_SEARCH = "SELECT p";

	private static final String SELECT_COUNT = "SELECT count(p)";

	private static final String FROM_PAQUETE = " FROM Paquete p LEFT OUTER JOIN p.detalleRecepcion r LEFT OUTER JOIN p.detalleNotificacion d";

	private static final String FROM_PAQUETE_CLIENTE = " FROM Paquete p join p.detalleNotificacion d";

	private static final String WHERE_PAQUETE = " WHERE p.tracking LIKE :tracking or r.descripcion LIKE :descripcion2 or d.descripcion LIKE :descripcion1";

	private static final String WHERE_PAQUETE_LIKE_CLIENTE = " WHERE p.tracking LIKE :tracking or r.descripcion LIKE :descripcion2 or d.descripcion LIKE :descripcion1 or d.usersId.firstName LIKE :firstname or d.usersId.lastName LIKE :lastname";

	private static final String WHERE_PAQUETE_ESTATUS = " WHERE (p.tracking LIKE :tracking or r.descripcion LIKE :descripcion2 or d.descripcion LIKE :descripcion1) and p.estatus = :estatus";

	private static final String WHERE_PAQUETE_ESTATUS_AND_TRACKING_GENERADO = " WHERE (p.tracking LIKE :tracking or r.descripcion LIKE :descripcion2 or d.descripcion LIKE :descripcion1) and p.estatus = :estatus and p.trackingGenerado = :trackingGenerado";

	private static final String WHERE_PAQUETE_LIKE_CLIENTE_ESTATUS = " WHERE (p.tracking LIKE :tracking or r.descripcion LIKE :descripcion2 or d.descripcion LIKE :descripcion1 or d.usersId.firstName LIKE :firstname or d.usersId.lastName LIKE :lastname) and p.estatus = :estatus";

	private static final String WHERE_PAQUETE_LIKE_CLIENTE_ESTATUS_AND_TRACKING_GENERADO = " WHERE (p.tracking LIKE :tracking or r.descripcion LIKE :descripcion2 or d.descripcion LIKE :descripcion1 or d.usersId.firstName LIKE :firstname or d.usersId.lastName LIKE :lastname) and p.estatus = :estatus and p.trackingGenerado = :trackingGenerado";

	private static final String WHERE_PAQUETE_ONLY_ESTATUS = " WHERE p.estatus = :estatus";

	private static final String WHERE_PAQUETE_ONLY_TRACKING_GENERADO = " WHERE p.trackingGenerado = :trackingGenerado";

	private static final String WHERE_PAQUETE_CLIENTE = " WHERE (p.tracking LIKE :tracking or d.descripcion LIKE :descripcion) and d.usersId = :cliente";

	private static final String FROM_NOTIFICADO_CONFIRMADO = " FROM PaquetesNotificadoConfirmado p";

	private static final String WHERE_NOTIFICADO_CONFIRMADO = " WHERE p.id.username LIKE :username or p.id.firstName LIKE :firstname or p.id.lastName LIKE :lastname or p.id.email LIKE :email";

	private static final String WHERE_PAQUETE_TRACKING_GENERADO = " WHERE (p.tracking LIKE :tracking or r.descripcion LIKE :descripcion2 or d.descripcion LIKE :descripcion1) and p.trackingGenerado = :trackingGenerado";

	private static final String WHERE_PAQUETE_LIKE_CLIENTE_TRACKING_GENERADO = " WHERE (p.tracking LIKE :tracking or r.descripcion LIKE :descripcion2 or d.descripcion LIKE :descripcion1 or d.usersId.firstName LIKE :firstname or d.usersId.lastName LIKE :lastname) and p.trackingGenerado = :trackingGenerado";

	private static final String WHERE_PAQUETE_ESTATUS_AND_TRACKING_GENERADO_ONLY = " WHERE p.estatus = :estatus and p.trackingGenerado = :trackingGenerado";

	private static final String NOTIFICADO_CONFIRMADO_SQL_SEARCH = SELECT_SEARCH
			+ FROM_NOTIFICADO_CONFIRMADO + WHERE_NOTIFICADO_CONFIRMADO;

	private static final String NOTIFICADO_CONFIRMADO_SQL_SEARCH_COUNT = SELECT_COUNT
			+ FROM_NOTIFICADO_CONFIRMADO + WHERE_NOTIFICADO_CONFIRMADO;

	private static final String PAQUETES_SQL_SEARCH = SELECT_SEARCH
			+ FROM_PAQUETE + WHERE_PAQUETE;

	private static final String PAQUETES_SQL_SEARCH_COUNT = SELECT_COUNT
			+ FROM_PAQUETE + WHERE_PAQUETE;

	private static final String PAQUETES_SQL_SEARCH_ONLY_ESTATUS = SELECT_SEARCH
			+ FROM_PAQUETE + WHERE_PAQUETE_ONLY_ESTATUS;

	private static final String PAQUETES_SQL_SEARCH_ONLY_ESTATUS_COUNT = SELECT_COUNT
			+ FROM_PAQUETE + WHERE_PAQUETE_ONLY_ESTATUS;

	private static final String PAQUETES_SQL_SEARCH_LIKE_CLIENTE = SELECT_SEARCH
			+ FROM_PAQUETE + WHERE_PAQUETE_LIKE_CLIENTE;

	private static final String PAQUETES_SQL_SEARCH_LIKE_CLIENTE_COUNT = SELECT_COUNT
			+ FROM_PAQUETE + WHERE_PAQUETE_LIKE_CLIENTE;

	private static final String PAQUETES_SQL_SEARCH_ESTATUS = SELECT_SEARCH
			+ FROM_PAQUETE + WHERE_PAQUETE_ESTATUS;

	private static final String PAQUETES_SQL_SEARCH_ESTATUS_COUNT = SELECT_COUNT
			+ FROM_PAQUETE + WHERE_PAQUETE_ESTATUS;

	private static final String PAQUETES_SQL_SEARCH_LIKE_CLIENTE_ESTATUS = SELECT_SEARCH
			+ FROM_PAQUETE + WHERE_PAQUETE_LIKE_CLIENTE_ESTATUS;

	private static final String PAQUETES_SQL_SEARCH_LIKE_CLIENTE_ESTATUS_COUNT = SELECT_COUNT
			+ FROM_PAQUETE + WHERE_PAQUETE_LIKE_CLIENTE_ESTATUS;

	private static final String PAQUETES_SQL_SEARCH_CLIENTE = SELECT_SEARCH
			+ FROM_PAQUETE_CLIENTE + WHERE_PAQUETE_CLIENTE;

	private static final String PAQUETES_SQL_SEARCH_COUNT_CLIENTE = SELECT_COUNT
			+ FROM_PAQUETE + WHERE_PAQUETE_CLIENTE;

	private static final String PAQUETES_SQL_SEARCH_ESTATUS_AND_TRACKING_GENERADO = SELECT_SEARCH
			+ FROM_PAQUETE + WHERE_PAQUETE_ESTATUS_AND_TRACKING_GENERADO;

	private static final String PAQUETES_SQL_SEARCH_ESTATUS_AND_TRACKING_GENERADO_COUNT = SELECT_COUNT
			+ FROM_PAQUETE + WHERE_PAQUETE_ESTATUS_AND_TRACKING_GENERADO;

	private static final String PAQUETES_SQL_SEARCH_TRACKING_GENERADO = SELECT_SEARCH
			+ FROM_PAQUETE + WHERE_PAQUETE_TRACKING_GENERADO;

	private static final String PAQUETES_SQL_SEARCH_LIKE_CLIENTE_AND_TRACKING_GENERADO = SELECT_SEARCH
			+ FROM_PAQUETE + WHERE_PAQUETE_LIKE_CLIENTE_TRACKING_GENERADO;

	private static final String PAQUETES_SQL_SEARCH_TRACKING_GENERADO_COUNT = SELECT_COUNT
			+ FROM_PAQUETE + WHERE_PAQUETE_TRACKING_GENERADO;

	private static final String PAQUETES_SQL_SEARCH_LIKE_CLIENTE_TRACKING_GENERADO_COUNT = SELECT_COUNT
			+ FROM_PAQUETE + WHERE_PAQUETE_LIKE_CLIENTE_TRACKING_GENERADO;

	private static final String PAQUETES_SQL_SEARCH_LIKE_CLIENTE_ESTATUS_AND_TRACKING_GENERADO = SELECT_SEARCH
			+ FROM_PAQUETE
			+ WHERE_PAQUETE_LIKE_CLIENTE_ESTATUS_AND_TRACKING_GENERADO;

	private static final String PAQUETES_SQL_SEARCH_LIKE_CLIENTE_ESTATUS_AND_TRACKING_GENERADO_COUNT = SELECT_COUNT
			+ FROM_PAQUETE
			+ WHERE_PAQUETE_LIKE_CLIENTE_ESTATUS_AND_TRACKING_GENERADO;

	private static final String PAQUETES_SQL_SEARCH_ESTATUS_AND_TRACKING_GENERADO_ONLY = SELECT_SEARCH
			+ FROM_PAQUETE + WHERE_PAQUETE_ESTATUS_AND_TRACKING_GENERADO_ONLY;

	private static final String PAQUETES_SQL_SEARCH_ESTATUS_AND_TRACKING_GENERADO_ONLY_COUNT = SELECT_COUNT
			+ FROM_PAQUETE + WHERE_PAQUETE_ESTATUS_AND_TRACKING_GENERADO_ONLY;

	private static final String PAQUETES_SQL_SEARCH_TRACKING_GENERADO_ONLY = SELECT_SEARCH
			+ FROM_PAQUETE + WHERE_PAQUETE_ONLY_TRACKING_GENERADO;

	private static final String PAQUETES_SQL_SEARCH_TRACKING_GENERADO_ONLY_COUNT = SELECT_COUNT
			+ FROM_PAQUETE + WHERE_PAQUETE_ONLY_TRACKING_GENERADO;

	private static final String[] PARAMS_SELECT_SEARCH_PAQUETES = { "tracking",
			"descripcion1", "descripcion2" };

	private static final String[] PARAMS_SELECT_SEARCH_PAQUETES_LIKE_CLIENTE = {
			"tracking", "descripcion1", "descripcion2", "firstname", "lastname" };

	private static final String[] PARAMS_SELECT_SEARCH_PAQUETES_CLIENTE = {
			"tracking", "descripcion" };

	private static final String[] PARAMS_SELECT_SEARCH_NOTIFICADO_CONFIRMADO = {
			"username", "firstname", "lastname", "email" };

	@Override
	public List<Paquete> searchPaquetes(String searchString, int firstResult,
			int maxResults) {
		TypedQuery<Paquete> q;
		if (StringUtils.containsIgnoreCase(searchString, "c:")) {
			searchString = StringUtils.substringAfter(searchString, ":");
			q = em.createQuery(PAQUETES_SQL_SEARCH_LIKE_CLIENTE, Paquete.class);
			q.setFirstResult(firstResult);
			q.setMaxResults(maxResults);
			setQuerySearchParams(searchString,
					PARAMS_SELECT_SEARCH_PAQUETES_LIKE_CLIENTE, q);
		} else {
			q = em.createQuery(PAQUETES_SQL_SEARCH, Paquete.class);
			q.setFirstResult(firstResult);
			q.setMaxResults(maxResults);
			setQuerySearchParams(searchString, PARAMS_SELECT_SEARCH_PAQUETES, q);

		}
		return q.getResultList();
	}

	@Override
	public List<Paquete> searchPaquetes(String searchString) {
		TypedQuery<Paquete> q;
		if (StringUtils.containsIgnoreCase(searchString, "c:")) {
			searchString = StringUtils.substringAfter(searchString, ":");
			q = em.createQuery(PAQUETES_SQL_SEARCH_LIKE_CLIENTE, Paquete.class);
			setQuerySearchParams(searchString,
					PARAMS_SELECT_SEARCH_PAQUETES_LIKE_CLIENTE, q);
		} else {
			q = em.createQuery(PAQUETES_SQL_SEARCH, Paquete.class);
			setQuerySearchParams(searchString, PARAMS_SELECT_SEARCH_PAQUETES, q);
		}
		return q.getResultList();
	}

	@Override
	public Long countSearchPaquetes(String searchString) {
		TypedQuery<Long> q;
		if (StringUtils.containsIgnoreCase(searchString, "c:")) {
			searchString = StringUtils.substringAfter(searchString, ":");
			q = em.createQuery(PAQUETES_SQL_SEARCH_LIKE_CLIENTE_COUNT,
					Long.class);
			setQuerySearchParams(searchString,
					PARAMS_SELECT_SEARCH_PAQUETES_LIKE_CLIENTE, q);
		} else {
			q = em.createQuery(PAQUETES_SQL_SEARCH_COUNT, Long.class);
			setQuerySearchParams(searchString, PARAMS_SELECT_SEARCH_PAQUETES, q);
		}
		return q.getSingleResult();
	}

	private <T> void setQuerySearchParams(String searchString, String[] params,
			TypedQuery<T> query) {
		for (int i = 0; i < params.length; i++) {
			query.setParameter(params[i], "%" + searchString + "%");
		}
	}

	@Override
	public List<PaquetesNotificadoConfirmado> findAllPaquetesNotificadosConfirmados() {
		TypedQuery<PaquetesNotificadoConfirmado> q = em.createQuery(
				SELECT_SEARCH + FROM_NOTIFICADO_CONFIRMADO,
				PaquetesNotificadoConfirmado.class);
		return q.getResultList();
	}

	@Override
	public List<PaquetesNotificadoConfirmado> searchPaquetesNotificadosConfirmados(
			String searchString, int firstResult, int maxResults) {
		TypedQuery<PaquetesNotificadoConfirmado> q = em.createQuery(
				NOTIFICADO_CONFIRMADO_SQL_SEARCH,
				PaquetesNotificadoConfirmado.class);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		setQuerySearchParams(searchString,
				PARAMS_SELECT_SEARCH_NOTIFICADO_CONFIRMADO, q);
		return q.getResultList();
	}

	@Override
	public List<PaquetesNotificadoConfirmado> searchPaquetesNotificadosConfirmados(
			String searchString) {
		TypedQuery<PaquetesNotificadoConfirmado> q = em.createQuery(
				NOTIFICADO_CONFIRMADO_SQL_SEARCH,
				PaquetesNotificadoConfirmado.class);
		setQuerySearchParams(searchString,
				PARAMS_SELECT_SEARCH_NOTIFICADO_CONFIRMADO, q);
		return q.getResultList();
	}

	@Override
	public Long countSearchPaquetesNotificadosConfirmados(String searchString) {
		TypedQuery<Long> q = em.createQuery(
				NOTIFICADO_CONFIRMADO_SQL_SEARCH_COUNT, Long.class);
		setQuerySearchParams(searchString,
				PARAMS_SELECT_SEARCH_NOTIFICADO_CONFIRMADO, q);
		return q.getSingleResult();
	}

	@Override
	public List<PaquetesNotificadoConfirmado> findEntries(int firstResult,
			int maxResults) {
		TypedQuery<PaquetesNotificadoConfirmado> q = em.createQuery(
				SELECT_SEARCH + FROM_NOTIFICADO_CONFIRMADO,
				PaquetesNotificadoConfirmado.class);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		System.out.println(q.getResultList().size());
		return q.getResultList();
	}

	@Override
	public List<PaquetesNotificadoConfirmado> findPaquetesNotificadoConfirmadoByUser(
			Long user) {
		TypedQuery<PaquetesNotificadoConfirmado> q = em.createQuery(
				SELECT_SEARCH + FROM_NOTIFICADO_CONFIRMADO
						+ " where p.id = :id",
				PaquetesNotificadoConfirmado.class);
		q.setParameter("id", user);
		return q.getResultList();
	}

	@Override
	public List<Paquete> findPaquetesByCliente(Users user,
			TipoEstatusPaquete estatus) {
		TypedQuery<Paquete> q = em.createQuery(SELECT_SEARCH
				+ FROM_PAQUETE_CLIENTE
				+ " where d.usersId = :user and p.estatus = :estatus",
				Paquete.class);
		q.setParameter("user", user);
		q.setParameter("estatus", estatus);
		return q.getResultList();
	}

	@Override
	public List<Paquete> findPaquetesByCliente(Users user) {
		TypedQuery<Paquete> q = em.createQuery(SELECT_SEARCH
				+ FROM_PAQUETE_CLIENTE + " where d.usersId = :user",
				Paquete.class);
		q.setParameter("user", user);
		return q.getResultList();
	}

	@Override
	public List<DetalleGuiasPaquete> findDetalleGuiasByPaquete(Long paqueteId) {
		TypedQuery<DetalleGuiasPaquete> q = em
				.createQuery(
						"select d from DetalleGuiasPaquete d where d.id.paquete = :paquete",
						DetalleGuiasPaquete.class);
		q.setParameter("paquete", paqueteId);
		// q.setParameter("paquete", "%," + paqueteId.toString() + ",%");
		return q.getResultList();
	}

	@Override
	public List<Paquete> searchPaquetesByCliente(String searchString,
			Users cliente, int firstResult, int maxResults) {
		TypedQuery<Paquete> q = em.createQuery(PAQUETES_SQL_SEARCH_CLIENTE,
				Paquete.class);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		q.setParameter("cliente", cliente);
		setQuerySearchParams(searchString,
				PARAMS_SELECT_SEARCH_PAQUETES_CLIENTE, q);
		return q.getResultList();
	}

	@Override
	public List<Paquete> searchPaquetesByCliente(String searchString,
			Users cliente) {
		TypedQuery<Paquete> q = em.createQuery(PAQUETES_SQL_SEARCH_CLIENTE,
				Paquete.class);
		q.setParameter("cliente", cliente);
		setQuerySearchParams(searchString,
				PARAMS_SELECT_SEARCH_PAQUETES_CLIENTE, q);
		return q.getResultList();
	}

	@Override
	public Long countSearchPaquetesByCliente(String searchString, Users cliente) {
		TypedQuery<Long> q = em.createQuery(PAQUETES_SQL_SEARCH_COUNT_CLIENTE,
				Long.class);
		q.setParameter("cliente", cliente);
		setQuerySearchParams(searchString,
				PARAMS_SELECT_SEARCH_PAQUETES_CLIENTE, q);
		return q.getSingleResult();
	}

	@Override
	public List<Paquete> findPaquetesByCliente(Users user, int firstResult,
			int maxResults) {
		TypedQuery<Paquete> q = em.createQuery(SELECT_SEARCH
				+ FROM_PAQUETE_CLIENTE + " where d.usersId = :user",
				Paquete.class);
		q.setParameter("user", user);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		return q.getResultList();
	}

	@Override
	public Long countPaquetesByCliente(Users cliente) {
		TypedQuery<Long> q = em.createQuery(SELECT_COUNT + FROM_PAQUETE_CLIENTE
				+ " where d.usersId = :cliente", Long.class);
		q.setParameter("cliente", cliente);
		return q.getSingleResult();
	}

	@Override
	public Long countPaquetesNotificadosConfirmado() {
		TypedQuery<Long> q = em.createQuery(SELECT_COUNT
				+ FROM_NOTIFICADO_CONFIRMADO, Long.class);
		System.out.println(q.getSingleResult());
		return q.getSingleResult();
	}

	@Override
	public List<Paquete> searchPaquetes(String searchString,
			TipoEstatusPaquete estatus, int firstResult, int maxResults) {
		TypedQuery<Paquete> q;
		if (StringUtils.containsIgnoreCase(searchString, "c:")) {
			searchString = StringUtils.substringAfter(searchString, ":");
			q = em.createQuery(PAQUETES_SQL_SEARCH_LIKE_CLIENTE_ESTATUS,
					Paquete.class);
			q.setFirstResult(firstResult);
			q.setMaxResults(maxResults);
			setQuerySearchParams(searchString,
					PARAMS_SELECT_SEARCH_PAQUETES_LIKE_CLIENTE, q);
		} else {
			q = em.createQuery(PAQUETES_SQL_SEARCH_ESTATUS, Paquete.class);
			q.setFirstResult(firstResult);
			q.setMaxResults(maxResults);
			setQuerySearchParams(searchString, PARAMS_SELECT_SEARCH_PAQUETES, q);

		}
		q.setParameter("estatus", estatus);
		return q.getResultList();
	}

	@Override
	public List<Paquete> searchPaquetes(String searchString,
			TipoEstatusPaquete estatus) {
		TypedQuery<Paquete> q;
		if (StringUtils.containsIgnoreCase(searchString, "c:")) {
			searchString = StringUtils.substringAfter(searchString, ":");
			q = em.createQuery(PAQUETES_SQL_SEARCH_LIKE_CLIENTE_ESTATUS,
					Paquete.class);
			setQuerySearchParams(searchString,
					PARAMS_SELECT_SEARCH_PAQUETES_LIKE_CLIENTE, q);
		} else {
			q = em.createQuery(PAQUETES_SQL_SEARCH_ESTATUS, Paquete.class);
			setQuerySearchParams(searchString, PARAMS_SELECT_SEARCH_PAQUETES, q);
		}
		q.setParameter("estatus", estatus);
		return q.getResultList();
	}

	@Override
	public Long countSearchPaquetes(String searchString,
			TipoEstatusPaquete estatus) {
		TypedQuery<Long> q;
		if (StringUtils.containsIgnoreCase(searchString, "c:")) {
			searchString = StringUtils.substringAfter(searchString, ":");
			q = em.createQuery(PAQUETES_SQL_SEARCH_LIKE_CLIENTE_ESTATUS_COUNT,
					Long.class);
			setQuerySearchParams(searchString,
					PARAMS_SELECT_SEARCH_PAQUETES_LIKE_CLIENTE, q);
		} else {
			q = em.createQuery(PAQUETES_SQL_SEARCH_ESTATUS_COUNT, Long.class);
			setQuerySearchParams(searchString, PARAMS_SELECT_SEARCH_PAQUETES, q);
		}
		q.setParameter("estatus", estatus);
		return q.getSingleResult();
	}

	@Override
	public List<Paquete> searchPaquetes(TipoEstatusPaquete estatus,
			int firstResult, int maxResults) {
		TypedQuery<Paquete> q;
		q = em.createQuery(PAQUETES_SQL_SEARCH_ONLY_ESTATUS, Paquete.class);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		q.setParameter("estatus", estatus);
		return q.getResultList();
	}

	@Override
	public List<Paquete> searchPaquetes(TipoEstatusPaquete estatus) {
		TypedQuery<Paquete> q;
		q = em.createQuery(PAQUETES_SQL_SEARCH_ONLY_ESTATUS, Paquete.class);
		q.setParameter("estatus", estatus);
		return q.getResultList();
	}

	@Override
	public Long countSearchPaquetes(TipoEstatusPaquete estatus) {
		TypedQuery<Long> q;
		q = em.createQuery(PAQUETES_SQL_SEARCH_ONLY_ESTATUS_COUNT, Long.class);
		q.setParameter("estatus", estatus);
		return q.getSingleResult();
	}

	@Override
	public List<Paquete> searchPaquetes(boolean trackingGenerado,
			String searchString, TipoEstatusPaquete enumEstatus,
			int firstResult, int maxResults) {
		TypedQuery<Paquete> q;
		if (StringUtils.containsIgnoreCase(searchString, "c:")) {
			searchString = StringUtils.substringAfter(searchString, ":");
			q = em.createQuery(
					PAQUETES_SQL_SEARCH_LIKE_CLIENTE_ESTATUS_AND_TRACKING_GENERADO,
					Paquete.class);
			setQuerySearchParams(searchString,
					PARAMS_SELECT_SEARCH_PAQUETES_LIKE_CLIENTE, q);
		} else {
			q = em.createQuery(
					PAQUETES_SQL_SEARCH_ESTATUS_AND_TRACKING_GENERADO,
					Paquete.class);
			setQuerySearchParams(searchString, PARAMS_SELECT_SEARCH_PAQUETES, q);
		}
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		q.setParameter("estatus", enumEstatus);
		q.setParameter("trackingGenerado", trackingGenerado);
		return q.getResultList();
	}

	@Override
	public List<Paquete> searchPaquetes(boolean trackingGenerado,
			String searchString, TipoEstatusPaquete enumEstatus) {
		TypedQuery<Paquete> q;
		if (StringUtils.containsIgnoreCase(searchString, "c:")) {
			searchString = StringUtils.substringAfter(searchString, ":");
			q = em.createQuery(
					PAQUETES_SQL_SEARCH_LIKE_CLIENTE_ESTATUS_AND_TRACKING_GENERADO,
					Paquete.class);
			setQuerySearchParams(searchString,
					PARAMS_SELECT_SEARCH_PAQUETES_LIKE_CLIENTE, q);
		} else {
			q = em.createQuery(
					PAQUETES_SQL_SEARCH_ESTATUS_AND_TRACKING_GENERADO,
					Paquete.class);
			setQuerySearchParams(searchString, PARAMS_SELECT_SEARCH_PAQUETES, q);
		}
		q.setParameter("estatus", enumEstatus);
		q.setParameter("trackingGenerado", trackingGenerado);
		return q.getResultList();
	}

	@Override
	public Long countSearchPaquetes(boolean trackingGenerado,
			String searchString, TipoEstatusPaquete enumEstatus) {
		TypedQuery<Long> q;
		if (StringUtils.containsIgnoreCase(searchString, "c:")) {
			searchString = StringUtils.substringAfter(searchString, ":");
			q = em.createQuery(
					PAQUETES_SQL_SEARCH_LIKE_CLIENTE_ESTATUS_AND_TRACKING_GENERADO_COUNT,
					Long.class);
			setQuerySearchParams(searchString,
					PARAMS_SELECT_SEARCH_PAQUETES_LIKE_CLIENTE, q);
		} else {
			q = em.createQuery(
					PAQUETES_SQL_SEARCH_ESTATUS_AND_TRACKING_GENERADO_COUNT,
					Long.class);
			setQuerySearchParams(searchString, PARAMS_SELECT_SEARCH_PAQUETES, q);
		}
		q.setParameter("estatus", enumEstatus);
		q.setParameter("trackingGenerado", trackingGenerado);
		return q.getSingleResult();
	}

	@Override
	public List<Paquete> searchPaquetes(boolean trackingGenerado,
			String searchString, int firstResult, int maxResults) {
		TypedQuery<Paquete> q;
		if (StringUtils.containsIgnoreCase(searchString, "c:")) {
			searchString = StringUtils.substringAfter(searchString, ":");
			q = em.createQuery(
					PAQUETES_SQL_SEARCH_LIKE_CLIENTE_AND_TRACKING_GENERADO,
					Paquete.class);
			setQuerySearchParams(searchString,
					PARAMS_SELECT_SEARCH_PAQUETES_LIKE_CLIENTE, q);
		} else {
			q = em.createQuery(PAQUETES_SQL_SEARCH_TRACKING_GENERADO,
					Paquete.class);
			setQuerySearchParams(searchString, PARAMS_SELECT_SEARCH_PAQUETES, q);
		}
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		q.setParameter("trackingGenerado", trackingGenerado);
		return q.getResultList();
	}

	@Override
	public List<Paquete> searchPaquetes(boolean trackingGenerado,
			String searchString) {
		TypedQuery<Paquete> q;
		if (StringUtils.containsIgnoreCase(searchString, "c:")) {
			searchString = StringUtils.substringAfter(searchString, ":");
			q = em.createQuery(
					PAQUETES_SQL_SEARCH_LIKE_CLIENTE_AND_TRACKING_GENERADO,
					Paquete.class);
			setQuerySearchParams(searchString,
					PARAMS_SELECT_SEARCH_PAQUETES_LIKE_CLIENTE, q);
		} else {
			q = em.createQuery(PAQUETES_SQL_SEARCH_TRACKING_GENERADO,
					Paquete.class);
			setQuerySearchParams(searchString, PARAMS_SELECT_SEARCH_PAQUETES, q);
		}
		q.setParameter("trackingGenerado", trackingGenerado);
		return q.getResultList();
	}

	@Override
	public Long countSearchPaquetes(boolean trackingGenerado,
			String searchString) {
		TypedQuery<Long> q;
		if (StringUtils.containsIgnoreCase(searchString, "c:")) {
			searchString = StringUtils.substringAfter(searchString, ":");
			q = em.createQuery(
					PAQUETES_SQL_SEARCH_LIKE_CLIENTE_TRACKING_GENERADO_COUNT,
					Long.class);
			setQuerySearchParams(searchString,
					PARAMS_SELECT_SEARCH_PAQUETES_LIKE_CLIENTE, q);
		} else {
			q = em.createQuery(PAQUETES_SQL_SEARCH_TRACKING_GENERADO_COUNT,
					Long.class);
			setQuerySearchParams(searchString, PARAMS_SELECT_SEARCH_PAQUETES, q);
		}
		q.setParameter("trackingGenerado", trackingGenerado);
		return q.getSingleResult();
	}

	@Override
	public List<Paquete> searchPaquetes(boolean trackingGenerado,
			TipoEstatusPaquete enumEstatus, int firstResult, int maxResults) {
		TypedQuery<Paquete> q;
		q = em.createQuery(
				PAQUETES_SQL_SEARCH_ESTATUS_AND_TRACKING_GENERADO_ONLY,
				Paquete.class);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		q.setParameter("trackingGenerado", trackingGenerado);
		q.setParameter("estatus", enumEstatus);
		return q.getResultList();
	}

	@Override
	public List<Paquete> searchPaquetes(boolean trackingGenerado,
			TipoEstatusPaquete enumEstatus) {
		TypedQuery<Paquete> q;
		q = em.createQuery(
				PAQUETES_SQL_SEARCH_ESTATUS_AND_TRACKING_GENERADO_ONLY,
				Paquete.class);
		q.setParameter("trackingGenerado", trackingGenerado);
		q.setParameter("estatus", enumEstatus);
		return q.getResultList();
	}

	@Override
	public Long countSearchPaquetes(boolean trackingGenerado,
			TipoEstatusPaquete enumEstatus) {
		TypedQuery<Long> q;
		q = em.createQuery(
				PAQUETES_SQL_SEARCH_ESTATUS_AND_TRACKING_GENERADO_ONLY_COUNT,
				Long.class);
		q.setParameter("trackingGenerado", trackingGenerado);
		q.setParameter("estatus", enumEstatus);
		return q.getSingleResult();
	}

	@Override
	public List<Paquete> searchPaquetes(boolean trackingGenerado,
			int firstResult, int maxResults) {
		TypedQuery<Paquete> q;
		q = em.createQuery(PAQUETES_SQL_SEARCH_TRACKING_GENERADO_ONLY,
				Paquete.class);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		q.setParameter("trackingGenerado", trackingGenerado);
		return q.getResultList();
	}

	@Override
	public List<Paquete> searchPaquetes(boolean trackingGenerado) {
		TypedQuery<Paquete> q;
		q = em.createQuery(PAQUETES_SQL_SEARCH_TRACKING_GENERADO_ONLY,
				Paquete.class);
		q.setParameter("trackingGenerado", trackingGenerado);
		return q.getResultList();
	}

	@Override
	public Long countSearchPaquetes(boolean trackingGenerado) {
		TypedQuery<Long> q;
		q = em.createQuery(PAQUETES_SQL_SEARCH_TRACKING_GENERADO_ONLY_COUNT,
				Long.class);
		q.setParameter("trackingGenerado", trackingGenerado);
		return q.getSingleResult();
	}

}
