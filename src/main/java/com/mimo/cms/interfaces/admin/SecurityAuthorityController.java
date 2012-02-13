package com.mimo.cms.interfaces.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import com.mimo.cms.application.security.AuthorityService;
import com.mimo.cms.domain.security.Authority;
import com.mimo.core.orm.Page;
import com.mimo.core.web.controller.CrudControllerSupport;
import com.mimo.util.EntityUtils;

/**
 * 
 * @author loudyn
 *
 */
@Controller
@RequestMapping("/security/authority")
public class SecurityAuthorityController extends CrudControllerSupport<String, Authority> {

	private static final String REDIRECT_LIST = "redirect:/security/authority/list";

	@Autowired
	private AuthorityService authorityService;

	@RequestMapping(value = "/list", method = GET)
	public String list(Page<Authority> page, Model model) {
		page = authorityService.queryPage(page);
		model.addAttribute(page);
		return listView();
	}

	@Override
	@RequestMapping(value = "/create", method = GET)
	public String create(Model model) {
		model.addAttribute(new Authority());
		return formView();
	}

	@Override
	@RequestMapping(value = "/create", method = POST)
	public String create(Authority entity, BindingResult result) {
		if (result.hasErrors()) {
			return null;
		}

		entity.create();
		return REDIRECT_LIST;
	}

	@Override
	@RequestMapping(value = "/{id}/edit", method = GET)
	public String edit(@PathVariable("id") String id, Model model) {
		Authority entity = authorityService.get(id);
		model.addAttribute(entity).addAttribute("_method", "PUT");
		return formView();
	}

	@Override
	@RequestMapping(value = "/{id}/edit", method = PUT)
	public String edit(@PathVariable("id") String id, HttpServletRequest request) {
		try {

			Authority entity = authorityService.get(id);
			bind(request, entity);
			entity.modify();
		} catch (Exception e) {
			return null;
		}

		return REDIRECT_LIST;
	}

	@Override
	@RequestMapping(value = "/{id}/delete", method = DELETE)
	public String delete(@PathVariable("id") String id) {
		authorityService.get(id).delete();
		return REDIRECT_LIST;
	}

	@Override
	@RequestMapping(value = "/delete", method = DELETE)
	public String delete(HttpServletRequest request) {
		String[] items = request.getParameterValues("items");
		for (String item : EntityUtils.nullSafe(items, new String[] {})) {
			delete(item);
		}

		return REDIRECT_LIST;
	}

	@Override
	protected String getViewPackage() {
		return "security/authority";
	}

}
