package com.mimo.cms.domain.template;

import java.util.List;

import com.mimo.core.orm.Page;

/**
 * 
 * @author loudyn
 *
 */
public interface TemplateRepository {

	/**
	 * 
	 * @param id
	 * @return
	 */
	Template get(String id);

	/**
	 * 
	 * @param object
	 * @return
	 */
	List<Template> query(Object object);

	/**
	 * 
	 * @param page
	 * @return
	 */
	Page<Template> queryPage(Page<Template> page);

	/**
	 * 
	 * @param template
	 */
	void update(Template template);

	/**
	 * 
	 * @param template
	 */
	void delete(Template template);

	/**
	 * 
	 * @param template
	 */
	void save(Template template);
}
