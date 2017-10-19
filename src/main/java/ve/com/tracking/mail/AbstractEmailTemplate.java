package ve.com.tracking.mail;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractEmailTemplate {
	
	protected String template = "default.vm";

	protected Map<String, Object> model = new HashMap<String, Object>();
	
	public AbstractEmailTemplate() {
	
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Map<String, Object> getModel() {
		return model;
	}

	public void setModel(Map<String, Object> model) {
		this.model = model;
	}
	
	public void put(String key, Object value){
		model.put(key, value);
	}
	
}
