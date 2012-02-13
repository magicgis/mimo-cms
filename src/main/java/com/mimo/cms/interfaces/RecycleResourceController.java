package com.mimo.cms.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mimo.cms.application.resource.RecycleResourceService;
import com.mimo.cms.domain.Configure;
import com.mimo.cms.domain.resource.RecycleResourceObject;
import com.mimo.cms.interfaces.util.ConfigureOnWeb;
import com.mimo.core.orm.Page;
import com.mimo.core.web.controller.ControllerSupport;

/**
 * 
 * @author loudyn
 * 
 */
@Controller
@RequestMapping("/recycle-resource")
public class RecycleResourceController extends ControllerSupport {

	@Autowired
	private RecycleResourceService recycleResourceService;

	@Autowired
	private ConfigureOnWeb confOnWeb;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Page<RecycleResourceObject> page, Model model) {
		page = recycleResourceService.queryPage(page);
		model.addAttribute(page);
		return "recycle-resource/list";
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}/recycle", method = RequestMethod.DELETE)
	public String recycle(@PathVariable("id") String id) {
		Configure conf = confOnWeb.wrap(Configure.get());
		RecycleResourceObject entity = recycleResourceService.get(id);
		entity.selfAdjust(conf).delete();
		return "redirect:/recycle-resource/list";
	}

}
