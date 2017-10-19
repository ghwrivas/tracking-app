package ve.com.tracking.views;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.view.AbstractView;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

public abstract class AbstractPdfView extends AbstractView {

	public static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
			Font.BOLD);
	public static Font fontBold20 = new Font(Font.FontFamily.TIMES_ROMAN, 20,
			Font.BOLD);	
	public static Font fontBold60 = new Font(Font.FontFamily.TIMES_ROMAN, 60,
			Font.BOLD);	
	public static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.NORMAL, BaseColor.RED);
	public static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
			Font.BOLD);
	public static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.BOLD);
	public static Font minimal = new Font(Font.FontFamily.TIMES_ROMAN, 8,
			Font.DEFAULTSIZE);
	public AbstractPdfView() {
		setContentType("application/pdf");
	}

	@Autowired
	protected MessageSource messageSource;
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ByteArrayOutputStream baos = createTemporaryOutputStream();

		// Apply preferences and build metadata.
		Document document = newDocument(getObjectModel(model));
		PdfWriter writer = PdfWriter.getInstance(document, baos);
		prepareWriter(model, writer, request);
		buildPdfMetadata(model, document, request);

		// Build PDF document.
		writer.setInitialLeading(16);
		document.open();
		buildPdfDocument(model, document, writer, request, response);
		document.close();

		// Flush to HTTP response.
		writeToResponse(response, baos);
	}

	protected Object getObjectModel(Map<String, Object> model) {
		Object object = model.get(PDFView.KEY_OBJECT);
		return object;
	}

	private Document newDocument(Object objectModel) {
		Document document = new Document();
		if (objectModel instanceof PDFReciboAlmacenView) {
			document.setPageSize(PageSize.LETTER);
		}else if (objectModel instanceof PDFContenedorView) {
			document.setPageSize(PageSize.LETTER.rotate());
		}else if (objectModel instanceof PDFEtiquetasProductosView) {
			document.setPageSize(new Rectangle(400, 300, 90));
		}
		return document;
	}
	
	protected abstract void buildPdfDocument(Map<String, Object> model,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	protected void buildPdfMetadata(Map<String, Object> model,
			Document document, HttpServletRequest request) {

	}

	protected void prepareWriter(Map<String, Object> model, PdfWriter writer,
			HttpServletRequest request) throws DocumentException {
		writer.setViewerPreferences(getViewerPreferences());

	}

	private int getViewerPreferences() {
		return PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage;
	}

	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}
}
