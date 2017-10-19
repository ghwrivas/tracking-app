package ve.com.tracking.model;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;

@Entity
@Table(name = "empresa_envio")
@RooJavaBean
@RooJpaEntity(versionField = "", table = "empresa_envio")
@RooDbManaged(automaticallyDelete = true)
@RooToString(excludeFields = { "detalleNotificacions", "detalleRecepcions" })
public class EmpresaEnvio implements Serializable {

    private static final long serialVersionUID = 1L;

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

    @OneToMany(mappedBy = "empresaEnvioId")
    private Set<DetalleNotificacion> detalleNotificacions;

    @OneToMany(mappedBy = "empresaEnvioId")
    private Set<DetalleRecepcion> detalleRecepcions;

    @Column(name = "name", length = 45, unique = true)
    @NotNull
    private String name;

    public Set<DetalleNotificacion> getDetalleNotificacions() {
        return detalleNotificacions;
    }

    public void setDetalleNotificacions(Set<DetalleNotificacion> detalleNotificacions) {
        this.detalleNotificacions = detalleNotificacions;
    }

    public Set<DetalleRecepcion> getDetalleRecepcions() {
        return detalleRecepcions;
    }

    public void setDetalleRecepcions(Set<DetalleRecepcion> detalleRecepcions) {
        this.detalleRecepcions = detalleRecepcions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("detalleNotificacions", "detalleRecepcions").toString();
    }
}
