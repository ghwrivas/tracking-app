/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.exceptions;

/**
 * 
 * @author Williams Rivas
 *
 * Created 08/03/2014 21:25:10
 */
public class EntregarGuiaClienteException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public EntregarGuiaClienteException(String message){
		super(message);
	}
	
	public EntregarGuiaClienteException(Throwable error){
		super(error);
	}
	
}
