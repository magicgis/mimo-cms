package com.mimo.cms.application.resource.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mimo.cms.application.resource.RecycleResourceService;
import com.mimo.cms.domain.resource.RecycleResourceObject;
import com.mimo.cms.domain.resource.RecycleResourceRepository;
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
public class RecycleResourceServiceImpl extends LifecycleEventHandler implements RecycleResourceService {

	@Autowired
	private RecycleResourceRepository recycleResourceRepository;

	@Override
	public RecycleResourceObject get(String id) {
		return recycleResourceRepository.get(id);
	}

	@Override
	public Page<RecycleResourceObject> queryPage(Page<RecycleResourceObject> page) {
		return recycleResourceRepository.queryPage(page);
	}

	@Override
	protected void onModify(Object source, long timestamp) {
		throw new UnsupportedOperationException("Unsupported!");
	}

	@Override
	protected void onDelete(Object source, long timestamp) {
		RecycleResourceObject entity = (RecycleResourceObject) source;
		recycleResourceRepository.delete(entity);
	}

	@Override
	protected void onCreate(Object source, long timestamp) {
		RecycleResourceObject entity = (RecycleResourceObject) source;
		recycleResourceRepository.save(entity);
	}

	@Override
	protected boolean isAcceptableLifecycleEvent(LifecycleEvent event) {
		return RecycleResourceObject.class.isAssignableFrom(event.getSource().getClass());
	}

}
