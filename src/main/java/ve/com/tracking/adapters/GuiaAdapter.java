/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.adapters;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Set;

import ve.com.tracking.forms.ReciboAlmacenForm;
import ve.com.tracking.model.CodigoGuia;
import ve.com.tracking.model.Guia;
import ve.com.tracking.model.Users;

/**
 * Adaptador de un objeto guía con implementación de métodos especiales útiles
 * al momento de consulta de información.
 * 
 * @author Williams Rivas
 * 
 *         Created 01/05/2014 09:46:25
 */
public class GuiaAdapter {

	private Guia guia;

	public GuiaAdapter(Guia guia) {
		this.guia = guia;
	}

	public Set<CodigoGuia> getCodigoGuias() {
		return guia.getCodigoGuias();
	}

	public ReciboAlmacenForm getGuiaReciboAlmacen() {
		return new ReciboAlmacenForm(guia.getGuiaReciboAlmacen()
				.getReciboAlmacenId());
	}

	public Long getId() {
		return guia.getId();
	}

	public Guia getGuia() {
		return guia;
	}

	public void setGuia(Guia guia) {
		if (guia == null || guia.getId() == null)
			throw new IllegalArgumentException(
					"El objeto tipo Guia debe estar persistido");
		this.guia = guia;
	}

	public Calendar getCreated() {
		return guia.getCreated();
	}

	public Users getCreadorId() {
		return guia.getCreadorId();
	}

	public BigDecimal getPeso() {
		return guia.getPeso();
	}

	public BigDecimal getLargo() {
		return guia.getLargo();
	}

	public BigDecimal getAncho() {
		return guia.getAncho();
	}

	public BigDecimal getAlto() {
		return guia.getAlto();
	}

	public Integer getPiezas() {
		if (guia.getCodigoGuias() != null && guia.getCodigoGuias().size() > 0) {
			return guia.getCodigoGuias().size();
		} else {
			return 0;
		}

	}

	public BigDecimal getPesoTotal() {
		return getPeso().multiply(new BigDecimal(getPiezas().intValue()));
	}

	public BigDecimal getPesoVolumetrico() {
		return guia.getPesoVolumetrico();
	}

	public BigDecimal getVolumen() {
		return guia.getVolumen();
	}
}
