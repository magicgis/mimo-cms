package com.mimo.cms.interfaces.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mimo.cms.domain.Configure;
import com.mimo.core.web.controller.ControllerSupport;

/**
 * 
 * @author loudyn
 * 
 */
@Controller
@RequestMapping("/conf")
public class ConfigureController extends ControllerSupport {

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String get(Model model) {
		model.addAttribute(Configure.get());
		return "conf/form";
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/put", method = RequestMethod.PUT)
	public String put(HttpServletRequest request) {

		try {

			Configure conf = Configure.get();
			bind(request, conf);
			conf.put();
		} catch (Exception e) {
			error(e.getMessage());
		}

		return "redirect:/conf/get";
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/use-default", method = RequestMethod.PUT)
	public String useDefault(HttpServletRequest request) {

		try {

			Configure.defaultOne().put();
		} catch (Exception e) {
			error(e.getMessage());
		}

		return "redirect:/conf/get";
	}

}
