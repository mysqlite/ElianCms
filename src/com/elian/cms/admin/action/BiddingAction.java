package com.elian.cms.admin.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Area;
import com.elian.cms.admin.model.Bidding;
import com.elian.cms.admin.service.AreaService;
import com.elian.cms.admin.service.BiddingService;
import com.elian.cms.admin.service.LogService;
import com.elian.cms.admin.service.SiteFileService;
import com.elian.cms.admin.service.UserService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.FilePathUtils;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.ValidateUtils;

@Component
@Scope("prototype")
public class BiddingAction extends BaseAction {
	private static final long serialVersionUID = 1409465486819596867L;
	
	private Integer id = Integer.valueOf(0);
	private boolean edit;
	private String areaName="";
	private Bidding bidding;
	
	private boolean leaf = false;  /** 树节点传递过来的是否叶子节点 */	
	private Integer channelId = Integer.valueOf(0);/** 树节点传递过来的栏目ID */	
	private String action;/** 树节点传递过来的action名称 */
	/** 内容状态 */
	private Integer status = Integer.valueOf(1);
	
	private BiddingService biddingService;
	private AreaService areaService;
	private UserService userService;
	private LogService logService;
	private SiteFileService siteFileService;
	
	public String list() {
		return LIST;
	}

	public String edit() {
		if (!edit)
			createBidding();
		else {
			bidding=biddingService.get(id);
			areaName = null != bidding.getAreaId() ? getAreaNames(bidding.getAreaId()): "";
			bidding.setBidDesc(FilePathUtils.setEditorOutPath(bidding.getBidDesc()));
		}
		return EDIT;
	}

	private void createBidding() {
		bidding=new Bidding();
		bidding.setCreater(ApplicationUtils.getUser());
		bidding.setCreateTime(new Date());		
	}

	public String show() {
		if (id > 0) {
			bidding = biddingService.get(id);
			areaName=areaService.get(bidding.getAreaId()).getAreaName();
			bidding.setBidDesc(FilePathUtils.setEditorOutPath(bidding.getBidDesc()));
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
		try{
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (CollectionUtils.isEmpty(idList))
			return;
		List<Bidding> biddingList = biddingService.findByContentId(idList,ApplicationUtils.getSite().getId());
		if (CollectionUtils.isEmpty(biddingList))
			return;
		for (Bidding bidding : biddingList) {
			biddingService.delete(bidding);
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String save() {
		bidding.setActionName(action);
		bidding.setChannelId(channelId);
		bidding.setContentStatus(status);
//		if(StringUtils.isBlank(bidding.getCreater()))
//			bidding.setCreater(ApplicationUtils.getUser().getRealName());
		if (bidding.getCreater().getId() != null) {
			bidding.setCreater(userService.get(bidding.getCreater().getId()));
		}
		
		//bidding.setCreater(userService.get(bidding.getCreater().getId()));		
		bidding.setBidDesc(FilePathUtils.getConContext(bidding.getBidDesc()));
		Integer controlId=0;
		if(isPublish())
			controlId=biddingService.save(bidding, edit, isPublish());
		else
		    biddingService.save(bidding,edit);		
		if(controlId!=0)
			setControlId(controlId);
		siteFileService.saveConContext(bidding,getEditorPrevImg(),bidding.getBidDesc());		
		return SAVE;
	}
	

	public void validateSave() {
		if (StringUtils.isBlank(bidding.getProjectName()))
			this.addFieldError("bidding.projectName", "请填写项目名称 ");
		else {
			if (ValidateUtils.isLengOut(bidding.getProjectName(), 100))
				this.addFieldError("bidding.projectName","项目名称长度在100字以内");
		}		
		if (ValidateUtils.isLengOut(bidding.getKeywords(), 100))
			this.addFieldError("bidding.keywords", "关键字长度在100字以内");
		if (ValidateUtils.isLengOut(bidding.getSourceName(), 50))
			this.addFieldError("bidding.sourceName", "来源名称长度在50字以内");
		if (ValidateUtils.isLengOut(bidding.getSourceUrl(), 255))
			this.addFieldError("bidding.sourceUrl", "来源路径长度在255字以内");
		if (ValidateUtils.isLengOut(bidding.getBidCompany(), 200))
			this.addFieldError("bidding.bidCompany", "中标单位长度在200字以内");		
		
		if(StringUtils.isNotBlank(bidding.getBidSum())){
			if(ValidateUtils.isLengOut(bidding.getBidSum(), 20)){
				this.addFieldError("bidding.bidSum", "金额的长度在20字以内");	
			}else{				
				if(!ValidateUtils.isDouble(bidding.getBidSum())){
					this.addFieldError("bidding.bidSum", "请输入合法的金额");				
				}else if(ValidateUtils.isDOUBLE_POSITIVE(bidding.getBidSum())){
					this.addFieldError("bidding.bidSum", "金额必须大于0");	
				}
			}
		}
		
		if (StringUtils.isBlank(bidding.getTitle()))
			this.addFieldError("bidding.title", "请填写标题名称 ");
		else {
			if (ValidateUtils.isLengOut(bidding.getTitle(), 100))
				this.addFieldError("bidding.title","项目名称长度在100字以内");
		}	
		if (null == bidding.getAreaId()|| bidding.getAreaId()==0)
			this.addFieldError("bidding.areaId", "请选择区域信息");		
		if (ValidateUtils.isLengOut(bidding.getDescription(), 255))
			this.addFieldError("bidding.description", "摘要长度在255字以内");		
		
		if (bidding.getCreateTime() == null)
			bidding.setCreateTime(new Date());
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

	public Bidding getBidding() {
		return bidding;
	}

	public void setBidding(Bidding bidding) {
		this.bidding = bidding;
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

	public BiddingService getBiddingService() {
		return biddingService;
	}

	@Resource
	public void setBiddingService(BiddingService biddingService) {
		this.biddingService = biddingService;
	}

	public AreaService getAreaService() {
		return areaService;
	}

	@Resource
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
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
	
	@Resource
	public void setSiteFileService(SiteFileService siteFileService) {
		this.siteFileService = siteFileService;
	}
}
