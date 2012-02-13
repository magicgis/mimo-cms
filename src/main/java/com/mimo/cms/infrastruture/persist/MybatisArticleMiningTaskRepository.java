package com.mimo.cms.infrastruture.persist;

import org.springframework.stereotype.Repository;

import com.mimo.cms.domain.article.ArticleMiningTask;
import com.mimo.cms.domain.article.ArticleMiningTaskRepository;
import com.mimo.core.orm.mybatis.MybatisRepositorySupport;

/**
 * 
 * @author loudyn
 * 
 */
@Repository
public class MybatisArticleMiningTaskRepository extends MybatisRepositorySupport<String, ArticleMiningTask> implements
		ArticleMiningTaskRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.orm.mybatis.MybatisRepositorySupport#getNamespace()
	 */
	@Override
	protected String getNamespace() {
		return "com.mimo.cms.articleMiningTask";
	}

}
