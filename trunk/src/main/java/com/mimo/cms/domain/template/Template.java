package com.mimo.cms.domain.template;

import org.apache.commons.lang.StringUtils;

import com.mimo.cms.domain.Configure;
import com.mimo.core.domain.event.AbstractLifecycleAwareObject;
import com.mimo.util.FileUtils;
import com.mimo.util.PathUtils;
import com.mimo.util.filecommand.FileCommandInvoker;
import com.mimo.util.filecommand.impl.DeleteFileCommand;
import com.mimo.util.filecommand.impl.MakeFileCommand;
import com.mimo.util.filecommand.impl.WriteStringToFileCommand;

/**
 * 
 * @author loudyn
 * 
 */
public class Template extends AbstractLifecycleAwareObject<Template> {

	private static final long serialVersionUID = 1L;

	private String name;
	private String path;
	private String encode;
	private String content;
	private long createTime;
	private long modifyTime;

	private transient String fullPrePath;
	private transient String fullPath;

	public String getName() {
		return name;
	}

	public Template setName(String name) {
		this.name = name;
		return this;
	}

	public String getPath() {
		return StringUtils.isNotBlank(path) ? PathUtils.asUnix(path) : path;
	}

	public Template setPath(String path) {
		this.path = path;
		return this;
	}

	public String getEncode() {
		return encode;
	}

	public Template setEncode(String encode) {
		this.encode = encode;
		return this;
	}

	public String getContent() {
		return content;
	}

	public Template setContent(String content) {
		this.content = content;
		return this;
	}

	public long getCreateTime() {
		return createTime;
	}

	protected Template setCreateTime(long createTime) {
		this.createTime = createTime;
		return this;
	}

	public long getModifyTime() {
		return modifyTime;
	}

	protected Template setModifyTime(long modifyTime) {
		this.modifyTime = modifyTime;
		return this;
	}

	/**
	 * 
	 * @param conf
	 * @return
	 */
	public Template selfAdjusting(Configure conf) {

		if (StringUtils.isNotBlank(getPath())) {
			this.fullPrePath = FileUtils.joinPaths(conf.getRootPath(), getPath());
		}

		String relativePath = StringUtils.substringAfter(conf.getTemplatePath(), conf.getRootPath());
		setPath(String.format("%s/%s.ftl", relativePath, getName()));
		this.fullPath = FileUtils.joinPaths(conf.getRootPath(), getPath());

		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.AbstractLifecycleAwareObject#beforeCreate()
	 */
	@Override
	protected boolean beforeCreate() {

		if (StringUtils.isBlank(this.fullPath)) {
			throw new IllegalStateException("Must call selfAdjusting() before modify!");
		}

		long current = System.currentTimeMillis();
		setCreateTime(current).setModifyTime(current);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.AbstractLifecycleAwareObject#afterCreate()
	 */
	@Override
	protected void afterCreate() {

		new FileCommandInvoker().command(new MakeFileCommand(this.fullPath))
				.command(new WriteStringToFileCommand(this.fullPath, getContent(), getEncode()))
				.invoke();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.AbstractLifecycleAwareObject#beforeModify()
	 */
	@Override
	protected boolean beforeModify() {

		if (StringUtils.isBlank(this.fullPath)) {
			throw new IllegalStateException("Must call selfAdjusting() before modify!");
		}

		setModifyTime(System.currentTimeMillis());
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.AbstractLifecycleAwareObject#afterModify()
	 */
	@Override
	protected void afterModify() {
		if (StringUtils.isNotBlank(this.fullPrePath)) {
			new DeleteFileCommand(this.fullPrePath).execute();
		}

		new FileCommandInvoker().command(new MakeFileCommand(this.fullPath))
								.command(new WriteStringToFileCommand(this.fullPath, getContent(), getEncode()))
								.invoke();
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
	protected final Template getCaller() {
		return this;
	}
}
