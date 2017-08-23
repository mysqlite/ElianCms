package com.elian.cms.admin.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.LogDao;
import com.elian.cms.admin.model.Log;
import com.elian.cms.admin.service.LogService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;
import com.elian.cms.syst.util.ApplicationUtils;
@Component("logService")
public class LogServiceImpl extends ServiceImpl<LogDao, Log, Integer> implements
		LogService {

	/**
	 * @param category	日志类型
	 * @param title	           日志标题
	 * @param contents	日志详细内容键值对组合(key1,value1,key2,value2.....)的形式，可不填
	 */
	public void save(int category, String title, String... contents) {
		Log log = createLog(category, title, formatContent(contents));
		save(log);
	}

	/**
	 * 创建日志对象
	 */
	public Log createLog(int category, String title, String content) {
		Log log = new Log();
		log.setUser(ApplicationUtils.getUser());
		log.setCategory(category);
		log.setCreateDate(new Date());
		log.setIp(ApplicationUtils.getIpAddr());
		log.setUrl(ApplicationUtils.getUrl());
		log.setTitle(title);
		log.setContent(content);
		log.setSiteId((ApplicationUtils.getSite()!=null)?ApplicationUtils.getSite().getId():1);
		return log;
	}

	/**
	 * 格式化内容
	 */
	public String formatContent(String... contents) {
		int length;
		if (contents == null || (length = contents.length) == 0
				|| length % 2 != 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i += 2) {
			if (i != 0)
				sb.append(";");
			sb.append(contents[i]).append("=").append(contents[i + 1]);
		}
		return sb.toString();
	}
	
	public List<Log> findLogByAll(Pagination<Log> p,Integer siteId,Integer userId){
		return dao.findLogByAll(p,siteId,userId);
	}
	
	public boolean deleteLogByBeforTime(int time) {
		return dao.deleteLogByBeforTime(time);
	}

	@Resource
	public void setDao(LogDao dao) {
		this.dao = dao;
	}
}
