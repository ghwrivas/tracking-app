/**
 * COPYRIGHT (C) 2014 WM C.A. Todos los derechos reservados.
 */
package ve.com.tracking.services;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import ve.com.tracking.core.FileAttach;
import ve.com.tracking.mail.AbstractEmailTemplate;
import ve.com.tracking.model.Empresa;
import ve.com.tracking.repository.EmpresaRepository;

/**
 * 
 * @author Williams Rivas Created 10/03/2014 08:47:13
 * 
 */
@Service
public class MailServiceImpl implements MailService {

	public static final String DIR_EMAIL_TEMPLATE = "templates/";

	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(MailServiceImpl.class);

	@Autowired
	EmpresaRepository empresaRepository;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private VelocityEngine velocityEngine;

	@Override
	public void sendEmail(String from, String to, String subject, String msg)
			throws MailException {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		mailSender.send(message);
	}

	@Override
	public void sendEmail(final String from, final String to,
			final String subject, String msg,
			final AbstractEmailTemplate template, final FileAttach file) {
		final MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
						true);
				message.setTo(to);
				message.setFrom(new InternetAddress(from));
				message.setSubject(subject);
				String body = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine,
						DIR_EMAIL_TEMPLATE + template.getTemplate(), "UTF-8",
						template.getModel());
				message.setText(body, true);
				if (file != null)
					message.addAttachment(file.getFileName(),
							file.getInputStreamSource(), file.getContentType());
			}
		};
		mailSender.send(preparator);
	}

	@Override
	public void sendEmail(String to, String subject,
			AbstractEmailTemplate template, FileAttach file)
			throws MailException {
		Empresa empresa = empresaRepository.findOne((short) 1);
		template.put("empresa", empresa.getNombre());
		template.put("direccion", empresa.getDireccion());
		template.put("telefono", empresa.getTelefono());
		sendEmail(getSenderEmailName(empresa.getNombre()), to, subject, "",
				template, file);
	}

	private String getSenderEmailName(String nombreEmpresa) {
		String prefijo = "TRACKING-APP";
		if (nombreEmpresa != null && !nombreEmpresa.equals("")) {
			prefijo = nombreEmpresa;
		}
		return "(" + prefijo + ")" + MailService.DEFAULT_SENDER;
	}
}
