package com.mimo.cms.domain.article;

import com.mimo.core.orm.Page;

/**
 * 
 * @author loudyn
 * 
 */
public interface ArticleCommentRepository {

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

	/**
	 * 
	 * @param entity
	 */
	void update(ArticleComment entity);

	/**
	 * 
	 * @param entity
	 */
	void delete(ArticleComment entity);

	/**
	 * 
	 * @param entity
	 */
	void save(ArticleComment entity);

}
