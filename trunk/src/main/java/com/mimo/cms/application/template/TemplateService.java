package com.mimo.cms.application.template;

import java.util.List;

import com.mimo.cms.domain.template.Template;
import com.mimo.core.orm.Page;

/**
 * 
 * @author loudyn
 *
 */
public interface TemplateService {

	/**
	 * 
	 * @param id
	 * @return
	 */
	Template get(String id);

	/**
	 * 
	 * @param page
	 * @return
	 */
	Page<Template> queryPage(Page<Template> page);

	/**
	 * 
	 * @param object
	 * @return
	 */
	List<Template> query(Object object);

}
