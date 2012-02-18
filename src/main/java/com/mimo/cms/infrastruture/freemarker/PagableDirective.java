package com.mimo.cms.infrastruture.freemarker;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Maps;
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

	public static final String PARAMS_PARAM = "params";

	public static final String PAGE_ORDER_PARAM = "pageOrder";
	public static final String PAGE_ORDERBY_PARAM = "pageOrderBy";
	public static final String PAGE_NO_PARAM = "pageNo";
	public static final String PAGE_SIZE_PARAM = "pageSize";

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

		Map<String, Object> json = Maps.newHashMap();
		TemplateScalarModel paramsModel = (TemplateScalarModel) params.get(PARAMS_PARAM);
		if (isNotBlankScalarModel(paramsModel)) {
			String paramsJsonString = getJsonString(paramsModel.getAsString());
			json = JsonUtils.fromJsonString(paramsJsonString, Map.class);
		}

		String pageNo = getPageNo(json);
		if (StringUtils.isNotBlank(pageNo)) {
			page.setPageNo(Integer.parseInt(pageNo));
		}

		String pageSize = getPageSize(json);
		if (StringUtils.isNotBlank(pageSize)) {
			page.setPageNo(Integer.parseInt(pageSize));
		}

		if (StringUtils.isNotBlank(getPageOrder(json))) {
			page.setOrderBy(getPageOrderBy(json)).setOrder(getPageOrder(json));
		}

		page.setParams(json);
		page = doOnPage(env, params, loopVars, body, page);
		loopVars[0] = beansWrapper.wrap(page.getResult());
		if (null != body) {
			body.render(env.getOut());
		}

	}

	private String getPageNo(Map<String, Object> json) {
		return (String) json.get(PAGE_NO_PARAM);
	}

	private String getPageSize(Map<String, Object> json) {
		return (String) json.get(PAGE_SIZE_PARAM);
	}

	private String getPageOrder(Map<String, Object> json) {
		return (String) json.get(PAGE_ORDER_PARAM);
	}

	private String getPageOrderBy(Map<String, Object> json) {
		return (String) json.get(PAGE_ORDERBY_PARAM);
	}

	private String getJsonString(String jsonString) {

		jsonString = jsonString.replaceAll("\\{?([^}]+)\\}?", "$1").replaceAll("\"", "")
																   .replaceAll("'", "");

		StringBuilder sb = new StringBuilder().append("{");
		String[] params = jsonString.split(",");
		for (String param : params) {
			String[] keyValue = param.split(":");
			sb.append("\"").append(keyValue[0]).append("\":\"").append(keyValue[1]).append("\",");
		}

		return sb.deleteCharAt(sb.length() - 1).append("}").toString();
	}

	protected abstract Page<T> doOnPage(Environment env, Map<String, ?> params, TemplateModel[] loopVars, TemplateDirectiveBody body,
			Page<T> page) throws TemplateException, IOException;

	@Override
	protected final boolean beforeExecute(Environment env, Map<String, ?> params, TemplateModel[] loopVars) {
		if (!params.containsKey(PARAMS_PARAM)) {
			return false;
		}
		
		if (loopVars.length != 1) {
			return false;
		}

		return true;
	}

}
