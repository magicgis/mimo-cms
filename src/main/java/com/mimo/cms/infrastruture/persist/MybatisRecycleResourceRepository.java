package com.mimo.cms.infrastruture.persist;

import org.springframework.stereotype.Repository;

import com.mimo.cms.domain.resource.RecycleResourceObject;
import com.mimo.cms.domain.resource.RecycleResourceRepository;
import com.mimo.core.orm.mybatis.MybatisRepositorySupport;

/**
 * 
 * @author loudyn
 *
 */
@Repository
public class MybatisRecycleResourceRepository extends MybatisRepositorySupport<String, RecycleResourceObject> implements
		RecycleResourceRepository {

	@Override
	protected String getNamespace() {
		return "com.mimo.cms.recycleResource";
	}

}
