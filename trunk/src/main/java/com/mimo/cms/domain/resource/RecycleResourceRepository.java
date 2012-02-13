package com.mimo.cms.domain.resource;

import com.mimo.core.orm.Page;

public interface RecycleResourceRepository {

	RecycleResourceObject get(String id);

	Page<RecycleResourceObject> queryPage(Page<RecycleResourceObject> page);

	void delete(RecycleResourceObject entity);

	void save(RecycleResourceObject entity);

}
