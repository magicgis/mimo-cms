package com.mimo.cms.infrastruture.persist;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mimo.cms.domain.channel.Channel;
import com.mimo.cms.domain.channel.ChannelRepository;
import com.mimo.core.orm.mybatis.MybatisRepositorySupport;

/**
 * 
 * @author loudyn
 * 
 */
@Repository
public class MybatisChannelRepository extends MybatisRepositorySupport<String, Channel> implements ChannelRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.cms.domain.channel.ChannelRepository#queryUniqueByPath(java.lang.String)
	 */
	@Override
	public Channel queryUniqueByPath(String path) {
		return (Channel) getSqlSession().selectOne(getNamespace().concat(".queryUniqueByPath"), path);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.cms.domain.channel.ChannelRepository#queryTop()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Channel> queryTop() {
		return getSqlSession().selectList(getNamespace().concat(".queryTop"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.cms.domain.channel.ChannelRepository#acquire(com.mimo.cms.domain.channel.Channel)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void acquire(Channel entity) {
		if(entity.hasFather()){
			Channel father = get(entity.getFather().getId());
			entity.setFather(father);
		}
		
		List<Channel> children = getSqlSession().selectList(getNamespace().concat(".queryChildren"), entity.getId());
		entity.setChildren(children);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.orm.mybatis.MybatisRepositorySupport#getNamespace()
	 */
	@Override
	protected String getNamespace() {
		return "com.mimo.cms.channel";
	}
}
