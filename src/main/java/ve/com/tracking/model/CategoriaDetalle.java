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
@Table(name = "categoria_detalle")
@RooJavaBean
@RooJpaEntity(versionField = "", table = "categoria_detalle")
@RooDbManaged(automaticallyDelete = true)
@RooToString(excludeFields = { "detalleItems" })
public class CategoriaDetalle implements Serializable {

    private static final long serialVersionUID = 1L;

    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("detalleItems").toString();
    }

    @OneToMany(mappedBy = "categoriaDetalleId")
    private Set<DetalleItem> detalleItems;

    @Column(name = "nombre", length = 45, unique = true)
    @NotNull
    private String nombre;

    public Set<DetalleItem> getDetalleItems() {
        return detalleItems;
    }

    public void setDetalleItems(Set<DetalleItem> detalleItems) {
        this.detalleItems = detalleItems;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
}
