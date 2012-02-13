package com.mimo.cms.domain.security;

import java.util.List;

import com.mimo.core.orm.Page;

/**
 * 
 * @author loudyn
 *
 */
public interface AuthorityRepository {

	Authority get(String id);

	List<Authority> query(Object object);

	Page<Authority> queryPage(Page<Authority> page);

	void update(Authority entity);

	void delete(Authority entity);

	void save(Authority entity);

}
