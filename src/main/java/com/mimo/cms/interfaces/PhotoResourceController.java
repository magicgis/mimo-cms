package com.mimo.cms.interfaces;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mimo.cms.application.resource.ResourceService;
import com.mimo.cms.domain.Configure;
import com.mimo.cms.domain.resource.PhotoResourceObject;
import com.mimo.cms.domain.resource.RecycleResourceObject;
import com.mimo.cms.domain.resource.ResourceObject;
import com.mimo.cms.interfaces.exception.BadRequestException;
import com.mimo.cms.interfaces.exception.MaliciousRequestException;
import com.mimo.cms.interfaces.util.ConfigureOnWeb;
import com.mimo.cms.interfaces.util.JsonMessage;
import com.mimo.core.orm.Page;
import com.mimo.core.web.WebUtils;
import com.mimo.core.web.WebUtils.ContentType;
import com.mimo.core.web.controller.ControllerSupport;
import com.mimo.util.AssertUtils;
import com.mimo.util.ExceptionUtils;
import com.mimo.util.FileUtils;
import com.mimo.util.PathUtils;
import com.mimo.util.filecommand.FileCommandInvoker;
import com.mimo.util.filecommand.impl.MakeFileCommand;
import com.mimo.util.filecommand.impl.WriteBytesToFileCommand;

/**
 * 
 * @author loudyn
 * 
 */
@Controller
@RequestMapping(value = "/photo-resource")
public class PhotoResourceController extends ControllerSupport {

	@Autowired
	@Qualifier("photo-resource-service")
	private ResourceService resourceService;

	@Autowired
	private ConfigureOnWeb confOnWeb;

	private static final String REDIRECT_LIST = "redirect:/photo-resource/list";

	/**
	 * 
	 * @param page
	 * @param pathname
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Page<ResourceObject> page, @RequestParam(value = "pathname", required = false) String pathname, Model model) {
		Configure conf = confOnWeb.wrap(Configure.get());

		page = resourceService.query(conf, createDefalutDirectoryIfNeccessary(pathname), page);
		model.addAttribute("page", page);
		return "photo-resource/list";
	}

	private String createDefalutDirectoryIfNeccessary(String pathname) {
		return StringUtils.isBlank(pathname) ? "/" : pathname;
	}

	/**
	 * 
	 * @param file
	 * @param pathname
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void upload(@RequestParam("file") MultipartFile file, @RequestParam(value = "pathname", required = false) String pathname,
			HttpServletResponse response) {

		try {

			response.setContentType("text/html");
			Configure conf = confOnWeb.wrap(Configure.get());

			if (isUnacceptableFile(file, conf)) {
				throw new MaliciousRequestException("The file is unacceptable!");
			}

			ResourceObject bean = resourceService.get(conf, createDefalutDirectoryIfNeccessary(pathname));
			ResourceObject root = createRootResourceBeanIfNeccessary(bean, pathname);

			if (null != root) {
				bean = root;
			}
			if (null == bean || !bean.isDirectory()) {
				throw new BadRequestException();
			}

			String path = PathUtils.asUnix(doUpload(bean, file, conf));

			jsonStringEnclosingWith(response, "%s", JsonMessage.success().message(path));
		} catch (Exception e) {
			jsonStringEnclosingWith(response, "%s", JsonMessage.error().message(e.getMessage()));
		}
	}

	private ResourceObject createRootResourceBeanIfNeccessary(ResourceObject bean, String pathname) {

		if (null == bean && StringUtils.isBlank(pathname)) {
			return new PhotoResourceObject().setDirectory(true).setPath("/");
		}

		return null;
	}

	private boolean isUnacceptableFile(MultipartFile file, Configure conf) {

		AssertUtils.notNull(file);
		if (file.isEmpty() || conf.isOverflowResourceSize(file.getSize())) {
			return true;
		}

		String fileSuffix = FileUtils.getSuffixWithoutDot(file.getOriginalFilename());
		if (!conf.isAllowedPhotoTypes(fileSuffix)) {
			return true;
		}

		return false;
	}

	private String doUpload(ResourceObject bean, MultipartFile file, Configure conf) throws IOException {
		String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String targetFilePath = FileUtils.joinPaths(conf.getPhotoPath(), bean.getPath(), currentDate, file.getOriginalFilename());

		new FileCommandInvoker().command(new MakeFileCommand(targetFilePath))
								.command(new WriteBytesToFileCommand(targetFilePath, file.getBytes()))
								.invoke();

		return StringUtils.substringAfter(targetFilePath, conf.getRootPath());
	}

	/**
	 * 
	 * @param pathname
	 * @param out
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(@RequestParam(value = "pathname", required = false) String pathname, HttpServletResponse response) {

		Configure conf = confOnWeb.wrap(Configure.get());
		ResourceObject bean = resourceService.get(conf, createDefalutDirectoryIfNeccessary(pathname));

		if (null == bean || !bean.isReadable()) {
			throw new BadRequestException();
		}

		try {

			WebUtils.prepareDownload(response, bean.getName(), ContentType.OCTET);
			if (bean.isDirectory()) {
				WebUtils.prepareDownload(response, String.format("%s.zip", bean.getName()), ContentType.OCTET);
			}

			bean.selfAdjusting(conf).piping(response.getOutputStream());
		} catch (Exception e) {
			throw ExceptionUtils.toUnchecked(e);
		}

	}

	/**
	 * 
	 * @param pathname
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public String delete(@RequestParam(value = "pathname", required = true) String pathname) {
		try {

			Configure conf = confOnWeb.wrap(Configure.get());
			ResourceObject bean = resourceService.get(conf, pathname);
			if (null == bean || bean.isDirectory()) {
				throw new UnsupportedOperationException("Bad pathname !");
			}

			new RecycleResourceObject().remember(conf, bean).create();
			bean.selfAdjusting(conf).free();
			return REDIRECT_LIST;
		} catch (Exception e) {
			throw ExceptionUtils.toUnchecked(e);
		}
	}
}
