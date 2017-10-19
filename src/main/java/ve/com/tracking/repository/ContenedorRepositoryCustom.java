package ve.com.tracking.repository;

import java.util.List;

import ve.com.tracking.core.TipoEstatusContenedor;
import ve.com.tracking.model.Contenedor;

/**
 * 
 * @author Williams Rivas
 * 
 *         Created 21/07/2014 05:13:31
 */
public interface ContenedorRepositoryCustom {

	public List<Contenedor> searchContenedores(String searchString,
			int firstResult, int maxResults);

	public List<Contenedor> searchContenedores(String searchString);

	public Long countSearchContenedores(String searchString);

	public List<Contenedor> searchContenedores(String searchString,
			TipoEstatusContenedor estatus, int firstResult, int maxResults);

	public List<Contenedor> searchContenedores(String searchString,
			TipoEstatusContenedor estatus);

	public Long countSearchContenedores(String searchString,
			TipoEstatusContenedor estatus);

	public List<Contenedor> searchContenedores(TipoEstatusContenedor estatus,
			int firstResult, int maxResults);

	public List<Contenedor> searchContenedores(TipoEstatusContenedor estatus);

	public Long countSearchContenedores(TipoEstatusContenedor estatus);
}
