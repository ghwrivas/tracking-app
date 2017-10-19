package ve.com.tracking.web;

import java.awt.Color;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.captcha.Captcha;
import nl.captcha.backgrounds.GradiatedBackgroundProducer;
import nl.captcha.gimpy.DropShadowGimpyRenderer;
import nl.captcha.servlet.CaptchaServletUtil;
import nl.captcha.text.producer.DefaultTextProducer;
import nl.captcha.text.renderer.DefaultWordRenderer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CaptchaController {

	@RequestMapping("/captcha/getSimpleCaptchaImage.do")
	public void getSimpleCaptchaImage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String charString = "abcdefghkmnpqrstuvwxyz23456789";
			char strs[] = charString.toCharArray();
			Captcha captcha = new Captcha.Builder(250, 50)
					.addText(new DefaultTextProducer(6, strs),
							new DefaultWordRenderer())
					.addBackground(
							new GradiatedBackgroundProducer(Color.RED,
									Color.lightGray))
					.gimp(new DropShadowGimpyRenderer()).addNoise().addBorder()
					.build();

			CaptchaServletUtil.writeImage(response, captcha.getImage());
			request.getSession().setAttribute(Captcha.NAME, captcha);
		} catch (Exception ex) {

		}
	}

	public static boolean validateCaptcha(HttpServletRequest request) {
		Captcha captcha = (Captcha) request.getSession().getAttribute(
				Captcha.NAME);
		String answer = request.getParameter("txtCaptchaAnswer");
		if (captcha.isCorrect(answer)) {
			return true;
		}
		return false;
	}
}
