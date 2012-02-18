package com.mimo.cms.domain.article;

import static com.mimo.util.AssertUtils.isTrue;
import static com.mimo.util.AssertUtils.notNull;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.mimo.cms.domain.channel.Channel;
import com.mimo.cms.infrastruture.PhotoPopulator;
import com.mimo.core.domain.event.AbstractLifecycleAwareObject;

/**
 * 
 * @author loudyn
 * 
 */
public class Article extends AbstractLifecycleAwareObject<Article> {
	private static final long serialVersionUID = 1L;

	public static enum ArticleStatus {
		ONLINE, OFFLINE
	}

	private Channel channel;

	private String title;
	private String content;
	private String source;
	private String tags;
	private int priority;

	private boolean onTop;
	private boolean forbidComments;
	private long createTime;
	private long modifyTime;

	private ArticleStatus status = ArticleStatus.ONLINE;

	public Channel getChannel() {
		return channel;
	}

	public Article setChannel(Channel channel) {
		this.channel = channel;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public Article setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getContent() {
		return content;
	}

	public Article setContent(String content) {
		this.content = content;
		return this;
	}

	public String getSource() {
		return source;
	}

	public Article setSource(String source) {
		this.source = source;
		return this;
	}

	public String getTags() {
		return tags;
	}

	public Article setTags(String tags) {
		this.tags = tags;
		return this;
	}

	public int getPriority() {
		return priority;
	}

	public Article setPriority(int priority) {
		this.priority = priority;
		return this;
	}

	public boolean isOnTop() {
		return onTop;
	}

	protected Article setOnTop(boolean onTop) {
		this.onTop = onTop;
		return this;
	}

	public Article onTop() {
		return setOnTop(true);
	}

	public Article notOnTop() {
		return setOnTop(false);
	}

	public boolean isForbidComments() {
		return forbidComments;
	}

	protected Article setForbidComments(boolean forbidComments) {
		this.forbidComments = forbidComments;
		return this;
	}

	public Article forbidComments() {
		return setForbidComments(true);
	}

	public Article notForbidComments() {
		return setForbidComments(false);
	}

	public long getCreateTime() {
		return createTime;
	}

	protected Article setCreateTime(long createTime) {
		this.createTime = createTime;
		return this;
	}

	public long getModifyTime() {
		return modifyTime;
	}

	protected Article setModifyTime(long modifyTime) {
		this.modifyTime = modifyTime;
		return this;
	}

	public ArticleStatus getStatus() {
		return status;
	}

	public Article setStatus(ArticleStatus status) {
		this.status = status;
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public Article online() {
		return setStatus(ArticleStatus.ONLINE);
	}

	/**
	 * 
	 * @return
	 */
	public Article offline() {
		return setStatus(ArticleStatus.OFFLINE);
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getPhotos() {
		return PhotoPopulator.populate(getContent());
	}

	/**
	 * 
	 * @return
	 */
	public boolean hasPhotos() {
		return !getPhotos().isEmpty();
	}

	/**
	 * 
	 * @return
	 */
	public boolean isEmptyPhotos() {
		return getPhotos().isEmpty();
	}

	@Override
	protected boolean beforeCreate() {
		long current = System.currentTimeMillis();
		setCreateTime(current).setModifyTime(current).selfCheck();
		return true;
	}

	/**
	 * 
	 * @return
	 */
	private Article selfCheck() {
		notNull(getChannel(), new IllegalStateException("Article required a channel!"));
		boolean channelMustAvaliable = StringUtils.isNotBlank(getChannel().getId());
		isTrue(channelMustAvaliable, new IllegalStateException("Channel must avaliable!"));

		return this;
	}

	@Override
	protected boolean beforeModify() {
		setModifyTime(System.currentTimeMillis()).selfCheck();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.AbstractLifecycleAwareObject#getCaller()
	 */
	@Override
	protected Article getCaller() {
		return this;
	}
}
