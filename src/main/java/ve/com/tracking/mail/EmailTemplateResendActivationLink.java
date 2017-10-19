package ve.com.tracking.mail;

public class EmailTemplateResendActivationLink extends AbstractEmailTemplate {

	public static final String TEMPLATE = "resendactivationtoken.vm";

	public EmailTemplateResendActivationLink(String destinatario, String url) {
		model.put("destinatario", destinatario);
		model.put("url", url);
		setTemplate(TEMPLATE);
	}
}
