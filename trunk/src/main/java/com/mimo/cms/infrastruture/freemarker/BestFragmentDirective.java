package com.mimo.cms.infrastruture.freemarker;

import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 
 * @author loudyn
 * 
 */
public class BestFragmentDirective implements TemplateMethodModel {

	/*
	 * (non-Javadoc)
	 * 
	 * @see freemarker.template.TemplateMethodModel#exec(java.util.List)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object exec(List arguments) throws TemplateModelException {

		String fragment = (String) arguments.get(0);
		int fragmentSize = Integer.parseInt((String) arguments.get(1));

		String omittedArg = null;

		// we has the omitted argument
		if (arguments.size() == 3) {
			omittedArg = (String) arguments.get(2);
		}

		String omitted = fragmentSize < fragment.length() ? (null == omittedArg ? "" : omittedArg) : "";

		int length = Math.min(fragment.length(), fragmentSize);

		// nerver return null
		return null == fragment ? "" : fragment.substring(0, length) + omitted;
	}

}
