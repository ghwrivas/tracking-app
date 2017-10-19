package ve.com.tracking.model;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Locale;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;
import ve.com.tracking.core.TipoEstatusPaquete;

@Entity
@Table(name = "paquete")
@RooJavaBean(gettersByDefault = false, settersByDefault = false)
@RooJpaEntity(versionField = "", table = "paquete")
@RooDbManaged(automaticallyDelete = false)
@RooToString(excludeFields = { "detalleNotificacions", "detalleRecepcions", "detalleItems", "estatusPaquetes" })
public class Paquete implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "tracking", length = 100, unique = true)
    @NotNull
    @NotBlank
    @NotEmpty
    private String tracking;

    @Column(name = "recepcionado")
    @NotNull
    private boolean recepcionado;

    @Column(name = "notificado")
    @NotNull
    private boolean notificado;

    @OneToOne(targetEntity = DetalleNotificacion.class, mappedBy = "paqueteId")
    private DetalleNotificacion detalleNotificacion;

    @OneToOne(targetEntity = DetalleRecepcion.class, mappedBy = "paqueteId")
    private DetalleRecepcion detalleRecepcion;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTracking() {
        return tracking;
    }

    public void setTracking(String tracking) {
        this.tracking = tracking;
    }

    public boolean isRecepcionado() {
        return recepcionado;
    }

    public void setRecepcionado(boolean recepcionado) {
        this.recepcionado = recepcionado;
    }

    public boolean isNotificado() {
        return notificado;
    }

    public void setNotificado(boolean notificado) {
        this.notificado = notificado;
    }

    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("detalleNotificacions", "detalleRecepcions", "detalleNotificacion", "detalleItems", "estatusPaquetes").toString();
    }

    public DetalleNotificacion getDetalleNotificacion() {
        return this.detalleNotificacion;
    }

    public void setDetalleNotificacion(DetalleNotificacion detalleNotificacion) {
        this.detalleNotificacion = detalleNotificacion;
    }

    public DetalleRecepcion getDetalleRecepcion() {
        return this.detalleRecepcion;
    }

    public void setDetalleRecepcion(DetalleRecepcion detalleRecepcion) {
        this.detalleRecepcion = detalleRecepcion;
    }

    @OneToMany(mappedBy = "paqueteId")
    private Set<DetalleNotificacion> detalleNotificacions;

    @OneToMany(mappedBy = "paqueteId")
    private Set<DetalleRecepcion> detalleRecepcions;

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

    @Column(name = "asegurar")
    @NotNull
    private boolean asegurar;

    @Column(name = "created", updatable = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar created = java.util.Calendar.getInstance();

    @Column(name = "updated")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar updated;

    public boolean isAsegurar() {
        return asegurar;
    }

    public void setAsegurar(boolean asegurar) {
        this.asegurar = asegurar;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    public Calendar getUpdated() {
        return updated;
    }

    public void setUpdated(Calendar updated) {
        this.updated = updated;
    }

    @OneToMany(mappedBy = "paqueteId")
    private Set<DetalleItem> detalleItems;

    @OneToMany(mappedBy = "paqueteId")
    private Set<EstatusPaquete> estatusPaquetes;

    @Enumerated(EnumType.STRING)
    @Column(name = "estatus", length = 255)
    @NotNull
    private TipoEstatusPaquete estatus;

    public TipoEstatusPaquete getEstatus() {
        return estatus;
    }

    public void setEstatus(TipoEstatusPaquete estatus) {
        this.estatus = estatus;
    }

    public Set<DetalleItem> getDetalleItems() {
        return detalleItems;
    }

    public void setDetalleItems(Set<DetalleItem> detalleItems) {
        this.detalleItems = detalleItems;
    }

    public Set<EstatusPaquete> getEstatusPaquetes() {
        return estatusPaquetes;
    }

    public void setEstatusPaquetes(Set<EstatusPaquete> estatusPaquetes) {
        this.estatusPaquetes = estatusPaquetes;
    }

    public String getLabelAsegurar() {
        Locale locale = LocaleContextHolder.getLocale();
        if (locale.getLanguage().equals("es")) {
            return (asegurar ? "Si" : "No");
        } else {
            return (asegurar ? "Yes" : "No");
        }
    }

    public String getLabelTrackingGenerado() {
        Locale locale = LocaleContextHolder.getLocale();
        if (locale.getLanguage().equals("es")) {
            return (trackingGenerado ? "Si" : "No");
        } else {
            return (trackingGenerado ? "Yes" : "No");
        }
    }

    @Column(name = "tracking_generado")
    @NotNull
    private boolean trackingGenerado;

    public boolean isTrackingGenerado() {
        return trackingGenerado;
    }

    public void setTrackingGenerado(boolean trackingGenerado) {
        this.trackingGenerado = trackingGenerado;
    }
}
