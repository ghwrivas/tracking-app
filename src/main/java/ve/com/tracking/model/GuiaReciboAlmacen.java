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
@Table(name = "guia_recibo_almacen")
@RooJavaBean
@RooJpaEntity(versionField = "", table = "guia_recibo_almacen")
@RooDbManaged(automaticallyDelete = true)
@RooToString(excludeFields = { "guiaId", "reciboAlmacenId" })
public class GuiaReciboAlmacen implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("guiaId", "reciboAlmacenId").toString();
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

    @OneToOne
    @JoinColumn(name = "guia_id", referencedColumnName = "id", nullable = false)
    private Guia guiaId;

    @ManyToOne
    @JoinColumn(name = "recibo_almacen_id", referencedColumnName = "id", nullable = false)
    private ReciboAlmacen reciboAlmacenId;

    public Guia getGuiaId() {
        return guiaId;
    }

    public void setGuiaId(Guia guiaId) {
        this.guiaId = guiaId;
    }

    public ReciboAlmacen getReciboAlmacenId() {
        return reciboAlmacenId;
    }

    public void setReciboAlmacenId(ReciboAlmacen reciboAlmacenId) {
        this.reciboAlmacenId = reciboAlmacenId;
    }
}
