package com.mimo.cms.infrastruture.persist;

import org.springframework.stereotype.Repository;

import com.mimo.cms.domain.security.Role;
import com.mimo.cms.domain.security.RoleRepository;
import com.mimo.core.orm.mybatis.MybatisRepositorySupport;

/**
 * 
 * @author loudyn
 * 
 */
@Repository
public class MybatisRoleRepository extends MybatisRepositorySupport<String, Role> implements RoleRepository {

	@Override
	public void save(Role entity) {
		super.save(entity);
		if (entity.hasAuthority()) {
			getSqlSession().insert(getNamespace().concat(".saveAuthorities"), entity);
		}
	}

	@Override
	public void update(Role entity) {
		super.update(entity);
		getSqlSession().delete(getNamespace().concat(".deleteAuthorities"), entity);
		if (entity.hasAuthority()) {
			getSqlSession().insert(getNamespace().concat(".saveAuthorities"), entity);
		}
	}

	@Override
	protected String getNamespace() {
		return "com.mimo.cms.security.role";
	}
}
