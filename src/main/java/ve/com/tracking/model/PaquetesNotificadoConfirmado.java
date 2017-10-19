package ve.com.tracking.model;
import java.io.Serializable;
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
@Table(name = "paquetes_notificado_confirmado")
@RooJavaBean
@RooToString
@RooJpaEntity(identifierType = PaquetesNotificadoConfirmadoPK.class, versionField = "", table = "paquetes_notificado_confirmado")
@RooDbManaged(automaticallyDelete = true)
public class PaquetesNotificadoConfirmado implements Serializable {

    private static final long serialVersionUID = 1L;

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @EmbeddedId
    private PaquetesNotificadoConfirmadoPK id;

    public PaquetesNotificadoConfirmadoPK getId() {
        return this.id;
    }

    public void setId(PaquetesNotificadoConfirmadoPK id) {
        this.id = id;
    }
}
