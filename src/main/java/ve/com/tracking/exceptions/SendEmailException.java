/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.exceptions;

/**
 * 
 * @author Williams Rivas
 *
 * Created 08/03/2014 21:24:43
 */
public class SendEmailException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public SendEmailException(String message){
		super(message);
	}
	
	public SendEmailException(Throwable error){
		super(error);
	}
	
}
