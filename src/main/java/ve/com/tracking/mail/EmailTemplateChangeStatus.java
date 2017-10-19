package ve.com.tracking.mail;

public class EmailTemplateChangeStatus extends AbstractEmailTemplate {

	public static final String TEMPLATE = "changestatus.vm";

	public EmailTemplateChangeStatus(String destinatario, String codigo,
			String estatus, String url) {
		
		model.put("destinatario", destinatario);
		model.put("codigo", codigo);
		model.put("estatus", estatus);
		model.put("url", url);


		setTemplate(TEMPLATE);

	}

}
