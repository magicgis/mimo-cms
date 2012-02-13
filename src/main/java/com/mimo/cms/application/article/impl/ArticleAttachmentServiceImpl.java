package com.mimo.cms.application.article.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mimo.cms.application.article.ArticleAttachmentService;
import com.mimo.cms.domain.article.ArticleAttachment;
import com.mimo.cms.domain.article.ArticleAttachmentRepository;
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
public class ArticleAttachmentServiceImpl extends LifecycleEventHandler implements ArticleAttachmentService {

	@Autowired
	private ArticleAttachmentRepository articleAttachmentRepository;

	@Override
	public ArticleAttachment get(String id) {
		return articleAttachmentRepository.get(id);
	}

	@Override
	public Page<ArticleAttachment> queryPage(Page<ArticleAttachment> page) {
		return articleAttachmentRepository.queryPage(page);
	}

	@Override
	protected void onModify(Object source, long timestamp) {
		throw new UnsupportedOperationException("ArticleAttachment didn't supported to modify!");
	}

	@Override
	@Monitoring(action = "删除文章附件")
	protected void onDelete(Object source, long timestamp) {
		ArticleAttachment entity = (ArticleAttachment) source;
		articleAttachmentRepository.delete(entity);
	}

	@Override
	@Monitoring(action = "创建文章附件")
	protected void onCreate(Object source, long timestamp) {
		ArticleAttachment entity = (ArticleAttachment) source;
		articleAttachmentRepository.save(entity);
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
		return ArticleAttachment.class.isAssignableFrom(event.getSource().getClass());
	}
}
