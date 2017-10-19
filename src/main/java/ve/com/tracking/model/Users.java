package ve.com.tracking.model;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;
import ve.com.tracking.core.RegexConstantes;

@SuppressWarnings("serial")
@Entity
@Table(name = "users")
@RooJavaBean
@RooJpaEntity(versionField = "", table = "users")
@RooDbManaged(automaticallyDelete = true)
@RooToString(excludeFields = { "authoritieses", "activationTokens", "detalleNotificacions", "detalleRecepcions", "estatusContenedors", "estatusGuias", "estatusPaquetes", "guias", "trackingLogs", "guias1", "reciboAlmacens", "reciboAlmacens1" })
public class Users implements Serializable {

    public Users() {
        super();
    }

    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("authoritieses").toString();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", length = 50, unique = true)
    @Length(min = 5, max = 10)
    @NotNull
    private String username;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String id) {
        this.username = id;
    }

    @OneToMany(mappedBy = "username")
    private Set<Authorities> authoritieses;

    @Column(name = "password", length = 100)
    @NotNull
    @NotEmpty
    @Length(min = 5, message = "Mínimo 5 caracteres")
    private String password;

    @Column(name = "enabled")
    @NotNull
    private boolean enabled;

    @Column(name = "first_name", length = 45)
    @Length(max = 45)
    @NotNull
    private String firstName;

    @Column(name = "last_name", length = 45)
    @Length(max = 45)
    @NotNull
    private String lastName;

    @Column(name = "direccion", length = 200)
    @Length(min = 1, max = 200)
    @NotNull
    private String direccion;

    @Column(name = "email", length = 100, unique = true)
    @Email
    @Length(max = 100)
    @NotNull
    private String email;

    @Column(name = "telefono", length = 45)
    @Length(max = 45)
    @NotNull
    private String telefono;

    @Column(name = "bb_pin", length = 45, unique = false)
    @Length(max = 45)
    private String bbPin;

    @Column(name = "created", updatable = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Calendar created = Calendar.getInstance();

    @Column(name = "updated")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Calendar updated = Calendar.getInstance();

    public Set<Authorities> getAuthoritieses() {
        return authoritieses;
    }

    public void setAuthoritieses(Set<Authorities> authoritieses) {
        this.authoritieses = authoritieses;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getBbPin() {
        return bbPin;
    }

    public void setBbPin(String bbPin) {
        this.bbPin = bbPin;
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

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "usersId")
    private Set<ActivationToken> activationTokens;

    @OneToMany(mappedBy = "usersId")
    private Set<DetalleNotificacion> detalleNotificacions;

    @OneToMany(mappedBy = "usersId")
    private Set<DetalleRecepcion> detalleRecepcions;

    @Column(name = "ci_rif_passport", length = 15, unique = true)
    @NotNull
    @Pattern(regexp = RegexConstantes.PATTERN_CEDULA_RIF_COMPLETO, message = RegexConstantes.MESSAGE_ERROR_CI_RIF_COMPLETO)
    private String ciRifPassport;

    public Set<ActivationToken> getActivationTokens() {
        return activationTokens;
    }

    public void setActivationTokens(Set<ActivationToken> activationTokens) {
        this.activationTokens = activationTokens;
    }

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

    public String getCiRifPassport() {
        return ciRifPassport;
    }

    public void setCiRifPassport(String ciRifPassport) {
        this.ciRifPassport = ciRifPassport;
    }

    @OneToMany(mappedBy = "usersId")
    private Set<EstatusContenedor> estatusContenedors;

    @OneToMany(mappedBy = "usersId")
    private Set<EstatusGuia> estatusGuias;

    @OneToMany(mappedBy = "usersId")
    private Set<EstatusPaquete> estatusPaquetes;

    @OneToMany(mappedBy = "creadorId")
    private Set<Guia> guias;

    @OneToMany(mappedBy = "usersId", fetch = FetchType.LAZY)
    private Set<TrackingLog> trackingLogs;

    public Set<EstatusContenedor> getEstatusContenedors() {
        return estatusContenedors;
    }

    public void setEstatusContenedors(Set<EstatusContenedor> estatusContenedors) {
        this.estatusContenedors = estatusContenedors;
    }

    public Set<EstatusGuia> getEstatusGuias() {
        return estatusGuias;
    }

    public void setEstatusGuias(Set<EstatusGuia> estatusGuias) {
        this.estatusGuias = estatusGuias;
    }

    public Set<EstatusPaquete> getEstatusPaquetes() {
        return estatusPaquetes;
    }

    public void setEstatusPaquetes(Set<EstatusPaquete> estatusPaquetes) {
        this.estatusPaquetes = estatusPaquetes;
    }

    public Set<Guia> getGuias() {
        return guias;
    }

    public void setGuias(Set<Guia> guias) {
        this.guias = guias;
    }

    public Set<TrackingLog> getTrackingLogs() {
        return trackingLogs;
    }

    public void setTrackingLogs(Set<TrackingLog> trackingLogs) {
        this.trackingLogs = trackingLogs;
    }

    public String getNombreCompleto() {
        return firstName + " " + lastName;
    }

    @OneToMany(mappedBy = "creadorId")
    private Set<Guia> guias1;

    public Set<Guia> getGuias1() {
        return guias1;
    }

    public void setGuias1(Set<Guia> guias1) {
        this.guias1 = guias1;
    }

    @OneToMany(mappedBy = "creatorId")
    private Set<ReciboAlmacen> reciboAlmacens;

    @OneToMany(mappedBy = "cliente")
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
