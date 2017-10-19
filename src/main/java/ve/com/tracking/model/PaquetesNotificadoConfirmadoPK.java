package ve.com.tracking.model;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.jpa.identifier.RooIdentifier;

@Configurable
@Embeddable
@RooIdentifier(dbManaged = true)
public final class PaquetesNotificadoConfirmadoPK implements Serializable {

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public String toJson(String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class")
				.serialize(this);
	}

	public static PaquetesNotificadoConfirmadoPK fromJsonToPaquetesNotificadoConfirmadoPK(
			String json) {
		return new JSONDeserializer<PaquetesNotificadoConfirmadoPK>().use(null,
				PaquetesNotificadoConfirmadoPK.class).deserialize(json);
	}

	public static String toJsonArray(
			Collection<PaquetesNotificadoConfirmadoPK> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static String toJsonArray(
			Collection<PaquetesNotificadoConfirmadoPK> collection,
			String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class")
				.serialize(collection);
	}

	public static Collection<PaquetesNotificadoConfirmadoPK> fromJsonArrayToPaquetesNotificadoConfirmadoPKs(
			String json) {
		return new JSONDeserializer<List<PaquetesNotificadoConfirmadoPK>>()
				.use(null, ArrayList.class)
				.use("values", PaquetesNotificadoConfirmadoPK.class)
				.deserialize(json);
	}

	private static final long serialVersionUID = 1L;

	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "username", nullable = false, length = 50)
	private String username;

	@Column(name = "first_name", nullable = false, length = 45)
	private String firstName;

	@Column(name = "last_name", nullable = false, length = 45)
	private String lastName;

	@Column(name = "email", nullable = false, length = 100)
	private String email;

	@Column(name = "estatus", nullable = false, length = 255)
	private String estatus;

	@Column(name = "total", nullable = false)
	private Long total;

	public PaquetesNotificadoConfirmadoPK(Long id, String username,
			String firstName, String lastName, String email, String estatus,
			Long total) {
		super();
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.estatus = estatus;
		this.total = total;
	}

	@SuppressWarnings("unused")
	private PaquetesNotificadoConfirmadoPK() {
		super();
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getEstatus() {
		return estatus;
	}

	public Long getTotal() {
		return total;
	}
}
