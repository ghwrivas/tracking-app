package ve.com.tracking.model;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;
import ve.com.tracking.core.TipoCargo;
import ve.com.tracking.core.TipoEstatusRecibo;
import ve.com.tracking.core.TipoTransportacion;
import ve.com.tracking.forms.ReciboAlmacenForm;

@Entity
@Table(name = "recibo_almacen")
@RooJavaBean
@RooJpaEntity(versionField = "", table = "recibo_almacen")
@RooDbManaged(automaticallyDelete = true)
@RooToString(excludeFields = { "guiaReciboAlmacens", "creatorId", "cliente", "origen", "destino" })
public class ReciboAlmacen implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "reciboAlmacenId")
    private Set<GuiaReciboAlmacen> guiaReciboAlmacens;

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id", nullable = false)
    private Users creatorId;

    @ManyToOne
    @JoinColumn(name = "cliente", referencedColumnName = "id", nullable = false)
    private Users cliente;

    @ManyToOne
    @JoinColumn(name = "origen", referencedColumnName = "id", nullable = false)
    private Destinos origen;

    @ManyToOne
    @JoinColumn(name = "destino", referencedColumnName = "id", nullable = false)
    private Destinos destino;

    @Column(name = "tipo_transportacion", length = 100)
    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoTransportacion tipoTransportacion;

    @Column(name = "tipo_cargo", length = 45)
    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoCargo tipoCargo;

    @Column(name = "precio_cargo", precision = 15, scale = 2)
    @NotNull
    private BigDecimal precioCargo;

    @Column(name = "total_cargo", precision = 15, scale = 2)
    @NotNull
    private BigDecimal totalCargo;

    @Column(name = "descuento", precision = 5, scale = 2)
    @NotNull
    private BigDecimal descuento;

    @Column(name = "total_cargo_cambio", precision = 15, scale = 2)
    @NotNull
    private BigDecimal totalCargoCambio;

    @Column(name = "estatus", length = 45)
    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoEstatusRecibo estatus;

    @Column(name = "created", updatable = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar created = java.util.Calendar.getInstance();

    public ReciboAlmacen() {
    }

    public ReciboAlmacen(Users creatorId, Users cliente, Destinos origen, Destinos destino, TipoTransportacion tipoTransportacion, TipoCargo tipoCargo, BigDecimal precioCargo, BigDecimal totalCargo, BigDecimal descuento, BigDecimal totalCargoCambio, Calendar created, Long id) {
        super();
        this.creatorId = creatorId;
        this.cliente = cliente;
        this.origen = origen;
        this.destino = destino;
        this.tipoTransportacion = tipoTransportacion;
        this.tipoCargo = tipoCargo;
        this.precioCargo = precioCargo;
        this.totalCargo = totalCargo;
        this.descuento = descuento;
        this.totalCargoCambio = totalCargoCambio;
        this.created = created;
        this.id = id;
    }

    public ReciboAlmacen(ReciboAlmacenForm recibo) {
        this.id = recibo.getId();
        this.tipoTransportacion = recibo.getTipoTransportacion();
        this.origen = recibo.getOrigen();
        this.destino = recibo.getDestino();
        this.cliente = recibo.getCliente();
        this.tipoCargo = recibo.getTipoCargo();
        this.precioCargo = recibo.getPrecioCargo();
        this.totalCargo = recibo.getTotalCargo();
        this.totalCargoCambio = recibo.getTotalCargoCambio();
        this.descuento = recibo.getDescuento();
        this.created = recibo.getCreated();
        this.creatorId = recibo.getCreadorId();
        this.estatus = recibo.getEstatus();
    }

    public Set<GuiaReciboAlmacen> getGuiaReciboAlmacens() {
        return guiaReciboAlmacens;
    }

    public void setGuiaReciboAlmacens(Set<GuiaReciboAlmacen> guiaReciboAlmacens) {
        this.guiaReciboAlmacens = guiaReciboAlmacens;
    }

    public Users getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Users creatorId) {
        this.creatorId = creatorId;
    }

    public Users getCliente() {
        return cliente;
    }

    public void setCliente(Users cliente) {
        this.cliente = cliente;
    }

    public Destinos getOrigen() {
        return origen;
    }

    public void setOrigen(Destinos origen) {
        this.origen = origen;
    }

    public Destinos getDestino() {
        return destino;
    }

    public void setDestino(Destinos destino) {
        this.destino = destino;
    }

    public TipoTransportacion getTipoTransportacion() {
        return tipoTransportacion;
    }

    public void setTipoTransportacion(TipoTransportacion tipoTransportacion) {
        this.tipoTransportacion = tipoTransportacion;
    }

    public TipoCargo getTipoCargo() {
        return tipoCargo;
    }

    public void setTipoCargo(TipoCargo tipoCargo) {
        this.tipoCargo = tipoCargo;
    }

    public BigDecimal getPrecioCargo() {
        return precioCargo;
    }

    public void setPrecioCargo(BigDecimal precioCargo) {
        this.precioCargo = precioCargo;
    }

    public BigDecimal getTotalCargo() {
        return totalCargo;
    }

    public void setTotalCargo(BigDecimal totalCargo) {
        this.totalCargo = totalCargo;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getTotalCargoCambio() {
        return totalCargoCambio;
    }

    public void setTotalCargoCambio(BigDecimal totalCargoCambio) {
        this.totalCargoCambio = totalCargoCambio;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
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
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("guiaReciboAlmacens", "creatorId", "cliente", "origen", "destino").toString();
    }

    public TipoEstatusRecibo getEstatus() {
        return this.estatus;
    }

    public void setEstatus(TipoEstatusRecibo estatus) {
        this.estatus = estatus;
    }
}
