/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.services;

import org.springframework.mail.MailException;

import ve.com.tracking.core.FileAttach;
import ve.com.tracking.mail.AbstractEmailTemplate;

public interface MailService {

	public static final String DEFAULT_SENDER = "issupplyincnoreply@gmail.com";

	/**
	 * Enviar un email segun los parametros especificados.
	 * 
	 * @param from
	 * @param to
	 * @param subject
	 * @param msg
	 * @throws MailException
	 */
	public void sendEmail(String from, String to, String subject, String msg)
			throws MailException;

	/**
	 * Enviar un email segun los parametros especificados y el template
	 * 
	 * @param from
	 * @param to
	 * @param subject
	 * @param msg
	 * @param file
	 * @param template
	 */
	public void sendEmail(String from, String to, String subject, String msg,
			AbstractEmailTemplate template, FileAttach file)
			throws MailException;

	/**
	 * Envia un correo electronico a la direccion email pasada como parametro.
	 * 
	 * @param to
	 * @param subject
	 * @param template
	 * @param file
	 * @throws MailException
	 */
	public void sendEmail(String to, String subject,
			AbstractEmailTemplate template, FileAttach file)
			throws MailException;

}
