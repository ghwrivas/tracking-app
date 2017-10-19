package ve.com.tracking.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;

import ve.com.tracking.model.DetalleItem;
import ve.com.tracking.model.Empresa;
import ve.com.tracking.model.Guia;
import ve.com.tracking.repository.EmpresaRepository;
import ve.com.tracking.services.ContenedorService;
import ve.com.tracking.services.GuiaService;
import ve.com.tracking.services.ReciboService;
import ve.com.tracking.views.JasperReportsViewFactory;
import ve.com.tracking.views.PDFReciboAlmacenView.MonedaRecibo;
import ve.com.tracking.views.PDFReciboGuiaView;
import ve.com.tracking.views.PDFView;

@RequestMapping("/admin/mediaview")
@Controller
public class MediaViewController {

	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private ContenedorService contenedorService;

	@Autowired
	private GuiaService guiaService;

	@Autowired
	private ReciboService reciboService;

	@Autowired
	private JasperReportsViewFactory jasperReportsViewFactory;

	@RequestMapping(method = RequestMethod.GET, value = "/contenedor/{id}")
	public ModelAndView reportContenedor(@PathVariable Long id,
			HttpServletRequest request, HttpServletResponse response,
			Model uiModel) throws Exception {
		try {
			String contenedorUrl = this.getClass()
					.getResource("/reports/contenedor.jasper").toString();
			Map<String, Object> model = new HashMap<String, Object>();
			JasperReportsPdfView jasperView = jasperReportsViewFactory
					.getJasperReportsView(request, contenedorUrl, null, "Download File");
			model.put("titulo", "Listado de Productos Agregados al Contenedor");
			model.put("numero", id);
			model.put("contenedor", contenedorService.findContenedor(id));
			model.put("empresa", empresaRepository.findOne((short) 1));
			return new ModelAndView(jasperView, model);			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	@RequestMapping(method = RequestMethod.GET, value = "/recibo/guia/{id}.pdf")
	public PDFReciboGuiaView viewReciboGuia(@PathVariable Long id,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Guia guia = guiaService.findGuia(id);

		List<DetalleItem> detalleItems = guiaService.findAllDetalleItems(guia);

		Empresa empresa = empresaRepository.findOne((short) 1);

		PDFReciboGuiaView view = new PDFReciboGuiaView(guia, detalleItems,
				empresa);

		model.addAttribute(PDFView.KEY_OBJECT, view);
		return view;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/recibo/{id}")
	public ModelAndView reportRecibo(@PathVariable Long id,
			HttpServletRequest request, HttpServletResponse response,
			Model uiModel) throws Exception {
		try {
			String moneda = request.getParameter("moneda");
			if (moneda == null || moneda.equals(""))
				moneda = MonedaRecibo.DOLAR.name();
			String reciboUrl = this.getClass()
					.getResource("/reports/recibo.jasper").toString();
			Map<String, Object> model = new HashMap<String, Object>();
			JasperReportsPdfView jasperView = jasperReportsViewFactory
					.getJasperReportsView(request, reciboUrl, null, "Download File");
			model.put("titulo", "Recibo de Almacén");
			model.put("numero", id);
			model.put("recibo", reciboService.findReciboAlmacenForm(id));
			model.put("empresa", empresaRepository.findOne((short) 1));
			model.put("moneda", moneda);
			return new ModelAndView(jasperView, model);			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/recibo/etiquetas/{id}")
	public ModelAndView reportEtiquetas(@PathVariable Long id,
			HttpServletRequest request, HttpServletResponse response,
			Model uiModel) throws Exception {
		try {
			String reciboUrl = this.getClass()
					.getResource("/reports/etiquetas.jasper").toString();
			Map<String, Object> model = new HashMap<String, Object>();
			JasperReportsPdfView jasperView = jasperReportsViewFactory
					.getJasperReportsView(request, reciboUrl, null, "Download File");
			model.put("numero_recibo", id);
			return new ModelAndView(jasperView, model);			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
