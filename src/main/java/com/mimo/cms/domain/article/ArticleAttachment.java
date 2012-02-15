package com.mimo.cms.domain.article;

import static com.mimo.util.AssertUtils.isTrue;
import static com.mimo.util.AssertUtils.notNull;

import java.io.File;
import java.io.OutputStream;

import org.apache.commons.lang.StringUtils;

import com.mimo.cms.domain.Configure;
import com.mimo.core.domain.event.AbstractLifecycleAwareObject;
import com.mimo.util.FileUtils;
import com.mimo.util.PathUtils;
import com.mimo.util.filecommand.impl.DeleteFileCommand;
import com.mimo.util.filecommand.impl.WriteFileToCommand;

/**
 * 
 * @author loudyn
 * 
 */
public class ArticleAttachment extends AbstractLifecycleAwareObject<ArticleAttachment> {

	private static final long serialVersionUID = 1L;

	private Article article;

	private String name;
	private String path;
	private String contentType;
	private long createTime;

	private transient String fullPath;

	public Article getArticle() {
		return article;
	}

	public ArticleAttachment setArticle(Article article) {
		this.article = article;
		return this;
	}

	public String getName() {
		return name;
	}

	public ArticleAttachment setName(String name) {
		this.name = name;
		return this;
	}

	public String getPath() {
		return StringUtils.isNotBlank(path) ? PathUtils.asUnix(path) : path;
	}

	public ArticleAttachment setPath(String path) {
		this.path = path;
		return this;
	}

	public String getContentType() {
		return contentType;
	}

	public ArticleAttachment setContentType(String contentType) {
		this.contentType = contentType;
		return this;
	}

	public long getCreateTime() {
		return createTime;
	}

	protected ArticleAttachment setCreateTime(long uploadTime) {
		this.createTime = uploadTime;
		return this;
	}

	/**
	 * 
	 * @param conf
	 * @return
	 */
	public ArticleAttachment selfAdjusting(Configure conf) {
		if (StringUtils.isBlank(getPath())) {
			String relativePath = StringUtils.substringAfter(conf.getAttachmentPath(), conf.getRootPath());
			String path = FileUtils.joinPaths(relativePath, getArticle().getId(), getName());
			setPath(PathUtils.asUnix(path));
		}

		this.fullPath = FileUtils.joinPaths(conf.getRootPath(), getPath());
		return this;
	}

	/**
	 * 
	 * @param out
	 * @return
	 */
	public ArticleAttachment piping(OutputStream out) {
		if (StringUtils.isBlank(this.fullPath)) {
			throw new IllegalStateException("Must call selfAdjusting() before piping!");
		}

		new WriteFileToCommand(new File(this.fullPath), out).execute();
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.AbstractLifecycleAwareObject#beforeCreate()
	 */
	@Override
	protected boolean beforeCreate() {
		setCreateTime(System.currentTimeMillis()).selfCheck();
		return true;
	}

	private ArticleAttachment selfCheck() {
		notNull(getArticle(), new IllegalStateException("ArticleComment required a article!"));
		boolean articleMustAvaliable = StringUtils.isNotBlank(getArticle().getId());
		isTrue(articleMustAvaliable, new IllegalStateException("Article must avaliable!"));

		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.AbstractLifecycleAwareObject#beforeDelete()
	 */
	@Override
	protected boolean beforeDelete() {
		if (StringUtils.isBlank(this.fullPath)) {
			throw new IllegalStateException("Must call selfAdjusting() before delete!");
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.AbstractLifecycleAwareObject#afterDelete()
	 */
	@Override
	protected void afterDelete() {
		new DeleteFileCommand(this.fullPath).execute();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.AbstractLifecycleAwareObject#getCaller()
	 */
	@Override
	protected ArticleAttachment getCaller() {
		return this;
	}

}
