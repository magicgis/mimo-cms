package com.mimo.cms.domain.security;

import com.mimo.core.orm.Page;

/**
 * 
 * @author loudyn
 * 
 */
public interface UserRepository {

	User lazyGet(String id);

	Page<User> queryPage(Page<User> page);

	void save(User entity);

	void delete(User entity);

	void update(User entity);

	User queryUniqueByUsername(String username);

	void markLocked(String[] ids);

	void markNotLocked(String[] ids);
}
