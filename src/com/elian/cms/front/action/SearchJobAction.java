package com.elian.cms.front.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Area;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Job;
import com.elian.cms.admin.service.AreaService;
import com.elian.cms.admin.service.ContentService;
import com.elian.cms.admin.service.JobService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.FreemarkerCodes;

/**
 * 搜索人才Action
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class SearchJobAction extends BaseAction implements Serializable {
	private static final long serialVersionUID = -7745551580039755550L;

	/** 站点ID */
	protected Integer siteId;
	private Integer provinceId;
	private Integer cityId;
	private String jobName;
	private Pagination<Content> pagination = new Pagination<Content>();
	private List<VirtualJob> virtualJobList;
	// 省级List
	private List<SelectItem> provinceList;
	// 市级List
	private List<SelectItem> cityList;

	private JobService jobService;
	private ContentService contentService;
	private AreaService areaService;

	/**
	 * 初始化搜索页面方法
	 */
	public String list() {
		List<Area> areaList = new ArrayList<Area>();
		if(cityId != null){
			areaList.add(areaService.get(cityId));
		}
		else if (provinceId != null) {
			areaList.add(areaService.get(provinceId));
			areaList.addAll(areaService.findByParentCodeNameAsc(provinceId));
		}
		
		List<Content> list = contentService.findJobBySite(siteId, Job.class, true,
				areaList, jobName, pagination);
		if (!CollectionUtils.isEmpty(list)) {
			virtualJobList = new ArrayList<VirtualJob>(10);
			List<Job> jobList = jobService.findFormContentList(list, siteId);
			if (!CollectionUtils.isEmpty(jobList)) {
				for (Content c : list) {
					virtualJobList.add(createVirtualJob(c, jobList));
				}
			}
		}
		return LIST;
	}
	
	private VirtualJob createVirtualJob(Content c, List<Job> jobList) {
		VirtualJob vJob = new VirtualJob();
		String path = "http://" + c.getSite().getDomain()
				+ FreemarkerCodes.DOMAIN_SUFFIX + c.getChannel().getPath()
				+ ElianCodes.SPRIT + c.getId() + ElianCodes.SUFFIX_SHTML;
		vJob.setPath(path);
		vJob.setCompanyName(c.getSite().getSiteName());
		Job job = getJobInList(c.getEntityId(), jobList);
		if (job != null) {
			vJob.setJobName(job.getJobName());
			vJob.setSex(job.getGender());
			if (job.getAreaId() != null && job.getAreaId() > 0) {
				vJob.setJobArea(areaService.get(job.getAreaId()).getAreaName());
			}
			vJob.setWorkExpe(job.getWorkExpe());
			vJob.setPublishTime(job.getPublishTime());
			vJob.setExpireTime(job.getExpireTime());
		}
		return vJob;
	}

	public Job getJobInList(Integer entityId, List<Job> jobList) {
		for (Job job : jobList) {
			if (entityId.equals(job.getId())) {
				return job;
			}
		}
		return null;
	}

	public void changeSelect() {
		Integer provinceId = Integer.valueOf(ApplicationUtils
				.getAjaxSelectedValue());
		Area area = areaService.get(provinceId);

		if (area != null) {
			provinceList = new ArrayList<SelectItem>();
			InvocationAreaList(provinceList, area.getId());
		}
		JSONObject obj = new JSONObject();
		obj.put(0, provinceList);
		ApplicationUtils.sendJsonpString(obj.toString());
	}

	public List<SelectItem> getProvinceList() {
		if (provinceList == null) {
			provinceList = new ArrayList<SelectItem>();
			InvocationAreaList(provinceList, 0);
		}
		return provinceList;
	}
	
	public List<SelectItem> getCityList() {
		if (cityList == null) {
			cityList = new ArrayList<SelectItem>();
			InvocationAreaList(cityList, provinceId);
		}
		return cityList;
	}

	private void InvocationAreaList(List<SelectItem> list, Integer parentId) {
		List<Area> areaList = areaService.findByParentCode(parentId);
		if (CollectionUtils.isEmpty(areaList))
			return;
		for (Area area : areaList) {
			list
					.add(new SelectItem(area.getAreaName(), area.getId()
							.toString()));
		}
	}
	
	public static List<SelectItem> getSexList() {
		return ElianUtils.getSexList();
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public List<VirtualJob> getVirtualJobList() {
		return virtualJobList;
	}

	public Pagination<Content> getPagination() {
		return pagination;
	}
	
	@Resource
	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}

	@Resource
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

	@Resource
	public void setJobService(JobService jobService) {
		this.jobService = jobService;
	}

	public class VirtualJob {
		/** 职位 */
		private String jobName;
		/** 链接 */
		private String path;
		/** 公司名称 */
		private String companyName;
		/** 性别 */
		private String sex;
		/** 工作地点 */
		private String jobArea;
		/** 工作经验要求 */
		private String workExpe;
		/** 发布时间 */
		private Date publishTime;
		/** 有效日期 */
		private Date expireTime;

		public String getJobName() {
			return jobName;
		}

		public void setJobName(String jobName) {
			this.jobName = jobName;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public String getCompanyName() {
			return companyName;
		}

		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public String getJobArea() {
			return jobArea;
		}

		public void setJobArea(String jobArea) {
			this.jobArea = jobArea;
		}

		public String getWorkExpe() {
			return workExpe;
		}

		public void setWorkExpe(String workExpe) {
			this.workExpe = workExpe;
		}

		public Date getPublishTime() {
			return publishTime;
		}

		public void setPublishTime(Date publishTime) {
			this.publishTime = publishTime;
		}

		public Date getExpireTime() {
			return expireTime;
		}

		public void setExpireTime(Date expireTime) {
			this.expireTime = expireTime;
		}
	}
}
