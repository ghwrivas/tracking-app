package ve.com.tracking.actions;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import ve.com.tracking.core.CustomMultiAction;
import ve.com.tracking.core.TipoEstatusContenedor;
import ve.com.tracking.core.TipoFlashMessage;
import ve.com.tracking.exceptions.ContenedorServiceException;
import ve.com.tracking.forms.CodigoGuiaContenedorForm;
import ve.com.tracking.forms.ConfirmGuiasAlmacenForm;
import ve.com.tracking.model.CodigoGuia;
import ve.com.tracking.model.Contenedor;
import ve.com.tracking.model.DetalleGuia;
import ve.com.tracking.model.CodigoGuiaContenedor;
import ve.com.tracking.repository.CodigoGuiaRepository;
import ve.com.tracking.repository.GuiaContenedorRepository;
import ve.com.tracking.services.ContenedorService;
import ve.com.tracking.services.EstatusService;
import ve.com.tracking.services.GuiaService;
import ve.com.tracking.services.UsersService;

@Service("contenedorManager")
public class ContenedorManagerImpl extends CustomMultiAction implements
		ContenedorManager {

	@Autowired
	private ContenedorService contenedorService;

	@Autowired
	private GuiaService guiaService;

	@Autowired
	private GuiaContenedorRepository guiaContenedorRepository;

	@Autowired
	private EstatusService estatusService;

	@Autowired
	private CodigoGuiaRepository codigoGuiaRepository;
	
	@Autowired
	private UsersService usersService;

	@Override
	public Contenedor createOrUpdateContenedor(Long id) {
		Contenedor contenedor = null;
		if (id != null) {
			contenedor = contenedorService.findContenedor(id);
		}
		if (contenedor == null) {
			contenedor = new Contenedor();
		} else {
			contenedor.setGuias(contenedorService
					.findAllGuiasContenedor(contenedor));
		}
		return contenedor;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Event addGuia(RequestContext context) {
		Contenedor contenedor = getFlowScopeObject(context, "contenedor",
				Contenedor.class);
		CodigoGuiaContenedorForm guiaToAdd = getFlowScopeObject(context,
				"codigoGuiaContenedorForm", CodigoGuiaContenedorForm.class);
		String codigo = guiaToAdd.getCodigo();
		CodigoGuia codigoGuia = null;
		if (codigo != null) {
			codigoGuia = codigoGuiaRepository.findByCodigo(codigo);
		} else {
			sendMessageToFlow(context, TipoFlashMessage.ERROR,
					"Debe escribir el código guía");
			return error();
		}
		if (codigoGuia == null) {
			sendMessageToFlow(context, TipoFlashMessage.ERROR,
					"No se encontro una guía de código: " + codigo);
			return error();
		}
		CodigoGuiaContenedor guiaContenedor = guiaContenedorRepository
				.findByCodigoGuiaId(codigoGuia);
		if (guiaContenedor != null) {
			sendMessageToFlow(context, TipoFlashMessage.ERROR,
					"Guía ya agregada al contenedor: [codigo="
							+ guiaContenedor.getContenedorId().getCodigo()
							+ ", descripción="
							+ guiaContenedor.getContenedorId().getDescripcion()
							+ "]");
			return error();
		} else {
			if (contenedor.hasGuia(codigoGuia.getId())) {
				sendMessageToFlow(context, TipoFlashMessage.ERROR,
						"Guía ya agregada al contenedor que se esta editando");
				return error();
			} else {
				if (codigoGuia.getGuiaId().getGuiaReciboAlmacen() == null) {
					sendMessageToFlow(context, TipoFlashMessage.ERROR,
							"Este guía aún no ha sido agregado a un recibo de almacén");
					return error();
				}
				contenedor.addGuia(codigoGuia);
				context.getFlowScope().asMap().put("contenedor", contenedor);
				return success();
			}

		}
	}

	@Override
	public Event canBeEdit(RequestContext context) {
		Contenedor contenedor = getFlowScopeObject(context, "contenedor",
				Contenedor.class);
		if (contenedor.getId() != null) {
			if(usersService.hasRole("ROLE_LIBERADOR"))
				return no();
			if (contenedor.getEstatus() != TipoEstatusContenedor.CREADO) {
				sendMessageToFlow(context, TipoFlashMessage.INFO,
						"Contenedor con estatus: "
								+ contenedor.getEstatus().getNombre());
				return no();
			}
		}else{
			if(usersService.hasRole("ROLE_LIBERADOR"))
				return error();
		}
		return yes();
	}

	private Contenedor getFlowScopeContenedor(RequestContext context) {
		return (Contenedor) context.getFlowScope().get("contenedor");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Event saveContenedor(RequestContext context) {
		Contenedor contenedor = getFlowScopeObject(context, "contenedor",
				Contenedor.class);
		try {
			contenedorService.saveContenedor(contenedor);
			contenedor.getGuias().clear();
			contenedor.setGuias(contenedorService
					.findAllGuiasContenedor(contenedor));
			context.getFlowScope().asMap().put("contenedor", contenedor);
		} catch (Exception e) {
			sendMessageToFlow(context, TipoFlashMessage.ERROR, e.getMessage());
			e.printStackTrace();
			return error();
		}

		return success();
	}

	@Override
	public CodigoGuiaContenedorForm createGuiaContenedorForm() {
		return new CodigoGuiaContenedorForm();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Event enviarContenedor(RequestContext context) {
		Contenedor contenedor = getFlowScopeObject(context, "contenedor",
				Contenedor.class);
		try {
			contenedor = contenedorService.enviarContenedor(contenedor);
			contenedor.getGuias().clear();
			contenedor.setGuias(contenedorService
					.findAllGuiasContenedor(contenedor));
			context.getFlowScope().asMap().put("contenedor", contenedor);
			sendMessageToFlow(context, TipoFlashMessage.SUCCESS,
					"Contenedor en transito a país destino");
		} catch (ContenedorServiceException e) {
			sendMessageToFlow(context, TipoFlashMessage.ERROR, e.getMessage());
		}
		return success();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Event confirmEnDestino(RequestContext context) {
		Contenedor contenedor = getFlowScopeObject(context, "contenedor",
				Contenedor.class);
		try {
			contenedor = contenedorService.confirmarEnPaisDestino(contenedor);
			contenedor.setGuias(contenedorService
					.findAllGuiasContenedor(contenedor));
			context.getFlowScope().asMap().put("contenedor", contenedor);
			sendMessageToFlow(context, TipoFlashMessage.SUCCESS,
					"Contenedor confirmado en país destino");
		} catch (ContenedorServiceException e) {
			sendMessageToFlow(context, TipoFlashMessage.ERROR, e.getMessage());
		}
		return success();
	}

	@Override
	public Event confirmEnAlmacen(RequestContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Event confirmGuiasContenedor(RequestContext context) {
		ConfirmGuiasAlmacenForm guiasToConfirm = getFlowScopeObject(context,
				"confirmGuiasAlmacenForm", ConfirmGuiasAlmacenForm.class);
		Contenedor contenedor = getFlowScopeObject(context, "contenedor",
				Contenedor.class);
		try {
			List<Long> guiasIds = guiasToConfirm.getGuiasIds();
			contenedor = contenedorService.confirmarGuiasContenedor(contenedor,
					guiasIds);
			contenedor.getGuias().clear();
			contenedor.setGuias(contenedorService
					.findAllGuiasContenedor(contenedor));
			context.getFlowScope().asMap().put("contenedor", contenedor);
			if (guiasIds != null && guiasIds.size() > 0) {
				sendMessageToFlow(context, TipoFlashMessage.SUCCESS,
						"Contenedor y productos selecionados confirmados en almacén destino");

			} else {
				sendMessageToFlow(context, TipoFlashMessage.SUCCESS,
						"Contenedor confirmado en almacén destino");
			}
			return success();
		} catch (ContenedorServiceException e) {
			sendMessageToFlow(context, TipoFlashMessage.ERROR, e.getMessage());
			return error();
		}
	}

	@Override
	public Event canConfirmContenedorGuiasAlmacen(RequestContext context) {
		Contenedor contenedor = getFlowScopeObject(context, "contenedor",
				Contenedor.class);
		boolean hasRole = false;
		Collection<? extends GrantedAuthority> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		for (GrantedAuthority rol : roles) {
			if(rol.getAuthority().equals("ROLE_ADMIN") || rol.getAuthority().equals("ROLE_LIBERADOR")){
				hasRole = true;
				break;
			}
		}
		if(!hasRole){
			sendMessageToFlow(
					context,
					TipoFlashMessage.ERROR,
					"No posee los permisos suficientes para confirmar el contenedor/guías en el país y almacén destino");
			return no();			
		}
		if (contenedor.getEstatus() == TipoEstatusContenedor.CONFIRMADO_ALMACEN_DESTINO) {
			return yes();
		} else if (estatusService.getNextEstatusContenedor(contenedor) == TipoEstatusContenedor.CONFIRMADO_ALMACEN_DESTINO) {
			return yes();
		} else {
			sendMessageToFlow(
					context,
					TipoFlashMessage.ERROR,
					"El contenedor no puede ser confirmado en el almacén destino porque su estatus actual es: "
							+ contenedor.getEstatus());
			return no();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Event deleteGuia(RequestContext context) {
		int index = context.getRequestScope().getInteger("index");
		Contenedor contenedor = getFlowScopeContenedor(context);
		List<CodigoGuia> guias = contenedor.getGuias();
		if (index < 0 || index >= guias.size()) { // fuera de limite
			sendMessageToFlow(context, TipoFlashMessage.ERROR,
					"Indice de lista inválido");
			return error();
		} else if (contenedor.getId() == null) { // contendor no persistido
			guias.remove(index);
			contenedor.setGuias(guias);
			context.getFlowScope().asMap().put("contenedor", contenedor);
			return success();
		} else if (contenedor.getEstatus() != TipoEstatusContenedor.CREADO) {
			sendMessageToFlow(context, TipoFlashMessage.ERROR,
					"Solo se pueden eliminar guías de un contendor con estatus igual a CREADO");
			return error();
		} else {
			CodigoGuia guia = guias.get(index);
			CodigoGuiaContenedor guiaContenedor = guiaContenedorRepository
					.findByCodigoGuiaId(guia);
			if (guiaContenedor != null) {
				if (guiaContenedor.getContenedorId().getId().longValue() == contenedor
						.getId().longValue()) {
					guias.remove(index);
					contenedor.setGuias(guias);
					contenedorService.deleteGuiaContenedor(guiaContenedor);
					context.getFlowScope().asMap()
							.put("contenedor", contenedor);
				} else {
					sendMessageToFlow(context, TipoFlashMessage.ERROR,
							"No se puede eliminar una guía agregada a otro contenedor");
					return error();
				}
			} else {
				guias.remove(index);
				contenedor.setGuias(guias);
				context.getFlowScope().asMap().put("contenedor", contenedor);
			}
			return success();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Event mapGuiasContenedorToConfirm(RequestContext context) {
		Contenedor contenedor = getFlowScopeContenedor(context);
		List<CodigoGuia> guias = contenedorService
				.findAllGuiasContenedor(contenedor);
		context.getViewScope().asMap().put("guias", getGuiasAsMap(guias));
		return null;
	}

	private Map<Long, String> getGuiasAsMap(List<CodigoGuia> guias) {
		Map<Long, String> guiasMap = new HashMap<Long, String>();
		for (CodigoGuia guia : guias) {
			guiasMap.put(guia.getId(), "Código Guía: " + guia.getCodigo()
					+ " - " + getDetalleGuiaAsString(guia));
		}
		return guiasMap;
	}

	private String getDetalleGuiaAsString(CodigoGuia guia) {
		if (guia == null)
			return "";
		guia = codigoGuiaRepository.findOne(guia.getId());
		StringBuilder sb = new StringBuilder();
		Iterator<DetalleGuia> it = guia.getGuiaId().getDetalleGuias()
				.iterator();
		while (it.hasNext()) {
			DetalleGuia detalle = it.next();
			sb.append(detalle.getDetalleItemId().getDescripcion()).append(" ");
		}
		return sb.toString();
	}

}
