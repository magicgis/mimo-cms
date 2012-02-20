package com.mimo.cms.domain;

import java.io.FileOutputStream;
import java.io.Serializable;
import java.net.URL;

import com.google.common.base.Optional;
import com.mimo.cms.domain.template.Template;
import com.mimo.util.EntityUtils;
import com.mimo.util.ExceptionUtils;
import com.mimo.util.FileUtils;

/**
 * 
 * @author loudyn
 * 
 */
public final class Home implements Serializable {

	private Home() {
	}

	private String title;
	private String metaTitle;
	private String metaKeyword;
	private String metaDescr;
	private String copyRight;

	private Template template;

	public String getTitle() {
		return title;
	}

	public Home setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getMetaTitle() {
		return metaTitle;
	}

	public Home setMetaTitle(String metaTitle) {
		this.metaTitle = metaTitle;
		return this;
	}

	public String getMetaKeyword() {
		return metaKeyword;
	}

	public Home setMetaKeyword(String metaKeyword) {
		this.metaKeyword = metaKeyword;
		return this;
	}

	public String getMetaDescr() {
		return metaDescr;
	}

	public Home setMetaDescr(String metaDescr) {
		this.metaDescr = metaDescr;
		return this;
	}

	public String getCopyRight() {
		return copyRight;
	}

	public Home setCopyRight(String copyRight) {
		this.copyRight = copyRight;
		return this;
	}

	public Template getTemplate() {
		return template;
	}

	public Home setTemplate(Template template) {
		this.template = template;
		return this;
	}

	public String getTemplatePath() {
		if (null != getTemplate()) {
			return FileUtils.getMajorName(getTemplate().getPath());
		}

		return null;
	}

	private static final long serialVersionUID = 1L;
	private static final String HOME_SER = "/META-INF/home.ser";
	private static final Home DEFAULT = new Home();

	/**
	 * 
	 * @return
	 */
	public static Home get() {

		Home home = null;
		try {

			URL url = Configure.class.getResource(HOME_SER);
			home = (Home) EntityUtils.deserialize(url.openStream());
		} catch (Exception e) {
			// ignore this exception,it always occur when never save home
		}

		// when null, return a clone default object
		return Optional.fromNullable(home).or((Home) EntityUtils.clone(DEFAULT));
	}

	/**
	 * 
	 * @return
	 */
	public Home put() {

		try {

			String file = Home.class.getResource(HOME_SER).getFile();
			EntityUtils.serialize(this, new FileOutputStream(file));
			return this;
		} catch (Exception e) {
			throw ExceptionUtils.toUnchecked(e);
		}
	}

}
