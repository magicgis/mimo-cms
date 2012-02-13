package com.mimo.cms.application.article.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mimo.cms.application.article.ArticleCommentService;
import com.mimo.cms.domain.article.ArticleComment;
import com.mimo.cms.domain.article.ArticleCommentRepository;
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
public class ArticleCommentServiceImpl extends LifecycleEventHandler implements ArticleCommentService {

	@Autowired
	private ArticleCommentRepository articleCommentRepository;

	@Override
	public ArticleComment get(String id) {
		return articleCommentRepository.get(id);
	}

	@Override
	public Page<ArticleComment> queryPage(Page<ArticleComment> page) {
		return articleCommentRepository.queryPage(page);
	}

	@Override
	@Monitoring(action = "修改文章评论")
	protected void onModify(Object source, long timestamp) {
		ArticleComment entity = (ArticleComment) source;
		articleCommentRepository.update(entity);
	}

	@Override
	@Monitoring(action = "删除文章评论")
	protected void onDelete(Object source, long timestamp) {
		ArticleComment entity = (ArticleComment) source;
		articleCommentRepository.delete(entity);
	}

	@Override
	@Monitoring(action = "创建文章评论")
	protected void onCreate(Object source, long timestamp) {
		ArticleComment entity = (ArticleComment) source;
		articleCommentRepository.save(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mimo.core.domain.event.LifecycleEventHandler#isAcceptableLifecycleEvent(com.mimo.core.domain.event.LifecycleEvent
	 * )
	 */
	@Override
	protected boolean isAcceptableLifecycleEvent(LifecycleEvent event) {
		return ArticleComment.class.isAssignableFrom(event.getSource().getClass());
	}

}
