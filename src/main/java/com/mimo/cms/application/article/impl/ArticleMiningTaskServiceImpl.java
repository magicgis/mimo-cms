package com.mimo.cms.application.article.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mimo.cms.application.article.ArticleMiningTaskService;
import com.mimo.cms.domain.article.ArticleMiningTask;
import com.mimo.cms.domain.article.ArticleMiningTaskRepository;
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
public class ArticleMiningTaskServiceImpl extends LifecycleEventHandler implements ArticleMiningTaskService {

	@Autowired
	private ArticleMiningTaskRepository articleMiningTaskRepository;

	@Override
	public ArticleMiningTask get(String id) {
		return articleMiningTaskRepository.get(id);
	}

	@Override
	@Monitoring(action = "启动文章采集任务")
	public void runTask(ArticleMiningTask task) {

		try {
			task.begin();

		} finally {
			task.end();
		}

	}

	@Override
	public Page<ArticleMiningTask> queryPage(Page<ArticleMiningTask> page) {
		return articleMiningTaskRepository.queryPage(page);
	}

	@Override
	@Monitoring(action = "修改文章采集任务")
	protected void onModify(Object source, long timestamp) {
		ArticleMiningTask task = (ArticleMiningTask) source;
		articleMiningTaskRepository.update(task);
	}

	@Override
	@Monitoring(action = "删除文章采集任务")
	protected void onDelete(Object source, long timestamp) {
		ArticleMiningTask task = (ArticleMiningTask) source;
		articleMiningTaskRepository.delete(task);
	}

	@Override
	@Monitoring(action = "创建文章采集任务")
	protected void onCreate(Object source, long timestamp) {
		ArticleMiningTask task = (ArticleMiningTask) source;
		articleMiningTaskRepository.save(task);
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
		return ArticleMiningTask.class.isAssignableFrom(event.getSource().getClass());
	}

}
