package com.mimo.cms.infrastruture.monitor;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.mimo.cms.domain.MonitoringRecord;
import com.mimo.core.domain.monitor.Monitoring;
import com.mimo.core.domain.monitor.MonitoringContext;

/**
 * 
 * @author loudyn
 * 
 */
@Aspect
@Component
public class MonitoringDevilkin {
	private static final String MONITORING_EXP = "@annotation(com.mimo.core.domain.monitor.Monitoring)&&@annotation(monitoring)";

	/**
	 * 
	 * @param jp
	 * @param monitoring
	 */
	@AfterReturning(pointcut = MONITORING_EXP)
	public void monitoring(Monitoring monitoring) {

		MonitoringContext context = MonitoringContext.get();
		long elapsedTime = System.currentTimeMillis() - context.getCreateTime();
		MonitoringRecord record = new MonitoringRecord();
		record.setAction(monitoring.action()).setActor(context.getActor()).setElapsedTime(elapsedTime);
		record.setSource(context.getSource()).setTarget(context.getTarget()).create();
	}

	/**
	 * 
	 * @param e
	 */
	@AfterThrowing(pointcut = MONITORING_EXP, throwing = "e")
	public void monitoring(Monitoring monitoring, Exception e) {

		// nerver rethrow e here
		// warnning: this required in a new transaction
		if (!monitoring.except().isAssignableFrom(e.getClass())) {
			// devilken tell us to ingore this exception
			return;
		}

		MonitoringContext context = MonitoringContext.get();
		MonitoringRecord record = new MonitoringRecord();
		record.setAction(monitoring.action().concat(" : ").concat(e.getMessage()));
		record.setActor(context.getActor()).setSource(context.getSource()).setTarget(context.getTarget()).create();
	}
}
