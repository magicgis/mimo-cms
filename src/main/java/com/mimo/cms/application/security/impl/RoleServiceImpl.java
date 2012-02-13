package com.mimo.cms.application.security.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mimo.cms.application.security.RoleService;
import com.mimo.cms.domain.security.Role;
import com.mimo.cms.domain.security.RoleRepository;
import com.mimo.core.domain.event.LifecycleEvent;
import com.mimo.core.domain.event.LifecycleEventHandler;
import com.mimo.core.orm.Page;

/**
 * 
 * @author loudyn
 * 
 */
@Transactional
@Service
public class RoleServiceImpl extends LifecycleEventHandler implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Role get(String id) {
		return roleRepository.get(id);
	}

	@Override
	public List<Role> query(Object object) {
		return roleRepository.query(object);
	}

	@Override
	public Page<Role> queryPage(Page<Role> page) {
		return roleRepository.queryPage(page);
	}

	@Override
	protected void onModify(Object source, long timestamp) {
		Role entity = (Role) source;
		roleRepository.update(entity);
	}

	@Override
	protected void onDelete(Object source, long timestamp) {
		Role entity = (Role) source;
		roleRepository.delete(entity);
	}

	@Override
	protected void onCreate(Object source, long timestamp) {
		Role entity = (Role) source;
		roleRepository.save(entity);

	}

	@Override
	protected boolean isAcceptableLifecycleEvent(LifecycleEvent event) {
		return Role.class.isAssignableFrom(event.getSource().getClass());
	}

}
