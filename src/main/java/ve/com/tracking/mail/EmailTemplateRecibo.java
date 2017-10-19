package ve.com.tracking.mail;

public class EmailTemplateRecibo extends AbstractEmailTemplate {

	public static final String TEMPLATE = "recibo.vm";

	public EmailTemplateRecibo(String destinatario, String message) {
		model.put("destinatario", destinatario);
		model.put("message", message);
		setTemplate(TEMPLATE);
	}

}
