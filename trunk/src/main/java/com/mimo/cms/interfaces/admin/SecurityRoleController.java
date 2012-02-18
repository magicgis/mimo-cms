package com.mimo.cms.interfaces.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import com.mimo.cms.application.security.AuthorityService;
import com.mimo.cms.application.security.RoleService;
import com.mimo.cms.domain.security.Authority;
import com.mimo.cms.domain.security.Role;
import com.mimo.core.orm.Page;
import com.mimo.core.web.controller.CrudControllerSupport;
import com.mimo.util.EntityUtils;

/**
 * 
 * @author loudyn
 * 
 */
@Controller
@RequestMapping("/security/role")
public class SecurityRoleController extends CrudControllerSupport<String, Role> {

	private static final String REDIRECT_LIST = "redirect:/security/role/list";

	@Autowired
	private RoleService roleService;

	@Autowired
	private AuthorityService authorityService;

	/**
	 * 
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = GET)
	public String list(Page<Role> page, Model model) {
		page = roleService.queryPage(page);
		model.addAttribute(page);
		return listView();
	}

	@Override
	@RequestMapping(value = "/create", method = GET)
	public String create(Model model) {
		List<Authority> authorities = authorityService.query(new Object());
		model.addAttribute(new Role()).addAttribute("authorities", authorities);
		return formView();
	}

	@Override
	@RequestMapping(value = "/create", method = POST)
	public String create(Role entity, BindingResult result) {
		if (result.hasErrors()) {
			return null;
		}

		entity.create();
		return REDIRECT_LIST;
	}

	@Override
	@RequestMapping(value = "/{id}/edit", method = GET)
	public String edit(@PathVariable("id") String id, Model model) {
		Role entity = roleService.get(id);
		List<Authority> authorities = authorityService.query(new Object());
		model.addAttribute(entity.ofAuths()).addAttribute("authorities", authorities).addAttribute("_method", "PUT");
		return formView();
	}

	@Override
	@RequestMapping(value = "/{id}/edit", method = PUT)
	public String edit(@PathVariable("id") String id, HttpServletRequest request) {
		try {

			Role entity = roleService.get(id);
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
		roleService.get(id).delete();
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
		return "security/role";
	}

}
