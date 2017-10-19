package ve.com.tracking.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "secuencia_codigo")
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "secuencia_codigo")
@RooDbManaged(automaticallyDelete = true)
public class SecuenciaCodigo implements Serializable {

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

	@Column(name = "clave", length = 4, unique = true)
	@NotEmpty
	private String clave;

	@Column(name = "valor")
	@NotNull
	private Integer valor;

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}

	public String toString() {
		return stringify(this);
	}

	@PersistenceContext
	@Transient
	EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new SecuenciaCodigo().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countSecuenciaCodigos() {
		return entityManager().createQuery("SELECT COUNT(o) FROM SecuenciaCodigo o",
				Long.class).getSingleResult();
	}

	public static List<SecuenciaCodigo> findAllSecuenciaCodigos() {
		return entityManager().createQuery("SELECT o FROM SecuenciaCodigo o",
				SecuenciaCodigo.class).getResultList();
	}

	public static SecuenciaCodigo findSecuenciaCodigo(Long id) {
		if (id == null)
			return null;
		return entityManager().find(SecuenciaCodigo.class, id);
	}

	public static List<SecuenciaCodigo> findSecuenciaCodigoEntries(
			int firstResult, int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM SecuenciaCodigo o",
						SecuenciaCodigo.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	@Transactional
	public void persist() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.persist(this);
	}

	@Transactional
	public void remove() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		if (this.entityManager.contains(this)) {
			this.entityManager.remove(this);
		} else {
			SecuenciaCodigo attached = SecuenciaCodigo
					.findSecuenciaCodigo(this.id);
			this.entityManager.remove(attached);
		}
	}

	@Transactional
	public void flush() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.flush();
	}

	@Transactional
	public void clear() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.clear();
	}

	@Transactional
	public SecuenciaCodigo merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		SecuenciaCodigo merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	public SecuenciaCodigo() {
	}

	public SecuenciaCodigo(String clave) {
		this.clave = clave;
		valor = 0;
	}

	public static String stringify(SecuenciaCodigo bean) {
		return "UIDSeq(" + bean.clave + "," + bean.valor + ")";
	}

	public int nextIndex() {
		valor += 1;
		return valor;
	}
}
