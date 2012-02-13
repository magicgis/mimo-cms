package com.mimo.cms.interfaces;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mimo.cms.application.article.ArticleAttachmentService;
import com.mimo.cms.application.article.ArticleService;
import com.mimo.cms.domain.Configure;
import com.mimo.cms.domain.article.Article;
import com.mimo.cms.domain.article.ArticleAttachment;
import com.mimo.cms.interfaces.util.ConfigureOnWeb;
import com.mimo.cms.interfaces.util.JsonMessage;
import com.mimo.core.orm.Page;
import com.mimo.core.web.WebUtils;
import com.mimo.core.web.controller.ControllerSupport;
import com.mimo.util.AssertUtils;
import com.mimo.util.EntityUtils;
import com.mimo.util.ExceptionUtils;
import com.mimo.util.FileUtils;
import com.mimo.util.PathUtils;

/**
 * 
 * @author loudyn
 * 
 */
@Controller
public class ArticleAttachmentController extends ControllerSupport {
	private static final String REDIRECT_LIST = "redirect:/article/attachment/list";

	@Autowired
	private ArticleService articleService;

	@Autowired
	private ArticleAttachmentService articleAttachmentService;

	@Autowired
	private ConfigureOnWeb confOnWeb;

	/**
	 * 
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/article/attachment/list", method = GET)
	public String list(Page<ArticleAttachment> page, Model model) {
		page = articleAttachmentService.queryPage(page);
		model.addAttribute(page);
		return PathUtils.asUnix(getViewPackage().concat("/list"));
	}

	/**
	 * 
	 * @param articleId
	 * @param entity
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/article/{articleId}/attachment/create", method = POST)
	public void create(@PathVariable("articleId") String articleId, @RequestParam("file") MultipartFile file, HttpServletResponse response) {

		try {

			response.setContentType("text/html");
			Configure conf = Configure.get();
			Configure wrapper = confOnWeb.wrap(conf);

			if (isUnacceptableFile(file, wrapper)) {
				throw new UnsupportedOperationException("The file is unacceptable!");
			}

			Article article = articleService.lazyGet(articleId);
			new ArticleAttachment().setName(file.getOriginalFilename())
									.setContentType(file.getContentType())
									.setArticle(article)
									.selfAdjusting(wrapper)
									.create();

			jsonStringEnclosingWith(response, "<textarea>%s</textarea>", JsonMessage.me().success());
		} catch (Exception e) {
			jsonStringEnclosingWith(response, "<textarea>%s</textarea>", JsonMessage.me().error().message(e.getMessage()));
		}

	}

	private boolean isUnacceptableFile(MultipartFile file, Configure conf) {
		AssertUtils.notNull(file);
		if (file.isEmpty() || conf.isOverflowResourceSize(file.getSize())) {
			return true;
		}

		String fileSuffix = FileUtils.getSuffixWithoutDot(file.getOriginalFilename());
		if (!conf.isAllowedResourceTypes(fileSuffix)) {
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @param id
	 * @param response
	 */
	@RequestMapping(value = "/article/attachment/{id}/download", method = RequestMethod.GET)
	public void download(@RequestParam(value = "id") String id, HttpServletResponse response) {

		Configure conf = confOnWeb.wrap(Configure.get());

		try {

			ArticleAttachment entity = articleAttachmentService.get(id);
			WebUtils.prepareDownload(response, entity.getName(), entity.getContentType());
			entity.selfAdjusting(conf).piping(response.getOutputStream());
		} catch (Exception e) {
			throw ExceptionUtils.toUnchecked(e);
		}

	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/article/attachment/{id}/delete", method = DELETE)
	public String delete(String id) {
		
		Configure conf = confOnWeb.wrap(Configure.get());
		ArticleAttachment entity = articleAttachmentService.get(id);
		entity.selfAdjusting(conf).delete();
		return REDIRECT_LIST;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/article/attachment/delete", method = DELETE)
	public String delete(HttpServletRequest request) {

		String[] items = request.getParameterValues("items");
		for (String item : EntityUtils.nullSafe(items, new String[] {})) {
			delete(item);
		}

		return REDIRECT_LIST;
	}

	protected String getViewPackage() {
		return "article/attachment";
	}
}
