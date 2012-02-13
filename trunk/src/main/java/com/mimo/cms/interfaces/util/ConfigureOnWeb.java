package com.mimo.cms.interfaces.util;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.mimo.cms.domain.Configure;
import com.mimo.util.AssertUtils;
import com.mimo.util.FileUtils;

/**
 * 
 * @author loudyn
 * 
 */
@Component
public final class ConfigureOnWeb implements ServletContextAware {
	private ServletContext context;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.context.ServletContextAware#setServletContext(javax.servlet.ServletContext)
	 */
	@Override
	public final void setServletContext(ServletContext servletContext) {
		this.context = servletContext;
	}

	/**
	 * 
	 * @param conf
	 * @return
	 */
	public final Configure wrap(Configure conf) {
		String templatePath = FileUtils.joinPaths(getServletContextPath(), conf.getTemplatePath());
		String resourcePath = FileUtils.joinPaths(getServletContextPath(), conf.getResourcePath());
		String securityResourcePath = FileUtils.joinPaths(getServletContextPath(), conf.getSecurityResourcePath());
		String recycleResourcePath = FileUtils.joinPaths(getServletContextPath(), conf.getRecycleResourcePath());
		String attachmentPath = FileUtils.joinPaths(getServletContextPath(), conf.getAttachmentPath());
		String photoPath = FileUtils.joinPaths(getServletContextPath(), conf.getPhotoPath());
		
		conf.setAttachmentPath(attachmentPath).setPhotoPath(photoPath);
		conf.setRecycleResourcePath(recycleResourcePath).setSecurityResourcePath(securityResourcePath);
		return conf.setTemplatePath(templatePath).setResourcePath(resourcePath).setRootPath(getServletContextPath());
	}

	private String getServletContextPath() {
		AssertUtils.notNull(this.context);
		return context.getRealPath("/");
	}

}
