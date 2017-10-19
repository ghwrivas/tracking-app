/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.forms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Transient;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import ve.com.tracking.core.Constantes;
import ve.com.tracking.model.Guia;
import ve.com.tracking.model.Paquete;
import ve.com.tracking.model.TipoEmbalaje;
import ve.com.tracking.model.Users;

/**
 * Formulario utilizado para cargar los datos de la Guía al momento de creación
 * y edición.
 * 
 * @author Williams Rivas
 * 
 *         Created 01/05/2014 10:49:34
 */
public class GuiaForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	/**
	 * Utilizado a nivel de negocio para modificar la cantidad de piezas que
	 * representaran la cantidad de codigos guias a ser generados
	 */
	@NotNull
	@Min(value = 1)
	private Integer piezas;

	@NotNull
	private TipoEmbalaje tipoEmbalajeId;

	@DecimalMin("0.1")
	@Digits(integer = 10, fraction = 2)
	@NotNull
	private BigDecimal peso;

	@DecimalMin("0.1")
	@Digits(integer = 10, fraction = 2)
	@NotNull
	private BigDecimal largo;

	@DecimalMin("0.1")
	@Digits(integer = 10, fraction = 2)
	@NotNull
	private BigDecimal ancho;

	@DecimalMin("0.1")
	@Digits(integer = 10, fraction = 2)
	@NotNull
	private BigDecimal alto;

	private BigDecimal volumen;

	private BigDecimal pesoVolumetrico;

	private Calendar created = java.util.Calendar.getInstance();

	private Users creadorId;

	private Users cliente;

	private List<ItemForm> items;

	private List<Paquete> paquetes;

	/**
	 * Utilizado para controlar la guia a editar y/o eliminar en el listado de
	 * guias en el recibo de almacen
	 */
	@Transient
	private int index;

	public GuiaForm() {
		this.volumen = new BigDecimal("0.0");
		this.pesoVolumetrico = new BigDecimal("0.0");
		this.items = new ArrayList<ItemForm>();
		this.paquetes = new ArrayList<Paquete>();
		this.peso = new BigDecimal("0.0");
		this.piezas = 0;
		this.largo = new BigDecimal("0.0");
		this.ancho = new BigDecimal("0.0");
		this.alto = new BigDecimal("0.0");
		this.piezas = 0;
	}

	/**
	 * Adapta la Guia pasada como parametro a GuiaForm.
	 * 
	 * @param guia
	 * @throws IllegalArgumentException
	 *             si el objeto guia pasado como parametro no ha sido persistido
	 *             aun en la bd.
	 */
	public GuiaForm(Guia guia, List<ItemForm> items, Integer piezas)
			throws IllegalArgumentException {
		if (guia == null || guia.getId() == null) {
			throw new IllegalArgumentException(
					"Para este tipo de contructor, el objeto Guia pasado como parametro debe estar persistido");
		}
		setPiezas(piezas);
		setId(guia.getId());
		setTipoEmbalajeId(guia.getTipoEmbalajeId());
		setPeso(guia.getPeso());
		setLargo(guia.getLargo());
		setAncho(guia.getAncho());
		setAlto(guia.getAlto());
		setVolumen(guia.getVolumen());
		setPesoVolumetrico(guia.getPesoVolumetrico());
		setCreated(guia.getCreated());
		setCreadorId(guia.getCreadorId());
		setItems(items);
	}

	public GuiaForm(Guia guia, List<ItemForm> items, List<Paquete> paquetes,
			Integer piezas) {
		if (guia == null || guia.getId() == null) {
			throw new IllegalArgumentException(
					"Para este tipo de contructor, el objeto Guia pasado como parametro debe estar persistido");
		}
		setPiezas(piezas);
		setId(guia.getId());
		setTipoEmbalajeId(guia.getTipoEmbalajeId());
		setPeso(guia.getPeso());
		setLargo(guia.getLargo());
		setAncho(guia.getAncho());
		setAlto(guia.getAlto());
		setVolumen(guia.getVolumen());
		setPesoVolumetrico(guia.getPesoVolumetrico());
		setCreated(guia.getCreated());
		setCreadorId(guia.getCreadorId());
		setItems(items);
		setPaquetes(paquetes);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getPeso() {
		return peso;
	}

	public void setPeso(BigDecimal peso) {
		this.peso = peso.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getLargo() {
		return largo;
	}

	public void setLargo(BigDecimal largo) {
		this.largo = largo.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getAncho() {
		return ancho;
	}

	public void setAncho(BigDecimal ancho) {
		this.ancho = ancho.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getAlto() {
		return alto;
	}

	public void setAlto(BigDecimal alto) {
		this.alto = alto.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getVolumen() {
		volumen = getTotalPiesCubicos();
		return volumen;
	}

	public void setVolumen(BigDecimal volumen) {
		this.volumen = volumen;
	}

	public BigDecimal getPesoVolumetrico() {
		pesoVolumetrico = getTotalPesoVolumetrico();
		return pesoVolumetrico;
	}

	public void setPesoVolumetrico(BigDecimal pesoVolumetrico) {
		this.pesoVolumetrico = pesoVolumetrico;
	}

	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}

	public TipoEmbalaje getTipoEmbalajeId() {
		return tipoEmbalajeId;
	}

	public void setTipoEmbalajeId(TipoEmbalaje tipoEmbalajeId) {
		this.tipoEmbalajeId = tipoEmbalajeId;
	}

	public Users getCreadorId() {
		return creadorId;
	}

	public void setCreadorId(Users creadorId) {
		this.creadorId = creadorId;
	}

	public List<ItemForm> getItems() {
		return items;
	}

	public void setItems(List<ItemForm> items) {
		this.items = items;
	}

	/**
	 * Obtiene el total calculado del peso volumétrico
	 * 
	 * @return
	 */
	public BigDecimal getTotalPesoVolumetrico() {
		BigDecimal total = new BigDecimal("0.0");
		total = largo
				.multiply(alto)
				.multiply(ancho)
				.divide(new BigDecimal(Constantes.DIVISOR_PESO_VOLUMETRICO),
						RoundingMode.HALF_UP);
		return total.multiply(new BigDecimal(piezas)).setScale(2,
				RoundingMode.HALF_UP);
	}

	/**
	 * Obtiene el total de pies cúbicos.
	 * 
	 * @return
	 */
	public BigDecimal getTotalPiesCubicos() {
		BigDecimal total = new BigDecimal("0.0");
		total = largo
				.multiply(alto)
				.multiply(ancho)
				.divide(new BigDecimal(Constantes.DIVISOR_PIE_CUBICO),
						RoundingMode.HALF_UP);
		return total.multiply(new BigDecimal(piezas)).setScale(2,
				RoundingMode.HALF_UP);
	}

	public BigDecimal getTotalPeso() {
		BigDecimal total = new BigDecimal("0.0");
		total = peso.multiply(new BigDecimal(piezas)).setScale(2,
				RoundingMode.HALF_UP);
		return total;
	}

	public void addItem(ItemForm item) {
		items.add(item);
	}

	/**
	 * Busca si existe un item en el listado de items generados para una guía si
	 * posee un paquete con el id pasado como parametro.
	 * 
	 * @param paqueteId
	 * @return
	 */
	public boolean hasItemByPaqueteId(Long paqueteId) {
		boolean existe = false;
		Iterator<ItemForm> it = items.iterator();
		while (it.hasNext() && !existe) {
			ItemForm item = it.next();
			if (item.getPaqueteId().getId().equals(paqueteId)) {
				existe = true;
			}
		}
		return existe;
	}

	public List<Paquete> getPaquetesToProcesar(){
		List<Paquete> paquetesToProcesar = new ArrayList<Paquete>();
		for(Paquete paquete : getPaquetes()){
			if(hasItemByPaqueteId(paquete.getId())){
				paquetesToProcesar.add(paquete);
			}
		}
		return paquetesToProcesar;
	}
	
	public List<Paquete> getPaquetes() {
		return paquetes;
	}

	public void setPaquetes(List<Paquete> paquetes) {
		this.paquetes = paquetes;
	}

	public Users getCliente() {
		return cliente;
	}

	public void setCliente(Users cliente) {
		this.cliente = cliente;
	}

	public Integer getPiezas() {
		return piezas;
	}

	public void setPiezas(Integer piezas) {
		this.piezas = piezas;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getTrackingItems(){
		String trackings = "";
		Iterator<ItemForm> it = getItems().iterator();
		while(it.hasNext()){
			ItemForm item = it.next();
			trackings += item.getPaqueteId().getTracking()+"-"+item.getDescripcion();
			if(it.hasNext())
				trackings+="</br>";
		}	
		return trackings;
		
	}
}
