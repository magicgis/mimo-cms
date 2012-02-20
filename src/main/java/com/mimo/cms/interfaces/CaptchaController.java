package com.mimo.cms.interfaces;

import java.awt.Color;
import java.awt.Font;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpSession;

import nl.captcha.Captcha;
import nl.captcha.servlet.CaptchaServletUtil;
import nl.captcha.text.renderer.DefaultWordRenderer;
import nl.captcha.text.renderer.WordRenderer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.ImmutableList;

/**
 * 
 * @author loudyn
 * 
 */
@Controller
public class CaptchaController {

	private static final List<Font> FONTS = ImmutableList.of(
															new Font("Courier New", Font.ITALIC, 32), 
															new Font("Arial", Font.ITALIC, 32),
															new Font("Times New Roman", Font.ITALIC, 32),
															new Font("Verdana", Font.ITALIC, 32)
														);
	private static final List<Color> COLORS = ImmutableList.of(Color.black);
	private static final WordRenderer RENDERER = new DefaultWordRenderer(COLORS, FONTS);

	@RequestMapping(value = "/captcha", method = RequestMethod.GET)
	public void captcha(HttpSession session, OutputStream out) {
		Captcha captcha = new Captcha.Builder(200, 50).addText(RENDERER).build();
		CaptchaServletUtil.writeImage(out, captcha.getImage());
		session.setAttribute("captcha", captcha);
	}
}
