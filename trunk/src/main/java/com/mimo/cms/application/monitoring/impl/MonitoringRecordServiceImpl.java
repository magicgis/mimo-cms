package com.mimo.cms.application.monitoring.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mimo.cms.application.monitoring.MonitoringRecordService;
import com.mimo.cms.domain.MonitoringRecord;
import com.mimo.cms.domain.MonitoringRecordRepository;
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
public class MonitoringRecordServiceImpl extends LifecycleEventHandler implements MonitoringRecordService {

	@Autowired
	private MonitoringRecordRepository monitoringRecordRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.cms.application.monitoring.MonitoringRecordService#queryPage(com.mimo.core.orm.Page)
	 */
	@Override
	public Page<MonitoringRecord> queryPage(Page<MonitoringRecord> page) {
		return monitoringRecordRepository.queryPage(page);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.LifecycleEventHandler#onModify(java.lang.Object, long)
	 */
	@Override
	protected void onModify(Object source, long timestamp) {
		throw new UnsupportedOperationException("MonitoringRecord didn't supported modify!");
	}

	@Override
	protected void onDelete(Object source, long timestamp) {
		throw new UnsupportedOperationException("MonitoringRecord didn't supported delete!");
	}

	@Override
	protected void onCreate(Object source, long timestamp) {
		MonitoringRecord entity = (MonitoringRecord) source;
		monitoringRecordRepository.save(entity);
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
		return MonitoringRecord.class.isAssignableFrom(event.getSource().getClass());
	}
}
