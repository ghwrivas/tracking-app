package ve.com.tracking.views;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import ve.com.tracking.model.CodigoGuia;
import ve.com.tracking.model.Contenedor;
import ve.com.tracking.model.DetalleGuia;
import ve.com.tracking.model.Empresa;
import ve.com.tracking.model.ReciboAlmacen;

/**
 * Genera un documento pdf con los datos de un contenedor y guias agregadas.
 * 
 * @author Williams Rivas
 * 
 *         Created 03/05/2014 13:30:36
 */
public class PDFContenedorView extends PDFAbstractModelView {

	private static final String TITLE_REPORT = "title_report_list_contenedor_guias";

	private Contenedor contenedor;

	private List<CodigoGuia> codigoGuias;

	public PDFContenedorView(Contenedor contenedor, List<CodigoGuia> codigoGuias,
			Empresa empresa) {
		super(empresa);
		this.contenedor = contenedor;
		this.setCodigoGuias(codigoGuias);
	}

	public Contenedor getContenedor() {
		return contenedor;
	}

	public void setContenedor(Contenedor contenedor) {
		this.contenedor = contenedor;
	}

	public List<CodigoGuia> getCodigoGuias() {
		return codigoGuias;
	}

	public void setCodigoGuias(List<CodigoGuia> codigoGuias) {
		this.codigoGuias = codigoGuias;
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

	public PdfPTable getTablePdfInfoContenedor() {

		PdfPTable table = new PdfPTable(5);

		PdfPCell c1 = new PdfPCell(getPhrase("Contenedor"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setPadding(0f);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

		c1 = new PdfPCell(getPhrase("Creado"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

		c1 = new PdfPCell(getPhrase("Actualizado"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

		c1 = new PdfPCell(getPhrase("Descripción"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

		c1 = new PdfPCell(getPhrase("Estatus"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

		SimpleDateFormat sdf = new SimpleDateFormat(
				DateTimeFormat.patternForStyle("MM",
						LocaleContextHolder.getLocale()));

		table.addCell(contenedor.getCodigo());
		table.addCell(sdf.format(contenedor.getCreated().getTime()));
		table.addCell(sdf.format(contenedor.getUpdated().getTime()));
		table.addCell(contenedor.getDescripcion());
		table.addCell(contenedor.getEstatus().getNombre());

		table.setWidthPercentage(100f);
		return table;

	}

	public PdfPTable getTableListGuias() throws BadElementException {
		PdfPTable table = new PdfPTable(7);

		PdfPCell c1 = new PdfPCell(getPhrase("Códigos Guía"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setPadding(0f);
		table.addCell(c1);
		c1 = new PdfPCell(getPhrase("Transportación"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);
		c1 = new PdfPCell(getPhrase("Origen"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);
		c1 = new PdfPCell(getPhrase("Destino"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);
		c1 = new PdfPCell(getPhrase("Recibo"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);
		c1 = new PdfPCell(getPhrase("Descripción"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);
		c1 = new PdfPCell(getPhrase("Estatus"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);
		table.setHeaderRows(1);

		for (CodigoGuia codigoGuia : codigoGuias) {
			table.addCell(codigoGuia.getCodigo());
			if (codigoGuia.getGuiaId().getGuiaReciboAlmacen() != null) {
				ReciboAlmacen ra = codigoGuia.getGuiaId().getGuiaReciboAlmacen()
						.getReciboAlmacenId();
				table.addCell(ra.getTipoTransportacion().getNombre());
				table.addCell(ra.getOrigen().getCiudad());
				table.addCell(ra.getDestino().getCiudad());
				table.addCell(ra.getId().toString());

			}else{
				for (int i = 0; i < 4; i++) {
					table.addCell("No agregada a Recibo");
				}
			}
			Set<DetalleGuia> detalles = codigoGuia.getGuiaId().getDetalleGuias();
			String detallesStr = "";
			for (DetalleGuia detalleGuia : detalles) {
				detallesStr = detallesStr + detalleGuia.getDetalleItemId().getDescripcion()+" ";
			}
			table.addCell(detallesStr);
			table.addCell(codigoGuia.getEstatus().getNombre());
		}

		table.setWidthPercentage(100f);

		return table;

	}
}
