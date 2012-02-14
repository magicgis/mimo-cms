package com.mimo.cms.interfaces.admin;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mimo.cms.application.security.RoleService;
import com.mimo.cms.application.security.UserService;
import com.mimo.cms.domain.security.Role;
import com.mimo.cms.domain.security.User;
import com.mimo.cms.interfaces.util.JsonMessage;
import com.mimo.core.orm.Page;
import com.mimo.core.web.AjaxUtils;
import com.mimo.core.web.controller.CrudControllerSupport;
import com.mimo.util.AssertUtils;
import com.mimo.util.EntityUtils;

/**
 * 
 * @author loudyn
 * 
 */
@Controller
@RequestMapping("/security/user")
public class SecurityUserController extends CrudControllerSupport<String, User> {
	private static final String REDIRECT_LIST = "redirect:/security/user/list";

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	/**
	 * 
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = GET)
	public String list(Page<User> page, Model model) {
		page = userService.queryPage(page);
		model.addAttribute(page);
		return listView();
	}

	@Override
	@RequestMapping(value = "/create", method = GET)
	public String create(Model model) {
		List<Role> roles = roleService.query(new Object());
		model.addAttribute(new User()).addAttribute("roles", roles);
		return formView();
	}

	@Override
	@RequestMapping(value = "/create", method = POST)
	public String create(User entity, BindingResult result) {
		if (result.hasErrors()) {
			return null;
		}

		entity.create();
		return REDIRECT_LIST;
	}

	@Override
	@RequestMapping(value = "/{id}/edit", method = GET)
	public String edit(@PathVariable("id") String id, Model model) {
		User entity = userService.lazyGet(id);
		List<Role> roles = roleService.query(new Object());
		model.addAttribute(entity.transitRoles()).addAttribute("roles", roles).addAttribute("_method", "PUT");
		return formView();
	}

	@Override
	@RequestMapping(value = "/{id}/edit", method = PUT)
	public String edit(@PathVariable("id") String id, HttpServletRequest request) {
		try {

			User entity = userService.lazyGet(id);
			String oldUsername = entity.getUsername();
			bind(request, entity);
			boolean usernameNotModify = StringUtils.equals(oldUsername, entity.getUsername());
			AssertUtils.isTrue(usernameNotModify, new UnsupportedOperationException("Username can't modify!"));
			entity.modify();
		} catch (Exception e) {
			return null;
		}

		return REDIRECT_LIST;
	}

	@Override
	@RequestMapping(value = "/{id}/delete", method = DELETE)
	public String delete(@PathVariable("id") String id) {
		userService.lazyGet(id).delete();
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

	@RequestMapping(value = "/lock", method = PUT)
	@ResponseBody
	public JsonMessage lock(HttpServletRequest request, @RequestHeader(value = "X-Requested-With", required = false) String requestWith) {
		try {
			if (!AjaxUtils.isAjaxRequest(requestWith)) {
				return JsonMessage.me().error().message("Not supported operation!");
			}

			String[] items = findItems(request);
			userService.markLocked(EntityUtils.nullSafe(items, new String[] {}));
			return JsonMessage.me().success();
		} catch (Exception e) {
			return JsonMessage.me().error().message(e.getMessage());
		}
	}

	@RequestMapping(value = "/not-lock", method = PUT)
	@ResponseBody
	public JsonMessage notlock(HttpServletRequest request, @RequestHeader(value = "X-Requested-With", required = false) String requestWith) {
		try {
			if (!AjaxUtils.isAjaxRequest(requestWith)) {
				return JsonMessage.me().error().message("Not supported operation!");
			}

			String[] items = findItems(request);
			userService.markNotLocked(EntityUtils.nullSafe(items, new String[] {}));
			return JsonMessage.me().success();
		} catch (Exception e) {
			return JsonMessage.me().error().message(e.getMessage());
		}
	}

	private String[] findItems(HttpServletRequest request) {
		String itemsAsString = request.getParameter("items");
		return StringUtils.split(itemsAsString, ',');
	}

	@Override
	protected String getViewPackage() {
		return "security/user";
	}
}
