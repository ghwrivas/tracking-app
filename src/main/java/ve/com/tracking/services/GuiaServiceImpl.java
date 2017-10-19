package ve.com.tracking.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ve.com.tracking.core.TipoEstatusGuia;
import ve.com.tracking.core.TipoEstatusPaquete;
import ve.com.tracking.exceptions.EntregarGuiaClienteException;
import ve.com.tracking.exceptions.ResourceNotFoundException;
import ve.com.tracking.exceptions.TipoCambioException;
import ve.com.tracking.forms.GuiaForm;
import ve.com.tracking.forms.ItemForm;
import ve.com.tracking.forms.PaquetesSelectedForm;
import ve.com.tracking.model.CodigoGuia;
import ve.com.tracking.model.DetalleGuia;
import ve.com.tracking.model.DetalleItem;
import ve.com.tracking.model.Guia;
import ve.com.tracking.model.Guias;
import ve.com.tracking.model.Paquete;
import ve.com.tracking.model.ReciboAlmacen;
import ve.com.tracking.model.TipoCambio;
import ve.com.tracking.repository.CategoriaDetalleRepository;
import ve.com.tracking.repository.CodigoGuiaRepository;
import ve.com.tracking.repository.DestinosRepository;
import ve.com.tracking.repository.DetalleGuiaRepository;
import ve.com.tracking.repository.DetalleItemRepository;
import ve.com.tracking.repository.EstatusGuiaRepository;
import ve.com.tracking.repository.EstatusPaqueteRepository;
import ve.com.tracking.repository.GuiaRepository;
import ve.com.tracking.repository.TipoCambioRepository;
import ve.com.tracking.repository.TipoEmbalajeRepository;
import ve.com.tracking.uidgen.UIDSequencerManager;

@Service
@Transactional
public class GuiaServiceImpl implements GuiaService {

	@Autowired
	private GuiaRepository guiaRepository;

	@Autowired
	private DestinosRepository destinoRepository;

	@Autowired
	private PaqueteService paqueteService;

	@Autowired
	private DetalleItemRepository detalleItemRepository;

	@Autowired
	private DetalleGuiaRepository detalleGuiaRepository;

	@Autowired
	private UsersService userService;

	@Autowired
	private CategoriaDetalleRepository categoriaDetalleRepository;

	@Autowired
	private TipoEmbalajeRepository tipoEmbalajeRepository;

	@Autowired
	private EstatusGuiaRepository estatusGuiaRepository;

	@Autowired
	private EstatusPaqueteRepository estatusPaqueteRepository;

	@Autowired
	private TipoCambioRepository tipoCambioRepository;

	@Autowired
	private EstatusService estatusService;

	@Autowired
	private UIDSequencerManager uidService;

	@Autowired
	private CodigoGuiaRepository codigoGuiaRepository;

	public long countAllGuias() {
		return guiaRepository.count();
	}

	public void deleteGuia(Guia guia) {
		guiaRepository.delete(guia);
	}

	public Guia findGuia(Long id) {
		return guiaRepository.findOne(id);
	}

	public List<Guia> findAllGuias() {
		return guiaRepository.findAll();
	}

	public List<Guia> findGuiaEntries(int firstResult, int maxResults) {
		return guiaRepository.findAll(
				new org.springframework.data.domain.PageRequest(firstResult
						/ maxResults, maxResults)).getContent();
	}

	public void saveGuia(Guia guia) {
		guiaRepository.save(guia);
	}

	public Guia updateGuia(Guia guia) {
		return guiaRepository.save(guia);
	}

	@Override
	public GuiaForm saveGuiaBusiness(GuiaForm guiaForm, ReciboAlmacen ra)
			throws TipoCambioException {
		List<ItemForm> listItemForm;
		Guia guia = null;
		if (guiaForm.getId() == null) { // new
			guia = new Guia();
			setDataToGuiaModel(guia, guiaForm);
			saveGuia(guia);
			listItemForm = processItemsForms(guiaForm, guia);
			setDataToGuiaForm(guia, guiaForm);
			processPiezasGuia(ra, guia, guiaForm.getPiezas());
		} else { // update
			guia = guiaRepository.findOne(guiaForm.getId());
			setDataToGuiaModel(guia, guiaForm);
			guia = updateGuia(guia);
			listItemForm = processItemsForms(guiaForm, guia);
		}
		guiaForm.setItems(listItemForm);
		return guiaForm;
	}

	private void processPiezasGuia(ReciboAlmacen ra, Guia guia, Integer piezas) {
		for (int i = 1; i <= piezas; i++) {
			CodigoGuia codigoGuia = new CodigoGuia();
			codigoGuia.setGuiaId(guia);
			codigoGuia.setEstatus(TipoEstatusGuia.AGREGADA_RECIBO_ALMACEN);
			codigoGuia.setCodigo(uidService.getNextCodigoGuia(ra.getId(),
					guia.getId()));
			codigoGuiaRepository.save(codigoGuia);
			estatusService.registerLogEstatusGuia(codigoGuia);
		}
	}

	private List<ItemForm> processItemsForms(GuiaForm guiaForm, Guia guia) {
		List<ItemForm> listItemForm = new ArrayList<ItemForm>();
		DetalleItem detalleItem;
		DetalleGuia detalleGuia;
		for (ItemForm itemForm : guiaForm.getItems()) {
			if (itemForm.getId() == null) { // new item
				detalleItem = new DetalleItem();
				setDataToItemModel(detalleItem, itemForm);
				detalleItem = detalleItemRepository.save(detalleItem);
				setDataToItemForm(detalleItem, itemForm);
				detalleGuia = new DetalleGuia();
				detalleGuia.setDetalleItemId(detalleItem);
				detalleGuia.setGuiaId(guia);
				detalleGuiaRepository.save(detalleGuia);
			} else { // update item
				detalleItem = detalleItemRepository.findOne(itemForm.getId());
				setDataToItemModel(detalleItem, itemForm);
				detalleItem = detalleItemRepository.save(detalleItem);
				setDataToItemForm(detalleItem, itemForm);
			}
			listItemForm.add(itemForm);
		}
		return listItemForm;
	}

	@SuppressWarnings("unused")
	private void setCargoMonedaLocal(ReciboAlmacen recibo) {
		TipoCambio tipoCambio = tipoCambioRepository.findByCountry(recibo
				.getDestino().getPais());
		if (tipoCambio == null) {
			throw new TipoCambioException(
					"No existe configurado un tipo de cambio para el país del destino seleccionado: "
							+ recibo.getDestino().getPais().getName());
		}
		BigDecimal valorDolar = tipoCambio.getValorDolar();
		BigDecimal totalCargoMonedaLocal = recibo.getTotalCargo().multiply(
				valorDolar);
		totalCargoMonedaLocal.setScale(2, RoundingMode.HALF_UP);
		recibo.setTotalCargoCambio(totalCargoMonedaLocal);
	}

	private void setDataToGuiaModel(Guia guia, GuiaForm guiaForm) {
		if (guia.getId() == null) { // new
			guia.setCreated(Calendar.getInstance());
			// guia.setEstatus(TipoEstatusGuia.CREADO);
			guia.setCreadorId(userService.getPrincipal());
			guia.setCliente(guiaForm.getCliente());
		}
		guia.setTipoEmbalajeId(guiaForm.getTipoEmbalajeId());
		guia.setPeso(guiaForm.getPeso());
		// guia.setPiezas(guiaForm.getPiezas());
		guia.setLargo(guiaForm.getLargo());
		guia.setAncho(guiaForm.getAncho());
		guia.setAlto(guiaForm.getAlto());
		guia.setVolumen(guiaForm.getVolumen());
		guia.setPesoVolumetrico(guiaForm.getPesoVolumetrico());
	}

	private void setDataToGuiaForm(Guia guia, GuiaForm guiaForm) {
		guiaForm.setId(guia.getId());
		guiaForm.setCreated(guia.getCreated());
		guiaForm.setCreadorId(guia.getCreadorId());
	}

	private void setDataToItemModel(DetalleItem item, ItemForm itemForm) {
		item.setPaqueteId(itemForm.getPaqueteId());
		item.setCategoriaDetalleId(itemForm.getCategoriaDetalleId());
		item.setDescripcion(itemForm.getDescripcion());
	}

	private void setDataToItemForm(DetalleItem item, ItemForm itemForm) {
		itemForm.setId(item.getId());
	}

	@Override
	public GuiaForm updateGuiaBusiness(GuiaForm guiaBusiness) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DetalleItem> findAllDetalleItems(Guia guia) {
		if (guia == null)
			throw new ResourceNotFoundException("Guia no encontrada");
		return guiaRepository.findDetalleItems(guia);
	}

	@Override
	public List<Guias> searchGuias(String searchString, int firstResult,
			int sizeNo) {
		return guiaRepository
				.searchGuiasView(searchString, firstResult, sizeNo);
	}

	@Override
	public long countSearchGuias(String searchString) {
		return guiaRepository.countSearchGuiasView(searchString);
	}

	@Override
	public List<Guias> findGuiasEntries(int firstResult, int sizeNo) {
		return guiaRepository.findGuiasEntries(firstResult, sizeNo);
	}

	@Override
	public List<Guias> findAllGuiasView() {
		return guiaRepository.findAllGuiasView();
	}

	@Override
	public long countAllGuiasView() {
		return guiaRepository.countAllGuiasView();
	}

	@Override
	public List<DetalleGuia> findAllDetalleGuias(Guia guia) {
		return detalleGuiaRepository.findByGuiaId(guia);
	}

	@Override
	public void processPaquetes(PaquetesSelectedForm paquetesSelected) {
		if (paquetesSelected != null) {
			List<Long> paqueteIds = paquetesSelected.getPaquetesId();
			Paquete paquete;
			for (Long id : paqueteIds) {
				paquete = paqueteService.findPaquete(id);
				if (paquete.getEstatus() == TipoEstatusPaquete.PROCESADO) {
					continue;
				}
				estatusService.setNextEstatusPaquete(paquete);
				paqueteService.savePaquete(paquete);
			}
		}
	}

	@Override
	public void entregarGuiaCliente(CodigoGuia guia) {
		if (guia == null) {
			throw new ResourceNotFoundException("Producto no encontrado");
		}
		if (guia.getEstatus() == TipoEstatusGuia.ENTREGADO_CLIENTE) {
			throw new EntregarGuiaClienteException(
					"El producto ya fue entregado al cliente");
		}			
		if (guia.getEstatus() != TipoEstatusGuia.CONFIRMADO_ALMACEN_DESTINO) {
			throw new EntregarGuiaClienteException(
					"Para realizar la entrega del producto debe estar en el estatus: "
							+ TipoEstatusGuia.CONFIRMADO_ALMACEN_DESTINO.name());
		}	
		estatusService.setNextEstatusGuia(guia);
		estatusService.registerLogEstatusGuia(guia);
		codigoGuiaRepository.save(guia);
	}

	@Override
	public void deleteItem(Long itemId) {
		detalleItemRepository.delete(itemId);

	}

	@Override
	public void deleteGuia(Long guiaId) {
		guiaRepository.delete(guiaId);
	}

	@Override
	public List<Guias> searchGuias(String searchString,
			TipoEstatusGuia enumEstatus, int firstResult, int sizeNo) {
		return guiaRepository.searchGuiasView(searchString, enumEstatus, firstResult, sizeNo);
	}

	@Override
	public long countSearchGuias(String searchString,
			TipoEstatusGuia enumEstatus) {
		return guiaRepository.countSearchGuiasView(searchString, enumEstatus);
	}

	@Override
	public List<Guias> searchGuias(TipoEstatusGuia enumEstatus,
			int firstResult, int sizeNo) {
		return guiaRepository.searchGuiasView(enumEstatus, firstResult, sizeNo);
	}

	@Override
	public long countSearchGuias(TipoEstatusGuia enumEstatus) {
		return guiaRepository.countSearchGuiasView(enumEstatus);
	}

}
