package com.mimo.cms.application.article;

import com.mimo.cms.domain.article.ArticleAttachment;
import com.mimo.core.orm.Page;

/**
 * 
 * @author loudyn
 *
 */
public interface ArticleAttachmentService {
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	ArticleAttachment get(String id);

	/**
	 * 
	 * @param page
	 * @return
	 */
	Page<ArticleAttachment> queryPage(Page<ArticleAttachment> page);
}
