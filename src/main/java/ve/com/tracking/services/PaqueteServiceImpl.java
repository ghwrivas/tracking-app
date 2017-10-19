package ve.com.tracking.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ve.com.tracking.core.TipoEstatusPaquete;
import ve.com.tracking.exceptions.ClientException;
import ve.com.tracking.forms.PackageNotifyForm;
import ve.com.tracking.forms.PackageRecepcionarForm;
import ve.com.tracking.model.DetalleGuiasPaquete;
import ve.com.tracking.model.DetalleNotificacion;
import ve.com.tracking.model.DetalleRecepcion;
import ve.com.tracking.model.Paquete;
import ve.com.tracking.model.PaquetesNotificadoConfirmado;
import ve.com.tracking.model.Users;
import ve.com.tracking.repository.DetalleNotificacionRepository;
import ve.com.tracking.repository.DetalleRecepcionRepository;
import ve.com.tracking.repository.PaqueteRepository;

/**
 * 
 * @author Williams Rivas
 * 
 *         Created 01/04/2014 13:46:33
 */
@Service
@Transactional
public class PaqueteServiceImpl implements PaqueteService {

	@Autowired
	PaqueteRepository paqueteRepository;

	@Autowired
	DetalleNotificacionRepository detalleNotificacionRepository;

	@Autowired
	DetalleRecepcionRepository detalleRecepcionRepository;

	@Autowired
	UsersService userService;

	@Autowired
	EstatusService estatusService;

	public long countAllPaquetes() {
		return paqueteRepository.count();
	}

	public void deletePaquete(Paquete paquete) {
		paqueteRepository.delete(paquete);
	}

	public Paquete findPaquete(Long id) {
		return paqueteRepository.findOne(id);
	}

	public List<Paquete> findAllPaquetes() {
		return paqueteRepository.findAll();
	}

	public List<Paquete> findPaqueteEntries(int firstResult, int maxResults) {
		return paqueteRepository.findAll(
				new org.springframework.data.domain.PageRequest(firstResult
						/ maxResults, maxResults)).getContent();
	}

	public void savePaquete(Paquete paquete) {
		paqueteRepository.save(paquete);
	}

	public Paquete updatePaquete(Paquete paquete) {
		return paqueteRepository.save(paquete);
	}

	@Override
	@Secured({ "ROLE_CLIENT" })
	public Paquete notifyPaquete(PackageNotifyForm notifyForm)
			throws ClientException {
		Paquete paquete = null;
		String tracking = "";
		// Buscar si dicho tracking no ha sido notificado
		if (notifyForm.getHasTracking().equalsIgnoreCase("s")) {
			tracking = notifyForm.getTracking();
			paquete = paqueteRepository.findPaqueteByTracking(tracking);
		}
		Users principal = userService.getPrincipal();
		DetalleNotificacion detalle = new DetalleNotificacion();
		Calendar date = Calendar.getInstance();
		if (paquete == null) { // crear paquete y detalle
			paquete = new Paquete();
			if (notifyForm.getHasTracking().equalsIgnoreCase("n")) {
				tracking = StringUtils.trim(UUID.randomUUID().toString());
				tracking = StringUtils.replace(tracking, "-", "");
				notifyForm.setTracking(tracking);
				paquete.setTrackingGenerado(true);
			} else {
				paquete.setTrackingGenerado(false);
			}
			paquete.setAsegurar(notifyForm.isAsegurar());
			paquete.setTracking(StringUtils.upperCase(tracking));
			paquete.setEstatus(TipoEstatusPaquete.NOTIFICADO);
			paquete.setNotificado(true);
			paquete.setCreated(date);
			paquete.setUpdated(date);
			paqueteRepository.save(paquete);
			detalle.setDescripcion(notifyForm.getDescripcion());
			detalle.setEmpresaEnvioId(notifyForm.getEmpresaEnvioId());
			detalle.setUsersId(principal);
			detalle.setCreated(date);
			detalle.setUpdated(date);
			detalle.setPaqueteId(paquete);
			detalleNotificacionRepository.save(detalle);
			estatusService.registerLogEstatusPaquete(paquete);
		} else { // existe. buscar si ya fue notificado
			detalle = detalleNotificacionRepository.findByPaqueteId(paquete);
			if (detalle == null) { // no notificado, crear detalle
				paquete.setUpdated(date);
				paquete.setNotificado(true);
				estatusService.setNextEstatusPaquete(paquete);
				paqueteRepository.save(paquete);
				detalle = new DetalleNotificacion();
				detalle.setDescripcion(notifyForm.getDescripcion());
				detalle.setEmpresaEnvioId(notifyForm.getEmpresaEnvioId());
				detalle.setUsersId(principal);
				detalle.setCreated(date);
				detalle.setUpdated(date);
				detalle.setPaqueteId(paquete);
				detalleNotificacionRepository.save(detalle);
				estatusService.registerLogEstatusPaquete(paquete);
			} else {// ya notificado
				throw new ClientException("Paquete ya notificado");
			}
		}
		return paquete;
	}

	@Override
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	public Paquete recepcionarPaquete(PackageRecepcionarForm recepcionarForm)
			throws ClientException {
		// Buscar si dicho tracking no ha sido notificado
		String tracking = StringUtils.deleteWhitespace(recepcionarForm
				.getTracking());
		Users principal = userService.getPrincipal();

		Paquete paquete = paqueteRepository.findPaqueteByTracking(tracking);

		DetalleRecepcion detalle = new DetalleRecepcion();
		Calendar date = Calendar.getInstance();

		if (paquete == null) { // crear paquete y detalle
			paquete = new Paquete();
			paquete.setAsegurar(false);
			paquete.setTracking(StringUtils.upperCase(tracking));
			paquete.setEstatus(TipoEstatusPaquete.CONFIRMADO);
			paquete.setRecepcionado(true);
			paquete.setCreated(date);
			paquete.setUpdated(date);
			paqueteRepository.save(paquete);
			detalle.setDescripcion(recepcionarForm.getDescripcion());
			detalle.setEmpresaEnvioId(recepcionarForm.getEmpresaEnvioId());
			detalle.setUsersId(principal);
			detalle.setCreated(date);
			detalle.setUpdated(date);
			detalle.setPaqueteId(paquete);
			detalleRecepcionRepository.save(detalle);
			estatusService.registerLogEstatusPaquete(paquete);
		} else { // existe. buscar si ya fue recepcionado
			detalle = detalleRecepcionRepository.findByPaqueteId(paquete);
			if (detalle == null) { // no recepcionado, crear detalle
				paquete.setUpdated(date);
				paquete.setRecepcionado(true);
				estatusService.setNextEstatusPaquete(paquete);
				paqueteRepository.save(paquete);
				detalle = new DetalleRecepcion();
				detalle.setDescripcion(recepcionarForm.getDescripcion());
				detalle.setEmpresaEnvioId(recepcionarForm.getEmpresaEnvioId());
				detalle.setUsersId(principal);
				detalle.setCreated(date);
				detalle.setUpdated(date);
				detalle.setPaqueteId(paquete);
				detalleRecepcionRepository.save(detalle);
				estatusService.registerLogEstatusPaquete(paquete);
			} else {// ya notificado
				throw new ClientException("Paquete ya recepcionado");
			}
		}
		return paquete;
	}

	@Override
	public Paquete updateNotificationPaquete(PackageNotifyForm notifyForm)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Paquete updateRecepcionPaquete(PackageRecepcionarForm recepcionarForm)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DetalleNotificacion> findAllDetalleNotificationByUsers(
			Users user, int firstResult, int maxResults) {
		return detalleNotificacionRepository.findByUsersId(
				user,
				new org.springframework.data.domain.PageRequest(firstResult
						/ maxResults, maxResults)).getContent();
	}

	@Override
	public Long countAllDetalleNotificationsByUser(Users user) {
		return detalleNotificacionRepository.countByUsersId(user);
	}

	@Override
	public List<DetalleNotificacion> findAllDetalleNotificationByUsers(
			Users user) {
		return detalleNotificacionRepository.findByUsersId(user);
	}

	@Override
	public List<DetalleRecepcion> findAllDetalleRecepcionEntries(
			int firstResult, int maxResults) {
		return detalleRecepcionRepository.findAll(
				new org.springframework.data.domain.PageRequest(firstResult
						/ maxResults, maxResults)).getContent();
	}

	@Override
	public List<DetalleRecepcion> findAllDetalleRecepcions() {
		return detalleRecepcionRepository.findAll();
	}

	@Override
	public Long countAllDetalleRecepcions() {
		return detalleRecepcionRepository.count();
	}

	@Override
	public List<Paquete> searchPaquetes(String searchString, int firstResult,
			int maxResults) {
		return paqueteRepository.searchPaquetes(searchString, firstResult,
				maxResults);
	}

	@Override
	public List<Paquete> searchPaquetes(String searchString) {
		return paqueteRepository.searchPaquetes(searchString);
	}

	@Override
	public Long countSearchPaquetes(String searchString) {
		return paqueteRepository.countSearchPaquetes(searchString);
	}

	@Override
	public List<PaquetesNotificadoConfirmado> findPaquetesNotificadoConfirmado() {
		return paqueteRepository.findAllPaquetesNotificadosConfirmados();
	}

	@Override
	public List<PaquetesNotificadoConfirmado> searchPaquetesNotificadoConfirmado(
			String searchString, int firstResult, int maxResults) {
		return paqueteRepository.searchPaquetesNotificadosConfirmados(
				searchString, firstResult, maxResults);
	}

	@Override
	public List<PaquetesNotificadoConfirmado> searchPaquetesNotificadoConfirmado(
			String searchString) {
		return paqueteRepository
				.searchPaquetesNotificadosConfirmados(searchString);
	}

	@Override
	public Long countSearchPaquetesNotificadoConfirmado(String searchString) {
		return paqueteRepository
				.countSearchPaquetesNotificadosConfirmados(searchString);
	}

	@Override
	public List<PaquetesNotificadoConfirmado> findEntries(int firstResult,
			int maxResults) {
		return paqueteRepository.findEntries(firstResult, maxResults);
	}

	@Override
	public List<Paquete> findPaquetesByCliente(Users cliente,
			TipoEstatusPaquete estatus) {
		return paqueteRepository.findPaquetesByCliente(cliente, estatus);
	}

	@Override
	public List<Paquete> findPaquetesByCliente(Users cliente) {
		return paqueteRepository.findPaquetesByCliente(cliente);
	}

	@Override
	public Paquete findPaqueteByTracking(String tracking) {
		return paqueteRepository.findPaqueteByTracking(tracking);
	}

	@Override
	public List<DetalleGuiasPaquete> findAllDetallesGuiasPaquete(Paquete paquete) {
		List<DetalleGuiasPaquete> detalles = new ArrayList<DetalleGuiasPaquete>();
		if (paquete == null)
			return detalles;
		return paqueteRepository.findDetalleGuiasByPaquete(paquete.getId());
	}

	@Override
	public List<Paquete> searchPaquetesByCliente(String searchString,
			Users cliente, int firstResult, int maxResults) {
		return paqueteRepository.searchPaquetesByCliente(searchString, cliente,
				firstResult, maxResults);
	}

	@Override
	public List<Paquete> searchPaquetesByCliente(String searchString,
			Users cliente) {
		return paqueteRepository.searchPaquetesByCliente(searchString, cliente);
	}

	@Override
	public Long countSearchPaquetesByCliente(String searchString, Users cliente) {
		return paqueteRepository.countSearchPaquetesByCliente(searchString,
				cliente);
	}

	@Override
	public List<Paquete> findPaquetesByCliente(Users cliente, int firstResult,
			int maxResults) {
		return paqueteRepository.findPaquetesByCliente(cliente, firstResult,
				maxResults);
	}

	@Override
	public Long countPaquetesByCliente(Users cliente) {
		return paqueteRepository.countPaquetesByCliente(cliente);
	}

	@Override
	public Long countPaquetesNotificadoConfirmado() {
		return paqueteRepository.countPaquetesNotificadosConfirmado();
	}

	@Override
	public List<Paquete> searchPaquetes(String searchString,
			TipoEstatusPaquete estatus, int firstResult, int maxResults) {
		return paqueteRepository.searchPaquetes(searchString, estatus,
				firstResult, maxResults);
	}

	@Override
	public List<Paquete> searchPaquetes(String searchString,
			TipoEstatusPaquete estatus) {
		return paqueteRepository.searchPaquetes(searchString, estatus);
	}

	@Override
	public Long countSearchPaquetes(String searchString,
			TipoEstatusPaquete estatus) {
		return paqueteRepository.countSearchPaquetes(searchString, estatus);
	}

	@Override
	public List<Paquete> searchPaquetes(TipoEstatusPaquete estatus,
			int firstResult, int maxResults) {
		return paqueteRepository.searchPaquetes(estatus, firstResult,
				maxResults);
	}

	@Override
	public List<Paquete> searchPaquetes(TipoEstatusPaquete estatus) {
		return paqueteRepository.searchPaquetes(estatus);
	}

	@Override
	public Long countSearchPaquetes(TipoEstatusPaquete estatus) {
		return paqueteRepository.countSearchPaquetes(estatus);
	}

	@Override
	public List<Paquete> searchPaquetes(boolean trackingGenerado,
			String searchString, TipoEstatusPaquete enumEstatus,
			int firstResult, int sizeNo) {
		return paqueteRepository.searchPaquetes(trackingGenerado, searchString,
				enumEstatus, firstResult, sizeNo);
	}

	@Override
	public Long countSearchPaquetes(boolean trackingGenerado,
			String searchString, TipoEstatusPaquete enumEstatus) {
		return paqueteRepository.countSearchPaquetes(trackingGenerado,
				searchString, enumEstatus);
	}

	@Override
	public List<Paquete> searchPaquetes(boolean trackingGenerado,
			String searchString, int firstResult, int sizeNo) {
		return paqueteRepository.searchPaquetes(trackingGenerado, searchString,
				firstResult, sizeNo);
	}

	@Override
	public Long countSearchPaquetes(boolean trackingGenerado,
			String searchString) {
		return paqueteRepository.countSearchPaquetes(trackingGenerado,
				searchString);
	}

	@Override
	public List<Paquete> searchPaquetes(boolean trackingGenerado,
			TipoEstatusPaquete enumEstatus, int firstResult, int sizeNo) {
		return paqueteRepository.searchPaquetes(trackingGenerado, enumEstatus,
				firstResult, sizeNo);
	}

	@Override
	public Long countSearchPaquetes(boolean trackingGenerado,
			TipoEstatusPaquete enumEstatus) {
		return paqueteRepository.countSearchPaquetes(trackingGenerado,
				enumEstatus);
	}

	@Override
	public List<Paquete> searchPaquetes(boolean trackingGenerado,
			int firstResult, int sizeNo) {
		return paqueteRepository.searchPaquetes(trackingGenerado, firstResult,
				sizeNo);
	}

	@Override
	public Long countSearchPaquetes(boolean trackingGenerado) {
		return paqueteRepository.countSearchPaquetes(trackingGenerado);
	}

}
