package com.mimo.cms.application.article.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mimo.cms.application.article.ArticleService;
import com.mimo.cms.domain.article.Article;
import com.mimo.cms.domain.article.ArticleRepository;
import com.mimo.core.domain.event.LifecycleEvent;
import com.mimo.core.domain.event.LifecycleEventHandler;
import com.mimo.core.domain.monitor.Monitoring;
import com.mimo.core.orm.Page;

/**
 * 
 * @author loudyn
 * 
 */
@Transactional
@Service
public class ArticleServiceImpl extends LifecycleEventHandler implements ArticleService {

	@Autowired
	private ArticleRepository articleRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.cms.application.article.ArticleService#get(java.lang.String)
	 */
	@Override
	public Article get(String id) {
		return articleRepository.get(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.cms.application.article.ArticleService#lazyGet(java.lang.String)
	 */
	@Override
	public Article lazyGet(String id) {
		return articleRepository.lazyGet(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.cms.application.article.ArticleService#queryPage(com.youboy.core.orm.Page)
	 */
	@Override
	public Page<Article> queryPage(Page<Article> page) {
		return articleRepository.lazyQueryPage(page);
	}

	@Override
	public void markOnTop(String[] ids) {
		articleRepository.markOnTop(ids);
	}

	@Override
	public void markNotOnTop(String[] ids) {
		articleRepository.markNotOnTop(ids);
	}

	@Override
	public void markOnline(String[] ids) {
		articleRepository.markOnline(ids);
	}

	@Override
	public void markOffline(String[] ids) {
		articleRepository.markOffline(ids);
	}

	@Override
	public void markNotComments(String[] ids) {
		articleRepository.markNotComments(ids);
	}

	@Override
	public void markAllowComments(String[] ids) {
		articleRepository.markAllowComments(ids);
	}

	@Override
	@Monitoring(action = "修改文章")
	protected void onModify(Object source, long timestamp) {
		Article entity = (Article) source;
		articleRepository.update(entity);
	}

	@Override
	@Monitoring(action = "删除文章")
	protected void onDelete(Object source, long timestamp) {
		Article entity = (Article) source;
		articleRepository.delete(entity);
	}

	@Override
	@Monitoring(action = "创建文章")
	protected void onCreate(Object source, long timestamp) {
		Article entity = (Article) source;
		articleRepository.save(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.cms.application.LifecycleEventService#isAcceptableLifecycleEvent(com.mimo.cms.domain.lifecycle.
	 * LifecycleEvent)
	 */
	@Override
	protected boolean isAcceptableLifecycleEvent(LifecycleEvent event) {
		return Article.class.isAssignableFrom(event.getSource().getClass());
	}
}
