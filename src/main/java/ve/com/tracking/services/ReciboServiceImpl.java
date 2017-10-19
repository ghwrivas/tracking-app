package ve.com.tracking.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ve.com.tracking.core.TipoEstatusPaquete;
import ve.com.tracking.core.TipoEstatusRecibo;
import ve.com.tracking.forms.GuiaForm;
import ve.com.tracking.forms.ItemForm;
import ve.com.tracking.forms.ReciboAlmacenForm;
import ve.com.tracking.model.CodigoGuia;
import ve.com.tracking.model.DetalleGuia;
import ve.com.tracking.model.Guia;
import ve.com.tracking.model.GuiaReciboAlmacen;
import ve.com.tracking.model.Paquete;
import ve.com.tracking.model.ReciboAlmacen;
import ve.com.tracking.model.Users;
import ve.com.tracking.repository.CodigoGuiaRepository;
import ve.com.tracking.repository.GuiaReciboAlmacenRepository;
import ve.com.tracking.repository.ReciboAlmacenRepository;

@Service
@Transactional
public class ReciboServiceImpl implements ReciboService {

	@Autowired
	ReciboAlmacenRepository reciboAlmacenRepository;

	@Autowired
	GuiaReciboAlmacenRepository guiaReciboAlmacenRepository;

	@Autowired
	CodigoGuiaRepository codigoGuiaRepository;

	@Autowired
	PaqueteService paqueteService;

	@Autowired
	GuiaService guiaService;

	public long countAllReciboAlmacens() {
		return reciboAlmacenRepository.count();
	}

	public void deleteReciboAlmacen(ReciboAlmacen reciboAlmacen) {
		reciboAlmacenRepository.delete(reciboAlmacen);
	}

	public ReciboAlmacen findReciboAlmacen(Long id) {
		return reciboAlmacenRepository.findOne(id);
	}

	public List<ReciboAlmacen> findAllReciboAlmacens() {
		return reciboAlmacenRepository.findAll();
	}

	public List<ReciboAlmacen> findReciboAlmacenEntries(int firstResult,
			int maxResults) {
		return reciboAlmacenRepository.findAll(
				new org.springframework.data.domain.PageRequest(firstResult
						/ maxResults, maxResults)).getContent();
	}

	public void saveReciboAlmacen(ReciboAlmacen reciboAlmacen) {
		reciboAlmacenRepository.save(reciboAlmacen);
	}

	public ReciboAlmacen updateReciboAlmacen(ReciboAlmacen reciboAlmacen) {
		return reciboAlmacenRepository.save(reciboAlmacen);
	}

	@Override
	public List<Guia> findAllGuiasRecibo(ReciboAlmacen reciboAlmacen) {
		List<GuiaReciboAlmacen> guiaReciboAlmacen = guiaReciboAlmacenRepository
				.findByReciboAlmacenId(reciboAlmacen);
		List<Guia> guias = new ArrayList<Guia>();
		if (guiaReciboAlmacen != null) {
			for (GuiaReciboAlmacen guiaRecibo : guiaReciboAlmacen) {
				guias.add(guiaRecibo.getGuiaId());
			}
		}
		return guias;
	}

	@Override
	public ReciboAlmacenForm findReciboAlmacenForm(Long id) {
		ReciboAlmacen reciboAlmacen = findReciboAlmacen(id);
		if (reciboAlmacen == null) {
			return null;
		}
		List<Paquete> paquetes = paqueteService.findPaquetesByCliente(
				reciboAlmacen.getCliente(),
				TipoEstatusPaquete.NOTIFICADO_CONFIRMADO);
		List<GuiaForm> guias = new ArrayList<GuiaForm>();
		List<Guia> guiasRecibo = findAllGuiasRecibo(reciboAlmacen);
		GuiaForm guiaForm = null;
		for (Guia guiaRecibo : guiasRecibo) {
			List<CodigoGuia> codigosGuia = codigoGuiaRepository
					.findByGuiaId(guiaRecibo);
			List<ItemForm> items = new ArrayList<ItemForm>();
			List<DetalleGuia> detallesGuias = guiaService
					.findAllDetalleGuias(guiaRecibo);
			ItemForm itemForm = null;
			for (DetalleGuia detalleGuia : detallesGuias) {
				itemForm = new ItemForm(detalleGuia.getDetalleItemId());
				items.add(itemForm);
			}
			guiaForm = new GuiaForm(guiaRecibo, items, paquetes,
					codigosGuia.size());
			guias.add(guiaForm);
		}
		return new ReciboAlmacenForm(reciboAlmacen, guias, paquetes);
	}

	@Override
	public List<ReciboAlmacen> searchReciboAlmacen(String searchString,
			int firstResult, int sizeNo) {
		return reciboAlmacenRepository.searchReciboAlmacen(searchString,
				firstResult, sizeNo);
	}

	@Override
	public long countSearchReciboAlmacen(String searchString) {
		return reciboAlmacenRepository.countSearchReciboAlmacen(searchString);
	}

	@Override
	public List<ReciboAlmacen> searchReciboAlmacenByCliente(
			String numeroRecibo, Users cliente, int firstResult, int sizeNo) {
		return reciboAlmacenRepository.searchReciboAlmacenByCliente(
				numeroRecibo, cliente, firstResult, sizeNo);
	}

	@Override
	public long countSearchReciboAlmacenByCliente(String numeroRecibo,
			Users cliente) {
		return reciboAlmacenRepository.countSearchReciboAlmacenByCliente(
				numeroRecibo, cliente);
	}

	@Override
	public List<ReciboAlmacen> findRecibosAlmacenByCliente(Users cliente) {
		return reciboAlmacenRepository.findReciboAlmacensByCliente(cliente);
	}

	@Override
	public List<ReciboAlmacen> searchReciboAlmacenByCliente(Users cliente,
			int firstResult, int sizeNo) {
		return reciboAlmacenRepository.searchReciboAlmacenByCliente(cliente,
				firstResult, sizeNo);
	}

	@Override
	public long countSearchReciboAlmacenByCliente(Users cliente) {
		return reciboAlmacenRepository
				.countSearchReciboAlmacenByCliente(cliente);
	}

	@Override
	public List<ReciboAlmacen> searchReciboAlmacen(String searchString,
			TipoEstatusRecibo enumEstatus, int firstResult, int sizeNo) {
		return reciboAlmacenRepository.searchReciboAlmacen(searchString,
				enumEstatus, firstResult, sizeNo);
	}

	@Override
	public long countSearchReciboAlmacen(String searchString,
			TipoEstatusRecibo enumEstatus) {
		return reciboAlmacenRepository.countSearchReciboAlmacen(searchString,
				enumEstatus);
	}

	@Override
	public List<ReciboAlmacen> searchReciboAlmacen(
			TipoEstatusRecibo enumEstatus, int firstResult, int sizeNo) {
		return reciboAlmacenRepository.searchReciboAlmacen(enumEstatus,
				firstResult, sizeNo);
	}

	@Override
	public long countSearchReciboAlmacen(TipoEstatusRecibo enumEstatus) {
		return reciboAlmacenRepository.countSearchReciboAlmacen(enumEstatus);
	}

}
