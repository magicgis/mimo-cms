package com.mimo.cms.application.security;

import java.util.List;

import com.mimo.cms.domain.security.Role;
import com.mimo.core.orm.Page;

/**
 * 
 * @author loudyn
 *
 */
public interface RoleService {

	Role get(String id);
	
	List<Role> query(Object object);


	Page<Role> queryPage(Page<Role> page);

}
