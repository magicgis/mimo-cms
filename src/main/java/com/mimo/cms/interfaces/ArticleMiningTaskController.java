package com.mimo.cms.interfaces;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import com.mimo.cms.application.article.ArticleMiningTaskService;
import com.mimo.cms.application.channel.ChannelService;
import com.mimo.cms.domain.article.ArticleMiningTask;
import com.mimo.cms.domain.article.ArticleMiningTask.ArticleMiningTaskListener;
import com.mimo.cms.domain.channel.Channel;
import com.mimo.cms.interfaces.util.JsonMessage;
import com.mimo.core.orm.Page;
import com.mimo.core.web.AjaxUtils;
import com.mimo.core.web.controller.CrudControllerSupport;
import com.mimo.util.EntityUtils;

/**
 * 
 * @author loudyn
 * 
 */
@Controller
@RequestMapping("/article/mining-task")
public class ArticleMiningTaskController extends CrudControllerSupport<String, ArticleMiningTask> {

	private static final String REDIRECT_LIST = "redirect:/article/mining-task/list";

	@Autowired
	private ChannelService channelService;

	@Autowired
	private ArticleMiningTaskService miningTaskService;

	private List<String> runningTaskQueue = new CopyOnWriteArrayList<String>();

	private final ArticleMiningTaskListener statusListener = new ArticleMiningTaskListener() {

		@Override
		public void beforeTaskExecute(ArticleMiningTask task) {
			runningTaskQueue.add(task.getId());
			task.modify();
		}

		@Override
		public void afterTaskExecute(ArticleMiningTask task) {
			runningTaskQueue.remove(task.getId());
			task.modify();
		}
	};

	@RequestMapping(value = "/list", method = GET)
	public String list(Page<ArticleMiningTask> page, Model model) {
		page = miningTaskService.queryPage(page);
		model.addAttribute(page);
		return listView();
	}

	@Override
	@RequestMapping(value = "/create", method = GET)
	public String create(Model model) {
		List<Channel> channels = channelService.query(new Object());
		model.addAttribute(new ArticleMiningTask()).addAttribute("channels", channels);
		return formView();

	}

	@Override
	@RequestMapping(value = "/create", method = POST)
	public String create(ArticleMiningTask entity, BindingResult result) {
		if (result.hasErrors()) {
			return null;
		}

		entity.create();
		return REDIRECT_LIST;
	}

	@Override
	@RequestMapping(value = "/{id}/edit", method = GET)
	public String edit(@PathVariable("id") String id, Model model) {

		ArticleMiningTask entity = miningTaskService.get(id);
		List<Channel> channels = channelService.query(new Object());
		model.addAttribute(entity).addAttribute("channels", channels).addAttribute("_method", "PUT");
		return formView();
	}

	@Override
	@RequestMapping(value = "/{id}/edit", method = PUT)
	public String edit(@PathVariable("id") String id, HttpServletRequest request) {

		try {

			ArticleMiningTask entity = miningTaskService.get(id);
			bind(request, entity);
			entity.modify();
		} catch (Exception e) {
			return null;
		}

		return REDIRECT_LIST;
	}

	@Override
	@RequestMapping(value = "/{id}/delete", method = DELETE)
	public String delete(String id) {
		miningTaskService.get(id).delete();
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

	/**
	 * 
	 * @param id
	 * @param requestWith
	 * @return
	 */
	@RequestMapping(value = "/{id}/run", method = POST)
	public JsonMessage runTask(@PathVariable("id") String id, @RequestHeader("X-Requested-With") String requestWith) {

		if (!AjaxUtils.isAjaxRequest(requestWith)) {
			return JsonMessage.me().error().message("Unsupported request!");
		}

		ArticleMiningTask entity = miningTaskService.get(id);
		if (entity.isRunning()) {
			return JsonMessage.me().error().message("Task already running!");
		}

		miningTaskService.runTask(entity.addListener(statusListener));
		return JsonMessage.me().success();
	}

	/**
	 * 
	 * @param requestWith
	 * @return
	 */
	@RequestMapping(value = "/status", method = GET)
	public List<String> taskStatus(@RequestHeader("X-Requested-With") String requestWith) {
		if (!AjaxUtils.isAjaxRequest(requestWith)) {
			return Collections.emptyList();
		}

		return runningTaskQueue;
	}

	@Override
	protected String getViewPackage() {
		return "article/mining-task";
	}

}
