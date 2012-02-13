package com.mimo.cms.domain.resource;

import java.io.File;

import com.mimo.cms.domain.Configure;

public class PhotoResourceObject extends ResourceObject {
	public PhotoResourceObject() {
	}

	public PhotoResourceObject(File file) {
		super(file);
	}

	@Override
	protected String getResourcePath(Configure conf) {
		return conf.getPhotoPath();
	}

}
