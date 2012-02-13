package com.mimo.cms.application.guestbook;

import com.mimo.cms.domain.guestbook.Guestbook;
import com.mimo.core.orm.Page;

public interface GuestbookService {

	Guestbook get(String id);

	Page<Guestbook> queryPage(Page<Guestbook> page);

}
