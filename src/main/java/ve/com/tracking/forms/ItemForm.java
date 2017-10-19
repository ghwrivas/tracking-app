/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.forms;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import ve.com.tracking.model.CategoriaDetalle;
import ve.com.tracking.model.DetalleItem;
import ve.com.tracking.model.Paquete;

/**
 * Formulario utilizado para agregar items a una guía al momento de creación y edición
 * 
 * @author Williams Rivas
 *
 * Created 01/05/2014 11:02:48
 */
public class ItemForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private int index;
	
	@NotNull(message="No puede estar vacío")
	private CategoriaDetalle categoriaDetalleId;

	@NotNull(message="No puede estar vacío")
	private Paquete paqueteId;

	@Length(min=1, max=255)
	@NotNull(message="No puede estar vacío")
	private String descripcion;

	public ItemForm(DetalleItem detalleItem) throws IllegalArgumentException {
		if (detalleItem == null || detalleItem.getId() == null) {
			throw new IllegalArgumentException(
					"Para el constructor con detalleItem, este debe ser un objeto ya persistido en la bd");
		}
		setId(detalleItem.getId());
		setCategoriaDetalleId(detalleItem.getCategoriaDetalleId());
		setDescripcion(detalleItem.getDescripcion());
		setPaqueteId(detalleItem.getPaqueteId());
	}

	public ItemForm() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CategoriaDetalle getCategoriaDetalleId() {
		return categoriaDetalleId;
	}

	public void setCategoriaDetalleId(CategoriaDetalle categoriaDetalleId) {
		this.categoriaDetalleId = categoriaDetalleId;
	}

	public Paquete getPaqueteId() {
		return paqueteId;
	}

	public void setPaqueteId(Paquete paqueteId) {
		this.paqueteId = paqueteId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
