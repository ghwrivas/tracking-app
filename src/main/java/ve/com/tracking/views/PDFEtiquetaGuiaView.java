package ve.com.tracking.views;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import ve.com.tracking.model.Empresa;
import ve.com.tracking.model.Guia;

/**
 * Genera un documento pdf que representara una etiqueta para una guía.
 * 
 * @author Williams Rivas
 * 
 *         Created 03/05/2014 13:30:36
 */
public class PDFEtiquetaGuiaView extends PDFAbstractModelView {

	private Guia guia;

	public Guia getGuia() {
		return guia;
	}

	public void setGuia(Guia guia) {
		this.guia = guia;
	}

	public PDFEtiquetaGuiaView(Guia guia, Empresa empresa) {
		super(empresa);
		this.guia = guia;
	}

	@Override
	public String getTitle() {
		return "";
	}

	private Phrase getBoldPhrase(String title, Font font) {
		Phrase phrase = new Phrase(title);
		phrase.setFont(font);
		return phrase;
	}

	public PdfPTable tableInfoGuia() throws DocumentException {
		PdfPTable table = new PdfPTable(2);
		table.setTotalWidth(new float[]{10, 1});
		PdfPCell c1 = new PdfPCell(getBoldPhrase("Número",
				AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);
		c1 = new PdfPCell(getBoldPhrase(guia.getId().toString(),
				AbstractPdfView.fontBold60));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);
		table.setWidthPercentage(100f);
		return table;
	}

	public void generateEtiquetaGuia(Document document)
			throws DocumentException {
		// addHeader(document, guia.getId().toString());
		document.add(tableInfoGuia());
		document.add(tableInfoCliente());
		document.add(tableOrigenDestino());
		document.add(tableInfoDimensiones());
		document.add(tableCargosAplicables());

	}

	public PdfPTable tableInfoDimensiones() {
		PdfPTable table = new PdfPTable(5);
		PdfPCell c1 = new PdfPCell(getBoldPhrase("Largo (in)",
				AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setPadding(0f);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);
		c1 = new PdfPCell(getBoldPhrase("Ancho (in)", AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setPadding(0f);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);
		c1 = new PdfPCell(getBoldPhrase("Alto (in)", AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setPadding(0f);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);
		c1 = new PdfPCell(
				getBoldPhrase("Volumen (ft)", AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setPadding(0f);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);
		c1 = new PdfPCell(getBoldPhrase("Volumen Peso (vlb)",
				AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setPadding(0f);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);
		table.addCell(guia.getLargo().toString());
		table.addCell(guia.getAncho().toString());
		table.addCell(guia.getAlto().toString());
		table.addCell(guia.getVolumen().toString());
		table.addCell(guia.getPesoVolumetrico().toString());
		table.setWidthPercentage(100f);
		return table;
	}

	public PdfPTable tableInfoCliente() {
		PdfPTable table = new PdfPTable(2);
		PdfPCell c1 = new PdfPCell(getBoldPhrase("Información del Expedidor",
				AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setPadding(0f);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);
		c1 = new PdfPCell(getBoldPhrase("Información del Consignatario",
				AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);
		table.setHeaderRows(1);
/*		table.addCell(guia.getCliente().getNombreCompleto());
		table.addCell(guia.getCliente().getNombreCompleto());*/
		table.setWidthPercentage(100f);
		return table;
	}

	public PdfPTable tableOrigenDestino() {
		PdfPTable table = new PdfPTable(2);
		PdfPCell c1 = new PdfPCell(getBoldPhrase("Origen",
				AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setPadding(0f);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);
		c1 = new PdfPCell(getBoldPhrase("Destino", AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);
		table.setHeaderRows(1);
/*		table.addCell(guia.getOrigen().getCiudad());
		table.addCell(guia.getDestino().getCiudad());*/
		table.setWidthPercentage(100f);
		return table;
	}

	public PdfPTable tableCargosAplicables() {
		PdfPTable table = new PdfPTable(2);
		PdfPCell c1 = new PdfPCell(getBoldPhrase("Tipo Transportación",
				AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setPadding(0f);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);
/*		String currencyDestino = guia.getDestino().getPais().getCurrencyCode();
		c1 = new PdfPCell(getBoldPhrase("Total Cargos (" + currencyDestino
				+ ")", AbstractPdfView.minimal));*/
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);
		table.setHeaderRows(1);
/*		table.addCell(guia.getTipoTransportacion().getNombre());
		table.addCell(guia.getTotalCargoCambio().toString());*/
		table.setWidthPercentage(100f);
		return table;
	}

}
