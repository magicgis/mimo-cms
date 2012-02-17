package com.mimo.cms.application.channel.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mimo.cms.application.channel.ChannelService;
import com.mimo.cms.domain.channel.Channel;
import com.mimo.cms.domain.channel.ChannelRepository;
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
public class ChannelServiceImpl extends LifecycleEventHandler implements ChannelService {

	private ChannelRepository channelRepository;

	/**
	 * 
	 * @param channelRepository
	 */
	@Autowired
	public void setChannelRepository(ChannelRepository channelRepository) {
		this.channelRepository = channelRepository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.cms.application.channel.ChannelService#queryUniqueByPath(java.lang.String)
	 */
	@Override
	public Channel queryUniqueByPath(String path) {
		return channelRepository.queryUniqueByPath(path);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.cms.application.channel.ChannelService#queryPage(com.youboy.core.orm.Page)
	 */
	@Override
	public Page<Channel> queryPage(Page<Channel> page) {
		return channelRepository.queryPage(page);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.cms.application.channel.ChannelService#get(java.lang.String)
	 */
	@Override
	public Channel get(String id) {
		return channelRepository.get(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.cms.application.channel.ChannelService#queryTop()
	 */
	@Override
	public List<Channel> queryTop() {
		return channelRepository.queryTop();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.cms.application.channel.ChannelService#query(java.lang.Object)
	 */
	@Override
	public List<Channel> query(Object object) {
		return channelRepository.query(object);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.cms.application.LifecycleEventService#onModify(java.lang.Object, long)
	 */
	@Override
	@Monitoring(action = "修改栏目")
	protected void onModify(Object source, long timestamp) {
		channelRepository.update((Channel) source);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.LifecycleEventHandler#onDelete(java.lang.Object, long)
	 */
	@Override
	@Monitoring(action = "删除栏目")
	protected void onDelete(Object source, long timestamp) {
		channelRepository.delete(((Channel) source));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.LifecycleEventHandler#onCreate(java.lang.Object, long)
	 */
	@Override
	@Monitoring(action = "创建栏目")
	protected void onCreate(Object source, long timestamp) {
		channelRepository.save((Channel) source);
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
		return Channel.class.isAssignableFrom(event.getSource().getClass());
	}
}
