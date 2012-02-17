package com.mimo.cms.infrastruture.freemarker;

import java.io.IOException;
import java.util.Map;

import com.mimo.cms.domain.channel.Channel;
import com.mimo.cms.domain.channel.ChannelRepository;
import com.mimo.cms.infrastruture.SpringBeanHolder;

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
public class ChannelDirective extends FreemarkerDirectiveSupport {
	public static final String CHANNEL__NAME_PARAM = "name";

	@Override
	protected void doExecute(Environment env, Map<String, ?> params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {

		TemplateScalarModel channelNameModel = (TemplateScalarModel) params.get(CHANNEL__NAME_PARAM);
		if (isNotBlankScalarModel(channelNameModel)) {

			ChannelRepository repository = SpringBeanHolder.getBean(ChannelRepository.class);
			Channel entity = repository.queryUniqueByName(channelNameModel.getAsString());

			loopVars[0] = beansWrapper.wrap(entity);
			if (null != body) {
				body.render(env.getOut());
			}
		}

	}

	@Override
	protected boolean beforeExecute(Environment env, Map<String, ?> params, TemplateModel[] loopVars) {
		if (!params.containsKey(CHANNEL__NAME_PARAM)) {
			return false;
		}

		if (loopVars.length != 1) {
			return false;
		}
		return true;
	}

}
