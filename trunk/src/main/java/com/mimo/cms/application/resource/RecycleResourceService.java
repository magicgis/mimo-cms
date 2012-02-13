package com.mimo.cms.application.resource;

import com.mimo.cms.domain.resource.RecycleResourceObject;
import com.mimo.core.orm.Page;

public interface RecycleResourceService {

	RecycleResourceObject get(String id);
	Page<RecycleResourceObject> queryPage(Page<RecycleResourceObject> page);

	
}
