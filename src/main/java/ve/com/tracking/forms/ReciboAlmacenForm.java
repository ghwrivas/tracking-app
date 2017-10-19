package ve.com.tracking.forms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import ve.com.tracking.core.TipoCargo;
import ve.com.tracking.core.TipoEstatusRecibo;
import ve.com.tracking.core.TipoTransportacion;
import ve.com.tracking.model.Destinos;
import ve.com.tracking.model.Paquete;
import ve.com.tracking.model.ReciboAlmacen;
import ve.com.tracking.model.Users;

public class ReciboAlmacenForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	@Enumerated(EnumType.STRING)
	@NotNull
	private TipoTransportacion tipoTransportacion;

	@NotNull
	private Destinos origen;

	@NotNull
	private Destinos destino;

	private Users cliente;

	@Enumerated(EnumType.STRING)
	@NotNull
	private TipoCargo tipoCargo;

	@DecimalMin("0.0")
	@Digits(integer = 10, fraction = 2)
	@NotNull
	private BigDecimal precioCargo;

	private BigDecimal totalCargo;

	private BigDecimal totalCargoCambio;

	@DecimalMin("0.0")
	@DecimalMax("100.0")
	@NotNull
	private BigDecimal descuento;

	private Calendar created;

	private Users creadorId;

	private List<GuiaForm> guias;

	private List<Paquete> paquetes;

	@Enumerated(EnumType.STRING)
	@NotNull
	private TipoEstatusRecibo estatus;

	public ReciboAlmacenForm() {
		this.tipoTransportacion = TipoTransportacion.AEREO;
		this.estatus = TipoEstatusRecibo.GENERADO;
		this.precioCargo = new BigDecimal("0.0");
		this.descuento = new BigDecimal("0.0");
		this.tipoCargo = TipoCargo.MANUAL;
		this.totalCargo = new BigDecimal("0.0");
		this.totalCargoCambio = new BigDecimal("0.0");
		this.paquetes = new ArrayList<Paquete>();
		this.guias = new ArrayList<GuiaForm>();
	}

	public ReciboAlmacenForm(Long id, TipoTransportacion tipoTransportacion,
			Destinos origen, Destinos destino, Users cliente,
			TipoCargo tipoCargo, BigDecimal precioCargo, BigDecimal totalCargo,
			BigDecimal totalCargoCambio, BigDecimal descuento,
			Calendar created, Users creadorId) {
		super();
		this.id = id;
		this.tipoTransportacion = tipoTransportacion;
		this.origen = origen;
		this.destino = destino;
		this.cliente = cliente;
		this.tipoCargo = tipoCargo;
		this.precioCargo = precioCargo;
		this.totalCargo = totalCargo;
		this.totalCargoCambio = totalCargoCambio;
		this.descuento = descuento;
		this.created = created;
		this.creadorId = creadorId;
	}

	public ReciboAlmacenForm(ReciboAlmacen recibo, List<GuiaForm> guiasForm,
			List<Paquete> paquetes) {
		this.id = recibo.getId();
		this.tipoTransportacion = recibo.getTipoTransportacion();
		this.origen = recibo.getOrigen();
		this.destino = recibo.getDestino();
		this.cliente = recibo.getCliente();
		this.tipoCargo = recibo.getTipoCargo();
		this.precioCargo = recibo.getPrecioCargo();
		this.totalCargo = recibo.getTotalCargo();
		this.totalCargoCambio = recibo.getTotalCargoCambio();
		this.descuento = recibo.getDescuento();
		this.created = recibo.getCreated();
		this.creadorId = recibo.getCreatorId();
		this.estatus = recibo.getEstatus();
		this.guias = guiasForm;
		this.paquetes = paquetes;
	}

	public ReciboAlmacenForm(ReciboAlmacen recibo) {
		this.id = recibo.getId();
		this.tipoTransportacion = recibo.getTipoTransportacion();
		this.origen = recibo.getOrigen();
		this.destino = recibo.getDestino();
		this.cliente = recibo.getCliente();
		this.tipoCargo = recibo.getTipoCargo();
		this.precioCargo = recibo.getPrecioCargo();
		this.totalCargo = recibo.getTotalCargo();
		this.totalCargoCambio = recibo.getTotalCargoCambio();
		this.descuento = recibo.getDescuento();
		this.created = recibo.getCreated();
		this.creadorId = recibo.getCreatorId();
		this.estatus = recibo.getEstatus();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoTransportacion getTipoTransportacion() {
		return tipoTransportacion;
	}

	public void setTipoTransportacion(TipoTransportacion tipoTransportacion) {
		this.tipoTransportacion = tipoTransportacion;
	}

	public Destinos getOrigen() {
		return origen;
	}

	public void setOrigen(Destinos origen) {
		this.origen = origen;
	}

	public Destinos getDestino() {
		return destino;
	}

	public void setDestino(Destinos destino) {
		this.destino = destino;
	}

	public Users getCliente() {
		return cliente;
	}

	public void setCliente(Users cliente) {
		this.cliente = cliente;
	}

	public TipoCargo getTipoCargo() {
		return tipoCargo;
	}

	public void setTipoCargo(TipoCargo tipoCargo) {
		this.tipoCargo = tipoCargo;
	}

	public BigDecimal getPrecioCargo() {
		return precioCargo;
	}

	public void setPrecioCargo(BigDecimal precioCargo) {
		this.precioCargo = precioCargo;
	}

	public BigDecimal getTotalCargo() {
		return totalCargo;
	}

	public void setTotalCargo(BigDecimal totalCargo) {
		this.totalCargo = totalCargo;
	}

	public BigDecimal getTotalCargoCambio() {
		return totalCargoCambio;
	}

	public void setTotalCargoCambio(BigDecimal totalCargoCambio) {
		this.totalCargoCambio = totalCargoCambio;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}

	public Users getCreadorId() {
		return creadorId;
	}

	public void setCreadorId(Users creadorId) {
		this.creadorId = creadorId;
	}

	public List<GuiaForm> getGuias() {
		return guias;
	}

	public void setGuias(List<GuiaForm> guias) {
		this.guias = guias;
	}

	public List<Paquete> getPaquetes() {
		return paquetes;
	}

	public void setPaquetes(List<Paquete> paquetes) {
		this.paquetes = paquetes;
	}

	public BigDecimal getTotalDescuento() {
		BigDecimal totalDescuento = new BigDecimal("0.0");
		BigDecimal total = getTotalCargoSinDescuento();
		totalDescuento = total.multiply(descuento)
				.divide(new BigDecimal("100"));
		totalDescuento.setScale(10, RoundingMode.HALF_UP);
		return totalDescuento;
	}

	/**
	 * De la lista de paquetes utilizados para generar una guía, este metodo
	 * obtiene un mapa donde la clave es el id del paquete y el valor el
	 * tracking y descripcion del mismo.
	 * 
	 * @return
	 */
	public Map<Long, String> getTrackingListAsMap() {
		Map<Long, String> trackings = new HashMap<Long, String>();
		for (Paquete paquete : paquetes) {
			trackings
					.put(paquete.getId(),
							"Tracking ID: "
									+ paquete.getTracking()
									+ " - "
									+ paquete.getDetalleNotificacion()
											.getDescripcion());
		}
		return trackings;
	}

	
	/**
	 * Calcula el total cargo del envío sin descuento
	 * 
	 * @return
	 */
	public BigDecimal getTotalCargoSinDescuento() {
		BigDecimal total = totalCargo;
		switch (tipoCargo) {
			case PESO :
				total = precioCargo.multiply(getTotalPesoGuias());
				break;
			case PESO_VOLUMETRICO :
				total = precioCargo.multiply(getTotalPesoVolumetrico());
				break;
			case VOLUMEN :
				total = precioCargo.multiply(getTotalPiesCubicos());
				break;
			default :
				total = precioCargo;
				total.setScale(10, RoundingMode.HALF_UP);
		}
		return total;
	}

	public BigDecimal getTotalPiesCubicos() {
		BigDecimal totalPeso = new BigDecimal("0.0");
		for (GuiaForm guia : guias) {
			totalPeso = totalPeso.add(guia.getTotalPiesCubicos());
		}
		return totalPeso;
	}

	public Integer getTotalPiezas() {
		Integer total = 0;
		for (GuiaForm guia : guias) {
			total += guia.getPiezas();
		}
		return total;
	}
	public BigDecimal getTotalPesoVolumetrico() {
		BigDecimal totalPeso = new BigDecimal("0.0");
		for (GuiaForm guia : guias) {
			totalPeso = totalPeso.add(guia.getTotalPesoVolumetrico());
		}
		return totalPeso;
	}

	public BigDecimal getTotalPesoGuias() {
		BigDecimal totalPeso = new BigDecimal("0.0");
		for (GuiaForm guia : guias) {
			totalPeso = totalPeso.add(guia.getTotalPeso());
		}
		return totalPeso;
	}

	/**
	 * Cacula el total cargo de la guía con descuento incluido
	 * 
	 * @return
	 */
	public BigDecimal getTotalCargoConDescuento() {
		BigDecimal total = getTotalCargoSinDescuento();
		total = total.subtract(total.multiply(descuento).divide(
				new BigDecimal("100"), RoundingMode.HALF_UP));
		return total;
	}

	public TipoEstatusRecibo getEstatus() {
		return estatus;
	}

	public void setEstatus(TipoEstatusRecibo estatus) {
		this.estatus = estatus;
	}
	
	public String getStringId(){
		return id.toString();
	}
}
