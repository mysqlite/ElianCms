package com.elian.cms.admin.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Log;
import com.elian.cms.admin.service.LogService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.SearchParamUtils;

/**
 * 日志功能
 * 
 * @author Gechuanyi
 * 
 */
@Component
@Scope("prototype")
public class LogThisSiteAction extends BaseAction {
	private static final long serialVersionUID = -7487144247763395265L;
	
	private Pagination<Log> pagination = new Pagination<Log>(SearchParamUtils.getLogConditionMap());
	private Integer id;
	private Log log;
	private LogService logService;
	private Integer days;
	private String logType;
	
	public String list() {
		logService.findLogByAll(pagination,ApplicationUtils.getSite().getId(),null);
		return LIST;
	}

	public String show() {
		if (id > 0) {
			log = logService.get(id);
			return SHOW;
		}
		else
			return SAVE;
	}

	public String delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null)
			for (Integer id : idList) {
				log = logService.get(id);
				logService.delete(log);
			}
		return NONE;
	}

	public String batchDelete() {
		if(days!=null) {
			logService.deleteLogByBeforTime(days);
		}
		list();
		return LIST;
	}

	public Pagination<Log> getPagination() {
		return pagination;
	}

	public void setPagination(Pagination<Log> pagination) {
		this.pagination = pagination;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	@Resource
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}
}
