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
@Table(name = "guias")
@RooJavaBean
@RooToString
@RooJpaEntity(identifierType = GuiasPK.class, versionField = "", table = "guias")
@RooDbManaged(automaticallyDelete = true)
public class Guias {

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @EmbeddedId
    private GuiasPK id;

    public GuiasPK getId() {
        return this.id;
    }

    public void setId(GuiasPK id) {
        this.id = id;
    }
}
