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
@Table(name = "detalle_guia")
@RooJavaBean
@RooJpaEntity(versionField = "", table = "detalle_guia")
@RooDbManaged(automaticallyDelete = true)
@RooToString(excludeFields = { "guiaId", "detalleItemId" })
public class DetalleGuia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "guia_id", referencedColumnName = "id", nullable = false)
    private Guia guiaId;

    @OneToOne
    @JoinColumn(name = "detalle_item_id", referencedColumnName = "id", nullable = false)
    private DetalleItem detalleItemId;

    public Guia getGuiaId() {
        return guiaId;
    }

    public void setGuiaId(Guia guiaId) {
        this.guiaId = guiaId;
    }

    public DetalleItem getDetalleItemId() {
        return detalleItemId;
    }

    public void setDetalleItemId(DetalleItem detalleItemId) {
        this.detalleItemId = detalleItemId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("guiaId", "detalleItemId").toString();
    }
}
