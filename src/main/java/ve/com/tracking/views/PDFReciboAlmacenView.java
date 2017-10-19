package ve.com.tracking.views;

import java.util.Calendar;
import java.util.List;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import ve.com.tracking.core.TipoTransportacion;
import ve.com.tracking.forms.GuiaForm;
import ve.com.tracking.forms.ItemForm;
import ve.com.tracking.forms.ReciboAlmacenForm;
import ve.com.tracking.model.Empresa;

/**
 * Pdf representativo de un recibo de almacén
 * 
 * @author Williams Rivas
 * 
 *         Created 22/07/2014 00:37:21
 */
public class PDFReciboAlmacenView extends PDFAbstractModelView {

	private static final String TITLE_REPORT = "label_title_recibo_de_almacen";

	private ReciboAlmacenForm ra;

	private MonedaRecibo moneda;

	public enum MonedaRecibo {
		DOLAR, DESTINO
	}

	public ReciboAlmacenForm getRa() {
		return ra;
	}

	public void setRa(ReciboAlmacenForm ra) {
		this.ra = ra;
	}

	public PDFReciboAlmacenView(ReciboAlmacenForm ra, Empresa empresa) {
		super(empresa);
		this.ra = ra;
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

	private Phrase getBoldPhrase(String title, Font font) {
		Phrase phrase = new Phrase(title);
		phrase.setFont(font);
		return phrase;
	}

	public PdfPTable tableInfoRecibo() {
		PdfPTable table = new PdfPTable(3);
		PdfPCell c1 = new PdfPCell(getBoldPhrase(empresa.getNombre(),
				AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setPadding(0f);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(0);
		table.addCell(c1);

		c1 = new PdfPCell(getBoldPhrase("Número de Recibo:",
				AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		c1.setPadding(0f);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(0);
		table.addCell(c1);

		c1 = new PdfPCell(getBoldPhrase(ra.getId().toString(),
				AbstractPdfView.fontBold20));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setPadding(0f);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(0);
		table.addCell(c1);

		c1 = new PdfPCell(getBoldPhrase(empresa.getDireccion(),
				AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(0);
		table.addCell(c1);

		c1 = new PdfPCell(getBoldPhrase("Fecha / Hora de Recibo:",
				AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(0);
		table.addCell(c1);

		c1 = new PdfPCell(
				getPhrase(Calendar.getInstance().getTime().toString()));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(0);
		table.addCell(c1);

		c1 = new PdfPCell(getPhrase("Telf.: " + empresa.getTelefono()));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(0);
		table.addCell(c1);

		c1 = new PdfPCell(getPhrase(""));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(0);
		table.addCell(c1);

		c1 = new PdfPCell(getPhrase(""));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(0);
		table.addCell(c1);

		table.setWidthPercentage(100f);

		return table;
	}

	public PdfPTable tableInfoCliente() {
		PdfPTable table = new PdfPTable(3);
		PdfPCell c1 = new PdfPCell(getBoldPhrase("Información del Cliente",
				AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setColspan(3);
		c1.setFixedHeight(20f);
		table.addCell(c1);

		c1 = new PdfPCell(getBoldPhrase("Cédula/RIF/Pasaporte",
				AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

		c1 = new PdfPCell(getBoldPhrase("Nombre", AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

		c1 = new PdfPCell(getBoldPhrase("Teléfono", AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

		table.setHeaderRows(2);

		table.addCell(ra.getCliente().getCiRifPassport());
		table.addCell(ra.getCliente().getNombreCompleto());
		table.addCell(ra.getCliente().getTelefono());

		table.setWidthPercentage(100f);
		return table;
	}

	public PdfPTable tableCargosAplicables() {
		PdfPTable table = new PdfPTable(2);
		PdfPCell c1 = new PdfPCell(getBoldPhrase("Notas",
				AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setPadding(0f);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

		c1 = new PdfPCell(getBoldPhrase("Cargos Aplicables",
				AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);
		table.setHeaderRows(1);

		// TODO notas del recibo
		table.addCell("Notas recibo");
		if (moneda.equals(MonedaRecibo.DOLAR)) {
			table.addCell(ra.getOrigen().getPais().getCurrencyCode() + " "
					+ ra.getTotalCargo().toString());
		} else {
			table.addCell(ra.getDestino().getPais().getCurrencyCode() + " "
					+ ra.getTotalCargoCambio().toString());
		}

		table.setWidthPercentage(100f);
		return table;
	}

	public PdfPTable tableInfoDimensiones() throws DocumentException {
		if (ra.getTipoTransportacion() != TipoTransportacion.MARITIMO) {
			return infoDimensionesAereo();
		} else {
			return infoDimensionesMaritimo();
		}
	}

	private PdfPTable infoDimensionesMaritimo() throws DocumentException {
		PdfPTable table = new PdfPTable(6);

		table.setTotalWidth(new float[] { 2, 5, 6, 2, 3, 3 });

		PdfPCell c1 = new PdfPCell(getBoldPhrase("Piezas",
				AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setPadding(0f);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

		c1 = new PdfPCell(getBoldPhrase("Dimensiones", AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setPadding(0f);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

		c1 = new PdfPCell(getBoldPhrase("Descripción", AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setPadding(0f);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

		c1 = new PdfPCell(getBoldPhrase("Peso", AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setPadding(0f);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

		c1 = new PdfPCell(getBoldPhrase("Piés Cúbicos (ft3)",
				AbstractPdfView.minimal));
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

		table.setHeaderRows(1);
		StringBuilder sb = new StringBuilder();
		for (GuiaForm guia : ra.getGuias()) {
			table.addCell(guia.getPiezas().toString());
			sb.append(guia.getLargo()).append("x")
					.append(guia.getAncho().toString()).append("x")
					.append(guia.getAlto().toString()).append("in");

			table.addCell(sb.toString());
			sb = new StringBuilder();

			List<ItemForm> items = guia.getItems();
			for (ItemForm item : items) {
				sb.append(item.getDescripcion()).append(", ")
						.append(item.getPaqueteId().getTracking()).append(" ");

			}

			table.addCell(sb.toString()); // descripcion

			sb = new StringBuilder();

			table.addCell(guia.getTotalPeso().toString());
			table.addCell(guia.getTotalPiesCubicos().toString());
			table.addCell(guia.getTotalPesoVolumetrico().toString());
		}

		table.setWidthPercentage(100f);
		return table;

	}

	private PdfPTable infoDimensionesAereo() throws DocumentException {
		PdfPTable table = new PdfPTable(5);
		table.setTotalWidth(new float[] { 1, 4, 6, 2, 3 });

		PdfPCell c1 = new PdfPCell(getBoldPhrase("Piezas",
				AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setPadding(0f);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

		c1 = new PdfPCell(getBoldPhrase("Dimensiones", AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setPadding(0f);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

		c1 = new PdfPCell(getBoldPhrase("Descripción", AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setPadding(0f);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

		c1 = new PdfPCell(getBoldPhrase("Peso", AbstractPdfView.minimal));
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

		table.setHeaderRows(1);
		StringBuilder sb = new StringBuilder();
		for (GuiaForm guia : ra.getGuias()) {
			table.addCell(guia.getPiezas().toString());
			sb.append(guia.getLargo()).append("x")
					.append(guia.getAncho().toString()).append("x")
					.append(guia.getAlto().toString()).append("in");

			table.addCell(sb.toString());
			sb = new StringBuilder();

			List<ItemForm> items = guia.getItems();
			for (ItemForm item : items) {
				sb.append("[");
				sb.append(item.getDescripcion()).append("-")
						.append(item.getPaqueteId().getTracking()).append("] ");

			}

			table.addCell(sb.toString()); // descripcion

			sb = new StringBuilder();

			table.addCell(guia.getTotalPeso().toString());
			table.addCell(guia.getTotalPesoVolumetrico().toString());
		}

		table.setWidthPercentage(100f);
		return table;

	}

	public PdfPTable tableTotales() throws DocumentException {
		if (ra.getTipoTransportacion() == TipoTransportacion.MARITIMO) {
			return totalesMaritimo();
		} else {
			return totalesAereo();
		}
	}

	private PdfPTable totalesMaritimo() throws DocumentException {
		PdfPTable table = new PdfPTable(6);
		table.setTotalWidth(new float[] { 1, 6, 4, 3, 3, 3 });

		PdfPCell c1 = new PdfPCell(getBoldPhrase("Recibido por / Firma",
				AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setColspan(2);
		table.addCell(c1);

		c1 = new PdfPCell(getPhrase("Piezas"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

		c1 = new PdfPCell(getBoldPhrase("Peso", AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setPadding(0f);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

		c1 = new PdfPCell(getBoldPhrase("Piés Cúbicos (ft3)",
				AbstractPdfView.minimal));
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
		table.setHeaderRows(1);

		c1 = new PdfPCell(getBoldPhrase("", AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setPadding(0f);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setColspan(2);
		table.addCell(c1);

		table.addCell(ra.getTotalPiezas().toString());

		table.addCell(ra.getTotalPesoGuias().toString());

		table.addCell(ra.getTotalPiesCubicos().toString());

		table.addCell(ra.getTotalPesoVolumetrico().toString());

		table.setWidthPercentage(100f);

		return table;

	}

	private PdfPTable totalesAereo() throws DocumentException {
		PdfPTable table = new PdfPTable(4);
		table.setTotalWidth(new float[] { 6f, 1, 1, 2.5f });

		PdfPCell c1 = new PdfPCell(getBoldPhrase("Recibido por / Firma",
				AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

		c1 = new PdfPCell(getPhrase("Piezas"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

		c1 = new PdfPCell(getBoldPhrase("Peso", AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);

		c1 = new PdfPCell(getBoldPhrase("Volumen Peso (vlb)",
				AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(c1);
		table.setHeaderRows(1);

		c1 = new PdfPCell(getBoldPhrase("", AbstractPdfView.minimal));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setFixedHeight(40f);
		table.addCell(c1);

		
		table.addCell(ra.getTotalPiezas().toString());

		table.addCell(ra.getTotalPesoGuias().toString());

		table.addCell(ra.getTotalPesoVolumetrico().toString());

		table.setWidthPercentage(100f);

		return table;

	}

	public MonedaRecibo getMoneda() {
		return moneda;
	}

	public void setMoneda(MonedaRecibo moneda) {
		this.moneda = moneda;
	}
}
