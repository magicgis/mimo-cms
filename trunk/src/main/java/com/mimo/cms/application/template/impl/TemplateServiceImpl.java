package com.mimo.cms.application.template.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mimo.cms.application.template.TemplateService;
import com.mimo.cms.domain.template.Template;
import com.mimo.cms.domain.template.TemplateRepository;
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
public class TemplateServiceImpl extends LifecycleEventHandler implements TemplateService {

	@Autowired
	private TemplateRepository templateRepository;

	@Override
	public Template get(String id) {
		return templateRepository.get(id);
	}

	@Override
	public List<Template> query(Object object) {
		return templateRepository.query(object);
	}

	@Override
	public Page<Template> queryPage(Page<Template> page) {
		return templateRepository.queryPage(page);
	}

	@Override
	@Monitoring(action = "修改模板")
	protected void onModify(Object source, long timestamp) {
		Template template = (Template) source;
		templateRepository.update(template);
	}

	@Override
	@Monitoring(action = "删除模板")
	protected void onDelete(Object source, long timestamp) {
		Template template = (Template) source;
		templateRepository.delete(template);
	}

	@Override
	@Monitoring(action = "创建模板")
	protected void onCreate(Object source, long timestamp) {
		Template template = (Template) source;
		templateRepository.save(template);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mimo.core.domain.event.LifecycleEventHandler#isAcceptableLifecycleEvent(com.mimo.core.domain.event.LifecycleEvent
	 * )
	 */
	@Override
	protected boolean isAcceptableLifecycleEvent(LifecycleEvent event) {
		return Template.class.isAssignableFrom(event.getSource().getClass());
	}

}
