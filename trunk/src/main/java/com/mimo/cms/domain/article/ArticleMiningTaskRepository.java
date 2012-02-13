package com.mimo.cms.domain.article;

import com.mimo.core.orm.Page;

public interface ArticleMiningTaskRepository {

	ArticleMiningTask get(String id);

	Page<ArticleMiningTask> queryPage(Page<ArticleMiningTask> page);

	void update(ArticleMiningTask task);

	void delete(ArticleMiningTask task);

	void save(ArticleMiningTask task);
	
}
