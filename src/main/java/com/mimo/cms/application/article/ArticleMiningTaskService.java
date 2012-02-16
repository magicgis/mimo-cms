package com.mimo.cms.application.article;

import com.mimo.cms.domain.article.ArticleMiningTask;
import com.mimo.core.orm.Page;

/**
 * 
 * @author loudyn
 *
 */
public interface ArticleMiningTaskService {

	/**
	 * 
	 * @param id
	 * @return
	 */
	ArticleMiningTask get(String id);
	
	/**
	 * 
	 * @param addListener
	 */
	void runTask(ArticleMiningTask addListener);

	/**
	 * 
	 * @param page
	 * @return
	 */
	Page<ArticleMiningTask> queryPage(Page<ArticleMiningTask> page);
	
}
