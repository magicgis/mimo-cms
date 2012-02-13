package com.mimo.cms.application.security;

import java.util.List;

import com.mimo.cms.domain.security.Authority;
import com.mimo.core.orm.Page;

/**
 * 
 * @author loudyn
 *
 */
public interface AuthorityService {

	Authority get(String id);

	List<Authority> query(Object object);

	Page<Authority> queryPage(Page<Authority> page);




}
