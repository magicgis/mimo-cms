package com.mimo.cms.infrastruture;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.mimo.core.domain.event.LifecycleEventBus;
import com.mimo.core.domain.event.LifecycleEventHandler;

/**
 * 
 * @author loudyn
 * 
 */
@Component
public class LifecycleEventHandlerPopulator implements BeanPostProcessor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessBeforeInitialization(java.lang.Object,
	 * java.lang.String)
	 */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(java.lang.Object,
	 * java.lang.String)
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (LifecycleEventHandler.class.isAssignableFrom(bean.getClass())) {
			LifecycleEventHandler target = (LifecycleEventHandler) bean;
			LifecycleEventBus.me().register(target);
		}

		return bean;
	}
}
