package ve.com.tracking.model;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "tipo_embalaje")
@RooJavaBean
@RooJpaEntity(versionField = "", table = "tipo_embalaje")
@RooDbManaged(automaticallyDelete = true)
@RooToString(excludeFields = { "guias" })
public class TipoEmbalaje implements Serializable {

    private static final long serialVersionUID = 1L;

    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("detalleItems", "guias").toString();
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

    @Column(name = "nombre", length = 45, unique = true)
    @NotNull
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @OneToMany(mappedBy = "tipoEmbalajeId")
    private Set<Guia> guias;

    @Column(name = "largo", precision = 15, scale = 2)
    @NotNull
    private BigDecimal largo;

    @Column(name = "ancho", precision = 15, scale = 2)
    @NotNull
    private BigDecimal ancho;

    @Column(name = "alto", precision = 15, scale = 2)
    @NotNull
    private BigDecimal alto;

    public Set<Guia> getGuias() {
        return guias;
    }

    public void setGuias(Set<Guia> guias) {
        this.guias = guias;
    }

    public BigDecimal getLargo() {
        return largo;
    }

    public void setLargo(BigDecimal largo) {
        this.largo = largo;
    }

    public BigDecimal getAncho() {
        return ancho;
    }

    public void setAncho(BigDecimal ancho) {
        this.ancho = ancho;
    }

    public BigDecimal getAlto() {
        return alto;
    }

    public void setAlto(BigDecimal alto) {
        this.alto = alto;
    }
}
