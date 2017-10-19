package ve.com.tracking.mail;

public class EmailTemplateCreateAccount extends AbstractEmailTemplate {

	public static final String TEMPLATE = "createaccount.vm";

	public EmailTemplateCreateAccount(String destinatario, String username,
			String password, String url) {
		
		model.put("destinatario", destinatario);
		model.put("username", username);
		model.put("password", password);
		model.put("url", url);
		setTemplate(TEMPLATE);

	}

}
