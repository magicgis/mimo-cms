package com.mimo.cms.infrastruture.persist;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mimo.cms.domain.article.Article;
import com.mimo.cms.domain.article.ArticleRepository;
import com.mimo.core.orm.Page;
import com.mimo.core.orm.mybatis.MybatisRepositorySupport;

/**
 * 
 * @author loudyn
 * 
 */
@Repository
public class MybatisArticleRepository extends MybatisRepositorySupport<String, Article> implements ArticleRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.cms.domain.article.ArticleRepository#lazyGet(java.lang.String)
	 */
	@Override
	public Article lazyGet(String id) {
		return (Article) getSqlSession().selectOne(getNamespace().concat(".lazyGet"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<Article> lazyQueryPage(Page<Article> page) {
		List<Article> result = getSqlSession().selectList(getNamespace() + ".lazyQueryPage", page);
		page.setResult(result);
		return page;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.cms.domain.article.ArticleRepository#markOnTop(java.lang.String[])
	 */
	@Override
	public void markOnTop(String[] ids) {
		if (isAvaliableIds(ids)) {
			getSqlSession().update(getNamespace().concat(".markOnTop"), ids);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.cms.domain.article.ArticleRepository#markNotOnTop(java.lang.String[])
	 */
	@Override
	public void markNotOnTop(String[] ids) {
		if (isAvaliableIds(ids)) {
			getSqlSession().update(getNamespace().concat(".markNotOnTop"), ids);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.cms.domain.article.ArticleRepository#markOnline(java.lang.String[])
	 */
	@Override
	public void markOnline(String[] ids) {
		if (isAvaliableIds(ids)) {
			getSqlSession().update(getNamespace().concat(".markOnline"), ids);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.cms.domain.article.ArticleRepository#markOffline(java.lang.String[])
	 */
	@Override
	public void markOffline(String[] ids) {
		if (isAvaliableIds(ids)) {
			getSqlSession().update(getNamespace().concat(".markOffline"), ids);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.cms.domain.article.ArticleRepository#markNotComments(java.lang.String[])
	 */
	@Override
	public void markNotComments(String[] ids) {
		if (isAvaliableIds(ids)) {
			getSqlSession().update(getNamespace().concat(".markNotComments"), ids);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.cms.domain.article.ArticleRepository#markAllowComments(java.lang.String[])
	 */
	@Override
	public void markAllowComments(String[] ids) {
		if (isAvaliableIds(ids)) {
			getSqlSession().update(getNamespace().concat(".markAllowComments"), ids);
		}
	}

	private boolean isAvaliableIds(String[] ids) {
		return null != ids && ids.length > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.orm.mybatis.MybatisRepositorySupport#getNamespace()
	 */
	@Override
	protected String getNamespace() {
		return "com.mimo.cms.article";
	}
}
