package com.mimo.cms.application.article;

import com.mimo.cms.domain.article.ArticleMiningTask;
import com.mimo.core.orm.Page;

public interface ArticleMiningTaskService {

	ArticleMiningTask get(String id);
	
	void runTask(ArticleMiningTask addListener);

	Page<ArticleMiningTask> queryPage(Page<ArticleMiningTask> page);
	
}
