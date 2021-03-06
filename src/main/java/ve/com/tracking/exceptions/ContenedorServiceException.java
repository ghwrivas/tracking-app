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
public class ContenedorServiceException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ContenedorServiceException(String message){
		super(message);
	}
	
	public ContenedorServiceException(Throwable error){
		super(error);
	}
	
}
