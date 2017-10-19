package ve.com.tracking.views;

import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import ve.com.tracking.services.UsersService;

public class PDFView extends AbstractPdfView {

	@Autowired
	private UsersService userService;

	public static final String KEY_OBJECT = "object";

	public PDFView() {

	}

	@Override
	protected void buildPdfDocument(Map<String, Object> model,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Object object = getObjectModel(model);
		if (object instanceof PDFContenedorView) {
			document = buildPdfContenedorView(model, document, writer, request,
					response);
		} else if (object instanceof PDFReciboGuiaView) {
			document = buildPdfReciboGuiaView(model, document, writer, request,
					response);
		}else if (object instanceof PDFReciboAlmacenView) {
			document = buildPdfReciboAlmacenView(model, document, writer, request,
					response);
		}else if (object instanceof PDFEtiquetaGuiaView) {
			document = buildPdfEtiquetaGuiaView(model, document, writer, request,
					response);
		}else if (object instanceof PDFEtiquetasProductosView) {
			document = buildPdfEtiquetasProductosView(model, document, writer, request,
					response);
		}

	}

	private Document buildPdfEtiquetasProductosView(Map<String, Object> model,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws DocumentException {
		PDFEtiquetasProductosView pdfEtiquetasProductosView = (PDFEtiquetasProductosView) model
				.get(KEY_OBJECT);
		pdfEtiquetasProductosView.generateEtiquetasProductos(document, writer);
		return document;
	}

	private Document buildPdfEtiquetaGuiaView(Map<String, Object> model,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) {
		PDFEtiquetaGuiaView pdfEtiquetaGuiaView = (PDFEtiquetaGuiaView) model.get(KEY_OBJECT);
		try {
			pdfEtiquetaGuiaView.generateEtiquetaGuia(document);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return document;
	}

	private Document buildPdfReciboGuiaView(Map<String, Object> model,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PDFReciboGuiaView pdfReciboGuiaView = (PDFReciboGuiaView) model
				.get(KEY_OBJECT);
		String params[] = {pdfReciboGuiaView.getGuia().getId().toString()};
		String title = messageSource.getMessage(pdfReciboGuiaView.getTitle(),
				params, LocaleContextHolder.getLocale());
		addHeader(document, title);
		document.add(pdfReciboGuiaView.tableInfoRecibo());
		document.add(pdfReciboGuiaView.tableInfoCliente());
		document.add(pdfReciboGuiaView.tableCargosAplicables());
		document.add(pdfReciboGuiaView.tableInfoDimensiones());
		document.add(pdfReciboGuiaView.getTableDetalleItems());
		addFooter(document);
		document.addTitle(pdfReciboGuiaView.getTitle());
		return document;
	}

	private Document buildPdfReciboAlmacenView(Map<String, Object> model,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PDFReciboAlmacenView pdfReciboAlmacenView = (PDFReciboAlmacenView) model
				.get(KEY_OBJECT);
		String params[] = {pdfReciboAlmacenView.getRa().getId().toString()};
		String title = messageSource.getMessage(pdfReciboAlmacenView.getTitle(),
				params, LocaleContextHolder.getLocale());
		addHeader(document, title);
		document.add(pdfReciboAlmacenView.tableInfoRecibo());
		document.add(pdfReciboAlmacenView.tableInfoCliente());
		document.add(pdfReciboAlmacenView.tableCargosAplicables());
		document.add(pdfReciboAlmacenView.tableInfoDimensiones());
		document.add(pdfReciboAlmacenView.tableTotales());
		addFooter(document);
		document.addTitle(pdfReciboAlmacenView.getTitle());
		return document;
	}

	private Document buildPdfContenedorView(Map<String, Object> model,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PDFContenedorView pdfContenedorView = (PDFContenedorView) model
				.get(KEY_OBJECT);
		String params[] = {pdfContenedorView.getContenedor().getCodigo()};
		String title = messageSource.getMessage(pdfContenedorView.getTitle(),
				params, LocaleContextHolder.getLocale());
		addHeader(document, title);
		document.add(Chunk.NEWLINE);
		document.add(pdfContenedorView.getTablePdfInfoContenedor());
		document.add(pdfContenedorView.getTableListGuias());
		addFooter(document);
		document.addTitle(pdfContenedorView.getTitle());
		return document;
	}

	private void addHeader(Document document, String title)
			throws DocumentException {
		Paragraph preface = new Paragraph();
		preface.add(new Paragraph(title, catFont));
		preface.setAlignment(Element.ALIGN_RIGHT);
		document.add(preface);
	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	private void addFooter(Document document) throws DocumentException {
		Paragraph preface = new Paragraph();
		addEmptyLine(preface, 1);
		preface.add(new Paragraph("Report generated by: "
				+ userService.getPrincipal().getNombreCompleto() + ", "
				+ Calendar.getInstance().getTime(), smallBold));
		document.add(preface);
	}

}
