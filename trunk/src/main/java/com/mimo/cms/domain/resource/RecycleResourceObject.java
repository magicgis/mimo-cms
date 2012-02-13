package com.mimo.cms.domain.resource;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.mimo.cms.domain.Configure;
import com.mimo.core.domain.event.AbstractLifecycleAwareObject;
import com.mimo.util.FileUtils;
import com.mimo.util.PathUtils;
import com.mimo.util.filecommand.FileCommandInvoker;
import com.mimo.util.filecommand.impl.CopyFileCommand;
import com.mimo.util.filecommand.impl.DeleteFileCommand;
import com.mimo.util.filecommand.impl.MakeFileCommand;

/**
 * 
 * @author loudyn
 * 
 */
public class RecycleResourceObject extends AbstractLifecycleAwareObject<RecycleResourceObject> {

	private static final long serialVersionUID = 1L;

	private static final SimpleDateFormat VERSION_SDF = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final SimpleDateFormat FOLDER_SDF = new SimpleDateFormat("yyyy-MM-dd");

	private String name;
	private String path;
	private String recyclePath;
	private String version;
	private long createTime;

	private transient String fullRecyclePath;
	private transient String fullPath;

	public String getName() {
		return name;
	}

	public RecycleResourceObject setName(String name) {
		this.name = name;
		return this;
	}

	public String getPath() {
		return StringUtils.isNotBlank(path) ? PathUtils.asUnix(path) : path;
	}

	public RecycleResourceObject setPath(String path) {
		this.path = path;
		return this;
	}

	public String getRecyclePath() {
		return StringUtils.isNotBlank(recyclePath) ? PathUtils.asUnix(recyclePath) : recyclePath;
	}

	public RecycleResourceObject setRecyclePath(String recyclePath) {
		this.recyclePath = recyclePath;
		return this;
	}

	public String getVersion() {
		return version;
	}

	public RecycleResourceObject setVersion(String version) {
		this.version = version;
		return this;
	}

	public long getCreateTime() {
		return createTime;
	}

	protected RecycleResourceObject setCreateTime(long createTime) {
		this.createTime = createTime;
		return this;
	}

	public RecycleResourceObject remember(Configure conf, ResourceObject resource) {
		this.fullRecyclePath = FileUtils.joinPaths(resource.getResourcePath(conf), resource.getPath());
		setRecyclePath(StringUtils.substringAfter(fullRecyclePath, conf.getRootPath()));

		Date current = new Date();
		String folder = FOLDER_SDF.format(current);
		String version = VERSION_SDF.format(current);
		String uniqueName = FileUtils.insertBeforeSuffix(resource.getName(), version);

		String relativePath = StringUtils.substringAfter(conf.getRecycleResourcePath(), conf.getRootPath());
		setPath(FileUtils.joinPaths(relativePath, folder, uniqueName));
		setName(resource.getName()).setVersion(version);
		this.fullPath = FileUtils.joinPaths(conf.getRootPath(), getPath());

		return this;
	}

	@Override
	protected boolean beforeCreate() {

		setCreateTime(System.currentTimeMillis());
		if (StringUtils.isBlank(fullRecyclePath)) {
			throw new IllegalStateException("Please call remerber() before!");
		}
		if (StringUtils.isBlank(fullPath)) {
			throw new IllegalStateException("Please call remerber() before!");
		}
		
		new FileCommandInvoker().command(new MakeFileCommand(fullPath))
								.command(new CopyFileCommand(fullRecyclePath,fullPath))
								.invoke();
		return true;
	}

	@Override
	protected boolean beforeModify() {
		throw new UnsupportedOperationException("RecycleResourceObject unsupport modify!");
	}

	public RecycleResourceObject selfAdjust(Configure conf) {
		this.fullRecyclePath = FileUtils.joinPaths(conf.getRootPath(), getRecyclePath());
		this.fullPath = FileUtils.joinPaths(conf.getRootPath(), getPath());
		return this;
	}

	@Override
	protected boolean beforeDelete() {
		if (StringUtils.isBlank(fullRecyclePath)) {
			throw new IllegalStateException("Please call selfAdjust() before!");
		}
		if (StringUtils.isBlank(fullPath)) {
			throw new IllegalStateException("Please call selfAdjust() before!");
		}

		new FileCommandInvoker().command(new CopyFileCommand(fullPath, fullRecyclePath))
				.command(new DeleteFileCommand(fullPath))
				.invoke();
		return true;
	}

	@Override
	protected RecycleResourceObject getCaller() {
		return this;
	}

}
