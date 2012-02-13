package com.mimo.cms.application.security;

import com.mimo.cms.domain.security.User;
import com.mimo.core.orm.Page;

/**
 * 
 * @author loudyn
 * 
 */
public interface UserService {

	/**
	 * 
	 * @param id
	 * @return
	 */
	User lazyGet(String id);

	/**
	 * 
	 * @param page
	 * @return
	 */
	Page<User> queryPage(Page<User> page);

	/**
	 * 
	 * @param ids
	 */
	void markLocked(String[] ids);

	/**
	 * 
	 * @param ids
	 */
	void markNotLocked(String[] ids);

}
