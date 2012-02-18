package com.mimo.cms.interfaces;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mimo.cms.application.channel.ChannelService;
import com.mimo.cms.application.template.TemplateService;
import com.mimo.cms.domain.channel.Channel;
import com.mimo.cms.domain.template.Template;
import com.mimo.core.orm.Page;
import com.mimo.core.web.controller.CrudControllerSupport;
import com.mimo.core.web.exception.ResourceNotFoundException;
import com.mimo.util.EntityUtils;

/**
 * 
 * @author loudyn
 * 
 */
@Controller
@RequestMapping("/channel")
public class ChannelController extends CrudControllerSupport<String, Channel> {

	private final static String REDIRECT_LIST = "redirect:/channel/list";

	@Autowired
	private ChannelService channelService;

	@Autowired
	private TemplateService templateService;

	/**
	 * 
	 * @param path
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/{path}", "/{path}/view" }, method = GET)
	public String view(@PathVariable("path") String path, Model model) {
		Channel entity = channelService.queryUniqueByPath(path);
		if(null == entity){
			throw new ResourceNotFoundException();
		}
		
		model.addAttribute(entity.acquire());
		return entity.getSelfTemplatePath();
	}

	/**
	 * 
	 * @param page
	 * @param model
	 */
	@RequestMapping(value = "/list", method = GET)
	public String list(Page<Channel> page, Model model) {
		page = channelService.queryPage(page);
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
		List<Channel> channels = channelService.query(new Object());
		List<Template> templates = templateService.query(new Object());
		model.addAttribute(new Channel()).addAttribute("channels", channels).addAttribute("templates", templates);
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
	public String create(Channel entity, BindingResult result) {

		if (result.hasErrors()) {
			return null;
		}

		entity.create();
		return REDIRECT_LIST;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.web.controller.CrudControllerSupport#edit(java.lang.Object, org.springframework.ui.Model)
	 */
	@Override
	@RequestMapping(value = "/{id}/edit", method = GET)
	public String edit(@PathVariable("id") String id, Model model) {
		List<Channel> channels = channelService.query(new Object());
		List<Template> templates = templateService.query(new Object());
		model.addAttribute(channelService.get(id)).addAttribute("channels", channels);
		model.addAttribute("templates", templates).addAttribute("_method", "PUT");
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

			Channel entity = channelService.get(id);
			bind(request, entity);
			entity.modify();
		} catch (Exception e) {
			return null;
		}

		return REDIRECT_LIST;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.web.controller.CrudControllerSupport#delete(java.lang.Object)
	 */
	@Override
	@RequestMapping(value = "/{id}/delete", method = DELETE)
	public String delete(@PathVariable("id") String id) {
		channelService.get(id).delete();
		return REDIRECT_LIST;
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

		return REDIRECT_LIST;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.web.controller.CrudControllerSupport#getViewPackage()
	 */
	@Override
	protected String getViewPackage() {
		return "channel";
	}

}
