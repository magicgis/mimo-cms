package com.mimo.cms.domain;

import java.io.FileOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Optional;
import com.mimo.util.AssertUtils;
import com.mimo.util.EntityUtils;
import com.mimo.util.ExceptionUtils;

/**
 * 
 * @author loudyn
 * 
 */
public class Configure implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String CONFIGURE_SER = "/META-INF/conf.ser";

	private static final Configure DEFAULT = new Configure().setTemplatePath("WEB-INF/front/template")
															.setResourcePath("resources")
															.setSecurityResourcePath("WEB-INF")
															.setRecycleResourcePath("recycle-resource")
															.setAttachmentPath("attachments")
															.setPhotoPath("resources/photos")
															.setAllowedPhotoTypes("jpg,jpeg,bmp,gif,png,ico")
															.setAllowedSecurityResourceTypes("jsp,ftl,html,htm,txt")
															.setAllowedResourceTypes("jpg,jpeg,bmp,gif,png,ico;txt,doc,docx,ppt,xls,pdf;js,css,xml;zip,rar;")
															.setMaxResourceSize(5 * 1024 * 1024);

	private Configure() {
	}

	public static Configure get() {

		Configure conf = null;
		try {
			URL url = Configure.class.getResource(CONFIGURE_SER);
			conf = (Configure) EntityUtils.deserialize(url.openStream());
		} catch (Exception e) {
			// ignore this exception,it always occur when never save configure
		}

		// always return a clone object
		return Optional.fromNullable(conf).or(defaultOne());
	}

	/**
	 * 
	 * @return
	 */
	public static Configure defaultOne() {
		return (Configure) EntityUtils.clone(DEFAULT);
	}

	/**
	 * @return
	 */
	public final Configure put() {
		try {

			String file = Configure.class.getResource(CONFIGURE_SER).getFile();
			EntityUtils.serialize(this, new FileOutputStream(file));
			return this;
		} catch (Exception e) {
			throw ExceptionUtils.toUnchecked(e);
		}
	}

	private String rootPath;
	private String templatePath;
	private String resourcePath;
	private String allowedResourceTypes;
	private String securityResourcePath;
	private String allowedSecurityResourceTypes;
	private long maxResourceSize;
	private String attachmentPath;
	private String photoPath;
	private String allowedPhotoTypes;
	private String recycleResourcePath;

	public String getRootPath() {
		return rootPath;
	}

	public Configure setRootPath(String rootPath) {
		this.rootPath = rootPath;
		return this;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public Configure setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
		return this;
	}

	public String getResourcePath() {
		return resourcePath;
	}

	public Configure setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
		return this;
	}

	public String getSecurityResourcePath() {
		return this.securityResourcePath;
	}

	public Configure setSecurityResourcePath(String securityResourcePath) {
		this.securityResourcePath = securityResourcePath;
		return this;
	}

	public String getRecycleResourcePath() {
		return this.recycleResourcePath;
	}

	public Configure setRecycleResourcePath(String recycleResourcePath) {
		this.recycleResourcePath = recycleResourcePath;
		return this;
	}

	public Configure setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
		return this;
	}

	public String getPhotoPath() {
		return this.photoPath;
	}

	public Configure setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
		return this;
	}

	public String getAllowedPhotoTypes() {
		return allowedPhotoTypes;
	}

	public Configure setAllowedPhotoTypes(String allowedPhotoTypes) {
		this.allowedPhotoTypes = allowedPhotoTypes;
		return this;
	}

	public String getAttachmentPath() {
		return this.attachmentPath;
	}

	public String getAllowedResourceTypes() {
		return allowedResourceTypes;
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getAllowedResourceTypesAsList() {
		if (StringUtils.isBlank(getAllowedResourceTypes())) {
			return Collections.emptyList();
		}

		String[] types = getAllowedResourceTypes().toLowerCase().split("[\\s;,]");
		return Arrays.asList(types);
	}

	public Configure setAllowedResourceTypes(String allowedResourceTypes) {
		this.allowedResourceTypes = allowedResourceTypes;
		return this;
	}

	public String getAllowedSecurityResourceTypes() {
		return allowedSecurityResourceTypes;
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getAllowedSecurityResourceTypesAsList() {
		if (StringUtils.isBlank(getAllowedSecurityResourceTypes())) {
			return Collections.emptyList();
		}

		String[] types = getAllowedSecurityResourceTypes().toLowerCase().split("[\\s;,]");
		return Arrays.asList(types);
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getAllowedPhotoTypesAsList() {
		if (StringUtils.isBlank(getAllowedPhotoTypes())) {
			return Collections.emptyList();
		}

		String[] types = getAllowedPhotoTypes().toLowerCase().split("[\\s;,]");
		return Arrays.asList(types);
	}

	public Configure setAllowedSecurityResourceTypes(String allowedSecurityResourceTypes) {
		this.allowedSecurityResourceTypes = allowedSecurityResourceTypes;
		return this;
	}

	/**
	 * 
	 * @param type
	 * @return
	 */
	public boolean isAllowedResourceTypes(String type) {
		AssertUtils.hasLength(type);
		return getAllowedResourceTypesAsList().contains(type.toLowerCase());
	}

	/**
	 * 
	 * @param type
	 * @return
	 */
	public boolean isAllowedSecurityResourceTypes(String type) {
		AssertUtils.hasLength(type);
		return getAllowedSecurityResourceTypesAsList().contains(type.toLowerCase());
	}

	/**
	 * 
	 * @param type
	 * @return
	 */
	public boolean isAllowedPhotoTypes(String type) {
		AssertUtils.hasLength(type);
		return getAllowedPhotoTypes().contains(type.toLowerCase());
	}

	public long getMaxResourceSize() {
		return maxResourceSize;
	}

	public Configure setMaxResourceSize(long maxResourceSize) {
		this.maxResourceSize = maxResourceSize;
		return this;
	}

	/**
	 * 
	 * @param bytes
	 * @return
	 */
	public boolean isOverflowResourceSize(long bytes) {
		return bytes > getMaxResourceSize();
	}

}
