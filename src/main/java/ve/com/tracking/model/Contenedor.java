package ve.com.tracking.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;

import ve.com.tracking.core.TipoEstatusContenedor;
import ve.com.tracking.services.GuiaService;

@Entity
@Table(name = "contenedor")
@RooJavaBean
@RooJpaEntity(versionField = "", table = "contenedor")
@RooDbManaged(automaticallyDelete = true)
@RooToString(excludeFields = { "estatusContenedors", "guiaContenedors" })
public class Contenedor implements Serializable {

	private static final long serialVersionUID = 1L;

	public String toString() {
		return new ReflectionToStringBuilder(this,
				ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames(
				"estatusContenedors", "guiaContenedors").toString();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToMany(mappedBy = "contenedorId", cascade = CascadeType.REMOVE)
	private Set<EstatusContenedor> estatusContenedors;

	@OneToMany(mappedBy = "contenedorId", cascade = CascadeType.REMOVE)
	private Set<CodigoGuiaContenedor> guiaContenedors;

	@Column(name = "codigo", length = 100, unique = true)
	private String codigo;

	@Column(name = "descripcion", length = 100)
	private String descripcion;

	@Enumerated(EnumType.STRING)
	@Column(name = "estatus", length = 255)
	private TipoEstatusContenedor estatus;

	@Transient
	private List<CodigoGuia> guias = new ArrayList<CodigoGuia>();

	@Autowired
	@Transient
	private GuiaService guiaService;

	public TipoEstatusContenedor getEstatus() {
		return estatus;
	}

	public void setEstatus(TipoEstatusContenedor estatus) {
		this.estatus = estatus;
	}

	@Column(name = "created", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "MM")
	private Calendar created;

	@Column(name = "updated")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "MM")
	private Calendar updated;

	public List<CodigoGuia> getGuias() {
		return guias;
	}

	public void setGuias(List<CodigoGuia> guias) {
		this.guias = guias;
	}

	public boolean addGuia(CodigoGuia guia) {
		return this.guias.add(guia);
	}

	public CodigoGuia removeGuia(int index) {
		return this.guias.remove(index);
	}

	public boolean hasGuia(Long codigoGuiaId) {
		boolean existe = false;
		Iterator<CodigoGuia> it = guias.iterator();
		while (it.hasNext() && !existe) {
			CodigoGuia guia = (CodigoGuia) it.next();
			if (guia.getId().equals(codigoGuiaId)) {
				existe = true;
			}
		}
		return existe;
	}

	public Set<EstatusContenedor> getEstatusContenedors() {
		return estatusContenedors;
	}

	public void setEstatusContenedors(Set<EstatusContenedor> estatusContenedors) {
		this.estatusContenedors = estatusContenedors;
	}

	public Set<CodigoGuiaContenedor> getGuiaContenedors() {
		return guiaContenedors;
	}

	public void setGuiaContenedors(Set<CodigoGuiaContenedor> guiaContenedors) {
		this.guiaContenedors = guiaContenedors;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}

	public Calendar getUpdated() {
		return updated;
	}

	public void setUpdated(Calendar updated) {
		this.updated = updated;
	}

}
