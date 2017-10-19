package ve.com.tracking.views;

import java.util.Set;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import ve.com.tracking.model.CodigoGuia;
import ve.com.tracking.model.DetalleGuia;
import ve.com.tracking.model.Empresa;
import ve.com.tracking.model.GuiaReciboAlmacen;
import ve.com.tracking.model.ReciboAlmacen;

/**
 * Pdf view para la impresion de etiquetas recibos
 * 
 * @author Williams Rivas
 * 
 *         Created 06/08/2014 04:23:42
 */
public class PDFEtiquetasProductosView extends PDFAbstractModelView {

	private ReciboAlmacen ra;

	private Integer totalPiezasRecibo = 0;

	public PDFEtiquetasProductosView(ReciboAlmacen ra, Empresa empresa) {
		super(empresa);
		this.ra = ra;
		for (GuiaReciboAlmacen guiaRa : ra.getGuiaReciboAlmacens()) {
			Set<CodigoGuia> codigosGuia = guiaRa.getGuiaId().getCodigoGuias();
			totalPiezasRecibo += codigosGuia.size();
		}
	}

	public ReciboAlmacen getRa() {
		return ra;
	}

	public void setRa(ReciboAlmacen ra) {
		this.ra = ra;
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

	public PdfPTable tableInfoRecibo(CodigoGuia codigoGuia)
			throws DocumentException {
		PdfPTable table = new PdfPTable(2);
		table.setTotalWidth(new float[] { 8, 5 });
		PdfPCell c1 = new PdfPCell(getBoldPhrase("Recibo de Almacén",
				AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

		c1 = new PdfPCell(getBoldPhrase(ra.getId().toString(),
				AbstractPdfView.fontBold60));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);
		table.setWidthPercentage(100f);
		return table;
	}

	public PdfPTable tableInfoGuia(int pieza, int totalPiezas)
			throws DocumentException {
		PdfPTable table = new PdfPTable(2);
		table.setTotalWidth(new float[] { 5, 5 });

		PdfPCell c1 = new PdfPCell(getBoldPhrase("Pieza",
				AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

		c1 = new PdfPCell(getBoldPhrase(String.valueOf(pieza) + " de "
				+ totalPiezas, AbstractPdfView.fontBold60));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

		table.setWidthPercentage(100f);
		return table;
	}

	public PdfPTable tableDescripcionGuia(CodigoGuia codigoGuia)
			throws DocumentException {
		PdfPTable table = new PdfPTable(2);
		table.setTotalWidth(new float[] { 4, 10 });

		PdfPCell c1 = new PdfPCell(getBoldPhrase("Contenido",
				AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

		Set<DetalleGuia> detalles = codigoGuia.getGuiaId().getDetalleGuias();
		String descripcion = "";
		for (DetalleGuia detalleGuia : detalles) {
			descripcion += detalleGuia.getDetalleItemId().getDescripcion()
					+ " ";
		}
		c1 = new PdfPCell(
				getBoldPhrase(descripcion, AbstractPdfView.fontBold60));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

		table.setWidthPercentage(100f);
		return table;
	}

	public void generateEtiquetasProductos(Document document, PdfWriter writer)
			throws DocumentException {
		Set<GuiaReciboAlmacen> guiasRecibo = ra.getGuiaReciboAlmacens();
		int j = 1;
		for (GuiaReciboAlmacen guiaReciboAlmacen : guiasRecibo) {
			Set<CodigoGuia> codigosGuia = guiaReciboAlmacen.getGuiaId()
					.getCodigoGuias();
			for (CodigoGuia codigoGuia : codigosGuia) {
				document.add(tableInfoRecibo(codigoGuia));
				document.add(tableInfoGuia(j, totalPiezasRecibo));
				document.add(tableDescripcionGuia(codigoGuia));
				document.add(tableInfoCliente());
				document.add(tableOrigenDestino());
				document.add(tableInfoDimensiones(codigoGuia));
				document.add(tableCargosAplicables());
				document.add(tableBarcode(codigoGuia, writer));
				document.newPage();
				j++;
			}
		}
	}

	public PdfPTable tableBarcode(CodigoGuia codigoGuia, PdfWriter writer) {
		PdfPTable table = new PdfPTable(2);
		PdfPCell c1 = new PdfPCell(getBoldPhrase("# Seguimiento ",
				AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		c1.setPadding(0f);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

		PdfContentByte pdfcb = new PdfContentByte(writer);

		Barcode128 code128 = new Barcode128();
		code128.setCode(codigoGuia.getCodigo());
		code128.setCodeType(Barcode128.CODE128);
		code128.setTextAlignment(Element.ALIGN_CENTER);

		Image image = code128.createImageWithBarcode(pdfcb, null, null);
		image.setAlignment(Element.ALIGN_CENTER);

		c1 = new PdfPCell(image);
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setPadding(0f);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

		table.setWidthPercentage(100f);
		return table;

	}

	public PdfPTable tableInfoDimensiones(CodigoGuia codigoGuia) {
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
		table.addCell(codigoGuia.getGuiaId().getLargo().toString());
		table.addCell(codigoGuia.getGuiaId().getAncho().toString());
		table.addCell(codigoGuia.getGuiaId().getAlto().toString());
		table.addCell(codigoGuia.getGuiaId().getVolumen().toString());
		table.addCell(codigoGuia.getGuiaId().getPesoVolumetrico().toString());
		table.setWidthPercentage(100f);
		return table;
	}

	public PdfPTable tableInfoCliente() throws DocumentException {
		PdfPTable table = new PdfPTable(2);
		table.setTotalWidth(new float[] { 5, 10 });
		PdfPCell c1 = new PdfPCell(getBoldPhrase("Cliente",
				AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setPadding(0f);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

		c1 = new PdfPCell(getBoldPhrase(ra.getCliente().getNombreCompleto(),
				AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);
		table.setWidthPercentage(100f);
		return table;
	}

	public PdfPTable tableOrigenDestino() {
		PdfPTable table = new PdfPTable(4);
		PdfPCell c1 = new PdfPCell(getBoldPhrase("Origen",
				AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setPadding(0f);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

		c1 = new PdfPCell(getBoldPhrase(ra.getOrigen().getCiudad(),
				AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

		c1 = new PdfPCell(getBoldPhrase("Destino", AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

		c1 = new PdfPCell(getBoldPhrase(ra.getDestino().getCiudad(),
				AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

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

		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);
		table.setHeaderRows(1);
		table.setWidthPercentage(100f);
		return table;
	}

}
