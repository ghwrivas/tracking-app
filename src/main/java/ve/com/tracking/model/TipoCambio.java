package ve.com.tracking.model;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;

@Entity
@Table(name = "tipo_cambio")
@RooJavaBean
@RooJpaEntity(versionField = "", table = "tipo_cambio")
@RooDbManaged(automaticallyDelete = true)
@RooToString(excludeFields = { "country" })
public class TipoCambio implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "country", referencedColumnName = "iso_alpha2", nullable = false)
    private Countries country;

    @Column(name = "valor_dolar", precision = 15, scale = 2)
    @NotNull
    private BigDecimal valorDolar;

    public Countries getCountry() {
        return country;
    }

    public void setCountry(Countries country) {
        this.country = country;
    }

    public BigDecimal getValorDolar() {
        return valorDolar;
    }

    public void setValorDolar(BigDecimal valorDolar) {
        this.valorDolar = valorDolar;
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

    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("country").toString();
    }
}
