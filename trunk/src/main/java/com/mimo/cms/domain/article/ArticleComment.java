package com.mimo.cms.domain.article;

import org.apache.commons.lang.StringUtils;

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
public class ArticleComment extends AbstractLifecycleAwareObject<ArticleComment> {
	private static final long serialVersionUID = 1L;

	private Article article;
	@HtmlEscape
	private String author;
	@HtmlEscape
	private String content;
	private long createTime;

	public Article getArticle() {
		return article;
	}

	public ArticleComment setArticle(Article article) {
		this.article = article;
		return this;
	}

	public String getAuthor() {
		return author;
	}

	public ArticleComment setAuthor(String author) {
		this.author = author;
		return this;
	}

	public String getContent() {
		return content;
	}

	public ArticleComment setContent(String content) {
		this.content = content;
		return this;
	}

	public long getCreateTime() {
		return createTime;
	}

	protected ArticleComment setCreateTime(long createTime) {
		this.createTime = createTime;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.AbstractLifecycleAwareObject#beforeCreate()
	 */
	@Override
	protected boolean beforeCreate() {
		setCreateTime(System.currentTimeMillis()).selfCheck().selfEscape();
		return true;
	}

	private ArticleComment selfCheck() {
		if (null == getArticle()) {
			throw new IllegalStateException("ArticleComment required a article!");
		}

		if (StringUtils.isBlank(getArticle().getId())) {
			new IllegalStateException("Article must avaliable!");
		}

		if (getArticle().isForbidComments()) {
			new IllegalStateException("Article forbid comments!");
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
		selfCheck().selfEscape();
		return true;
	}

	protected ArticleComment selfEscape() {
		HtmlEscapeUtils.escape(this);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.AbstractLifecycleAwareObject#getCaller()
	 */
	@Override
	protected ArticleComment getCaller() {
		return this;
	}
}
