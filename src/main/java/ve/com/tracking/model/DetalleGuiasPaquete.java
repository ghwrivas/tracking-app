package ve.com.tracking.model;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;

@Entity
@Table(name = "detalle_guias_paquete")
@RooJavaBean
@RooToString
@RooJpaEntity(identifierType = DetalleGuiasPaquetePK.class, versionField = "", table = "detalle_guias_paquete")
@RooDbManaged(automaticallyDelete = true)
public class DetalleGuiasPaquete {

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @EmbeddedId
    private DetalleGuiasPaquetePK id;

    public DetalleGuiasPaquetePK getId() {
        return this.id;
    }

    public void setId(DetalleGuiasPaquetePK id) {
        this.id = id;
    }
}
