package com.mimo.cms.infrastruture.freemarker;

import java.io.IOException;
import java.util.Map;

import com.mimo.cms.domain.article.Article;
import com.mimo.cms.domain.article.Article.ArticleStatus;
import com.mimo.cms.domain.article.ArticleRepository;
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
public class ArticlesDirective extends PagableDirective<Article> {

	public static final String CHANNEL_PARAM = "channel";

	@Override
	protected Page<Article> doOnPage(Environment env, Map<String, ?> params, 
									 TemplateModel[] loopVars, TemplateDirectiveBody body, 
									 Page<Article> page) throws TemplateException, IOException {

		TemplateScalarModel channelModel = (TemplateScalarModel) params.get(CHANNEL_PARAM);
		if (isNotBlankScalarModel(channelModel)) {
			page.getParams().put("channel", channelModel.getAsString());
		}

		page.getParams().put("status", ArticleStatus.ONLINE);
		ArticleRepository repository = SpringBeanHolder.getBean(ArticleRepository.class);
		page = repository.queryPage(page);
		return page;
	}

}
