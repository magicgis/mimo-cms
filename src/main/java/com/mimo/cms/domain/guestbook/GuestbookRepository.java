package com.mimo.cms.domain.guestbook;

import com.mimo.core.orm.Page;

public interface GuestbookRepository {

	Guestbook get(String id);

	Page<Guestbook> queryPage(Page<Guestbook> page);

	void update(Guestbook entity);

	void save(Guestbook entity);

	void delete(Guestbook entity);
	
}
