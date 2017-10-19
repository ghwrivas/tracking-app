/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.core;

import org.springframework.webflow.action.MultiAction;
import org.springframework.webflow.execution.RequestContext;

/**
 * Clase que define métodos generales para obtener y asignar valores a variables
 * definidas en el ámbito de web flow.
 * 
 * @author Williams Rivas
 * 
 *         Created 01/05/2014 10:12:14
 */
public class CustomMultiAction extends MultiAction {

	/**
	 * Envía mensajes al contenxto del web flow.
	 * 
	 * @param context
	 * @param tipoFlashMessage
	 * @param msj
	 */
	@SuppressWarnings("unchecked")
	protected void sendMessageToFlow(RequestContext context,
			TipoFlashMessage tipoFlashMessage, String msj) {
		context.getFlashScope().asMap().put(tipoFlashMessage.getNombre(), msj);
	}

	/**
	 * Obtiene el valor de una variable del ambito flowScope y es retornada en
	 * el tipo pasado como parámetro
	 * 
	 * @param context
	 * @param attributeName
	 *            nombre de la variable en el web flow
	 * @param clazz
	 *            tipo de retorno del objeto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T> T getFlowScopeObject(RequestContext context,
			String attributeName, Class<T> clazz) {
		return (T) context.getFlowScope().get(attributeName);
	}

	/**
	 * Obtiene el valor de una variable del ambito requestScope y es retornada
	 * en el el tipo pasado como parámetro
	 * 
	 * @param context
	 * @param attributeName
	 *            nombre de la variable en el web flow
	 * @param clazz
	 *            tipo de retorno del objeto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T> T getRequestScopeObject(RequestContext context,
			String attributeName, Class<T> clazz) {
		return (T) context.getRequestScope().get(attributeName);
	}
}
