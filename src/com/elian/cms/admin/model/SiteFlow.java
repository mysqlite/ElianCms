package com.elian.cms.admin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.elian.cms.syst.model.PersistentLogInterface;

/**
 * 站点流量统计
 * 
 * @author thy
 * 
 */
@javax.persistence.Entity
@Table(name="T_SITE_FLOW")
public class SiteFlow implements PersistentLogInterface {
	private static final long serialVersionUID = -3855671238253931704L;
	/**id*/
	private Integer id=null;
    /**站点id*/
    private Integer siteId=null;
    /**访问者的id*/
    private String accessIp=null;
    /**访问时间*/
    private Date accessTime=null;
    /**访问时间*/
    private String accessDate=null;
    /**访问页面*/
    private String accessPage=null;
    /**来访者域名*/
    private String refererSite=null;
    /**来访者页面*/
    private String refererPage=null;
    /**关键字*/
    private String refererKeyword=null;
    /**来访者*/
    private String area=null;
    /**session_id*/
    private String sessionId=null;
    
    
    @Column(name="flow_id")
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="site_id")
	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	@Column(name="access_ip")
	public String getAccessIp() {
		return accessIp;
	}
	
	public void setAccessIp(String accessIp) {
		this.accessIp = accessIp;
	}
	
	@Column(name="access_time")
	public Date getAccessTime() {
		return accessTime;
	}


	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}
	
	@Column(name="access_date")
	public String getAccessDate() {
		return accessDate;
	}
	
	public void setAccessDate(String accessDate) {
		this.accessDate = accessDate;
	}
	
	@Column(name="access_page")
	public String getAccessPage() {
		return accessPage;
	}

	public void setAccessPage(String accessPage) {
		this.accessPage = accessPage;
	}

	@Column(name="referer_site")
	public String getRefererSite() {
		return refererSite;
	}

	public void setRefererSite(String refererSite) {
		this.refererSite = refererSite;
	}

	@Column(name="referer_page")
	public String getRefererPage() {
		return refererPage;
	}

	public void setRefererPage(String refererPage) {
		this.refererPage = refererPage;
	}

	@Column(name="referer_keyword")
	public String getRefererKeyword() {
		return refererKeyword;
	}

	public void setRefererKeyword(String refererKeyword) {
		this.refererKeyword = refererKeyword;
	}

	@Column(name="area")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Column(name="session_id")
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	@Transient
	public String[] getSysLog() {
		return new String[] { "站点流量统计", "accessIp=" + accessIp };
	}
}
