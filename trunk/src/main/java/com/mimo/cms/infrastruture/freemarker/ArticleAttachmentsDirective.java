package com.mimo.cms.infrastruture.freemarker;

import java.io.IOException;
import java.util.Map;

import com.mimo.cms.domain.article.ArticleAttachment;
import com.mimo.cms.domain.article.ArticleAttachmentRepository;
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
public class ArticleAttachmentsDirective extends PagableDirective<ArticleAttachment> {

	@Override
	protected Page<ArticleAttachment> doOnPage(Environment env, Map<String, ?> params, 
											   TemplateModel[] loopVars, TemplateDirectiveBody body, 
											   Page<ArticleAttachment> page) throws TemplateException, IOException {

		ArticleAttachmentRepository repository = SpringBeanHolder.getBean(ArticleAttachmentRepository.class);
		page = repository.queryPage(page);
		return page;
	}

}
