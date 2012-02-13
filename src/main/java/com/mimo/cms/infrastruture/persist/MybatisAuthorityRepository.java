package com.mimo.cms.infrastruture.persist;

import org.springframework.stereotype.Repository;

import com.mimo.cms.domain.security.Authority;
import com.mimo.cms.domain.security.AuthorityRepository;
import com.mimo.core.orm.mybatis.MybatisRepositorySupport;

/**
 * 
 * @author loudyn
 * 
 */
@Repository
public class MybatisAuthorityRepository extends MybatisRepositorySupport<String, Authority> implements AuthorityRepository {

	@Override
	protected String getNamespace() {
		return "com.mimo.cms.security.authority";
	}
}
