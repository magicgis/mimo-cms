package com.mimo.cms.domain.channel;

import java.util.List;

import com.mimo.core.orm.Page;

/**
 * 
 * @author loudyn
 * 
 */
public interface ChannelRepository {

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
	 * @param id
	 * @return
	 */
	Channel get(String id);
	

	/**
	 * 
	 * @param channel
	 */
	void save(Channel channel);

	/**
	 * 
	 * @param channel
	 */
	void update(Channel channel);
	/**
	 * 
	 * @param id
	 */
	void delete(String id);

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