package com.mimo.cms.application.resource.impl;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.mimo.cms.application.resource.ResourceService;
import com.mimo.cms.domain.Configure;
import com.mimo.cms.domain.resource.ResourceObject;
import com.mimo.core.orm.Page;
import com.mimo.util.AssertUtils;
import com.mimo.util.ExceptionUtils;
import com.mimo.util.FileUtils;
import com.mimo.util.PathUtils;
import com.mimo.util.filefilter.AbstractCompoFileFilter;
import com.mimo.util.filefilter.CompoFileFilter;

/**
 * 
 * @author loudyn
 * 
 */
@Service("common-resource-service")
public class ResourceServiceImpl implements ResourceService {

	// this fileFilter to filter hidden file
	private final CompoFileFilter notHiddenFileFilter = new AbstractCompoFileFilter() {

		@Override
		public boolean accept(File pathname) {
			return !pathname.isHidden() && pathname.canRead();
		}
	};

	// this fileFilter to filter the sensitive file
	private final CompoFileFilter notSensitiveFileFilter = new AbstractCompoFileFilter() {

		@Override
		public boolean accept(File pathname) {
			String filename = pathname.getName();
			return (filename.indexOf("WEB-INF") == -1) && (filename.indexOf("classes") == -1) && (!filename.endsWith(".class")) && (filename.indexOf("web.xml") == -1);
		}
	};

	private final CompoFileFilter resourceFileFilter = notHiddenFileFilter.and(notSensitiveFileFilter);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.cms.application.resource.ResourceService#get(com.mimo.cms.domain.Configure, java.lang.String)
	 */
	@Override
	public ResourceObject get(final Configure conf, final String pathname) {

		try {

			String resourcePath = getResourcePath(conf);
			String filename = FileUtils.joinPaths(resourcePath, pathname);

			File file = new File(filename);
			if (!file.getCanonicalPath().startsWith(resourcePath)) {
				// the pathname that user submit is malicious, alarm it.
				throw new UnsupportedOperationException("Bad pathname!");
			}

			CompoFileFilter pathnameFileFilter = new AbstractCompoFileFilter() {

				@Override
				public boolean accept(File testFile) {

					try {

						// match one at most.
						String path = PathUtils.asUnix(testFile.getCanonicalPath());
						return path.endsWith(PathUtils.asUnix(pathname));
					} catch (Exception e) {
						return false;
					}
				}
			};

			CompoFileFilter extensionFileFilter = createExtensionFileFilter(conf);
			
			// back to parent and find
			File[] files = file.getParentFile().listFiles(resourceFileFilter.and(pathnameFileFilter).and(extensionFileFilter));
			if (null == files || files.length == 0) {
				return null;
			}

			if (files.length > 1) {
				throw new IllegalStateException("Bad pathname!");
			}

			return createSingleResourceBean(files[0], conf);
		} catch (Exception e) {
			throw ExceptionUtils.toUnchecked(e);
		}
	}
	
	protected CompoFileFilter createExtensionFileFilter(final Configure conf) {
		return new AbstractCompoFileFilter() {

			@Override
			public boolean accept(File testFile) {
				if (testFile.isDirectory()) {
					return true;
				}

				String extension = FileUtils.getSuffixWithoutDot(testFile.getName());
				return conf.isAllowedResourceTypes(extension);
			}
		};
	}

	protected ResourceObject createSingleResourceBean(File file, Configure conf) throws IOException {

		String fileCanonicalPath = file.getCanonicalPath();
		String relativePath = StringUtils.substringAfter(fileCanonicalPath, getResourcePath(conf));

		boolean precondition = relativePath.length() < fileCanonicalPath.length();
		AssertUtils.isTrue(precondition, new RuntimeException("Can't get the ResourceBean path!"));

		return new ResourceObject(file).setPath(PathUtils.asUnix(relativePath));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.cms.application.resource.ResourceService#query(com.mimo.cms.domain.Configure, java.lang.String,
	 * com.mimo.core.orm.Page)
	 */
	@Override
	public Page<ResourceObject> query(final Configure conf, final String pathname, final Page<ResourceObject> page) {
		try {

			String resourcePath = getResourcePath(conf);
			String filename = FileUtils.joinPaths(resourcePath, pathname);

			File file = new File(filename);
			if (!file.getCanonicalPath().startsWith(resourcePath)) {
				// the pathname is malicious,always return empty list,not the end of world.
				return page.setResult(Collections.<ResourceObject> emptyList());
			}

			final AtomicInteger counter = new AtomicInteger(0);
			CompoFileFilter pageFileFilter = new AbstractCompoFileFilter() {

				@Override
				public boolean accept(File pathname) {

					int count = counter.incrementAndGet();
					int first = page.getFirst();
					int end = first + page.getPageSize();
					if (count >= first && count < end) {
						return true;
					}

					return false;
				}
			};

			CompoFileFilter extensionFileFilter = createExtensionFileFilter(conf);

			// Warnning: make sure the pageFileFilter is called at last
			// otherwise page.totalCount may be wrong
			File[] files = file.listFiles(resourceFileFilter.and(extensionFileFilter).and(pageFileFilter));
			return page.setTotalCount(counter.get()).setResult(createResourceBeans(files, conf));
		} catch (Exception e) {
			throw ExceptionUtils.toUnchecked(e);
		}
	}

	/**
	 * 
	 * @param files
	 * @param conf
	 * @return
	 * @throws IOException
	 */
	private List<ResourceObject> createResourceBeans(File[] files, Configure conf) throws IOException {
		if (null == files) {
			return Collections.emptyList();
		}

		List<ResourceObject> beans = new LinkedList<ResourceObject>();
		for (File file : files) {
			ResourceObject bean = createSingleResourceBean(file, conf);
			beans.add(bean);
		}

		return beans;
	}
	
	
	/**
	 * 
	 * @param conf
	 * @return
	 */
	protected String getResourcePath(Configure conf) {
		return conf.getResourcePath();
	}
}
