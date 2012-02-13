package com.mimo.cms.application.channel;

import java.util.List;

import com.mimo.cms.domain.channel.Channel;
import com.mimo.core.orm.Page;

/**
 * 
 * @author loudyn
 * 
 */
public interface ChannelService {

	/**
	 * 
	 * @param id
	 * @return
	 */
	Channel get(String id);

	/**
	 * 
	 * @param path
	 * @return
	 */
	Channel queryUniqueByPath(String path);

	/**
	 * 
	 * @param page
	 * @return
	 */
	Page<Channel> queryPage(Page<Channel> page);

	/**
	 * 
	 * @return
	 */
	List<Channel> queryTop();

	/**
	 * 
	 * @param object
	 * @return
	 */
	List<Channel> query(Object object);
}
