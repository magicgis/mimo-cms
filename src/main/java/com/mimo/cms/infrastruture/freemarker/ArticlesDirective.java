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

/**
 * 
 * @author loudyn
 * 
 */
public class ArticlesDirective extends PagableDirective<Article> {

	@Override
	protected Page<Article> doOnPage(Environment env, Map<String, ?> params, 
									 TemplateModel[] loopVars, TemplateDirectiveBody body, 
									 Page<Article> page) throws TemplateException, IOException {
		
		page.getParams().put("status", ArticleStatus.ONLINE);
		ArticleRepository repository = SpringBeanHolder.getBean(ArticleRepository.class);
		page = repository.queryPage(page);
		return page;
	}

}
