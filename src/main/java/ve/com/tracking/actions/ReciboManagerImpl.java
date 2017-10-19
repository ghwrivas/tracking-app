package ve.com.tracking.actions;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import ve.com.tracking.core.CustomMultiAction;
import ve.com.tracking.core.FileAttach;
import ve.com.tracking.core.TipoCargo;
import ve.com.tracking.core.TipoEstatusGuia;
import ve.com.tracking.core.TipoEstatusPaquete;
import ve.com.tracking.core.TipoEstatusRecibo;
import ve.com.tracking.core.TipoFlashMessage;
import ve.com.tracking.core.TipoTransportacion;
import ve.com.tracking.forms.GuiaForm;
import ve.com.tracking.forms.ItemForm;
import ve.com.tracking.forms.PaquetesSelectedForm;
import ve.com.tracking.forms.ReciboAlmacenForm;
import ve.com.tracking.mail.EmailTemplateRecibo;
import ve.com.tracking.model.CodigoGuia;
import ve.com.tracking.model.Destinos;
import ve.com.tracking.model.GuiaReciboAlmacen;
import ve.com.tracking.model.Paquete;
import ve.com.tracking.model.ReciboAlmacen;
import ve.com.tracking.model.TipoCambio;
import ve.com.tracking.model.Users;
import ve.com.tracking.repository.CategoriaDetalleRepository;
import ve.com.tracking.repository.CodigoGuiaRepository;
import ve.com.tracking.repository.DestinosRepository;
import ve.com.tracking.repository.DetalleGuiaRepository;
import ve.com.tracking.repository.DetalleItemRepository;
import ve.com.tracking.repository.EmpresaRepository;
import ve.com.tracking.repository.GuiaReciboAlmacenRepository;
import ve.com.tracking.repository.TipoEmbalajeRepository;
import ve.com.tracking.services.EstatusService;
import ve.com.tracking.services.GuiaService;
import ve.com.tracking.services.MailService;
import ve.com.tracking.services.PaqueteService;
import ve.com.tracking.services.ReciboService;
import ve.com.tracking.services.TipoCambioService;
import ve.com.tracking.services.UsersService;
import ve.com.tracking.views.PDFReciboAlmacenView.MonedaRecibo;

@Service("reciboManager")
public class ReciboManagerImpl extends CustomMultiAction implements
		ReciboManager {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private UsersService userService;

	@Autowired
	private PaqueteService paqueteService;

	@Autowired
	private TipoEmbalajeRepository tipoEmbalajeRepository;

	@Autowired
	private CategoriaDetalleRepository categoriaDetalleRepository;

	@Autowired
	private DetalleGuiaRepository detalleGuiaRepository;

	@Autowired
	private DetalleItemRepository detalleItemRepository;

	@Autowired
	private DestinosRepository destinosRepository;

	@Autowired
	private GuiaService guiaService;

	@Autowired
	private ReciboService reciboService;

	@Autowired
	private TipoCambioService tipoCambioService;

	@Autowired
	private GuiaReciboAlmacenRepository guiaReciboAlmacenRepository;

	@Autowired
	private EstatusService estatusService;

	@Autowired
	private CodigoGuiaRepository codigoGuiaRepository;

	@Autowired
	private MailService mailService;

	@Override
	public ReciboAlmacenForm createRecibo(Long userId) {
		ReciboAlmacenForm recibo = null;
		try {
			if (userId == null)
				return null;
			Users cliente = userService.findUsers(userId);
			if (cliente == null)
				return null;
			List<Paquete> paquetes = paqueteService.findPaquetesByCliente(
					cliente, TipoEstatusPaquete.NOTIFICADO_CONFIRMADO);
			if (paquetes == null || paquetes.size() < 1)
				return null;
			recibo = new ReciboAlmacenForm();
			recibo.setCliente(cliente);
			recibo.setCreated(Calendar.getInstance());
			recibo.setCreadorId(userService.getPrincipal());
			recibo.setOrigen(destinosRepository.findOne(new Long(1)));
			recibo.setDestino(destinosRepository.findOne(new Long(2)));
			recibo.setPaquetes(paquetes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recibo;
	}

	@Override
	public ReciboAlmacenForm editRecibo(Long id) {
		ReciboAlmacenForm recibo = null;
		try {
			if (id == null)
				return null;
			recibo = reciboService.findReciboAlmacenForm(id);
			if (recibo == null) {
				return null;
			}
			return recibo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recibo;
	}

	@Override
	public GuiaForm createGuia() {
		GuiaForm guia = new GuiaForm();
		guia.setCreadorId(userService.getPrincipal());
		guia.setCreated(Calendar.getInstance());
		guia.setPiezas(1);
		return guia;
	}

	@Override
	public GuiaForm editGuia(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Event populateDataReferenceFormAddGuia(RequestContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Event populateDataReferenceFormRecibo(RequestContext context) {
		context.getFlowScope()
				.asMap()
				.put("transportacion",
						Arrays.asList(TipoTransportacion.values()));
		context.getFlowScope().asMap()
				.put("destinos", destinosRepository.findAll());
		context.getFlowScope().asMap()
				.put("cargos", Arrays.asList(TipoCargo.values()));
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Event addGuia(RequestContext context) {
		try {
			ReciboAlmacenForm recibo = getFlowScopeObject(context, "recibo",
					ReciboAlmacenForm.class);
			GuiaForm guia = getFlowScopeObject(context, "guia", GuiaForm.class);
			if (guia.getItems().size() < 1) { // no items
				sendMessageToFlow(context, TipoFlashMessage.ERROR,
						"La guia no posee items");
				return error();
			}
			List<GuiaForm> guias = recibo.getGuias();
			guia.setIndex(guias.size());
			guia.setCliente(recibo.getCliente());
			guia.setPaquetes(recibo.getPaquetes());
			guias.add(guia);
			recibo.setGuias(guias);
			context.getFlowScope().asMap().put("recibo", recibo);
		} catch (Exception e) {
			sendMessageToFlow(context, TipoFlashMessage.ERROR, e.getMessage());
			return error();
		}
		return success();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Event saveRecibo(RequestContext context) {
		try {
			ReciboAlmacenForm recibo = getFlowScopeObject(context, "recibo",
					ReciboAlmacenForm.class);
			Destinos destino = recibo.getDestino();
			TipoCambio tipoCambio = destino.getPais().getTipoCambio();
			recibo.setTotalCargo(recibo.getTotalCargoConDescuento());
			BigDecimal totalCargoCambio = tipoCambioService.getExchangeRate(
					recibo.getTotalCargo(), tipoCambio);
			recibo.setTotalCargoCambio(totalCargoCambio);
			List<GuiaForm> guias = recibo.getGuias();
			ReciboAlmacen reciboModel = new ReciboAlmacen(recibo);
			String messageEmail = "";
			if (reciboModel.getId() == null) {
				messageEmail = "Se ha registrado un nuevo recibo de almacén a su nombre. Por favor descárguelo.";
				reciboService.saveReciboAlmacen(reciboModel);
			} else {
				messageEmail = "Se ha actualizado un recibo de almacén registrado a su nombre. Por favor descárguelo.";
				reciboModel = reciboService.updateReciboAlmacen(reciboModel);
			}
			List<GuiaForm> guiasUpdated = new ArrayList<GuiaForm>();
			GuiaForm guiaUpdated = null;
			for (GuiaForm guiaForm : guias) {
				guiaUpdated = guiaService.saveGuiaBusiness(guiaForm,
						reciboModel);
				boolean esta = false;
				List<GuiaReciboAlmacen> guiasRecibos = guiaReciboAlmacenRepository
						.findByReciboAlmacenId(reciboModel);
				if (guiasRecibos.size() > 0) {
					for (GuiaReciboAlmacen guiaReciboAlmacen : guiasRecibos) {
						if (guiaReciboAlmacen.getGuiaId().getId().longValue() == guiaUpdated
								.getId().longValue()) {
							esta = true;
						}
					}
				}
				if (esta == false) {
					GuiaReciboAlmacen gra = new GuiaReciboAlmacen();
					gra.setGuiaId(guiaService.findGuia(guiaUpdated.getId()));
					gra.setReciboAlmacenId(reciboModel);
					guiaReciboAlmacenRepository.save(gra);
				}
				guiasUpdated.add(guiaUpdated);

			}
			recibo = editRecibo(reciboModel.getId());
			context.getFlowScope().asMap().put("recibo", recibo);
			sendMessageToFlow(context, TipoFlashMessage.SUCCESS,
					"Recibo Guardado");

			/* enviar correo */
			System.out.println("email " + recibo.getCliente().getEmail());
			mailService.sendEmail(recibo.getCliente().getEmail(),
					"Recibo de Almacén - Número: " + recibo.getStringId(),
					new EmailTemplateRecibo(recibo.getCliente()
							.getNombreCompleto(), messageEmail),
					generatePdf(recibo));
			return success();
		} catch (MailSendException e) {
			e.printStackTrace();
			return success();
		} catch (Exception e) {
			e.printStackTrace();
			sendMessageToFlow(context, TipoFlashMessage.ERROR, e.getMessage());
			return error();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Event populateDataReferenceFormAddItemGuia(RequestContext context) {
		context.getFlowScope().asMap()
				.put("categorias", categoriaDetalleRepository.findAll());
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Event populateDataReferenceFormGuia(RequestContext context) {
		context.getFlowScope().asMap()
				.put("embalajes", tipoEmbalajeRepository.findAll());
		return null;
	}

	@Override
	public ItemForm createItem() {
		return new ItemForm();
	}

	@Override
	public PaquetesSelectedForm createPaquetesSelectedForm() {
		return new PaquetesSelectedForm();
	}

	@Override
	public Event addItem(RequestContext context) {
		ItemForm item = getFlowScopeObject(context, "item", ItemForm.class);
		GuiaForm guia = getFlowScopeObject(context, "guia", GuiaForm.class);
		guia.addItem(item);
		return success();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Event renderFormCargos(RequestContext context) {
		try {
			ReciboAlmacenForm recibo = getFlowScopeObject(context, "recibo",
					ReciboAlmacenForm.class);
			BigDecimal totalPesoGuias = recibo.getTotalPesoGuias();
			BigDecimal totalPesoVolumetrico = recibo.getTotalPesoVolumetrico();
			BigDecimal totalVolumen = recibo.getTotalPiesCubicos();
			TipoTransportacion transportacion = recibo.getTipoTransportacion();
			if (transportacion == TipoTransportacion.AEREO) {
				if (totalPesoGuias.compareTo(totalPesoVolumetrico) > 0) {
					recibo.setTipoCargo(TipoCargo.PESO);
				} else {
					recibo.setTipoCargo(TipoCargo.PESO_VOLUMETRICO);
				}
			} else if (transportacion == TipoTransportacion.MARITIMO) {
				if (totalPesoGuias.compareTo(totalVolumen) > 0) {
					recibo.setTipoCargo(TipoCargo.PESO);
				} else {
					recibo.setTipoCargo(TipoCargo.VOLUMEN);
				}
			} else {

			}
			context.getFlowScope().asMap().put("recibo", recibo);
		} catch (Exception e) {
			e.printStackTrace();
			sendMessageToFlow(context, TipoFlashMessage.ERROR, e.getMessage());
			return error();
		}
		return success();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Event deleteItem(RequestContext context) {
		int index = context.getRequestScope().getInteger("index");
		GuiaForm guia = getFlowScopeObject(context, "guia", GuiaForm.class);
		List<ItemForm> items = guia.getItems();
		if (index < 0 || index >= items.size()) { // fuera de limite
			sendMessageToFlow(context, TipoFlashMessage.ERROR,
					"Indice de lista inválido");
			return error();
		} else if (guia.getId() == null) { // guia no persistido
			items.remove(index);
			guia.setItems(items);
			context.getFlowScope().asMap().put("guia", guia);
			return success();
		} else {
			ItemForm item = items.get(index);
			if (item.getId() != null) {
				detalleItemRepository.delete(item.getId());
				items.remove(index);
				guia.setItems(items);
				context.getFlashScope().asMap().put("guia", guia);
			} else {
				items.remove(index);
				guia.setItems(items);
				context.getFlashScope().asMap().put("guia", guia);
			}
			return success();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Event deleteGuia(RequestContext context) {
		int index = context.getRequestScope().getInteger("index");
		ReciboAlmacenForm recibo = getFlowScopeObject(context, "recibo",
				ReciboAlmacenForm.class);
		List<GuiaForm> guias = recibo.getGuias();
		if (index < 0 || index >= guias.size()) { // fuera de limite
			sendMessageToFlow(context, TipoFlashMessage.ERROR,
					"Indice de lista inválido");
			return error();
		} else if (recibo.getId() == null) { // recibo no persistido
			guias.remove(index);
			recibo.setGuias(guias);
			context.getFlowScope().asMap().put("recibo", recibo);
			return success();
		} else {
			GuiaForm guia = guias.get(index);
			if (guia.getId() != null) {
				boolean canDelete = true;
				List<CodigoGuia> codigosGuia = codigoGuiaRepository
						.findByGuiaId(guiaService.findGuia(guia.getId()));
				for (CodigoGuia codigoGuia : codigosGuia) {
					if (codigoGuia.getEstatus() == TipoEstatusGuia.EN_TRANSITO_PAIS_DESTINO) {
						canDelete = false;
						break;
					}
				}
				if (canDelete) {
					guiaService.deleteGuia(guia.getId());
					guias.remove(index);
					recibo.setGuias(guias);
					context.getFlashScope().asMap().put("recibo", recibo);
				} else {
					sendMessageToFlow(
							context,
							TipoFlashMessage.ERROR,
							"Alguna de las piezas de este producto se encuentran en estatus: "
									+ TipoEstatusGuia.EN_TRANSITO_PAIS_DESTINO
											.toString());
					return error();
				}
			} else {
				guias.remove(index);
				recibo.setGuias(guias);
				context.getFlashScope().asMap().put("recibo", recibo);
			}
			return success();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Event confirmarPago(RequestContext context) {
		try {
			ReciboAlmacenForm recibo = getFlowScopeObject(context, "recibo",
					ReciboAlmacenForm.class);
			List<GuiaForm> guias = recibo.getGuias();
			if (recibo.getId() == null) {
				sendMessageToFlow(
						context,
						TipoFlashMessage.ERROR,
						"Antes de confirmar pago debe guardar los cambios realizados y productos agregados");
				return error();
			} else if (guias.size() == 0) {
				sendMessageToFlow(context, TipoFlashMessage.ERROR,
						"El recibo no posee productos generados");
				return error();
			}
			boolean canConfirm = true;
			if (canConfirm) {
				for (GuiaForm guiaForm : guias) {
					if (guiaForm.getId() == null) {
						canConfirm = false;
						break;
					}
				}
			}
			if (canConfirm) {
				recibo.setEstatus(TipoEstatusRecibo.CONFIRMADO);
				reciboService.updateReciboAlmacen(new ReciboAlmacen(recibo));
				context.getFlowScope().asMap().put("recibo", recibo);
				sendMessageToFlow(context, TipoFlashMessage.SUCCESS,
						"Recibo de almacén confirmado");
				return success();
			} else {
				sendMessageToFlow(context, TipoFlashMessage.ERROR,
						"Antes de confirmar debe guardar los cambios realizados y productos agregados");
				return error();
			}

		} catch (Exception e) {
			e.printStackTrace();
			sendMessageToFlow(context, TipoFlashMessage.ERROR, e.getMessage());
			return error();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Event mapPaquetesToProcess(RequestContext context) {
		ReciboAlmacenForm recibo = getFlowScopeObject(context, "recibo",
				ReciboAlmacenForm.class);
		List<GuiaForm> guias = recibo.getGuias();
		if (guias == null || guias.size() == 0)
			return result("next");
		Map<Long, String> paquetesMap = new HashMap<Long, String>();
		for (GuiaForm guiaForm : guias) {
			for (Paquete paquete : guiaForm.getPaquetesToProcesar()) {
				if (paquete.getEstatus() == TipoEstatusPaquete.NOTIFICADO_CONFIRMADO) {

					paquetesMap.put(paquete.getId(),
							"Tracking ID: "
									+ paquete.getTracking()
									+ " - "
									+ paquete.getDetalleNotificacion()
											.getDescripcion());

				}
			}
		}
		context.getViewScope().asMap().put("paquetes", paquetesMap);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Event processPaquetes(RequestContext context) {
		PaquetesSelectedForm paquetesToProcess = getFlowScopeObject(context,
				"paquetesSelected", PaquetesSelectedForm.class);
		ReciboAlmacenForm recibo = getFlowScopeObject(context, "recibo",
				ReciboAlmacenForm.class);
		List<Long> ids = paquetesToProcess.getPaquetesId();
		if (ids != null && ids.size() > 0) {
			boolean process = false;
			for (Long id : ids) {
				for (Paquete paquete : recibo.getPaquetes())
					if (id.longValue() == paquete.getId().longValue()) {
						estatusService.setNextEstatusPaquete(paquete);
						paqueteService.savePaquete(paquete);
						estatusService.registerLogEstatusPaquete(paquete);
						process = true;
					}
			}
			// actualizamos la lista de paquetes
			if (process) {
				List<Paquete> paquetes = paqueteService.findPaquetesByCliente(
						recibo.getCliente(),
						TipoEstatusPaquete.NOTIFICADO_CONFIRMADO);
				recibo.setPaquetes(paquetes);
				context.getFlashScope().asMap().put("recibo", recibo);
			}
		}
		return success();
	}

	@Override
	public Event hasPaquetesToProcess(RequestContext context) {
		ReciboAlmacenForm recibo = getFlowScopeObject(context, "recibo",
				ReciboAlmacenForm.class);
		List<GuiaForm> guias = recibo.getGuias();
		if (guias == null || guias.size() == 0)
			return no();
		for (GuiaForm guiaForm : guias) {
			for (Paquete paquete : guiaForm.getPaquetesToProcesar()) {
				if (paquete.getEstatus() == TipoEstatusPaquete.NOTIFICADO_CONFIRMADO) {
					return yes();
				}
			}
		}
		return no();
	}

	private FileAttach generatePdf(ReciboAlmacenForm recibo)
			throws JRException, SQLException {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("titulo", "Recibo de Almacén");
		model.put("numero", recibo.getId());
		model.put("recibo", recibo);
		model.put("empresa", empresaRepository.findOne((short) 1));
		model.put("moneda", MonedaRecibo.DESTINO.name());
		return new FileAttach(JasperRunManager.runReportToPdf(this.getClass()
				.getResource("/reports/recibo.jasper").getPath(), model,
				dataSource.getConnection()), "Recibo de Almacén "
				+ recibo.getId() + ".pdf", "application/pdf");
	}
}
