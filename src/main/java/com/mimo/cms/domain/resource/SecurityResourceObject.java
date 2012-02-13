package com.mimo.cms.domain.resource;

import java.io.File;

import com.mimo.cms.domain.Configure;

/**
 * 
 * @author loudyn
 * 
 */
public class SecurityResourceObject extends ResourceObject {

	public SecurityResourceObject() {
	}

	public SecurityResourceObject(File file) {
		super(file);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.cms.domain.resource.ResourceObject#getResourcePath(com.mimo.cms.domain.Configure)
	 */
	@Override
	protected String getResourcePath(Configure conf) {
		return conf.getSecurityResourcePath();
	}
}
