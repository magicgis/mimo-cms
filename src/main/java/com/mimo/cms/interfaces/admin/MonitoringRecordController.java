package com.mimo.cms.interfaces.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mimo.cms.application.monitoring.MonitoringRecordService;
import com.mimo.cms.domain.MonitoringRecord;
import com.mimo.core.orm.Page;
import com.mimo.core.web.controller.ControllerSupport;

/**
 * 
 * @author loudyn
 * 
 */
@Controller
@RequestMapping("monitoring-record")
public class MonitoringRecordController extends ControllerSupport {

	@Autowired
	private MonitoringRecordService monitoringRecordService;

	/**
	 * 
	 * @param page
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Page<MonitoringRecord> page, Model model) {
		page = monitoringRecordService.queryPage(page);
		model.addAttribute(page);
		return "monitoring-record/list";
	}
}
