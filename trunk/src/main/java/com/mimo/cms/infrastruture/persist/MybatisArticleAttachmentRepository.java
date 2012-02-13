package com.mimo.cms.infrastruture.persist;

import org.springframework.stereotype.Repository;

import com.mimo.cms.domain.article.ArticleAttachment;
import com.mimo.cms.domain.article.ArticleAttachmentRepository;
import com.mimo.core.orm.mybatis.MybatisRepositorySupport;

/**
 * 
 * @author loudyn
 *
 */
@Repository
public class MybatisArticleAttachmentRepository extends MybatisRepositorySupport<String, ArticleAttachment> implements
		ArticleAttachmentRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.orm.mybatis.MybatisRepositorySupport#getNamespace()
	 */
	@Override
	protected String getNamespace() {
		return "com.mimo.cms.articleAttachment";
	}

}
