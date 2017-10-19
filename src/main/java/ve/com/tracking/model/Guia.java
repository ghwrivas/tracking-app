package ve.com.tracking.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;

@Entity
@Table(name = "guia")
@RooJavaBean
@RooJpaEntity(versionField = "", table = "guia")
@RooDbManaged(automaticallyDelete = true)
@RooToString(excludeFields = { "detalleGuias", "creadorId", "tipoEmbalajeId",
		"guiaReciboAlmacens", "cliente", "codigoGuias" })
public class Guia implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "tipo_embalaje_id", referencedColumnName = "id", nullable = false)
	private TipoEmbalaje tipoEmbalajeId;

	@Column(name = "peso", precision = 15, scale = 2)
	@NotNull
	private BigDecimal peso;

	@Column(name = "largo", precision = 15, scale = 2)
	@NotNull
	private BigDecimal largo;

	@Column(name = "ancho", precision = 15, scale = 2)
	@NotNull
	private BigDecimal ancho;

	@Column(name = "alto", precision = 15, scale = 2)
	@NotNull
	private BigDecimal alto;

	@Column(name = "volumen", precision = 15, scale = 2)
	@NotNull
	private BigDecimal volumen;

	@Column(name = "peso_volumetrico", precision = 15, scale = 2)
	@NotNull
	private BigDecimal pesoVolumetrico;

	@Column(name = "created", updatable = false)
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "MM")
	private Calendar created = java.util.Calendar.getInstance();

	@ManyToOne
	@JoinColumn(name = "creador_id", referencedColumnName = "id", nullable = false)
	private Users creadorId;

	@ManyToOne
	@JoinColumn(name = "cliente", referencedColumnName = "id", nullable = false)
	private Users cliente;

	@OneToMany(mappedBy = "guiaId", cascade=CascadeType.REMOVE)
	private Set<DetalleGuia> detalleGuias;

	@OneToOne(mappedBy = "guiaId", cascade=CascadeType.REMOVE)
	private GuiaReciboAlmacen guiaReciboAlmacen;

	@OneToMany(mappedBy = "guiaId", fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
	private Set<CodigoGuia> codigoGuias;

	public Guia(Long id, BigDecimal peso, BigDecimal largo, BigDecimal ancho,
			BigDecimal alto, BigDecimal volumen, BigDecimal pesoVolumetrico,
			Calendar created, Users creadorId, TipoEmbalaje tipoEmbalajeId) {
		this.id = id;
		this.peso = peso;
		this.largo = largo;
		this.ancho = ancho;
		this.alto = alto;
		this.volumen = volumen;
		this.pesoVolumetrico = pesoVolumetrico;
		this.created = created;
		this.creadorId = creadorId;
		this.tipoEmbalajeId = tipoEmbalajeId;
	}

	public Guia() {
	}

	public GuiaReciboAlmacen getGuiaReciboAlmacen() {
		return guiaReciboAlmacen;
	}

	public void setGuiaReciboAlmacen(GuiaReciboAlmacen guiaReciboAlmacen) {
		this.guiaReciboAlmacen = guiaReciboAlmacen;
	}

	public Set<DetalleGuia> getDetalleGuias() {
		return detalleGuias;
	}

	public void setDetalleGuias(Set<DetalleGuia> detalleGuias) {
		this.detalleGuias = detalleGuias;
	}

	public Users getCreadorId() {
		return creadorId;
	}

	public void setCreadorId(Users creadorId) {
		this.creadorId = creadorId;
	}

	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}

	public void setVolumen(BigDecimal volumen) {
		this.volumen = volumen;
	}

	public void setPesoVolumetrico(BigDecimal pesoVolumetrico) {
		this.pesoVolumetrico = pesoVolumetrico;
	}

	public TipoEmbalaje getTipoEmbalajeId() {
		return tipoEmbalajeId;
	}

	public void setTipoEmbalajeId(TipoEmbalaje tipoEmbalajeId) {
		this.tipoEmbalajeId = tipoEmbalajeId;
	}

	public BigDecimal getPeso() {
		return peso;
	}

	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

	public BigDecimal getLargo() {
		return largo;
	}

	public void setLargo(BigDecimal largo) {
		this.largo = largo;
	}

	public BigDecimal getAncho() {
		return ancho;
	}

	public void setAncho(BigDecimal ancho) {
		this.ancho = ancho;
	}

	public BigDecimal getAlto() {
		return alto;
	}

	public void setAlto(BigDecimal alto) {
		this.alto = alto;
	}

	public BigDecimal getVolumen() {
		return volumen;
	}

	public BigDecimal getPesoVolumetrico() {
		return pesoVolumetrico;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String toString() {
		return new ReflectionToStringBuilder(this,
				ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames(
				"detalleGuias", "creadorId", "tipoEmbalajeId",
				"guiaReciboAlmacens", "cliente", "codigoGuias").toString();
	}

	public Users getCliente() {
		return cliente;
	}

	public void setCliente(Users cliente) {
		this.cliente = cliente;
	}

	public Set<CodigoGuia> getCodigoGuias() {
		return codigoGuias;
	}

	public void setCodigoGuias(Set<CodigoGuia> codigoGuias) {
		this.codigoGuias = codigoGuias;
	}


}
