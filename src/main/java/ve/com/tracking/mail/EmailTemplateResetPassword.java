package ve.com.tracking.mail;

public class EmailTemplateResetPassword extends AbstractEmailTemplate {

	public static final String RESET_PASSWORD_EMAIL_TEMPLATE = "resetpassword.vm";

	public EmailTemplateResetPassword(String destinatario, String username,
			String password) {
		
		model.put("destinatario", destinatario);
		model.put("username", username);
		model.put("password", password);
		setTemplate(RESET_PASSWORD_EMAIL_TEMPLATE);

	}

}
