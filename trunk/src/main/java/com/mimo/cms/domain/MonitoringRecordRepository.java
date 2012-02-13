package com.mimo.cms.domain;

import com.mimo.core.orm.Page;

/**
 * 
 * @author loudyn
 *
 */
public interface MonitoringRecordRepository {

	/**
	 * 
	 * @param page
	 * @return
	 */
	Page<MonitoringRecord> queryPage(Page<MonitoringRecord> page);

	/**
	 * 
	 * @param entity
	 */
	void save(MonitoringRecord entity);

}
