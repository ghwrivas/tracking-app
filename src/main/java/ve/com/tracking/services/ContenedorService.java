package ve.com.tracking.services;

import java.util.List;

import ve.com.tracking.core.TipoEstatusContenedor;
import ve.com.tracking.exceptions.ContenedorServiceException;
import ve.com.tracking.model.CodigoGuia;
import ve.com.tracking.model.Contenedor;
import ve.com.tracking.model.CodigoGuiaContenedor;

public interface ContenedorService {

	public abstract long countAllContenedors();

	public abstract void deleteContenedor(Contenedor contenedor);

	public abstract Contenedor findContenedor(Long id);

	public abstract List<Contenedor> findAllContenedors();

	public abstract List<Contenedor> findContenedorEntries(int firstResult,
			int maxResults);

	public abstract void saveContenedor(Contenedor contenedor);

	public abstract Contenedor updateContenedor(Contenedor contenedor);

	public abstract Contenedor enviarContenedor(Contenedor contenedor)
			throws ContenedorServiceException;

	public abstract Contenedor confirmarEnPaisDestino(Contenedor contenedor)
			throws ContenedorServiceException;

	public abstract Contenedor confirmarEnAlmacen(Contenedor contenedor)
			throws ContenedorServiceException;

	public abstract Contenedor confirmarGuiasContenedor(Contenedor contenedor,
			List<Long> guiasIds) throws ContenedorServiceException;

	public abstract List<CodigoGuia> findAllGuiasContenedor(
			Contenedor contenedor) throws ContenedorServiceException;

	public abstract void deleteGuiaContenedor(
			CodigoGuiaContenedor guiaContenedor);

	public List<Contenedor> searchContenedores(String searchString,
			int firstResult, int maxResults);

	public List<Contenedor> searchContenedores(String searchString);

	public Long countSearchContenedores(String searchString);

	public abstract List<Contenedor> searchContenedores(String searchString,
			TipoEstatusContenedor enumEstatus, int firstResult, int sizeNo);

	public abstract Long countSearchContenedores(String searchString,
			TipoEstatusContenedor enumEstatus);

	public abstract List<Contenedor> searchContenedores(
			TipoEstatusContenedor enumEstatus, int firstResult, int sizeNo);

	public abstract Long countSearchContenedores(
			TipoEstatusContenedor enumEstatus);

}
