package ve.com.tracking.actions;

import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import ve.com.tracking.forms.GuiaForm;
import ve.com.tracking.forms.ItemForm;
import ve.com.tracking.forms.PaquetesSelectedForm;
import ve.com.tracking.forms.ReciboAlmacenForm;

public interface ReciboManager {

	public ReciboAlmacenForm createRecibo(Long userId);

	public ReciboAlmacenForm editRecibo(Long id);

	public GuiaForm createGuia();

	public GuiaForm editGuia(Long id);

	public Event populateDataReferenceFormAddGuia(RequestContext context);

	public Event populateDataReferenceFormRecibo(RequestContext context);

	public Event addGuia(RequestContext context);

	public Event saveRecibo(RequestContext context);

	/**
	 * Obtiene los datos para los combos de seleccion de datos utilizados en el
	 * web flow. Retorna null event.
	 */
	public Event populateDataReferenceFormAddItemGuia(RequestContext context);

	/**
	 * Obtiene los datos para los combos de seleccion de datos utilizados en el
	 * web flow. Retorna null event.
	 */
	public Event populateDataReferenceFormGuia(RequestContext context);

	public ItemForm createItem();

	public PaquetesSelectedForm createPaquetesSelectedForm();

	/**
	 * Añade un item al listado de items de la guia a ser generada
	 * 
	 * @param context
	 * @return
	 */
	public Event addItem(RequestContext context);

	public Event renderFormCargos(RequestContext context);

	/**
	 * Accion que elimina un item de un producto
	 * 
	 * @param context
	 * @return
	 */
	public Event deleteItem(RequestContext context);

	/**
	 * Accion que elimina guia
	 * 
	 * @param context
	 * @return
	 */
	public Event deleteGuia(RequestContext context);
	
	/**
	 * Coloca el estatus del recibo en confirmado
	 * 
	 * @param context
	 * @return
	 */
	public Event confirmarPago(RequestContext context);
	
	public Event mapPaquetesToProcess(RequestContext context);
	
	public Event processPaquetes(RequestContext context);
	
	public Event hasPaquetesToProcess(RequestContext context);
}
