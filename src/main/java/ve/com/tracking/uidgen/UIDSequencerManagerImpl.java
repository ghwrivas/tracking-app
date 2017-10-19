/**
 * COPYRIGHT (C) 2014 Alcaldía de Iribarren. Todos los derechos reservados.
 */
package ve.com.tracking.uidgen;

import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ve.com.tracking.model.SecuenciaCodigo;

/**
 * 
 * @author Williams Rivas Created 01/07/2014 11:04:04
 * 
 */
@Service
public class UIDSequencerManagerImpl implements UIDSequencerManager {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	@Override
	public Integer getNext(String key) {
		SecuenciaCodigo seq;
		try {
			seq = (SecuenciaCodigo) em
					.createQuery(
							"SELECT o FROM SecuenciaCodigo AS o WHERE o.clave = :clave")
					.setParameter("clave", key).getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			seq = new SecuenciaCodigo(key);
			em.persist(seq);
		}
		Integer next = seq.nextIndex();
		em.merge(seq);
		return next;
	}

	@Override
	public String getNextCodigoGuia(Long reciboAlmacenId, Long guiaId) {
		StringBuilder key = new StringBuilder();
		key.append("RA").append(reciboAlmacenId);
		key.append("G").append(guiaId).append("A");
		return key.toString() + getNext(guiaId.toString()).toString();

	}

	@Override
	public String getNextCodigoContenedor() {
		Calendar c = Calendar.getInstance();
		String clave = String.valueOf(c.get(Calendar.YEAR));
		return "C" + clave + "O" + getNext(clave).toString();
	}

}
