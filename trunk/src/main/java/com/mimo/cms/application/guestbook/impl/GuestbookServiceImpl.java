package com.mimo.cms.application.guestbook.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mimo.cms.application.guestbook.GuestbookService;
import com.mimo.cms.domain.guestbook.Guestbook;
import com.mimo.cms.domain.guestbook.GuestbookRepository;
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
public class GuestbookServiceImpl extends LifecycleEventHandler implements GuestbookService {

	@Autowired
	private GuestbookRepository guestbookRepository;

	@Override
	public Guestbook get(String id) {
		return guestbookRepository.get(id);
	}

	@Override
	public Page<Guestbook> queryPage(Page<Guestbook> page) {
		return guestbookRepository.queryPage(page);
	}

	@Override
	protected void onModify(Object source, long timestamp) {
		Guestbook entity = (Guestbook) source;
		guestbookRepository.update(entity);
	}

	@Override
	protected void onDelete(Object source, long timestamp) {
		Guestbook entity = (Guestbook) source;
		guestbookRepository.delete(entity);

	}

	@Override
	protected void onCreate(Object source, long timestamp) {
		Guestbook entity = (Guestbook) source;
		guestbookRepository.save(entity);

	}

	@Override
	protected boolean isAcceptableLifecycleEvent(LifecycleEvent event) {
		return Guestbook.class.isAssignableFrom(event.getSource().getClass());
	}

}
