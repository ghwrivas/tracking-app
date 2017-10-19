package ve.com.tracking.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ve.com.tracking.core.TipoEstatusContenedor;
import ve.com.tracking.core.TipoEstatusGuia;
import ve.com.tracking.core.TipoEstatusPaquete;
import ve.com.tracking.exceptions.EstatusServiceException;
import ve.com.tracking.model.CodigoGuia;
import ve.com.tracking.model.Contenedor;
import ve.com.tracking.model.EstatusContenedor;
import ve.com.tracking.model.EstatusGuia;
import ve.com.tracking.model.EstatusPaquete;
import ve.com.tracking.model.Paquete;
import ve.com.tracking.repository.EstatusContenedorRepository;
import ve.com.tracking.repository.EstatusGuiaRepository;
import ve.com.tracking.repository.EstatusPaqueteRepository;

/**
 * 
 * @author Williams Rivas
 * 
 *         Created 20/03/2014 00:30:45
 */
@Service
@Transactional
public class EstatusServiceImpl implements EstatusService {

	@Autowired
	private EstatusContenedorRepository estatusContenedorRepository;

	@Autowired
	private EstatusPaqueteRepository estatusPaqueteRepository;

	@Autowired
	private EstatusGuiaRepository estatusGuiaRepository;

	@Autowired
	private UsersService userService;
	
	@Autowired
	private MailService mailService;

	@Override
	public void setNextEstatusPaquete(Paquete paquete)
			throws EstatusServiceException {
		TipoEstatusPaquete estatus = paquete.getEstatus();
		switch (estatus) {
		case NOTIFICADO:
			paquete.setEstatus(TipoEstatusPaquete.NOTIFICADO_CONFIRMADO);
			break;
		case CONFIRMADO:
			paquete.setEstatus(TipoEstatusPaquete.NOTIFICADO_CONFIRMADO);
			break;
		case NOTIFICADO_CONFIRMADO:
			paquete.setEstatus(TipoEstatusPaquete.PROCESADO);
			break;
		default:
			break;
		}
	}

	@Override
	public void setNextEstatusContenedor(Contenedor contenedor)
			throws EstatusServiceException {
		TipoEstatusContenedor estatus = contenedor.getEstatus();
		switch (estatus) {
		case CREADO:
			contenedor
					.setEstatus(TipoEstatusContenedor.EN_TRANSITO_PAIS_DESTINO);
			break;
		case EN_TRANSITO_PAIS_DESTINO:
			contenedor
					.setEstatus(TipoEstatusContenedor.CONFIRMADO_PAIS_DESTINO);
			break;
		case CONFIRMADO_PAIS_DESTINO:
			contenedor
					.setEstatus(TipoEstatusContenedor.CONFIRMADO_ALMACEN_DESTINO);
			break;
		default:
			break;
		}
	}

	@Override
	public void setNextEstatusGuia(CodigoGuia guia)
			throws EstatusServiceException {
		TipoEstatusGuia estatus = guia.getEstatus();
		switch (estatus) {
		case CREADO:
			guia.setEstatus(TipoEstatusGuia.AGREGADO_AL_CONTENEDOR);
			break;
		case AGREGADO_AL_CONTENEDOR:
			guia.setEstatus(TipoEstatusGuia.EN_TRANSITO_PAIS_DESTINO);
			break;
		case EN_TRANSITO_PAIS_DESTINO:
			guia.setEstatus(TipoEstatusGuia.CONFIRMADO_ALMACEN_DESTINO);
			break;
		case CONFIRMADO_ALMACEN_DESTINO:
			guia.setEstatus(TipoEstatusGuia.ENTREGADO_CLIENTE);
			break;
		default:
			break;
		}
	}

	@Override
	public void registerLogEstatusPaquete(Paquete paquete)
			throws EstatusServiceException {
		EstatusPaquete estatus = new EstatusPaquete();
		estatus.setCreated(Calendar.getInstance());
		estatus.setEstatus(paquete.getEstatus().name());
		estatus.setPaqueteId(paquete);
		estatus.setUsersId(userService.getPrincipal());
		estatusPaqueteRepository.saveAndFlush(estatus);
	}

	@Override
	public void registerLogEstatusContenedor(Contenedor contenedor)
			throws EstatusServiceException {
		EstatusContenedor estatus = new EstatusContenedor();
		estatus.setCreated(Calendar.getInstance());
		estatus.setEstatus(contenedor.getEstatus().name());
		estatus.setContenedorId(contenedor);
		estatus.setUsersId(userService.getPrincipal());
		estatusContenedorRepository.saveAndFlush(estatus);

	}

	@Override
	public void registerLogEstatusGuia(CodigoGuia guia)
			throws EstatusServiceException {
		EstatusGuia estatus = new EstatusGuia();
		estatus.setCreated(Calendar.getInstance());
		estatus.setEstatus(guia.getEstatus().name());
		estatus.setGuiaId(guia);
		estatus.setUsersId(userService.getPrincipal());
		estatusGuiaRepository.saveAndFlush(estatus);
		
		/*try {
			EmailTemplateChangeStatus template = new EmailTemplateChangeStatus(guia.getGuiaId().getCliente().getNombreCompleto(), guia.getCodigo(),
					guia.getEstatus().getNombre(), getUrlGuia(guia.getCodigo()));
			mailService.sendEmail(guia.getGuiaId().getCliente().getEmail().trim(), "Notificación de cambio de estatus",
					template);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	@Override
	public TipoEstatusContenedor getNextEstatusContenedor(Contenedor contenedor)
			throws EstatusServiceException {
		TipoEstatusContenedor estatusActual = contenedor.getEstatus();
		if (estatusActual == TipoEstatusContenedor.CONFIRMADO_ALMACEN_DESTINO) {
			return null;
		}
		TipoEstatusContenedor estatusNuevo = null;
		switch (estatusActual) {
		case CREADO:
			estatusNuevo = TipoEstatusContenedor.EN_TRANSITO_PAIS_DESTINO;
			break;
		case EN_TRANSITO_PAIS_DESTINO:
			estatusNuevo = TipoEstatusContenedor.CONFIRMADO_PAIS_DESTINO;
			break;
		case CONFIRMADO_PAIS_DESTINO:
			estatusNuevo = TipoEstatusContenedor.CONFIRMADO_ALMACEN_DESTINO;
			break;
		default:
			break;
		}
		return estatusNuevo;
	}

	@Override
	public TipoEstatusGuia getNextEstatusGuia(CodigoGuia guia)
			throws EstatusServiceException {
		TipoEstatusGuia estatusActual = guia.getEstatus();
		if (estatusActual == TipoEstatusGuia.ENTREGADO_CLIENTE) {
			return null;
		}
		TipoEstatusGuia estatusNuevo = null;
		switch (estatusActual) {
		case CREADO:
			estatusNuevo = TipoEstatusGuia.AGREGADA_RECIBO_ALMACEN;
			break;
		case AGREGADA_RECIBO_ALMACEN:
			estatusNuevo = TipoEstatusGuia.AGREGADO_AL_CONTENEDOR;
			break;
		case AGREGADO_AL_CONTENEDOR:
			estatusNuevo = TipoEstatusGuia.EN_TRANSITO_PAIS_DESTINO;
			break;
		case EN_TRANSITO_PAIS_DESTINO:
			estatusNuevo = TipoEstatusGuia.CONFIRMADO_ALMACEN_DESTINO;
			break;
		case CONFIRMADO_ALMACEN_DESTINO:
			estatusNuevo = TipoEstatusGuia.ENTREGADO_CLIENTE;
			break;
		default:
			break;
		}
		return estatusNuevo;
	}

	@Override
	public TipoEstatusPaquete getNextEstatusPaquete(Paquete paquete)
			throws EstatusServiceException {
		TipoEstatusPaquete estatusActual = paquete.getEstatus();
		if (estatusActual == TipoEstatusPaquete.PROCESADO) {
			return null;
		}
		TipoEstatusPaquete estatusNuevo = null;
		switch (estatusActual) {
		case NOTIFICADO:
			estatusNuevo = TipoEstatusPaquete.NOTIFICADO_CONFIRMADO;
			break;
		case CONFIRMADO:
			estatusNuevo = TipoEstatusPaquete.NOTIFICADO_CONFIRMADO;
			break;
		case NOTIFICADO_CONFIRMADO:
			estatusNuevo = TipoEstatusPaquete.PROCESADO;
			break;
		default:
			break;
		}
		return estatusNuevo;
	}

	@Override
	public boolean hasNextEstatusPaquete(TipoEstatusPaquete tipoEstatus)
			throws EstatusServiceException {
		if (tipoEstatus == null) {
			throw new EstatusServiceException(
					"El tipo de estatus pasado como parametro es inválido: "
							+ tipoEstatus);
		}
		return tipoEstatus != TipoEstatusPaquete.PROCESADO;
	}

	@Override
	public boolean hasNextEstatusContenedor(TipoEstatusContenedor tipoEstatus)
			throws EstatusServiceException {
		if (tipoEstatus == null) {
			throw new EstatusServiceException(
					"El tipo de estatus pasado como parametro es inválido: "
							+ tipoEstatus);
		}
		return tipoEstatus != TipoEstatusContenedor.CONFIRMADO_ALMACEN_DESTINO;
	}

	@Override
	public boolean hasNextEstatusGuia(TipoEstatusGuia tipoEstatus)
			throws EstatusServiceException {
		if (tipoEstatus == null) {
			throw new EstatusServiceException(
					"El tipo de estatus pasado como parametro es inválido: "
							+ tipoEstatus);
		}
		return tipoEstatus != TipoEstatusGuia.ENTREGADO_CLIENTE;
	}

	@Override
	public TipoEstatusGuia getPreviousEstatusGuia(CodigoGuia guia) {
		TipoEstatusGuia estatusActual = guia.getEstatus();
		if (estatusActual == TipoEstatusGuia.CREADO) {
			return null;
		}
		TipoEstatusGuia estatusPrevio = null;
		switch (estatusActual) {
		case ENTREGADO_CLIENTE:
			estatusPrevio = TipoEstatusGuia.CONFIRMADO_ALMACEN_DESTINO;
			break;
		case CONFIRMADO_ALMACEN_DESTINO:
			estatusPrevio = TipoEstatusGuia.EN_TRANSITO_PAIS_DESTINO;
			break;
		case EN_TRANSITO_PAIS_DESTINO:
			estatusPrevio = TipoEstatusGuia.AGREGADO_AL_CONTENEDOR;
			break;
		case AGREGADO_AL_CONTENEDOR:
			estatusPrevio = TipoEstatusGuia.AGREGADA_RECIBO_ALMACEN;
			break;
		case AGREGADA_RECIBO_ALMACEN:
			estatusPrevio = TipoEstatusGuia.CREADO;
			break;
		default:
			break;
		}
		return estatusPrevio;
	}

	@Override
	public TipoEstatusContenedor getPreviousEstatusContenedor(
			Contenedor contenedor) {
		TipoEstatusContenedor estatusActual = contenedor.getEstatus();
		if (estatusActual == TipoEstatusContenedor.CREADO) {
			return null;
		}
		TipoEstatusContenedor estatusPrevio = null;
		switch (estatusActual) {
		case CONFIRMADO_ALMACEN_DESTINO:
			estatusPrevio = TipoEstatusContenedor.CONFIRMADO_PAIS_DESTINO;
			break;
		case CONFIRMADO_PAIS_DESTINO:
			estatusPrevio = TipoEstatusContenedor.EN_TRANSITO_PAIS_DESTINO;
			break;
		case EN_TRANSITO_PAIS_DESTINO:
			estatusPrevio = TipoEstatusContenedor.CREADO;
			break;
		default:
			break;
		}
		return estatusPrevio;
	}

	@Override
	public List<EstatusPaquete> findAllEstatusPaquete(Paquete paquete) {
		return estatusPaqueteRepository.findByPaqueteId(paquete);
	}

	@Override
	public List<String> getAllEstatusPaquete() {
		TipoEstatusPaquete[] allEstatusPaquetes = TipoEstatusPaquete.values();
		List<String> allEstatus = new ArrayList<String>();
		for (TipoEstatusPaquete tipoEstatusPaquete : allEstatusPaquetes) {
			allEstatus.add(tipoEstatusPaquete.name());
		}
		return allEstatus;
	}

	@Override
	public List<String> getAllEstatusGuia() {
		TipoEstatusGuia[] allEstatusGuia = TipoEstatusGuia.values();
		List<String> allEstatus = new ArrayList<String>();
		for (TipoEstatusGuia tipoEstatusGuia : allEstatusGuia) {
			allEstatus.add(tipoEstatusGuia.name());
		}
		return allEstatus;
	}

	@Override
	public List<String> getAllEstatusContenedor() {
		TipoEstatusContenedor[] allEstatusContenedor = TipoEstatusContenedor
				.values();
		List<String> allEstatus = new ArrayList<String>();
		for (TipoEstatusContenedor tipoEstatusContenedor : allEstatusContenedor) {
			allEstatus.add(tipoEstatusContenedor.name());
		}
		return allEstatus;
	}

	@Override
	public List<EstatusGuia> findAllEstatusGuia(CodigoGuia guia) {
		return estatusGuiaRepository.findByCodigoGuiaId(guia);
	}

}
