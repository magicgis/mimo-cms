package com.mimo.cms.domain.resource;

import java.io.File;
import java.io.OutputStream;

import org.apache.commons.lang.StringUtils;

import com.mimo.cms.domain.Configure;
import com.mimo.util.ExceptionUtils;
import com.mimo.util.FileUtils;
import com.mimo.util.filecommand.FileCommand;
import com.mimo.util.filecommand.FileCommandInvoker;
import com.mimo.util.filecommand.impl.DeleteFileCommand;
import com.mimo.util.filecommand.impl.WriteFileToCommand;
import com.mimo.util.filecommand.impl.ZipCompressFileCommand;

/**
 * 
 * @author loudyn
 * 
 */
public class ResourceObject {

	private String name;
	private String path;

	private long size;
	private long lastModified;

	private boolean editable;
	private boolean readable;
	private boolean directory;

	private transient String fullPath;

	public ResourceObject() {
		// do nothing
	}

	/**
	 * 
	 * @param file
	 */
	public ResourceObject(File file) {
		this.setName(file.getName()).setDirectory(file.isDirectory()).setEditable(file.canWrite());
		this.setReadable(file.canRead()).setSize(file.length()).setLastModified(file.lastModified());
	}

	public String getName() {
		return name;
	}

	public ResourceObject setName(String name) {
		this.name = name;
		return this;
	}

	public String getPath() {
		return path;
	}

	public ResourceObject setPath(String path) {
		this.path = path;
		return this;
	}

	public long getSize() {
		return size;
	}

	public ResourceObject setSize(long size) {
		this.size = size;
		return this;
	}

	public long getLastModified() {
		return lastModified;
	}

	public ResourceObject setLastModified(long lastModified) {
		this.lastModified = lastModified;
		return this;
	}

	public boolean isEditable() {
		return editable;
	}

	public ResourceObject setEditable(boolean editable) {
		this.editable = editable;
		return this;
	}

	public boolean isReadable() {
		return readable;
	}

	public ResourceObject setReadable(boolean readable) {
		this.readable = readable;
		return this;
	}

	public boolean isDirectory() {
		return directory;
	}

	public ResourceObject setDirectory(boolean directory) {
		this.directory = directory;
		return this;
	}

	public ResourceObject selfAdjusting(Configure conf) {
		this.fullPath = FileUtils.joinPaths(getResourcePath(conf), getPath());
		return this;
	}
	
	protected String getResourcePath(Configure conf) {
		return conf.getResourcePath();
	}

	/**
	 * 
	 * @return
	 */
	public ResourceObject free(){
		if (StringUtils.isBlank(this.fullPath)) {
			throw new IllegalStateException("Must call selfAdjusting() before!");
		}
		
		new DeleteFileCommand(this.fullPath).execute();
		return this;
	}

	/**
	 * 
	 * @param out
	 * @return
	 */
	public ResourceObject piping(OutputStream out) {
		if (StringUtils.isBlank(this.fullPath)) {
			throw new IllegalStateException("Must call selfAdjusting() before!");
		}

		try {

			if (isDirectory()) {
				File temp = File.createTempFile("temp", ".zip");
				FileCommand zipCompress = new ZipCompressFileCommand(new File(this.fullPath), temp, "utf-8");
				FileCommand writeResponse = new WriteFileToCommand(temp, out);
				FileCommand release = new DeleteFileCommand(temp);
				new FileCommandInvoker().command(zipCompress).command(writeResponse).command(release).invoke();
				return this;
			}

			new WriteFileToCommand(new File(this.fullPath), out).execute();
			return this;
		} catch (Exception e) {
			throw ExceptionUtils.toUnchecked(e);
		}
	}
	
}
