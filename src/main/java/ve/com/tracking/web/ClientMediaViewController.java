package ve.com.tracking.web;

import java.util.HashMap;
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

import ve.com.tracking.repository.EmpresaRepository;
import ve.com.tracking.services.ContenedorService;
import ve.com.tracking.services.GuiaService;
import ve.com.tracking.services.ReciboService;
import ve.com.tracking.services.UsersService;
import ve.com.tracking.views.JasperReportsViewFactory;
import ve.com.tracking.views.PDFReciboAlmacenView.MonedaRecibo;

@RequestMapping("/client/mediaview")
@Controller
public class ClientMediaViewController {

	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private ContenedorService contenedorService;

	@Autowired
	private GuiaService guiaService;

	@Autowired
	private ReciboService reciboService;


	@Autowired
	private UsersService usersService;

	@Autowired
	private JasperReportsViewFactory jasperReportsViewFactory;
	
	@RequestMapping(method = RequestMethod.GET, value = "/recibo/{id}")
	public ModelAndView reportRecibo(@PathVariable Long id,
			HttpServletRequest request, HttpServletResponse response,
			Model uiModel) throws Exception {
		try {
			String moneda = MonedaRecibo.DESTINO.name();
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
			e.printStackTrace();return null;
		}
	}

}
