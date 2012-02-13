package com.mimo.cms.infrastruture.persist;

import org.springframework.stereotype.Repository;

import com.mimo.cms.domain.security.User;
import com.mimo.cms.domain.security.UserRepository;
import com.mimo.core.orm.mybatis.MybatisRepositorySupport;

/**
 * 
 * @author loudyn
 * 
 */
@Repository
public class MybatisUserRepository extends MybatisRepositorySupport<String, User> implements UserRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.cms.domain.security.UserRepository#lazyGet(java.lang.String)
	 */
	@Override
	public User lazyGet(String id) {
		return (User) getSqlSession().selectOne(getNamespace().concat(".lazyGet"), id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.cms.domain.security.UserRepository#queryUniqueByUsername(java.lang.String)
	 */
	@Override
	public User queryUniqueByUsername(String username) {
		return (User) getSqlSession().selectOne(getNamespace().concat(".queryUniqueByUsername"), username);
	}

	@Override
	public void markLocked(String[] ids) {
		getSqlSession().update(getNamespace().concat(".markLocked"), ids);
	}

	@Override
	public void markNotLocked(String[] ids) {
		getSqlSession().update(getNamespace().concat(".markNotLocked"), ids);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.orm.mybatis.MybatisRepositorySupport#save(java.lang.Object)
	 */
	@Override
	public void save(User entity) {
		super.save(entity);
		if (entity.hasRole()) {
			getSqlSession().insert(getNamespace().concat(".saveRoles"), entity);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.orm.mybatis.MybatisRepositorySupport#update(java.lang.Object)
	 */
	@Override
	public void update(User entity) {
		super.update(entity);
		getSqlSession().delete(getNamespace().concat(".deleteRoles"), entity);
		if (entity.hasRole()) {
			getSqlSession().insert(getNamespace().concat(".saveRoles"), entity);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.orm.mybatis.MybatisRepositorySupport#getNamespace()
	 */
	@Override
	protected String getNamespace() {
		return "com.mimo.cms.security.user";
	}
}
