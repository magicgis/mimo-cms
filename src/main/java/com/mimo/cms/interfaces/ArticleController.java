package com.mimo.cms.interfaces;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mimo.cms.application.article.ArticleService;
import com.mimo.cms.application.channel.ChannelService;
import com.mimo.cms.domain.article.Article;
import com.mimo.cms.domain.article.Article.ArticleStatus;
import com.mimo.cms.domain.channel.Channel;
import com.mimo.cms.interfaces.util.JsonMessage;
import com.mimo.core.orm.Page;
import com.mimo.core.web.AjaxUtils;
import com.mimo.core.web.controller.CrudControllerSupport;
import com.mimo.core.web.exception.ResourceNotFoundException;
import com.mimo.util.EntityUtils;
import com.mimo.util.PathUtils;

/**
 * 
 * @author loudyn
 * 
 */
@Controller
@RequestMapping("/article")
public class ArticleController extends CrudControllerSupport<String, Article> {

	private static final String REDIRECT_ONLINE_LIST = "redirect:/article/list/online";
	private static final String REDIRECT_OFFLINE_LIST = "redirect:/article/list/offline";

	@Autowired
	private ChannelService channelService;

	@Autowired
	private ArticleService articleService;

	@RequestMapping(value = { "/{id}", "/{id}/view" }, method = GET)
	public String view(@PathVariable("id") String id, Model model) {
		Article entity = articleService.get(id);
		if (null == entity) {
			throw new ResourceNotFoundException();
		}

		Channel channel = entity.getChannel();
		model.addAttribute(entity).addAttribute(channel);
		return channel.getArticleTemplatePath();
	}

	/**
	 * 
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = GET)
	public String list(Page<Article> page, Model model) {
		page = articleService.queryPage(page);
		model.addAttribute(page);
		return listView();
	}

	/**
	 * 
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list/online", method = GET)
	public String listOnline(Page<Article> page, Model model) {
		page.getParams().put("status", ArticleStatus.ONLINE);
		page = articleService.queryPage(page);
		model.addAttribute(page);
		return listView();
	}

	/**
	 * 
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list/offline", method = GET)
	public String listOffline(Page<Article> page, Model model) {
		page.getParams().put("status", ArticleStatus.OFFLINE);
		page = articleService.queryPage(page);
		model.addAttribute(page);
		return PathUtils.asUnix(getViewPackage().concat("/offline-list"));
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
		model.addAttribute(new Article()).addAttribute("channels", channels);
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
	public String create(Article entity, BindingResult result) {
		if (result.hasErrors()) {
			return null;
		}

		entity.create();
		return REDIRECT_ONLINE_LIST;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.web.controller.CrudControllerSupport#edit(java.lang.Object, org.springframework.ui.Model)
	 */
	@Override
	@RequestMapping(value = "/{id}/edit", method = GET)
	public String edit(@PathVariable("id") String id, Model model) {
		Article entity = articleService.get(id);
		List<Channel> channels = channelService.query(new Object());
		model.addAttribute(entity).addAttribute("channels", channels).addAttribute("_method", "PUT");
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

			Article entity = articleService.get(id);
			bind(request, entity);
			entity.modify();
		} catch (Exception e) {
			return null;
		}

		return REDIRECT_ONLINE_LIST;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.web.controller.CrudControllerSupport#delete(java.lang.Object)
	 */
	@Override
	@RequestMapping(value = "/{id}/delete", method = DELETE)
	public String delete(@PathVariable("id") String id) {
		Article entity = articleService.get(id);
		entity.delete();
		return REDIRECT_OFFLINE_LIST;
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
		return REDIRECT_OFFLINE_LIST;
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @return
	 */
	@RequestMapping(value = "/on-top", method = PUT)
	@ResponseBody
	public JsonMessage onTop(HttpServletRequest request, @RequestHeader(value = "X-Requested-With", required = false) String requestWith) {
		try {

			if (!AjaxUtils.isAjaxRequest(requestWith)) {
				return JsonMessage.me().error().message("Not supported operation!");
			}

			String[] items = findItems(request);
			articleService.markOnTop(EntityUtils.nullSafe(items, new String[] {}));
			return JsonMessage.me().success();
		} catch (Exception e) {
			return JsonMessage.me().error().message(e.getMessage());
		}
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @return
	 */
	@RequestMapping(value = "/not-on-top", method = PUT)
	@ResponseBody
	public JsonMessage notOnTop(HttpServletRequest request, @RequestHeader(value = "X-Requested-With", required = false) String requestWith) {
		try {

			if (!AjaxUtils.isAjaxRequest(requestWith)) {
				return JsonMessage.me().error().message("Not supported operation!");
			}

			String[] items = findItems(request);
			articleService.markNotOnTop(EntityUtils.nullSafe(items, new String[] {}));
			return JsonMessage.me().success();
		} catch (Exception e) {
			return JsonMessage.me().error().message(e.getMessage());
		}
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @return
	 */
	@RequestMapping(value = "/not-comments", method = PUT)
	@ResponseBody
	public JsonMessage notComments(HttpServletRequest request,
			@RequestHeader(value = "X-Requested-With", required = false) String requestWith) {
		try {

			if (!AjaxUtils.isAjaxRequest(requestWith)) {
				return JsonMessage.me().error().message("Not supported operation!");
			}

			String[] items = findItems(request);
			articleService.markNotComments(EntityUtils.nullSafe(items, new String[] {}));
			return JsonMessage.me().success();
		} catch (Exception e) {
			return JsonMessage.me().error().message(e.getMessage());
		}
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @return
	 */
	@RequestMapping(value = "/allow-comments", method = PUT)
	@ResponseBody
	public JsonMessage allowComments(HttpServletRequest request,
			@RequestHeader(value = "X-Requested-With", required = false) String requestWith) {
		try {

			if (!AjaxUtils.isAjaxRequest(requestWith)) {
				return JsonMessage.me().error().message("Not supported operation!");
			}

			String[] items = findItems(request);
			articleService.markAllowComments(EntityUtils.nullSafe(items, new String[] {}));
			return JsonMessage.me().success();
		} catch (Exception e) {
			return JsonMessage.me().error().message(e.getMessage());
		}
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @return
	 */
	@RequestMapping(value = "/online", method = PUT)
	@ResponseBody
	public JsonMessage online(HttpServletRequest request, @RequestHeader(value = "X-Requested-With", required = false) String requestWith) {
		try {

			if (!AjaxUtils.isAjaxRequest(requestWith)) {
				return JsonMessage.me().error().message("Not supported operation!");
			}

			String[] items = findItems(request);
			articleService.markOnline(EntityUtils.nullSafe(items, new String[] {}));
			return JsonMessage.me().success();
		} catch (Exception e) {
			return JsonMessage.me().error().message(e.getMessage());
		}
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @return
	 */
	@RequestMapping(value = "/offline", method = PUT)
	@ResponseBody
	public JsonMessage offline(HttpServletRequest request, @RequestHeader(value = "X-Requested-With", required = false) String requestWith) {
		try {

			if (!AjaxUtils.isAjaxRequest(requestWith)) {
				return JsonMessage.me().error().message("Not supported operation!");
			}

			String[] items = findItems(request);
			articleService.markOffline(EntityUtils.nullSafe(items, new String[] {}));
			return JsonMessage.me().success();
		} catch (Exception e) {
			return JsonMessage.me().error().message(e.getMessage());
		}
	}

	private String[] findItems(HttpServletRequest request) {
		String itemsAsString = request.getParameter("items");
		return StringUtils.split(itemsAsString, ',');
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.web.controller.CrudControllerSupport#getViewPackage()
	 */
	@Override
	protected String getViewPackage() {
		return "article";
	}
}
