package ve.com.tracking.actions;

import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import ve.com.tracking.forms.CodigoGuiaContenedorForm;
import ve.com.tracking.model.Contenedor;

public interface ContenedorManager {

	/**
	 * Crea un objeto de tipo Contenedor. Se busca un contenedor dado el id
	 * pasado como parametro; si existe se trata en modo update y se buscan las
	 * guias agregadas hasta ese momento al contendor. Si no existe se trata en
	 * modo creacion y se inicializa la variable de guias agregadas al
	 * contenedor.
	 * 
	 * @param id
	 * @return Contenedor
	 */
	public Contenedor createOrUpdateContenedor(Long id);

	/**
	 * Busca un numero guia pasado como parametro y lo agrega al contenedor. No
	 * se podra agregar si ya esa guia fue agregada al contenedor siendo
	 * procesado o a otro contenedor.
	 * 
	 * @param guia
	 * @return success() o error()
	 */
	public Event addGuia(RequestContext context);

	/**
	 * Elimina una guia agregada al contenedor siempre y cuando el contenedor
	 * posea el estatus CREADO.
	 * 
	 * @param context
	 * @return
	 */
	public Event deleteGuia(RequestContext context);

	/**
	 * Valida que un contenedor pueda ser editado o no segun la logica de
	 * negocio. Un contenedor que ya se le ha dado salida al pais destino no
	 * puede ser editado agregandole guias.
	 * 
	 * @param context
	 * @return yes() or no() events
	 */
	public Event canBeEdit(RequestContext context);

	/**
	 * Persiste el contenedor con las guias agregadas. Verificar que estatus le
	 * corresponde al contenedor y a las guias que tenga agregadas segun la
	 * logica de negocio.
	 * 
	 * @param context
	 * @return success() o error()
	 */
	public Event saveContenedor(RequestContext context);

	/**
	 * Accion que establece su estatus del contenedor en transito a país destino
	 * Retorna success() en caso de que dicho contenedor pueda ser enviado al
	 * pais destino o error() en cualquier otro caso debido al estatus
	 * 
	 * @param context
	 * @return
	 */
	public Event enviarContenedor(RequestContext context);

	/**
	 * Accion que estable el estatus del contenedor confirmado en pais destino.
	 * Retorna success() en el caso que se pueda establecer dicho estatus o
	 * error() en cualquier otro caso
	 * 
	 * El estatus de las guias agregadas no es modificado.
	 * 
	 * @param context
	 * @return
	 */
	public Event confirmEnDestino(RequestContext context);

	/**
	 * Accion que establece el estatus del contenedor confirmado en almacen
	 * destino. Retorna success() en el caso que se pueda establecer dicho
	 * estatus o error() en cualquier otro caso
	 * 
	 * Se establecen los estatus de las guias que esten agregadas al contenedor
	 * pero que el usuario previa revision confirmo que tambien llegaron con el
	 * contendor.
	 * 
	 * @param context
	 * @return
	 */
	public Event confirmEnAlmacen(RequestContext context);

	/**
	 * Accion mediante la cual se verifican las guias agregadas al contenedor
	 * que se esta procesando para confirmarlas en el almacen destino.
	 * 
	 * @param context
	 * @return
	 */
	public Event confirmGuiasContenedor(RequestContext context);

	/**
	 * El model form mostrado para agregar guias al contenedor.
	 * 
	 * @return
	 */
	public CodigoGuiaContenedorForm createGuiaContenedorForm();

	public Event canConfirmContenedorGuiasAlmacen(RequestContext context);

	/**
	 * Coloca en el contexto de la vista un mapa con clave id de la guia y valor
	 * un string descriptivo de la guia y su detalle de items-
	 * 
	 * @param context
	 * @return
	 */
	public Event mapGuiasContenedorToConfirm(RequestContext context);
}
