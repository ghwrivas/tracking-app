package ve.com.tracking.views;

import java.util.Calendar;
import java.util.List;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import ve.com.tracking.model.DetalleItem;
import ve.com.tracking.model.Empresa;
import ve.com.tracking.model.Guia;

/**
 * Genera un documento pdf con los datos de una guía.
 * 
 * @author Williams Rivas
 * 
 *         Created 03/05/2014 13:30:36
 */
public class PDFReciboGuiaView extends PDFAbstractModelView {

	private static final String TITLE_REPORT = "title_report_recibo_guia";

	private Guia guia;

	private List<DetalleItem> detalleItems;

	public Guia getGuia() {
		return guia;
	}

	public void setGuia(Guia guia) {
		this.guia = guia;
	}

	public List<DetalleItem> getDetalleItems() {
		return detalleItems;
	}

	public void setDetalleItems(List<DetalleItem> detalleItems) {
		this.detalleItems = detalleItems;
	}

	public PDFReciboGuiaView(Guia guia, List<DetalleItem> detalleItems,
			Empresa empresa) {
		super(empresa);
		this.guia = guia;
		this.setDetalleItems(detalleItems);
	}

	@Override
	public String getTitle() {
		return TITLE_REPORT;
	}

	private Phrase getPhrase(String title) {
		Phrase phrase = new Phrase(title);
		phrase.setFont(AbstractPdfView.minimal);
		return phrase;
	}

	private Phrase getBoldPhrase(String title, Font font){
		Phrase phrase = new Phrase(title);
		phrase.setFont(font);
		return phrase;		
	}

	public PdfPTable tableInfoDimensiones(){
		PdfPTable table = new PdfPTable(5);
		
		
		PdfPCell c1 = new PdfPCell(getBoldPhrase("Largo (in)", AbstractPdfView.minimal));
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
		
		c1 = new PdfPCell(getBoldPhrase("Volumen (ft)", AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setPadding(0f);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);
		
		c1 = new PdfPCell(getBoldPhrase("Volumen Peso (vlb)", AbstractPdfView.minimal));
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
	
	public PdfPTable tableInfoRecibo() {
		PdfPTable table = new PdfPTable(3);
		PdfPCell c1 = new PdfPCell(getBoldPhrase(empresa.getNombre(), AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setPadding(0f);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(0);
		table.addCell(c1);

		c1 = new PdfPCell(getBoldPhrase("Número de Recibo:", AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		c1.setPadding(0f);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(0);
		table.addCell(c1);

		c1 = new PdfPCell(getBoldPhrase(guia.getId().toString(), AbstractPdfView.fontBold20));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setPadding(0f);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(0);
		table.addCell(c1);
		
		
		c1 = new PdfPCell(getBoldPhrase(empresa.getDireccion(), AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(0);
		table.addCell(c1);

		c1 = new PdfPCell(getBoldPhrase("Fecha / Hora de Recibo:", AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(0);
		table.addCell(c1);

		c1 = new PdfPCell(getPhrase(Calendar.getInstance().getTime().toString()));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(0);
		table.addCell(c1);

		c1 = new PdfPCell(getPhrase("Telf.: "+empresa.getTelefono()));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(0);
		table.addCell(c1);
		
		c1 = new PdfPCell(getPhrase("Procesado por:"));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(0);
		table.addCell(c1);
		
		c1 = new PdfPCell(getPhrase(guia.getCreadorId().getNombreCompleto()));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(0);
		table.addCell(c1);

		table.setWidthPercentage(100f);
		
		return table;
	}
	
	public PdfPTable tableInfoCliente() {
		PdfPTable table = new PdfPTable(2);
		PdfPCell c1 = new PdfPCell(getBoldPhrase("Información del Expedidor", AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setPadding(0f);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);
		c1 = new PdfPCell(getBoldPhrase("Información del Consignatario", AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);
		table.setHeaderRows(1);
/*		table.addCell(guia.getCliente().getNombreCompleto());
		table.addCell(guia.getCliente().getNombreCompleto());*/
		table.setWidthPercentage(100f);
		return table;
	}
	
	public PdfPTable tableCargosAplicables() {
		PdfPTable table = new PdfPTable(2);
		PdfPCell c1 = new PdfPCell(getBoldPhrase("Tipo Transportación", AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setPadding(0f);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);
		c1 = new PdfPCell(getBoldPhrase("Total Cargos (USD)", AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);
		table.setHeaderRows(1);
/*		table.addCell(guia.getTipoTransportacion().getNombre());
		table.addCell(guia.getTotalCargo().toString());*/
		table.setWidthPercentage(100f);
		return table;
	}
	

	public PdfPTable getTableDetalleItems() throws DocumentException {
		PdfPTable table = new PdfPTable(3);
		table.setTotalWidth(new float[]{6,4,3});

		PdfPCell c1 = new PdfPCell(getPhrase("Tracking"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

		c1 = new PdfPCell(getBoldPhrase("Descripción", AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);
		
		c1 = new PdfPCell(getBoldPhrase("Categoría", AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

		for (DetalleItem detalle : detalleItems) {
			table.addCell(detalle.getPaqueteId().getTracking());
			table.addCell(detalle.getDescripcion());
			table.addCell(detalle.getCategoriaDetalleId().getNombre());
		}

		table.setWidthPercentage(100f);

		return table;

	}
}
