package com.mimo.cms.infrastruture.freemarker;

import java.io.IOException;
import java.util.Map;

import com.mimo.cms.domain.article.ArticleComment;
import com.mimo.cms.domain.article.ArticleCommentRepository;
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
public class ArticleCommentDirective extends PagableDirective<ArticleComment> {

	@Override
	protected Page<ArticleComment> doOnPage(Environment env, Map<String, ?> params, TemplateModel[] loopVars, 
											TemplateDirectiveBody body,
											Page<ArticleComment> page) throws TemplateException, IOException {

		ArticleCommentRepository repository = SpringBeanHolder.getBean(ArticleCommentRepository.class);
		page = repository.queryPage(page);
		return page;
	}

}
