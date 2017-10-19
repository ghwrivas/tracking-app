/**
 * COPYRIGHT (C) 2014 Alcaldía de Iribarren. Todos los derechos reservados.
 */
package ve.com.tracking.views;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;


/**
 * Fabrica de vistas que renderizan reportes jasper
 * 
 * @author Williams Rivas Created 11/07/2014 10:57:41
 * 
 */
public class JasperReportsViewFactory {

	protected static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";

	private DataSource dataSource;

	/**
	 * Retorna la vista que renderizara el reporte jasper.
	 * 
	 * @param httpServletRequest
	 * @param report
	 *            entidad que representa el reporte obtenido desde la base de
	 *            datos.
	 * @param format
	 *            por defecto solo se soporta el formato pdf.
	 * @param fileName
	 *            el nombre del archivo por si el usuario descarga el reporte
	 *            desde el navegador.
	 * @return
	 */
	public JasperReportsPdfView getJasperReportsView(
			HttpServletRequest httpServletRequest, String url, String format, String fileName) {
		String viewFormat = format == null ? "pdf" : format;
		Properties availableHeaders = new Properties();
		availableHeaders.put("pdf", "inline; filename=" + fileName + ".pdf");
		 JasperReportsPdfView jasperView = new JasperReportsPdfView();
		// appContext. requerido por la vista
		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(httpServletRequest.getSession()
						.getServletContext());
		Properties headers = new Properties();
		headers.put(HEADER_CONTENT_DISPOSITION, availableHeaders.get(viewFormat));
		jasperView.setJdbcDataSource(dataSource);
		jasperView.setUrl(url);
		jasperView.setApplicationContext(ctx);
		jasperView.setHeaders(headers);
		return jasperView;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}