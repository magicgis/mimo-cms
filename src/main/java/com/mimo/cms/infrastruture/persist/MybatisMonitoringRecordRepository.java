package com.mimo.cms.infrastruture.persist;

import org.springframework.stereotype.Repository;

import com.mimo.cms.domain.MonitoringRecord;
import com.mimo.cms.domain.MonitoringRecordRepository;
import com.mimo.core.orm.mybatis.MybatisRepositorySupport;

/**
 * 
 * @author loudyn
 * 
 */
@Repository
public class MybatisMonitoringRecordRepository extends MybatisRepositorySupport<String, MonitoringRecord> implements
		MonitoringRecordRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.orm.mybatis.MybatisRepositorySupport#getNamespace()
	 */
	@Override
	protected String getNamespace() {
		return "com.mimo.cms.monitoringRecord";
	}
}
