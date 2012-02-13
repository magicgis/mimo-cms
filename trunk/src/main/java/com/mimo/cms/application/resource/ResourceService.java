package com.mimo.cms.application.resource;

import com.mimo.cms.domain.Configure;
import com.mimo.cms.domain.resource.ResourceObject;
import com.mimo.core.orm.Page;

/**
 * 
 * @author loudyn
 * 
 */
public interface ResourceService {

	/**
	 * 
	 * @param conf
	 * @param pathname
	 * @return
	 */
	ResourceObject get(Configure conf, String pathname);

	/**
	 * 
	 * @param conf
	 * @param pathname
	 * @param page 
	 * @return
	 */
	Page<ResourceObject> query(Configure conf, String pathname, Page<ResourceObject> page);
}
