package com.mimo.cms.interfaces.interceptor;

import static org.apache.commons.lang.StringUtils.equalsIgnoreCase;
import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mimo.core.domain.monitor.MonitoringContext;

/**
 * 
 * @author loudyn
 * 
 */
public class MinotoringInterceptor extends HandlerInterceptorAdapter {

	private final static String USE_PROXY = "x-forwarded-for";
	private final static String UNKNOW_HOST = "unknow";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		// get method means just read.
		if (!equalsIgnoreCase("GET", request.getMethod())) {

			MonitoringContext context = MonitoringContext.get();
			if (null == context) {
				context = new MonitoringContext(new HashMap<String, Object>());
				MonitoringContext.set(context);
			}
			
			String username = SecurityUtils.getSubject().getPrincipal().toString();
			String source = getRequestIp(request);
			String target = request.getRequestURI();
			context.setSource(source).setTarget(target).setActor(username);
		}

		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		MonitoringContext.set(null);
	}

	private String getRequestIp(HttpServletRequest request) {
		// the client didn't use proxy
		if (null == request.getHeader(USE_PROXY)) {
			return request.getRemoteAddr();
		}

		// the client use proxy
		String ip = request.getHeader(USE_PROXY);

		if (isBlank(ip) || equalsIgnoreCase(UNKNOW_HOST, ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (isBlank(ip) || equalsIgnoreCase(UNKNOW_HOST, ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (isBlank(ip) || equalsIgnoreCase(UNKNOW_HOST, ip)) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}
}
