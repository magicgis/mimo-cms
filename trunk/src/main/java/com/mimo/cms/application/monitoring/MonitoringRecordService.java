package com.mimo.cms.application.monitoring;

import com.mimo.cms.domain.MonitoringRecord;
import com.mimo.core.orm.Page;

/**
 * 
 * @author loudyn
 *
 */
public interface MonitoringRecordService {

	/**
	 * 
	 * @param page
	 * @return
	 */
	Page<MonitoringRecord> queryPage(Page<MonitoringRecord> page);
}
