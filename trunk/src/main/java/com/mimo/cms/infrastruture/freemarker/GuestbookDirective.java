package com.mimo.cms.infrastruture.freemarker;

import java.io.IOException;
import java.util.Map;

import com.mimo.cms.domain.guestbook.Guestbook;
import com.mimo.cms.domain.guestbook.GuestbookRepository;
import com.mimo.cms.infrastruture.SpringBeanHolder;
import com.mimo.core.orm.Page;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 
 * @author loudyn
 * 
 */
public class GuestbookDirective extends PagableDirective<Guestbook> {

	@Override
	protected Page<Guestbook> doOnPage(Environment env, Map<String, ?> params, 
										TemplateModel[] loopVars, TemplateDirectiveBody body,
										Page<Guestbook> page) throws TemplateException, IOException {

		GuestbookRepository repository = SpringBeanHolder.getBean(GuestbookRepository.class);
		page = repository.queryPage(page);
		return page;
	}
}
