package com.mimo.cms.interfaces;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mimo.cms.domain.Home;
import com.mimo.core.web.controller.ControllerSupport;
import com.mimo.core.web.exception.ResourceNotFoundException;

/**
 * 
 * @author loudyn
 * 
 */
@Controller
public class HomeController extends ControllerSupport {

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/", "/welcome", "/home", "/index" }, method = RequestMethod.GET)
	public String home() {
		Home home = Home.get();
		if (StringUtils.isBlank(home.getTemplatePath())) {
			throw new ResourceNotFoundException();
		}

		return home.getTemplatePath();
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/home/get", method = RequestMethod.GET)
	public String get(Model model) {
		model.addAttribute(Home.get());
		return "home/form";
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/home/put", method = RequestMethod.PUT)
	public String put(HttpServletRequest request) {

		try {

			Home conf = Home.get();
			bind(request, conf);
			conf.put();
		} catch (Exception e) {
			error(e.getMessage());
		}

		return "redirect:/home/get";
	}
}
