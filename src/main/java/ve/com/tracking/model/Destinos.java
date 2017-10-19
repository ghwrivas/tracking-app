package ve.com.tracking.model;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "destinos")
@RooJavaBean
@RooJpaEntity(versionField = "", table = "destinos")
@RooDbManaged(automaticallyDelete = true)
@RooToString(excludeFields = { "pais", "reciboAlmacens", "reciboAlmacens1" })
public class Destinos implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "pais", referencedColumnName = "iso_alpha2", nullable = false)
    private Countries pais;

    @Column(name = "ciudad", length = 45, unique = true)
    @NotNull
    private String ciudad;

    public Countries getPais() {
        return pais;
    }

    public void setPais(Countries pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("pais", "reciboAlmacens", "reciboAlmacens1").toString();
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

    @OneToMany(mappedBy = "origen")
    private Set<ReciboAlmacen> reciboAlmacens;

    @OneToMany(mappedBy = "destino")
    private Set<ReciboAlmacen> reciboAlmacens1;

    public Set<ReciboAlmacen> getReciboAlmacens() {
        return reciboAlmacens;
    }

    public void setReciboAlmacens(Set<ReciboAlmacen> reciboAlmacens) {
        this.reciboAlmacens = reciboAlmacens;
    }

    public Set<ReciboAlmacen> getReciboAlmacens1() {
        return reciboAlmacens1;
    }

    public void setReciboAlmacens1(Set<ReciboAlmacen> reciboAlmacens1) {
        this.reciboAlmacens1 = reciboAlmacens1;
    }
}
