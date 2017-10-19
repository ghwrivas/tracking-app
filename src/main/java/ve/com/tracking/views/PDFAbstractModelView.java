package ve.com.tracking.views;

import ve.com.tracking.model.Empresa;

public abstract class PDFAbstractModelView {

	protected Empresa empresa;
	
	public PDFAbstractModelView(Empresa empresa) {
		this.empresa = empresa;
	}

	public abstract String getTitle();

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

}
