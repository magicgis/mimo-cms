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
import freemarker.template.TemplateScalarModel;

/**
 * 
 * @author loudyn
 * 
 */
public class ChannelsDirective extends PagableDirective<Channel> {
	public static String ON_TOP_PARAM = "onTop";

	@Override
	protected Page<Channel> doOnPage(Environment env, Map<String, ?> params, TemplateModel[] loopVars, TemplateDirectiveBody body,
			Page<Channel> page) throws TemplateException, IOException {

		boolean onTop = false;
		TemplateScalarModel onTopModel = (TemplateScalarModel) params.get(ON_TOP_PARAM);
		if (isNotBlankScalarModel(onTopModel)) {
			onTop = Boolean.parseBoolean(onTopModel.getAsString());
		}

		ChannelRepository repository = SpringBeanHolder.getBean(ChannelRepository.class);
		page.getParams().put("onTop", onTop);
		page = repository.queryPage(page);
		return page;
	}
}
