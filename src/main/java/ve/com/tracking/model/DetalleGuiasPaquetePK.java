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

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.jpa.identifier.RooIdentifier;

@Embeddable
@Configurable
@RooIdentifier(dbManaged = true)
public final class DetalleGuiasPaquetePK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "guia", nullable = false)
	private Long guia;

	@Column(name = "paquete", nullable = false)
	private Long paquete;

	@Column(name = "fecha_creacion", nullable = false)
	private Calendar fechaCreacion;

	@Column(name = "fecha_ultimo_estatus", nullable = false)
	private Calendar fechaUltimoEstatus;

	@Column(name = "detalle", nullable = false, length = 341)
	private String detalle;

	@Column(name = "codigo_guia", nullable = false, length = 341)
	private String codigo;
	
	@Column(name = "estatus", nullable = false, length = 341)
	private String estatus;
	
	@Column(name = "paquetes", nullable = false, length = 341)
	private String paquetes;
	
	public DetalleGuiasPaquetePK() {
		super();
	}

	public DetalleGuiasPaquetePK(Long guia, Long paquete,
			Calendar fechaCreacion, Calendar fechaUltimoEstatus, String detalle) {
		super();
		this.guia = guia;
		this.paquete = paquete;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimoEstatus = fechaUltimoEstatus;
		this.detalle = detalle;
	}

	
	
	public Long getGuia() {
		return guia;
	}

	public void setGuia(Long guia) {
		this.guia = guia;
	}

	public Long getPaquete() {
		return paquete;
	}

	public void setPaquete(Long paquete) {
		this.paquete = paquete;
	}

	public Calendar getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Calendar fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Calendar getFechaUltimoEstatus() {
		return fechaUltimoEstatus;
	}

	public void setFechaUltimoEstatus(Calendar fechaUltimoEstatus) {
		this.fechaUltimoEstatus = fechaUltimoEstatus;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public String toJson(String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class")
				.serialize(this);
	}

	public static DetalleGuiasPaquetePK fromJsonToDetalleGuiasPaquetePK(
			String json) {
		return new JSONDeserializer<DetalleGuiasPaquetePK>().use(null,
				DetalleGuiasPaquetePK.class).deserialize(json);
	}

	public static String toJsonArray(
			Collection<DetalleGuiasPaquetePK> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static String toJsonArray(
			Collection<DetalleGuiasPaquetePK> collection, String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class")
				.serialize(collection);
	}

	public static Collection<DetalleGuiasPaquetePK> fromJsonArrayToDetalleGuiasPaquetePKs(
			String json) {
		return new JSONDeserializer<List<DetalleGuiasPaquetePK>>()
				.use(null, ArrayList.class)
				.use("values", DetalleGuiasPaquetePK.class).deserialize(json);
	}

	public String getPaquetes() {
		return paquetes;
	}

	public void setPaquetes(String paquetes) {
		this.paquetes = paquetes;
	}

	
}
