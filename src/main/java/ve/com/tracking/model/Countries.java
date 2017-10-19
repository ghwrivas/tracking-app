package ve.com.tracking.model;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;

@Entity
@Table(name = "countries")
@RooJavaBean
@RooJpaEntity(versionField = "", table = "countries")
@RooDbManaged(automaticallyDelete = true)
@RooToString(excludeFields = { "tipoCambios", "destinoss" })
public class Countries implements Serializable {

    private static final long serialVersionUID = 1L;

    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("tipoCambios", "destinoss").toString();
    }

    @Id
    @Column(name = "iso_alpha2", length = 2)
    private String isoAlpha2;

    public String getIsoAlpha2() {
        return this.isoAlpha2;
    }

    public void setIsoAlpha2(String id) {
        this.isoAlpha2 = id;
    }

    @OneToMany(mappedBy = "country")
    private Set<TipoCambio> tipoCambios;

    @OneToOne(mappedBy = "country")
    private TipoCambio tipoCambio;

    public TipoCambio getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(TipoCambio tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    @Column(name = "name", length = 200)
    @NotNull
    private String name;

    @Column(name = "iso_alpha3", length = 3)
    @NotNull
    private String isoAlpha3;

    @Column(name = "iso_numeric")
    @NotNull
    private Integer isoNumeric;

    @Column(name = "currency_code", length = 3)
    @NotNull
    private String currencyCode;

    @Column(name = "currency_name", length = 32)
    private String currencyName;

    @Column(name = "currrency_symbol", length = 3)
    private String currrencySymbol;

    @Column(name = "flag", length = 6)
    private String flag;

    public Set<TipoCambio> getTipoCambios() {
        return tipoCambios;
    }

    public void setTipoCambios(Set<TipoCambio> tipoCambios) {
        this.tipoCambios = tipoCambios;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsoAlpha3() {
        return isoAlpha3;
    }

    public void setIsoAlpha3(String isoAlpha3) {
        this.isoAlpha3 = isoAlpha3;
    }

    public Integer getIsoNumeric() {
        return isoNumeric;
    }

    public void setIsoNumeric(Integer isoNumeric) {
        this.isoNumeric = isoNumeric;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrrencySymbol() {
        return currrencySymbol;
    }

    public void setCurrrencySymbol(String currrencySymbol) {
        this.currrencySymbol = currrencySymbol;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @OneToMany(mappedBy = "pais")
    private Set<Destinos> destinoss;

    public Set<Destinos> getDestinoss() {
        return destinoss;
    }

    public void setDestinoss(Set<Destinos> destinoss) {
        this.destinoss = destinoss;
    }
}
