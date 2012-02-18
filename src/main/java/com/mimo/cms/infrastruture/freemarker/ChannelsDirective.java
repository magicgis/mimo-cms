package com.mimo.cms.infrastruture.freemarker;

import java.io.IOException;
import java.util.Map;

import com.mimo.cms.domain.channel.Channel;
import com.mimo.cms.domain.channel.ChannelRepository;
import com.mimo.cms.infrastruture.SpringBeanHolder;
import com.mimo.core.orm.Page;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 
 * @author loudyn
 * 
 */
public class ChannelsDirective extends PagableDirective<Channel> {
	
	@Override
	protected Page<Channel> doOnPage(Environment env, Map<String, ?> params, TemplateModel[] loopVars, TemplateDirectiveBody body,
			Page<Channel> page) throws TemplateException, IOException {

		ChannelRepository repository = SpringBeanHolder.getBean(ChannelRepository.class);
		page = repository.queryPage(page);
		return page;
	}
}
