package ve.com.tracking.model;
import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;

@Entity
@Table(name = "detalle_item")
@RooJavaBean
@RooJpaEntity(versionField = "", table = "detalle_item")
@RooDbManaged(automaticallyDelete = true)
@RooToString(excludeFields = { "detalleGuiaId", "categoriaDetalleId", "paqueteId", "detalleGuias" })
public class DetalleItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToOne(mappedBy = "detalleItemId", cascade=CascadeType.REMOVE)
    private DetalleGuia detalleGuiaId;

    @ManyToOne
    @JoinColumn(name = "categoria_detalle_id", referencedColumnName = "id", nullable = false)
    private CategoriaDetalle categoriaDetalleId;

    @ManyToOne
    @JoinColumn(name = "paquete_id", referencedColumnName = "id", nullable = false)
    private Paquete paqueteId;

    @Column(name = "descripcion", length = 255)
    @NotNull
    private String descripcion;

    public DetalleItem() {
    }

    public DetalleItem(CategoriaDetalle categoriaDetalleId, Paquete paqueteId, String descripcion) {
        super();
        this.categoriaDetalleId = categoriaDetalleId;
        this.paqueteId = paqueteId;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DetalleGuia getDetalleGuias() {
        return detalleGuiaId;
    }

    public void setDetalleGuias(DetalleGuia detalle) {
        this.detalleGuiaId = detalle;
    }

    public CategoriaDetalle getCategoriaDetalleId() {
        return categoriaDetalleId;
    }

    public void setCategoriaDetalleId(CategoriaDetalle categoriaDetalleId) {
        this.categoriaDetalleId = categoriaDetalleId;
    }

    public Paquete getPaqueteId() {
        return paqueteId;
    }

    public void setPaqueteId(Paquete paqueteId) {
        this.paqueteId = paqueteId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("detalleGuiaId", "categoriaDetalleId", "paqueteId").toString();
    }

    @OneToMany(mappedBy = "detalleItemId")
    private Set<DetalleGuia> detalleGuias;

    public void setDetalleGuias(Set<DetalleGuia> detalleGuias) {
        this.detalleGuias = detalleGuias;
    }

    public DetalleGuia getDetalleGuiaId() {
        return this.detalleGuiaId;
    }

    public void setDetalleGuiaId(DetalleGuia detalleGuiaId) {
        this.detalleGuiaId = detalleGuiaId;
    }
}
