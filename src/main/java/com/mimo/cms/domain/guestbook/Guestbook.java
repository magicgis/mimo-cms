package com.mimo.cms.domain.guestbook;

import com.mimo.cms.infrastruture.safe.HtmlEscape;
import com.mimo.cms.infrastruture.safe.HtmlEscapeRequired;
import com.mimo.cms.infrastruture.safe.HtmlEscapeUtils;
import com.mimo.core.domain.event.AbstractLifecycleAwareObject;

/**
 * 
 * @author loudyn
 * 
 */
@HtmlEscapeRequired
public class Guestbook extends AbstractLifecycleAwareObject<Guestbook> {

	private static final long serialVersionUID = 1L;

	@HtmlEscape
	private String email;
	@HtmlEscape
	private String content;
	private long createTime;

	public String getEmail() {
		return email;
	}

	public Guestbook setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getContent() {
		return content;
	}

	public Guestbook setContent(String content) {
		this.content = content;
		return this;
	}

	public long getCreateTime() {
		return createTime;
	}

	protected Guestbook setCreateTime(long createTime) {
		this.createTime = createTime;
		return this;
	}

	protected Guestbook selfEscape() {
		HtmlEscapeUtils.escape(this);
		return this;
	}

	@Override
	protected boolean beforeCreate() {
		setCreateTime(System.currentTimeMillis()).selfEscape();
		return true;
	}

	@Override
	protected boolean beforeModify() {
		selfEscape();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.AbstractLifecycleAwareObject#getCaller()
	 */
	@Override
	protected Guestbook getCaller() {
		return this;
	}
}
