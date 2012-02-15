package com.mimo.cms.domain.channel;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.mimo.cms.domain.template.Template;
import com.mimo.core.domain.event.AbstractLifecycleAwareObject;

/**
 * 
 * @author loudyn
 * 
 */
public class Channel extends AbstractLifecycleAwareObject<Channel> {

	private static final long serialVersionUID = 1L;

	private Channel father;
	private List<Channel> children = new ArrayList<Channel>(0);

	private Template selfTemplate;

	private String name;
	private String path;

	private String metaKeyword;
	private String metaTitle;
	private String metaDescr;

	private int priority;

	public Channel getFather() {
		return father;
	}

	public Channel setFather(Channel father) {
		this.father = father;
		return this;
	}

	public boolean hasFather() {
		return null != getFather();
	}

	public List<Channel> getChildren() {
		return children;
	}

	public Channel setChildren(List<Channel> children) {
		this.children = children;
		return this;
	}

	public boolean hasChildren() {
		if (null == getChildren()) {
			return false;
		}

		return !getChildren().isEmpty();
	}

	public Template getSelfTemplate() {
		return selfTemplate;
	}

	public Channel setSelfTemplate(Template selfTemplate) {
		this.selfTemplate = selfTemplate;
		return this;
	}

	public String getName() {
		return name;
	}

	public Channel setName(String name) {
		this.name = name;
		return this;
	}

	public String getPath() {
		return path;
	}

	public Channel setPath(String path) {
		this.path = path;
		return this;
	}

	public String getMetaKeyword() {
		return metaKeyword;
	}

	public Channel setMetaKeyword(String metaKeyword) {
		this.metaKeyword = metaKeyword;
		return this;
	}

	public String getMetaTitle() {
		return metaTitle;
	}

	public Channel setMetaTitle(String metaTitle) {
		this.metaTitle = metaTitle;
		return this;
	}

	public String getMetaDescr() {
		return metaDescr;
	}

	public Channel setMetaDescr(String metaDescr) {
		this.metaDescr = metaDescr;
		return this;
	}

	public int getPriority() {
		return priority;
	}

	public Channel setPriority(int priority) {
		this.priority = priority;
		return this;
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

	private Channel selfCheck() {

		if (!hasFather()) {
			return this;
		}

		if (StringUtils.isBlank(getFather().getId())) {
			setFather(null);
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
	 * @see com.mimo.core.domain.event.AbstractLifecycleAwareObject#beforeAcquire()
	 */
	@Override
	protected boolean beforeAcquire() {
		if (!this.hasIdentity()) {
			throw new IllegalStateException("The channel didn't has the identity!");
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.AbstractLifecycleAwareObject#getCaller()
	 */
	@Override
	protected final Channel getCaller() {
		return this;
	}
}
