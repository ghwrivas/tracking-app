package ve.com.tracking.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import ve.com.tracking.core.TipoEstatusGuia;

@Entity
@Table(name = "codigo_guia")
public class CodigoGuia implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(nullable = false, name = "codigo", unique = true)
	private String codigo;

	@Enumerated(EnumType.STRING)
	@Column(name = "estatus", length = 255)
	@NotNull
	private TipoEstatusGuia estatus;

	@ManyToOne
	@JoinColumn(name = "guia_id", referencedColumnName = "id", nullable = false)
	private Guia guiaId;

	@OneToMany(mappedBy = "codigoGuiaId", cascade=CascadeType.REMOVE)
	private Set<EstatusGuia> estatusCodigoGuias;

	@OneToOne(mappedBy = "codigoGuiaId", cascade=CascadeType.REMOVE)
	private CodigoGuiaContenedor codigoGuiaContenedor;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public TipoEstatusGuia getEstatus() {
		return estatus;
	}

	public void setEstatus(TipoEstatusGuia estatus) {
		this.estatus = estatus;
	}

	public Guia getGuiaId() {
		return guiaId;
	}

	public void setGuiaId(Guia guiaId) {
		this.guiaId = guiaId;
	}

	public Set<EstatusGuia> getEstatusCodigoGuias() {
		return estatusCodigoGuias;
	}

	public void setEstatusCodigoGuias(Set<EstatusGuia> estatusCodigoGuias) {
		this.estatusCodigoGuias = estatusCodigoGuias;
	}

	public CodigoGuiaContenedor getCodigoGuiaContenedor() {
		return codigoGuiaContenedor;
	}

	public void setCodigoGuiaContenedor(
			CodigoGuiaContenedor codigoGuiaContenedor) {
		this.codigoGuiaContenedor = codigoGuiaContenedor;
	}

}
