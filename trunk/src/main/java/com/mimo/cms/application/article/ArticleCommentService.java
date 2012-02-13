package com.mimo.cms.application.article;

import com.mimo.cms.domain.article.ArticleComment;
import com.mimo.core.orm.Page;

/**
 * 
 * @author loudyn
 *
 */
public interface ArticleCommentService {

	/**
	 * 
	 * @param id
	 * @return
	 */
	ArticleComment get(String id);

	/**
	 * 
	 * @param page
	 * @return
	 */
	Page<ArticleComment> queryPage(Page<ArticleComment> page);

}
