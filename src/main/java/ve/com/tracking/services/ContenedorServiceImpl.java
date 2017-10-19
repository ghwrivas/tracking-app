package ve.com.tracking.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ve.com.tracking.core.TipoEstatusContenedor;
import ve.com.tracking.core.TipoEstatusGuia;
import ve.com.tracking.exceptions.ContenedorServiceException;
import ve.com.tracking.model.CodigoGuia;
import ve.com.tracking.model.Contenedor;
import ve.com.tracking.model.CodigoGuiaContenedor;
import ve.com.tracking.repository.CodigoGuiaRepository;
import ve.com.tracking.repository.ContenedorRepository;
import ve.com.tracking.repository.GuiaContenedorRepository;
import ve.com.tracking.uidgen.UIDSequencerManager;

@Service
@Transactional
public class ContenedorServiceImpl implements ContenedorService {

	@Autowired
	ContenedorRepository contenedorRepository;

	@Autowired
	private GuiaContenedorRepository guiaContenedorRepository;

	@Autowired
	private GuiaService guiaService;

	@Autowired
	private EstatusService estatusService;

	@Autowired
	private UIDSequencerManager uidService;

	@Autowired
	private CodigoGuiaRepository codigoGuiaRepository;

	public long countAllContenedors() {
		return contenedorRepository.count();
	}

	public void deleteContenedor(Contenedor contenedor) {
		if (contenedor == null || contenedor.getId() == null) {
			throw new IllegalArgumentException("Argumento Invalido");
		}
		if (contenedor.getEstatus() != TipoEstatusContenedor.CREADO) {
			throw new ContenedorServiceException(
					"Para poder eliminar un contenedor, su estatus debe ser: "
							+ TipoEstatusContenedor.CREADO);
		}
		List<CodigoGuia> codigoGuias = findAllGuiasContenedor(contenedor);
		for (CodigoGuia guia : codigoGuias) {
			guia.setEstatus(TipoEstatusGuia.CREADO);
			codigoGuiaRepository.save(guia);
			estatusService.registerLogEstatusGuia(guia);
		}
		contenedorRepository.delete(contenedor);
	}

	public Contenedor findContenedor(Long id) {
		return contenedorRepository.findOne(id);
	}

	public List<Contenedor> findAllContenedors() {
		return contenedorRepository.findAll();
	}

	public List<Contenedor> findContenedorEntries(int firstResult,
			int maxResults) {
		return contenedorRepository.findAll(
				new org.springframework.data.domain.PageRequest(firstResult
						/ maxResults, maxResults)).getContent();
	}

	/**
	 * Crea un contendor y define el estatus a CREADO
	 */
	public void saveContenedor(Contenedor contenedor) {
		boolean isNew = (contenedor.getId() == null ? true : false);
		Calendar c = Calendar.getInstance();
		contenedor.setUpdated(c);
		if (isNew) {
			contenedor.setEstatus(TipoEstatusContenedor.CREADO);
			contenedor.setCodigo(uidService.getNextCodigoContenedor());
			contenedor.setCreated(c);
		}
		contenedorRepository.save(contenedor);
		if (isNew) {
			estatusService.registerLogEstatusContenedor(contenedor);
		}
		saveGuiasWebflowContenedor(contenedor);
	}

	/**
	 * Actualiza las propiedades del contenedor sin modificar el estatus
	 */
	public Contenedor updateContenedor(Contenedor contenedor) {
		contenedor.setUpdated(Calendar.getInstance());
		saveGuiasWebflowContenedor(contenedor);
		return contenedorRepository.save(contenedor);
	}

	private void saveGuiasWebflowContenedor(Contenedor contenedor) {
		List<CodigoGuia> guias = contenedor.getGuias();
		if (guias != null && guias.size() > 0) {
			for (CodigoGuia guia : guias) {
				CodigoGuiaContenedor guiaContenedor = guiaContenedorRepository
						.findByCodigoGuiaId(guia);
				if (guiaContenedor == null) {
					guiaContenedor = new CodigoGuiaContenedor();
					guiaContenedor.setContenedorId(contenedor);
					guiaContenedor.setGuiaId(guia);
					guiaContenedorRepository.save(guiaContenedor);

					guia.setEstatus(TipoEstatusGuia.AGREGADO_AL_CONTENEDOR);

					codigoGuiaRepository.save(guia);

					estatusService.registerLogEstatusGuia(guia);
				}
			}
		}
	}

	@Override
	public Contenedor enviarContenedor(Contenedor contenedor)
			throws ContenedorServiceException {
		if (contenedor == null) {
			throw new ContenedorServiceException(
					"Parametro invalido al intentar enviar un contenedor");
		}
		if (contenedor.getId() == null) {
			throw new ContenedorServiceException(
					"El contenedor a enviar no ha sido persistido en la bd");
		}
		if (contenedor.getEstatus() != TipoEstatusContenedor.CREADO) {
			throw new ContenedorServiceException(
					"El contenedor debe tener estatus CREADO para poder ser enviado");
		}
		List<CodigoGuia> guias = findAllGuiasContenedor(contenedor);
		if (guias == null || guias.size() < 1) {
			throw new ContenedorServiceException(
					"No puede enviar un contenedor vacío");
		}
		contenedor.setEstatus(TipoEstatusContenedor.EN_TRANSITO_PAIS_DESTINO);
		contenedor.setUpdated(Calendar.getInstance());

		for (CodigoGuia guia : guias) {
			guia.setEstatus(TipoEstatusGuia.EN_TRANSITO_PAIS_DESTINO);
			codigoGuiaRepository.save(guia);
			estatusService.registerLogEstatusGuia(guia);
		}

		Contenedor contenedorUpdated = contenedorRepository.save(contenedor);
		estatusService.registerLogEstatusContenedor(contenedorUpdated);
		return contenedorUpdated;
	}

	@Override
	public Contenedor confirmarEnPaisDestino(Contenedor contenedor)
			throws ContenedorServiceException {
		if (contenedor == null) {
			throw new ContenedorServiceException(
					"Parametro invalido contenedor: " + contenedor);
		}
		if (contenedor.getId() == null) {
			throw new ContenedorServiceException(
					"El contenedor a confirmar en país destino no ha sido persistido en la base de datos");
		}
		TipoEstatusContenedor nextEstatus = estatusService
				.getNextEstatusContenedor(contenedor);
		if (nextEstatus != TipoEstatusContenedor.CONFIRMADO_PAIS_DESTINO) {
			throw new ContenedorServiceException(
					"El contenedor no puede ser confirmado en el país destino porque su estatus actual es: "
							+ contenedor.getEstatus());
		}
		contenedor.setEstatus(nextEstatus);
		contenedor.setUpdated(Calendar.getInstance());
		Contenedor contenedorUpdated = contenedorRepository.save(contenedor);
		estatusService.registerLogEstatusContenedor(contenedorUpdated);
		return contenedorUpdated;
	}

	@Override
	public Contenedor confirmarEnAlmacen(Contenedor contenedor)
			throws ContenedorServiceException {
		if (contenedor == null) {
			throw new ContenedorServiceException(
					"Parametro invalido contenedor: " + contenedor);
		}
		if (contenedor.getId() == null) {
			throw new ContenedorServiceException(
					"El contenedor a confirmar en país destino no ha sido persistido en la base de datos");
		}
		TipoEstatusContenedor nextEstatus = estatusService
				.getNextEstatusContenedor(contenedor);
		if (nextEstatus != TipoEstatusContenedor.CONFIRMADO_ALMACEN_DESTINO) {
			throw new ContenedorServiceException(
					"El contenedor no puede ser confirmado en el almacén destino porque su estatus actual es: "
							+ contenedor.getEstatus());
		}
		contenedor.setEstatus(nextEstatus);
		contenedor.setUpdated(Calendar.getInstance());

		Contenedor contenedorUpdated = contenedorRepository.save(contenedor);
		estatusService.registerLogEstatusContenedor(contenedorUpdated);

		return contenedorUpdated;
	}

	@Override
	public Contenedor confirmarGuiasContenedor(Contenedor contenedor,
			List<Long> guiasIds) throws ContenedorServiceException {
		Contenedor contenedorUpdated = contenedor;
		Calendar dateUpdate = Calendar.getInstance();
		if (contenedor == null) {
			throw new ContenedorServiceException(
					"Parametro invalido contenedor: " + contenedor);
		}
		if (contenedor.getId() == null) {
			throw new ContenedorServiceException(
					"El contenedor a confirmar en país destino no ha sido persistido en la base de datos");
		}
		TipoEstatusContenedor nextEstatus = estatusService
				.getNextEstatusContenedor(contenedor);
		if (nextEstatus != null) {
			if (nextEstatus != TipoEstatusContenedor.CONFIRMADO_ALMACEN_DESTINO) {
				throw new ContenedorServiceException(
						"El contenedor no puede ser confirmado en el almacén destino porque su estatus actual es: "
								+ contenedor.getEstatus());
			} else {
				contenedor.setEstatus(nextEstatus);
				contenedor.setUpdated(dateUpdate);
				contenedorUpdated = contenedorRepository.save(contenedor);
				estatusService.registerLogEstatusContenedor(contenedorUpdated);
			}
		}
		processGuias(contenedorUpdated, guiasIds);
		return contenedorUpdated;
	}

	private Contenedor processGuias(Contenedor contenedor,
			List<Long> codigoGuiasIds) {
		if (codigoGuiasIds != null) {
			// confirmar guias
			for (Long codigoGuiaId : codigoGuiasIds) {
				CodigoGuia guia = codigoGuiaRepository.findOne(codigoGuiaId);
				if (guia != null) {
					TipoEstatusGuia nextEstatusGuia = estatusService
							.getNextEstatusGuia(guia);
					if (nextEstatusGuia != null
							&& nextEstatusGuia == TipoEstatusGuia.CONFIRMADO_ALMACEN_DESTINO) {
						guia.setEstatus(TipoEstatusGuia.CONFIRMADO_ALMACEN_DESTINO);

						guia = codigoGuiaRepository.save(guia);

						estatusService.registerLogEstatusGuia(guia);
					}

				}
			}
		}
		return contenedor;
	}

	@Override
	public List<CodigoGuia> findAllGuiasContenedor(Contenedor contenedor)
			throws ContenedorServiceException {
		List<CodigoGuia> guias = new ArrayList<CodigoGuia>();
		List<CodigoGuiaContenedor> guiasContenedor = guiaContenedorRepository
				.findByContenedorId(contenedor);
		if (guiasContenedor != null && guiasContenedor.size() > 0) {
			for (CodigoGuiaContenedor guiaContenedor : guiasContenedor) {
				guias.add(guiaContenedor.getGuiaId());
			}
		}
		return guias;
	}

	@Override
	public void deleteGuiaContenedor(CodigoGuiaContenedor guiaContenedor) {
		CodigoGuia guia = guiaContenedor.getGuiaId();
		guiaContenedorRepository.delete(guiaContenedor);
		if (guia.getEstatus() != TipoEstatusGuia.AGREGADA_RECIBO_ALMACEN) {
			guia.setEstatus(TipoEstatusGuia.AGREGADA_RECIBO_ALMACEN);
			guia = codigoGuiaRepository.save(guia);
			estatusService.registerLogEstatusGuia(guia);
		}
	}

	@Override
	public List<Contenedor> searchContenedores(String searchString,
			int firstResult, int maxResults) {
		return contenedorRepository.searchContenedores(searchString,
				firstResult, maxResults);
	}

	@Override
	public List<Contenedor> searchContenedores(String searchString) {
		return contenedorRepository.searchContenedores(searchString);
	}

	@Override
	public Long countSearchContenedores(String searchString) {
		return contenedorRepository.countSearchContenedores(searchString);
	}

	@Override
	public List<Contenedor> searchContenedores(String searchString,
			TipoEstatusContenedor enumEstatus, int firstResult, int sizeNo) {
		return contenedorRepository.searchContenedores(searchString,
				enumEstatus, firstResult, sizeNo);
	}

	@Override
	public Long countSearchContenedores(String searchString,
			TipoEstatusContenedor enumEstatus) {
		return contenedorRepository.countSearchContenedores(searchString,
				enumEstatus);
	}

	@Override
	public List<Contenedor> searchContenedores(
			TipoEstatusContenedor enumEstatus, int firstResult, int sizeNo) {
		return contenedorRepository.searchContenedores(enumEstatus,
				firstResult, sizeNo);
	}

	@Override
	public Long countSearchContenedores(TipoEstatusContenedor enumEstatus) {
		return contenedorRepository.countSearchContenedores(enumEstatus);
	}

}
