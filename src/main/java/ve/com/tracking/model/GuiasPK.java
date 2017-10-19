package ve.com.tracking.model;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.jpa.identifier.RooIdentifier;

import ve.com.tracking.core.TipoEstatusGuia;

@Configurable
@Embeddable
@RooIdentifier(dbManaged = true)
public final class GuiasPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
	@Column(name = "estatus", nullable = false, length = 255)
    private TipoEstatusGuia estatus;

	@Column(name = "codigo", nullable = false, length = 100)
    private String codigo;
	
	@Column(name = "created", nullable = false)
    private Calendar created;

	@Column(name = "username", nullable = false, length = 50)
    private String username;
	
	@Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

	@Column(name = "last_name", nullable = false, length = 45)
    private String lastName;
	
	@Column(name = "nombre_cliente", nullable = false, length = 91)
    private String nombreCliente;

	@Column(name = "detalle", nullable = false)
    private String detalle;

	@Column(name = "trackings", nullable = false)
    private String trackings;


	@SuppressWarnings("unused")
	private GuiasPK() {
        super();
    }

	
	public GuiasPK(Long id, TipoEstatusGuia estatus, String codigo, Calendar created,
			String username, String firstName, String lastName,
			String nombreCliente, String detalle, String trackings) {
		super();
		this.id = id;
		this.estatus = estatus;
		this.codigo = codigo;
		this.created = created;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.nombreCliente = nombreCliente;
		this.detalle = detalle;
		this.trackings = trackings;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public TipoEstatusGuia getEstatus() {
		return estatus;
	}


	public void setEstatus(TipoEstatusGuia estatus) {
		this.estatus = estatus;
	}


	public String getCodigo() {
		return codigo;
	}


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}


	public Calendar getCreated() {
		return created;
	}


	public void setCreated(Calendar created) {
		this.created = created;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
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


	public String getNombreCliente() {
		return nombreCliente;
	}


	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}


	public String getDetalle() {
		return detalle;
	}


	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}


	public String getTrackings() {
		return trackings;
	}


	public void setTrackings(String trackings) {
		this.trackings = trackings;
	}


	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer().include(fields).exclude("*.class").serialize(this);
    }

	public static GuiasPK fromJsonToGuiasPK(String json) {
        return new JSONDeserializer<GuiasPK>().use(null, GuiasPK.class).deserialize(json);
    }

	public static String toJsonArray(Collection<GuiasPK> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static String toJsonArray(Collection<GuiasPK> collection, String[] fields) {
        return new JSONSerializer().include(fields).exclude("*.class").serialize(collection);
    }

	public static Collection<GuiasPK> fromJsonArrayToGuiasPKs(String json) {
        return new JSONDeserializer<List<GuiasPK>>().use(null, ArrayList.class).use("values", GuiasPK.class).deserialize(json);
    }

}
