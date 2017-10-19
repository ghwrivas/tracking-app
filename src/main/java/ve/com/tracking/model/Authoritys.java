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

@SuppressWarnings("serial")
@Entity
@Table(name = "authoritys")
@RooJavaBean
@RooJpaEntity(versionField = "", table = "authoritys")
@RooDbManaged(automaticallyDelete = true)
@RooToString(excludeFields = { "authoritieses" })
public class Authoritys implements Serializable {

    @OneToMany(mappedBy = "authority")
    private Set<Authorities> authoritieses;

    @Column(name = "authority", length = 50, unique = true)
    @NotNull
    private String authority;

    @Column(name = "name", length = 45, unique = true)
    @NotNull
    private String name;

    @Column(name = "descripcion", length = 255, unique = false)
    @NotNull
    private String descripcion;
    
    public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set<Authorities> getAuthoritieses() {
        return authoritieses;
    }

    public void setAuthoritieses(Set<Authorities> authoritieses) {
        this.authoritieses = authoritieses;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("authoritieses").toString();
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
