package ve.com.tracking.model;
import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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

@Entity
@Table(name = "detalle_recepcion")
@RooJavaBean
@RooJpaEntity(versionField = "", table = "detalle_recepcion")
@RooDbManaged(automaticallyDelete = true)
@RooToString(excludeFields = { "paqueteId", "empresaEnvioId", "usersId" })
public class DetalleRecepcion implements Serializable {

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

    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("paqueteId", "empresaEnvioId", "usersId").toString();
    }

    @OneToOne
    @JoinColumn(name = "paquete_id", referencedColumnName = "id", nullable = false)
    private Paquete paqueteId;

    @ManyToOne
    @JoinColumn(name = "empresa_envio_id", referencedColumnName = "id", nullable = false)
    private EmpresaEnvio empresaEnvioId;

    @ManyToOne
    @JoinColumn(name = "users_id", referencedColumnName = "id", nullable = false)
    private Users usersId;

    @Column(name = "descripcion")
    @NotNull
    private String descripcion;

    public Paquete getPaqueteId() {
        return paqueteId;
    }

    public void setPaqueteId(Paquete paqueteId) {
        this.paqueteId = paqueteId;
    }

    public EmpresaEnvio getEmpresaEnvioId() {
        return empresaEnvioId;
    }

    public void setEmpresaEnvioId(EmpresaEnvio empresaEnvioId) {
        this.empresaEnvioId = empresaEnvioId;
    }

    public Users getUsersId() {
        return usersId;
    }

    public void setUsersId(Users usersId) {
        this.usersId = usersId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

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
}
