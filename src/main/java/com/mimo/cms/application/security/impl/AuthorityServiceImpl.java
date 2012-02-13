package com.mimo.cms.application.security.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mimo.cms.application.security.AuthorityService;
import com.mimo.cms.domain.security.Authority;
import com.mimo.cms.domain.security.AuthorityRepository;
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
public class AuthorityServiceImpl extends LifecycleEventHandler implements AuthorityService {

	@Autowired
	private AuthorityRepository authorityRepository;

	@Override
	public Authority get(String id) {
		return authorityRepository.get(id);
	}

	@Override
	public List<Authority> query(Object object) {
		return authorityRepository.query(object);
	}

	@Override
	public Page<Authority> queryPage(Page<Authority> page) {
		return authorityRepository.queryPage(page);
	}

	@Override
	protected void onModify(Object source, long timestamp) {
		Authority entity = (Authority) source;
		authorityRepository.update(entity);
	}

	@Override
	protected void onDelete(Object source, long timestamp) {
		Authority entity = (Authority) source;
		authorityRepository.delete(entity);

	}

	@Override
	protected void onCreate(Object source, long timestamp) {
		Authority entity = (Authority) source;
		authorityRepository.save(entity);

	}

	@Override
	protected boolean isAcceptableLifecycleEvent(LifecycleEvent event) {
		return Authority.class.isAssignableFrom(event.getSource().getClass());
	}

}
