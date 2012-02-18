package com.mimo.cms.infrastruture.freemarker;

import java.io.IOException;
import java.util.Map;

import com.mimo.core.orm.Page;
import com.mimo.util.JsonUtils;

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
public abstract class PagableDirective<T> extends FreemarkerDirectiveSupport {
	public static final String PAGE_ORDER_PARAM = "pageOrder";
	public static final String PAGE_ORDERBY_PARAM = "pageOrderBy";
	public static final String PAGE_NO_PARAM = "pageNo";
	public static final String PAGE_SIZE_PARAM = "pageSize";
	public static final String PAGE_PARAMS_PARAM = "params";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.cms.infrastruture.freemarker.FreemarkerDirectiveSupport#doExecute(freemarker.core.Environment,
	 * java.util.Map, freemarker.template.TemplateModel[], freemarker.template.TemplateDirectiveBody)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected final void doExecute(Environment env, Map<String, ?> params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {

		Page<T> page = new Page<T>().setPageSize(10);
		TemplateScalarModel pageOrderModel = getPageOrderModel(params);
		if (isNotBlankScalarModel(pageOrderModel)) {
			page.setOrder(pageOrderModel.getAsString());
		}

		TemplateScalarModel pageOrderByModel = getPageOrderByModel(params);
		if (isNotBlankScalarModel(pageOrderByModel)) {
			page.setOrderBy(pageOrderByModel.getAsString());
		}

		TemplateScalarModel pageNoModel = getPageNoModel(params);
		if (isNotBlankScalarModel(pageNoModel)) {
			page.setPageNo(Integer.parseInt(pageNoModel.getAsString()));
		}

		TemplateScalarModel pageSizeModel = getPageSizeModel(params);
		if (isNotBlankScalarModel(pageSizeModel)) {
			page.setPageSize(Integer.parseInt(pageSizeModel.getAsString()));
		}

		TemplateScalarModel pageParamsModel = getPageParamsModel(params);
		if (isNotBlankScalarModel(pageParamsModel)) {
			page.setParams(JsonUtils.fromJsonString(pageParamsModel.getAsString(), Map.class));
		}

		page = doOnPage(env, params, loopVars, body, page);
		loopVars[0] = beansWrapper.wrap(page.getResult());
		if (null != body) {
			body.render(env.getOut());
		}

	}

	protected abstract Page<T> doOnPage(Environment env, Map<String, ?> params, TemplateModel[] loopVars, TemplateDirectiveBody body,
			Page<T> page) throws TemplateException, IOException;

	protected final TemplateScalarModel getPageOrderModel(Map<String, ?> params) {
		return (TemplateScalarModel) params.get(PAGE_ORDER_PARAM);
	}

	protected final TemplateScalarModel getPageOrderByModel(Map<String, ?> params) {
		return (TemplateScalarModel) params.get(PAGE_ORDERBY_PARAM);
	}

	protected final TemplateScalarModel getPageNoModel(Map<String, ?> params) {
		return (TemplateScalarModel) params.get(PAGE_NO_PARAM);
	}

	protected final TemplateScalarModel getPageSizeModel(Map<String, ?> params) {
		return (TemplateScalarModel) params.get(PAGE_SIZE_PARAM);
	}

	protected final TemplateScalarModel getPageParamsModel(Map<String, ?> params) {
		return (TemplateScalarModel) params.get(PAGE_PARAMS_PARAM);
	}

	@Override
	protected boolean beforeExecute(Environment env, Map<String, ?> params, TemplateModel[] loopVars) {
		if (loopVars.length != 1) {
			return false;
		}

		return true;
	}

}
