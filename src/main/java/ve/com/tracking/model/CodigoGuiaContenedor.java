package ve.com.tracking.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;

@Entity
@Table(name = "codigo_guia_contenedor")
@RooJavaBean
@RooJpaEntity(versionField = "", table = "codigo_guia_contenedor")
@RooDbManaged(automaticallyDelete = true)
@RooToString(excludeFields = { "contenedorId", "codigoGuiaId" })
public class CodigoGuiaContenedor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "contenedor_id", referencedColumnName = "id", nullable = false)
	private Contenedor contenedorId;

	@OneToOne
	@JoinColumn(name = "codigo_guia_id", referencedColumnName = "id", nullable = false)
	private CodigoGuia codigoGuiaId;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Contenedor getContenedorId() {
		return contenedorId;
	}

	public void setContenedorId(Contenedor contenedorId) {
		this.contenedorId = contenedorId;
	}

	public CodigoGuia getGuiaId() {
		return codigoGuiaId;
	}

	public void setGuiaId(CodigoGuia codigoGuiaId) {
		this.codigoGuiaId = codigoGuiaId;
	}

	public String toString() {
		return new ReflectionToStringBuilder(this,
				ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames(
				"contenedorId", "codigoGuiaId").toString();
	}

}
