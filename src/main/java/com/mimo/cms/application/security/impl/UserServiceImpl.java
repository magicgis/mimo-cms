package com.mimo.cms.application.security.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mimo.cms.application.security.UserService;
import com.mimo.cms.domain.security.User;
import com.mimo.cms.domain.security.UserRepository;
import com.mimo.core.domain.event.LifecycleEvent;
import com.mimo.core.domain.event.LifecycleEventHandler;
import com.mimo.core.domain.monitor.Monitoring;
import com.mimo.core.orm.Page;

/**
 * 
 * @author loudyn
 * 
 */
@Transactional
@Service
public class UserServiceImpl extends LifecycleEventHandler implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User lazyGet(String id) {
		return userRepository.lazyGet(id);
	}

	@Override
	public Page<User> queryPage(Page<User> page) {
		return userRepository.queryPage(page);
	}

	@Override
	public void markLocked(String[] ids) {
		userRepository.markLocked(ids);
	}

	@Override
	public void markNotLocked(String[] ids) {
		userRepository.markNotLocked(ids);
	}

	@Override
	@Monitoring(action = "修改用户")
	protected void onModify(Object source, long timestamp) {
		User entity = (User) source;
		userRepository.update(entity);
	}

	@Override
	@Monitoring(action = "删除用户")
	protected void onDelete(Object source, long timestamp) {
		User entity = (User) source;
		userRepository.delete(entity);
	}

	@Override
	@Monitoring(action = "创建用户")
	protected void onCreate(Object source, long timestamp) {
		User entity = (User) source;
		userRepository.save(entity);
	}

	@Override
	protected boolean isAcceptableLifecycleEvent(LifecycleEvent event) {
		return User.class.isAssignableFrom(event.getSource().getClass());
	}
}
