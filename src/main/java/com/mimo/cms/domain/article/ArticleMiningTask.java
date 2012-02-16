package com.mimo.cms.domain.article;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.mimo.cms.domain.channel.Channel;
import com.mimo.core.annotation.NotThreadSafe;
import com.mimo.core.domain.event.AbstractLifecycleAwareObject;

/**
 * 
 * @author loudyn
 * 
 */
public class ArticleMiningTask extends AbstractLifecycleAwareObject<ArticleMiningTask> {

	private static final long serialVersionUID = 1L;

	public static enum TaskStatus {
		RUNNING, STOPED;
	}

	public static interface ArticleMiningTaskListener {
		/**
		 * 
		 * @param task
		 */
		public void beforeTaskExecute(ArticleMiningTask task);

		/**
		 * 
		 * @param task
		 */
		public void afterTaskExecute(ArticleMiningTask task);
	}

	private Channel channel;
	private TaskStatus status;
	private String name;

	private String itemsExpression;
	private String titleExpression;
	private String contentExpression;
	private String sourceExpression;
	private String tagsExpression;
	private String createTimeExpression;

	private List<ArticleMiningTaskListener> listeners = new ArrayList<ArticleMiningTaskListener>();

	public Channel getChannel() {
		return channel;
	}

	public ArticleMiningTask setChannel(Channel channel) {
		this.channel = channel;
		return this;
	}

	public TaskStatus getStatus() {
		return status;
	}

	protected void setStatus(TaskStatus status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public ArticleMiningTask setName(String name) {
		this.name = name;
		return this;
	}

	public String getItemsExpression() {
		return itemsExpression;
	}
	
	public ArticleMiningTask setItemsExpression(String itemsExpression) {
		this.itemsExpression = itemsExpression;
		return this;
	}

	public String getTitleExpression() {
		return titleExpression;
	}

	public ArticleMiningTask setTitleExpression(String titleExpression) {
		this.titleExpression = titleExpression;
		return this;
	}

	public String getContentExpression() {
		return contentExpression;
	}

	public ArticleMiningTask setContentExpression(String contentExpression) {
		this.contentExpression = contentExpression;
		return this;
	}

	public String getSourceExpression() {
		return sourceExpression;
	}

	public ArticleMiningTask setSourceExpression(String sourceExpression) {
		this.sourceExpression = sourceExpression;
		return this;
	}

	public String getTagsExpression() {
		return tagsExpression;
	}

	public ArticleMiningTask setTagsExpression(String tagsExpression) {
		this.tagsExpression = tagsExpression;
		return this;
	}

	public String getCreateTimeExpression() {
		return createTimeExpression;
	}

	public ArticleMiningTask setCreateTimeExpression(String createTimeExpression) {
		this.createTimeExpression = createTimeExpression;
		return this;
	}

	/**
	 * 
	 * @param listener
	 * @return
	 */
	@NotThreadSafe
	public ArticleMiningTask addListener(ArticleMiningTaskListener listener) {
		this.listeners.add(listener);
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public ArticleMiningTask begin() {
		executeBefore();
		return this;
	}

	private void executeBefore() {
		setStatus(TaskStatus.RUNNING);
		for (ArticleMiningTaskListener listener : this.listeners) {
			listener.beforeTaskExecute(this);
		}
	}

	/**
	 * 
	 * @return
	 */
	public ArticleMiningTask end() {
		executeAfter();
		return this;
	}

	private void executeAfter() {
		setStatus(TaskStatus.STOPED);
		for (ArticleMiningTaskListener listener : this.listeners) {
			listener.beforeTaskExecute(this);
		}
	}

	/**
	 * 
	 * @return
	 */
	public boolean isRunning() {
		return getStatus() == TaskStatus.RUNNING;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.AbstractLifecycleAwareObject#beforeCreate()
	 */
	@Override
	protected boolean beforeCreate() {
		selfCheck();
		return true;
	}

	private ArticleMiningTask selfCheck() {
		if (null == getChannel()) {
			throw new IllegalStateException("ArticleMiningTask required a channel!");
		}

		if (StringUtils.isBlank(getChannel().getId())) {
			throw new IllegalStateException("Channel must be avaliable!");
		}

		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.AbstractLifecycleAwareObject#beforeModify()
	 */
	@Override
	protected boolean beforeModify() {
		selfCheck();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.AbstractLifecycleAwareObject#getCaller()
	 */
	@Override
	protected ArticleMiningTask getCaller() {
		return this;
	}

}
