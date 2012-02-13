package com.mimo.cms.infrastruture.persist;

import org.springframework.stereotype.Repository;

import com.mimo.cms.domain.guestbook.Guestbook;
import com.mimo.cms.domain.guestbook.GuestbookRepository;
import com.mimo.core.orm.mybatis.MybatisRepositorySupport;

/**
 * 
 * @author loudyn
 *
 */
@Repository
public class MybatisGuestbookRepository extends MybatisRepositorySupport<String, Guestbook> implements GuestbookRepository{

	@Override
	protected String getNamespace() {
		return "com.mimo.cms.guestbook";
	}
	
}
