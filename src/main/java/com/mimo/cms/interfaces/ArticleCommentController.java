package com.mimo.cms.interfaces;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mimo.cms.application.article.ArticleCommentService;
import com.mimo.cms.application.article.ArticleService;
import com.mimo.cms.domain.article.Article;
import com.mimo.cms.domain.article.ArticleComment;
import com.mimo.core.orm.Page;
import com.mimo.core.web.controller.ControllerSupport;
import com.mimo.util.EntityUtils;
import com.mimo.util.PathUtils;

/**
 * 
 * @author loudyn
 * 
 */
@Controller
public class ArticleCommentController extends ControllerSupport {
	private static final String REDIRECT_LIST = "redirect:/article/comment/list";

	@Autowired
	private ArticleService articleService;

	@Autowired
	private ArticleCommentService articleCommentService;

	/**
	 * 
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/article/comment/list", method = GET)
	public String list(Page<ArticleComment> page, Model model) {
		page = articleCommentService.queryPage(page);
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
	@RequestMapping(value = "/article/{articleId}/comment/create", method = POST)
	@ResponseBody
	public ArticleComment create(@PathVariable("articleId") String articleId, ArticleComment entity, BindingResult result) {
		if (result.hasErrors()) {
			return null;
		}

		Article article = articleService.lazyGet(articleId);
		return entity.setArticle(article).create();
	}

	/**
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/article/comment/{id}/edit", method = GET)
	public String edit(@PathVariable("id") String id, Model model) {

		ArticleComment entity = articleCommentService.get(id);
		model.addAttribute(entity).addAttribute("_method", "PUT");
		return PathUtils.asUnix(getViewPackage().concat("/form"));
	}

	/**
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/article/comment/{id}/edit", method = PUT)
	public String edit(@PathVariable("id") String id, HttpServletRequest request) {

		try {

			ArticleComment entity = articleCommentService.get(id);
			bind(request, entity);
			entity.modify();
		} catch (Exception e) {
			return null;
		}

		return REDIRECT_LIST;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/article/comment/{id}/delete", method = DELETE)
	public String delete(String id) {
		ArticleComment entity = articleCommentService.get(id);
		entity.delete();
		return REDIRECT_LIST;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/article/comment/delete", method = DELETE)
	public String delete(HttpServletRequest request) {

		String[] items = request.getParameterValues("items");
		for (String item : EntityUtils.nullSafe(items, new String[] {})) {
			delete(item);
		}

		return REDIRECT_LIST;
	}

	private String getViewPackage() {
		return "article/comment";
	}
}
