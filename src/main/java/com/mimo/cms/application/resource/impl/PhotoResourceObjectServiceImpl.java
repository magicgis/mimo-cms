package com.mimo.cms.application.resource.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.mimo.cms.domain.Configure;
import com.mimo.cms.domain.resource.PhotoResourceObject;
import com.mimo.cms.domain.resource.ResourceObject;
import com.mimo.util.AssertUtils;
import com.mimo.util.FileUtils;
import com.mimo.util.PathUtils;
import com.mimo.util.filefilter.AbstractCompoFileFilter;
import com.mimo.util.filefilter.CompoFileFilter;

/**
 * 
 * @author loudyn
 * 
 */
@Service("photo-resource-service")
public class PhotoResourceObjectServiceImpl extends ResourceServiceImpl {

	@Override
	protected CompoFileFilter createExtensionFileFilter(final Configure conf) {
		return new AbstractCompoFileFilter() {

			@Override
			public boolean accept(File testFile) {
				if (testFile.isDirectory()) {
					return true;
				}

				String extension = FileUtils.getSuffixWithoutDot(testFile.getName());
				return conf.isAllowedPhotoTypes(extension);
			}
		};
	}

	@Override
	protected ResourceObject createSingleResourceBean(File file, Configure conf) throws IOException {
		String fileCanonicalPath = file.getCanonicalPath();
		String relativePath = StringUtils.substringAfter(fileCanonicalPath, getResourcePath(conf));

		boolean precondition = relativePath.length() < fileCanonicalPath.length();
		AssertUtils.isTrue(precondition, new RuntimeException("Can't get the ResourceBean path!"));

		return new PhotoResourceObject(file).setPath(PathUtils.asUnix(relativePath));
	}

	@Override
	protected String getResourcePath(Configure conf) {
		return conf.getPhotoPath();
	}

}
