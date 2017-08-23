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
import com.elian.cms.admin.model.Invitation;
import com.elian.cms.admin.model.Type;
import com.elian.cms.admin.service.AreaService;
import com.elian.cms.admin.service.ContacterService;
import com.elian.cms.admin.service.InvitationService;
import com.elian.cms.admin.service.LogService;
import com.elian.cms.admin.service.SiteFileService;
import com.elian.cms.admin.service.TypeService;
import com.elian.cms.admin.service.UserService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.FilePathUtils;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.ValidateUtils;
@Component
@Scope("prototype")
public class InvitationAction extends BaseAction {
	private static final long serialVersionUID = -7160259920696572066L;

	private Integer id = Integer.valueOf(0);
	private boolean edit;
	private String areaName="";
	
	private Invitation invitation; /** 树节点传递过来的是否叶子节点 */
	private boolean leaf = false;   /** 树节点传递过来的栏目ID */	
	private String action;  /** 树节点传递过来的action名称 */	
	private Integer channelId = Integer.valueOf(0);
	/** 内容状态 */
	private Integer status = Integer.valueOf(1);
	
	private List<Contacter> contacterList;
	private List<Type> typeList;
	
	
	private InvitationService invitationService;
	private AreaService areaService;
	private ContacterService contacterService;
	private UserService userService;
	private LogService logService;
	private TypeService typeService;
	
	public String list() {
		return LIST;
	}

	public String edit() {
		createList();
		if (!edit)
			createInvit();
		else {
			invitation=new Invitation();
			BeanUtils.copyProperties(invitationService.get(id), invitation);
			areaName = null != invitation.getAreaId() ? getAreaNames(invitation.getAreaId()): "";
			invitation.setInvitDesc(FilePathUtils.setEditorOutPath(invitation.getInvitDesc()));
		}
		return EDIT;
	}
	private void createInvit() {
		invitation=new Invitation();
		invitation.setCreater(ApplicationUtils.getUser());
		invitation.setCreateTime(new Date());
	}

	private void createList() {
		contacterList=contacterService.findByPage(null, ApplicationUtils.getSite());
		typeList=typeService.findByType(true, "invi_type");
	}

	public String show() {
		if (id > 0) {
			invitation=new Invitation();
			BeanUtils.copyProperties(invitationService.get(id), invitation);
			areaName=areaService.get(invitation.getAreaId()).getAreaName();
			invitation.setInvitDesc(FilePathUtils.setEditorOutPath(invitation.getInvitDesc()));
		}
		return SHOW;
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
		List<Invitation> invitationList = invitationService.findByContentId(idList,ApplicationUtils.getSite().getId());
		if (CollectionUtils.isEmpty(invitationList))
			return;
		for (Invitation invit : invitationList) {
			invitationService.delete(invit);
		}
	}
	
	public String save() {
		invitation.setActionName(action);
		invitation.setChannelId(channelId);
		invitation.setContentStatus(status);
//		if(StringUtils.isBlank(invitation.getCreater()))
//			invitation.setContacter(ApplicationUtils.getUser().getRealName());
		if(invitation.getCreater()==null)
			invitation.setContacter(ApplicationUtils.getUser().getRealName());
		if (invitation.getCreater().getId() != null) {
			invitation.setCreater(userService.get(invitation.getCreater().getId()));
		}
		
//		invitation.setContacter(contacterService.get(invitation.getContacter().getId()));
//		invitation.setCreater(userService.get(invitation.getCreater().getId()));
		invitation.setInvitDesc(FilePathUtils.getConContext(invitation.getInvitDesc()));
		Integer controlId=0;
	    if(isPublish())
	    	controlId=invitationService.save(invitation, edit, isPublish());
	    else
		    invitationService.save(invitation,edit);
	    if(controlId!=0) 
	    	setControlId(controlId);
		siteFileService.saveConContext(invitation,getEditorPrevImg(),invitation.getInvitDesc());
		return SAVE;
	}

	public void validateSave() {
		if (StringUtils.isBlank(invitation.getPublisher()))
			this.addFieldError("invitation.publisher", "请填写企业名称");
		else {
			if (ValidateUtils.isLengOut(invitation.getPublisher(), 200))
				this.addFieldError("invitation.publisher", "企业名称长度在200字以内");
		}		
		if (ValidateUtils.isLengOut(invitation.getKeywords(), 100))
			this.addFieldError("invitation.keywords", "关键字长度在100字以内");
		if (ValidateUtils.isLengOut(invitation.getSourceName(), 50))
			this.addFieldError("invitation.sourceName", "来源名称长度在50字以内");
		if (ValidateUtils.isLengOut(invitation.getSourceUrl(), 255))
			this.addFieldError("invitation.sourceUrl", "来源路径长度在255字以内");		
		
		if (StringUtils.isBlank(invitation.getProjectName()))
			this.addFieldError("invitation.projectName", "请填写项目名称");
		else {
			if (ValidateUtils.isLengOut(invitation.getProjectName(), 200))
				this.addFieldError("invitation.projectName", "企业名称长度在200字以内");
		}	
		
		if (StringUtils.isBlank(invitation.getTitle()))
			this.addFieldError("invitation.title", "请填写项目名称");
		else {
			if (ValidateUtils.isLengOut(invitation.getTitle(), 100))
				this.addFieldError("invitation.title", "项目名称长度在100字以内");
		}	
		
		if (StringUtils.isBlank(invitation.getInvitType()))
			this.addFieldError("invitation.invitType", "请选择招标方式");
		else {
			if (ValidateUtils.isLengOut(invitation.getInvitType(), 50))
				this.addFieldError("invitation.invitType", "招标方式长度在50字以内");
		}	
		
		if (null == invitation.getAreaId()|| invitation.getAreaId()==0)
			this.addFieldError("invitation.areaId", "请选择采购区域");
		if (ValidateUtils.isLengOut(invitation.getDescription(), 255))
			this.addFieldError("invitation.description", "摘要长度在255字以内");		
		if (null == invitation.getContacter())
			this.addFieldError("invitation.contacter.id", "请选择联系人");
		
		if (null == invitation.getExpireTime())
			this.addFieldError("invitation.expireTime", "请选择有效期至");
		else if(invitation.getExpireTime().before(invitation.getCreateTime())){
			this.addFieldError("invitation.expireTime", "有效期必须比创建时间晚");						
		}
		
		/*if (ValidateUtils.isLengOut(invitation.getInvitDesc(), 20000))
			this.addFieldError("invitation.invitDesc", "说明的长度要求在20000字以内");
		*/
		if (StringUtils.isBlank(invitation.getContacter()))
			this.addFieldError("invitation.contacter", "请填写联系人姓名");
		else {
			if (ValidateUtils.isLengOut(invitation.getContacter(), 50))
				this.addFieldError("invitation.contacter", "联系人姓名长度在50字以内");
		}	
		
		if (StringUtils.isBlank(invitation.getContacterPhone()))
			this.addFieldError("invitation.contacterPhone", "请填写联系人电话号码");
		else {
			if (ValidateUtils.isNotPhoneAndMobile(invitation.getContacterPhone()))
				this.addFieldError("invitation.contacterPhone", "请填写正确的电话号码");
		}	
		if (invitation.getCreateTime() == null)
			invitation.setCreateTime(new Date());
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

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Invitation getInvitation() {
		return invitation;
	}

	public void setInvitation(Invitation invitation) {
		this.invitation = invitation;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
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

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public List<Contacter> getContacterList() {
		return contacterList;
	}

	public void setContacterList(List<Contacter> contacterList) {
		this.contacterList = contacterList;
	}

	public List<Type> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<Type> typeList) {
		this.typeList = typeList;
	}

	public InvitationService getInvitationService() {
		return invitationService;
	}

	 private SiteFileService siteFileService;
	 @Resource
	public void setSiteFileService(SiteFileService siteFileService) {
		this.siteFileService = siteFileService;
	}
	
	@Resource(name="invitationService")
	public void setInvitationService(InvitationService invitationService) {
		this.invitationService = invitationService;
	}

	public AreaService getAreaService() {
		return areaService;
	}

	@Resource
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

	public ContacterService getContacterService() {
		return contacterService;
	}

	@Resource
	public void setContacterService(ContacterService contacterService) {
		this.contacterService = contacterService;
	}

	public UserService getUserService() {
		return userService;
	}
	
	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public LogService getLogService() {
		return logService;
	}
	
	@Resource
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	public TypeService getTypeService() {
		return typeService;
	}
	
	@Resource
	public void setTypeService(TypeService typeService) {
		this.typeService = typeService;
	}	
}
