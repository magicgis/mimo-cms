package com.mimo.cms.interfaces;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import com.mimo.cms.application.template.TemplateService;
import com.mimo.cms.domain.Configure;
import com.mimo.cms.domain.template.Template;
import com.mimo.cms.interfaces.util.ConfigureOnWeb;
import com.mimo.core.orm.Page;
import com.mimo.core.web.controller.CrudControllerSupport;
import com.mimo.util.EntityUtils;

/**
 * 
 * @author loudyn
 * 
 */
@Controller
@RequestMapping("/template")
public class TemplateController extends CrudControllerSupport<String, Template> {

	private static final String REIDRECT_LIST = "redirect:/template/list";

	@Autowired
	private ConfigureOnWeb confOnWeb;

	@Autowired
	private TemplateService templateService;

	/**
	 * 
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = GET)
	public String list(Page<Template> page, Model model) {
		page = templateService.queryPage(page);
		model.addAttribute(page);
		return listView();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.web.controller.CrudControllerSupport#create(org.springframework.ui.Model)
	 */
	@Override
	@RequestMapping(value = "/create", method = GET)
	public String create(Model model) {
		model.addAttribute(new Template());
		return formView();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.web.controller.CrudControllerSupport#create(java.lang.Object,
	 * org.springframework.validation.BindingResult)
	 */
	@Override
	@RequestMapping(value = "/create", method = POST)
	public String create(Template entity, BindingResult result) {
		if (result.hasErrors()) {
			return null;
		}

		Configure conf = confOnWeb.wrap(Configure.get());
		entity.selfAdjusting(conf).create();
		return REIDRECT_LIST;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.web.controller.CrudControllerSupport#edit(java.lang.Object, org.springframework.ui.Model)
	 */
	@Override
	@RequestMapping(value = "/{id}/edit", method = GET)
	public String edit(@PathVariable("id") String id, Model model) {
		Template entity = templateService.get(id);
		model.addAttribute(entity).addAttribute("_method", "PUT");
		return formView();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.web.controller.CrudControllerSupport#edit(java.lang.Object,
	 * javax.servlet.http.HttpServletRequest)
	 */
	@Override
	@RequestMapping(value = "/{id}/edit", method = PUT)
	public String edit(@PathVariable("id") String id, HttpServletRequest request) {
		try {

			Template entity = templateService.get(id);
			bind(request, entity);

			Configure conf = confOnWeb.wrap(Configure.get());
			entity.selfAdjusting(conf).modify();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return REIDRECT_LIST;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.web.controller.CrudControllerSupport#delete(java.lang.Object)
	 */
	@Override
	@RequestMapping(value = "/{id}/delete", method = DELETE)
	public String delete(@PathVariable("id") String id) {
		Configure conf = confOnWeb.wrap(Configure.get());
		templateService.get(id).selfAdjusting(conf).delete();
		return REIDRECT_LIST;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.web.controller.CrudControllerSupport#delete(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	@RequestMapping(value = "/delete", method = DELETE)
	public String delete(HttpServletRequest request) {
		String[] items = request.getParameterValues("items");
		for (String item : EntityUtils.nullSafe(items, new String[] {})) {
			delete(item);
		}

		return REIDRECT_LIST;
	}

	@Override
	protected String getViewPackage() {
		return "template";
	}
}
