package com.mimo.cms.domain.security;

import java.util.List;

import com.mimo.core.orm.Page;

/**
 * 
 * @author loudyn
 *
 */
public interface RoleRepository {

	Role get(String id);

	List<Role> query(Object object);

	Page<Role> queryPage(Page<Role> page);

	void update(Role entity);

	void delete(Role entity);

	void save(Role entity);

}
