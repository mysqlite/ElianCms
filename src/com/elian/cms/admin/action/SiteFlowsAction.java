package com.elian.cms.admin.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.SiteFlow;
import com.elian.cms.admin.service.SiteFlowService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.SearchParamUtils;
/**
 * 站点流量统计action
 * 
 * @author Gechuanyi
 * 
 */
@Component
@Scope("prototype")
public class SiteFlowsAction extends BaseAction {	
	private static final long serialVersionUID = -5983318337548919582L;
	private Pagination<SiteFlow> pagination = new Pagination<SiteFlow>(SearchParamUtils.getSiteFlowConditionMap());
	private SiteFlowService siteFlowService;
	private SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
	private static final String BAIDU="http://www.baidu.com";
	private static final String GOOGLE="http://www.google.com";
	private static final String SOSO="http://www.soso.com";
	private static final String SO360="http://so.360.cn";
	public String list() {
		/*System.out.println( EhcacheUtils.getCacheFtp(EhcacheUtils.STATIC_FTP).getFtpName());
		System.out.println(EhcacheUtils.getCacheFtp(EhcacheUtils.IMG_FTP).getFtpName());
		System.out.println(EhcacheUtils.getCacheFtp(EhcacheUtils.SWF_FTP).getFtpName());
		System.out.println(EhcacheUtils.getCacheFtp(EhcacheUtils.FILE_FTP).getFtpName());*/
		siteFlowService.findByPage(pagination, ApplicationUtils.getSite().getId());
		return LIST;
	}
	private String dateFormat(Date date) {
		if(date==null) {
			return null;
		}else {
			return sf.format(date);
		}
	}
	
	private JSONObject statisticals(Date now) {
		JSONObject obj=new JSONObject();
		Integer pv= siteFlowService.getSitePV(ApplicationUtils.getSite().getId(), dateFormat(now));
		Integer uv=siteFlowService.getSiteUV(ApplicationUtils.getSite().getId(), dateFormat(now));
		Integer ip=siteFlowService.getSiteIP(ApplicationUtils.getSite().getId(), dateFormat(now));
		Integer baidu=siteFlowService.getRefererSite(ApplicationUtils.getSite().getId(), BAIDU,dateFormat(now));
		Integer google=siteFlowService.getRefererSite(ApplicationUtils.getSite().getId(), GOOGLE, dateFormat(now));
		Integer soso=siteFlowService.getRefererSite(ApplicationUtils.getSite().getId(), SOSO, dateFormat(now));
		Integer so360=siteFlowService.getRefererSite(ApplicationUtils.getSite().getId(), SO360,dateFormat(now));
		obj.put("pv", pv);
		obj.put("uv", uv);
		obj.put("ip", ip);
		obj.put("baidu", baidu);
		obj.put("google", google);
		obj.put("soso", soso);
		obj.put("so360", so360);
		return obj;
	}
	
	public void today() {
		ApplicationUtils.sendJsonpObj(statisticals(new Date()));
	}
	
	@SuppressWarnings("deprecation")
	public void yesterday() {
		Date d=new Date();
		d.setDate(d.getDate()-1);
		ApplicationUtils.sendJsonpObj(statisticals(d));
	}
	
	public void statistical() {
		ApplicationUtils.sendJsonpObj(statisticals(null));
	}

	public Pagination<SiteFlow> getPagination() {
		return pagination;
	}

	public void setPagination(Pagination<SiteFlow> pagination) {
		this.pagination = pagination;
	}

	@Resource
	public void setSiteFlowService(SiteFlowService siteFlowService) {
		this.siteFlowService = siteFlowService;
	}
	
}
