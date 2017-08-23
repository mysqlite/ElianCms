package com.elian.cms.admin.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Area;
import com.elian.cms.admin.model.Contacter;
import com.elian.cms.admin.model.Industry;
import com.elian.cms.admin.model.Job;
import com.elian.cms.admin.service.AreaService;
import com.elian.cms.admin.service.ContacterService;
import com.elian.cms.admin.service.IndustryService;
import com.elian.cms.admin.service.JobService;
import com.elian.cms.admin.service.SiteFileService;
import com.elian.cms.admin.service.UserService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.FilePathUtils;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.SysXmlUtils;
import com.elian.cms.syst.util.ValidateUtils;

/**
 * 人才招聘
 * 
 * @author Gechuanyi
 * 
 */
@Component
@Scope("prototype")
public class JobAction extends BaseAction {
	private static final long serialVersionUID = -7124134287126165774L;
	private Integer id = Integer.valueOf(0);
	private boolean edit;
	private String areaName="";
	private Job job;
	/** 树节点传递过来的是否叶子节点 */
	private boolean leaf = false;
	/** 树节点传递过来的栏目ID */
	private Integer channelId = Integer.valueOf(0);
	/** 树节点传递过来的action名称 */
	private String action;
	/** 内容状态 */
	private Integer status = Integer.valueOf(1);
	private List<Contacter> contacterList;
	private List<SelectItem> educationList;
	private List<SelectItem> jobNatureList;
	private List<Industry> jobIndustryList;
	
	
	
	private JobService jobService;
	private AreaService areaService;
	private ContacterService contacterService;
	private UserService userService;
	private IndustryService industryService;
	
	public String list() {
		return LIST;
	}

	public String edit() {		
		createList();
		if (!edit)
			createJob();
		else {
			job=new Job();
			BeanUtils.copyProperties(jobService.get(id), job);
			areaName = null != job.getAreaId() ? getAreaNames(job.getAreaId()): "";
			job.setJobRequ(FilePathUtils.setEditorOutPath(job.getJobRequ()));

		}
		return EDIT;
	}

	public String show() {
		if (id > 0) {
			job=new Job();
			BeanUtils.copyProperties(jobService.get(id), job);
			areaName=areaService.get(job.getAreaId()).getAreaName();
			job.setJobRequ(FilePathUtils.setEditorOutPath(job.getJobRequ()));
			jobIndustryList=industryService.findByIndustry();
		}
		return SHOW;
	}
	
	private void createJob() {
		job = new Job();
		job.setCreaterId(ApplicationUtils.getUser());
		job.setCreateTime(new Date());
	}

	public String getAreaNames(Integer areaCode) {
		List<String> list = new ArrayList<String>();
		List<Area> area = areaService.findAreaNameByAreaCode(areaCode);
		for (int i = 0, len = area.size(); i < len; i++) {
			list.add(area.get(i).getAreaName());
		}
		for (int i = list.size() - 1; i >= 0; i--) {
			areaName += list.get(i) + " ";
		}
		return areaName;
	}

	public void delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (CollectionUtils.isEmpty(idList))
			return;
		List<Job> jobList = jobService.findByContentId(idList,ApplicationUtils.getSite().getId());
		if (CollectionUtils.isEmpty(jobList))
			return;
		for (Job job : jobList) {
			jobService.delete(job);
		}
	}
	
	public String save() {
		job.setActionName(action);
		job.setChannelId(channelId);
		job.setContentStatus(status);
		job.setCreaterId(userService.get(job.getCreaterId().getId()));		
		job.setJobRequ(FilePathUtils.getConContext(job.getJobRequ()));
		job.setIndustry(industryService.get(job.getIndustry().getId()));
		Integer controlId=0;
		if(isPublish())
			controlId=jobService.save(job, edit,isPublish());
		else
		   jobService.save(job,edit);
		if(controlId!=0)
			setControlId(controlId);
		siteFileService.saveConContext(job,getEditorPrevImg(),job.getJobRequ());
		return SAVE;
	}
	
	public List<SelectItem> getSexList(){
		return ElianUtils.getJobSex();
	}
	
	private void createList() {
		educationList = SysXmlUtils.getXMLSelect("education");
		contacterList = contacterService.findByPage(null, ApplicationUtils.getSite());
		jobNatureList=SysXmlUtils.getXMLSelect("jobnature");
		jobIndustryList=industryService.findByIndustry();
	}
	

	public void validateSave() {
		if (StringUtils.isBlank(job.getTitle()))
			this.addFieldError("job.title", "请填写招聘标题");
		else {
			if (ValidateUtils.isLengOut(job.getTitle(), 100))
				this.addFieldError("job.title", "标题长度在100字以内");
		}
		if (ValidateUtils.isLengOut(job.getDescription(), 255))
			this.addFieldError("job.description", "摘要长度在255字以内");
		if (ValidateUtils.isLengOut(job.getKeywords(), 100))
			this.addFieldError("job.keywords", "关键字长度在100字以内");
		if (ValidateUtils.isLengOut(job.getSourceName(), 50))
			this.addFieldError("job.sourceName", "来源名称长度在50字以内");
		if (ValidateUtils.isLengOut(job.getSourceUrl(), 255))
			this.addFieldError("job.sourceUrl", "来源路径长度在255字以内");
		if (ValidateUtils.isLengOut(job.getServantDepa(), 100))
			this.addFieldError("job.servantDepa", "用人部门长度在100字以内");
		if (ValidateUtils.isLengOut(job.getJobName(), 100))
			this.addFieldError("job.jobName", "职位名称长度在100字以内");
		if (ValidateUtils.isLengOut(job.getJobNature(), 100))
			this.addFieldError("job.jobNature", "工作性质长度在100字以内");
		if (ValidateUtils.isLengOut(job.getHireNum(), 50))
			this.addFieldError("job.hireNum", "招聘人数长度在50字以内");
		if (ValidateUtils.isLengOut(job.getHireNum(), 255))
			this.addFieldError("job.majorRequ", "专业要求长度在255字以内");
		if (ValidateUtils.isLengOut(job.getSalary(), 100))
			this.addFieldError("job.salary", "提供月薪长度在100字以内");
		if (ValidateUtils.isLengOut(job.getHousing(), 100))
			this.addFieldError("job.housing", "住宿情况长度在100字以内");
		if (null == job.getAreaId()|| job.getAreaId()==0)
			this.addFieldError("job.areaId", "请选择工作地点");
		if (ValidateUtils.isLengOut(job.getLanguage(), 100))
			this.addFieldError("job.language", "语言要求长度在100字以内");
		if (ValidateUtils.isLengOut(job.getDescription(), 100))
			this.addFieldError("job.education", "学历要求长度在100字以内");
		if (ValidateUtils.isLengOut(job.getAgeRange(), 100))
			this.addFieldError("job.ageRange", "年龄要求长度在100字以内");
		if (ValidateUtils.isLengOut(job.getGender(), 20))
			this.addFieldError("job.gender", "性别要求长度在20字以内");
		if (job.getIndustry().getId()==null)
			this.addFieldError("job.industry", "请选择行业类型");
		if (ValidateUtils.isLengOut(job.getWorkExpe(), 100))
			this.addFieldError("job.workExpe", "工作经验要求长度在100字以内");
		if (ValidateUtils.isLengOut(job.getJobRequ(), 50000))
			this.addFieldError("job.jobRequ", "岗位要求长度在5000字以内");
		if(StringUtils.isNotBlank(job.getContacter())) {
    		if(ValidateUtils.isLengOut(job.getContacter(), 50))
    			this.addFieldError("job.contacter", "[联系人]长度在50字以内");
		}
		if(StringUtils.isNotBlank(job.getContactPhone())) {
			if(ValidateUtils.isNotPhoneAndMobile(job.getContactPhone())){
				this.addFieldError("job.contactPhone", "请输入正确的联系电话");
			}
		}
		if (null == job.getExpireTime()){
			this.addFieldError("job.expireTime", "请选择有效期至");			
		}else if(job.getExpireTime().before(job.getCreateTime())){
			this.addFieldError("job.expireTime", "有效期必需比创建时间晚");
		}
		if (job.getCreaterId() == null)
			job.setCreaterId(ApplicationUtils.getUser());
		if (job.getCreateTime() == null)
			job.setCreateTime(new Date());
		if(this.hasFieldErrors()) 
			createList();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public List<Contacter> getContacterList() {
		return contacterList;
	}

	public void setContacterList(List<Contacter> contacterList) {
		this.contacterList = contacterList;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<SelectItem> getEducationList() {
		return educationList;
	}

	public void setEducationList(List<SelectItem> educationList) {
		this.educationList = educationList;
	}
	
	public List<SelectItem> getJobNatureList() {
		return jobNatureList;
	}

	
	public void setJobNatureList(List<SelectItem> jobNatureList) {
		this.jobNatureList = jobNatureList;
	}

	private SiteFileService siteFileService;
	@Resource
	public void setSiteFileService(SiteFileService siteFileService) {
		this.siteFileService = siteFileService;
	}
	@Resource
	public void setContacterService(ContacterService contacterService) {
		this.contacterService = contacterService;
	}

	@Resource
	public void setJobService(JobService jobService) {
		this.jobService = jobService;
	}

	@Resource
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Resource
	public void setIndustryService(IndustryService industryService) {
		this.industryService = industryService;
	}

	public IndustryService getIndustryService() {
		return industryService;
	}

	public void setJobIndustryList(List<Industry> jobIndustryList) {
		this.jobIndustryList = jobIndustryList;
	}

	public List<Industry> getJobIndustryList() {
		return jobIndustryList;
	}
	
}
