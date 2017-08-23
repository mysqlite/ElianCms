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
import com.elian.cms.admin.model.Purchase;
import com.elian.cms.admin.service.AreaService;
import com.elian.cms.admin.service.ContacterService;
import com.elian.cms.admin.service.LogService;
import com.elian.cms.admin.service.PurchaseService;
import com.elian.cms.admin.service.SiteFileService;
import com.elian.cms.admin.service.UserService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.FilePathUtils;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.ValidateUtils;

/**
 * 求购
 * 
 * @author thy
 * 
 */
@Component
@Scope("prototype")
public class PurchaseAction extends BaseAction {
	private static final long serialVersionUID = -7124134287126165784L;
	private Integer id=Integer.valueOf(0);
	private boolean edit;	
	private Purchase purchase;
	/** 树节点传递过来的是否叶子节点 */
	private boolean leaf = false;
	/** 树节点传递过来的栏目ID */
	private Integer channelId = Integer.valueOf(0);
	/** 树节点传递过来的action名称 */
	private String action;
	/** 内容状态 */
	private Integer status = Integer.valueOf(1);
	
	private String areaName="";

	
	private List<Contacter> contacterList;	
	
	private PurchaseService purchaseService;
	private AreaService areaService;
	private ContacterService contacterService;
	private UserService userService;
	private LogService logService;

	
	public String list() {
		return LIST;
	}

	public String edit() {
		createList();
		if (!edit){
			createPurchase();
		}
		else {
			purchase=new Purchase();
			BeanUtils.copyProperties(purchaseService.get(id), purchase);
			areaName = null != purchase.getAreaId() ? getAreaNames(purchase.getAreaId()): "";
			purchase.setDesc(FilePathUtils.setEditorOutPath(purchase.getDesc()));
			purchase.setNotice(FilePathUtils.setEditorOutPath(purchase.getNotice()));
		}
		return EDIT;
	}

	public String show() {
		if (id > 0) {
			purchase=new Purchase();
			BeanUtils.copyProperties(purchaseService.get(id), purchase);
			purchase.setDesc(FilePathUtils.setEditorOutPath(purchase.getDesc()));
			purchase.setNotice(FilePathUtils.setEditorOutPath(purchase.getNotice()));
		}
		return SHOW;
	}
	

	public String save() {
		purchase.setActionName(action);
		purchase.setChannelId(channelId);
		purchase.setContentStatus(status);
		purchase.setContacter(contacterService.get(purchase.getContacter().getId()));
		purchase.setCreaterUser(userService.get(purchase.getCreaterUser().getId()));		
		purchase.setDesc(FilePathUtils.getConContext(purchase.getDesc()));
		purchase.setNotice(FilePathUtils.getConContext(purchase.getNotice()));
		Integer controlId=0;
		if(isPublish())
		     controlId=purchaseService.save(purchase, edit,isPublish());
		else 
			purchaseService.save(purchase,edit);
		if(controlId!=0)
			setControlId(controlId);
		siteFileService.saveConContext(purchase,getEditorPrevImg(),purchase.getDesc(),purchase.getNotice());
		return SAVE;
	}
	
	public void delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (CollectionUtils.isEmpty(idList))
			return;
		List<Purchase> purchaseList = purchaseService.findByContentId(idList,ApplicationUtils.getSite().getId());
		if (CollectionUtils.isEmpty(purchaseList))
			return;
		for (Purchase purchase: purchaseList) {
			purchaseService.delete(purchase);
		}
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
	
	private void createList() {
		contacterList = contacterService.findByPage(null, ApplicationUtils.getSite());
	}

	private void createPurchase() {
		purchase=new Purchase();
		purchase.setCreaterUser(ApplicationUtils.getUser());
		purchase.setCreateTime(new Date());
	}
	
	public void validateSave() {
		if (StringUtils.isBlank(purchase.getName()))
			this.addFieldError("purchase.name", "请填加产品名称");
		else {
			if (ValidateUtils.isLengOut(purchase.getTitle(), 100))
				this.addFieldError("purchase.title", "标题长度在100字以内");
		}
		if (ValidateUtils.isLengOut(purchase.getKeywords(), 100))
			this.addFieldError("purchase.keywords", "关键字长度在100字以内");
		if (ValidateUtils.isLengOut(purchase.getSourceName(), 50))
			this.addFieldError("purchase.sourceName", "来源名称长度在50字以内");
		if (ValidateUtils.isLengOut(purchase.getSourceUrl(), 255))
			this.addFieldError("purchase.sourceUrl", "来源路径长度在255字以内");
		if(StringUtils.isNotBlank(purchase.getPrice())){			
			if(!ValidateUtils.isDouble(purchase.getPrice())){
				this.addFieldError("purchase.price", "请输入合法的金额");			
			}else if(ValidateUtils.isDOUBLE_POSITIVE(purchase.getPrice())){
				this.addFieldError("purchase.price", "必须大于等于0");				
			}
		}
		
		if(purchase.getNumber()<0)
			this.addFieldError("purchase.number", "必修为正整数");
		if (ValidateUtils.isLengOut(purchase.getSpecification(), 100))
			this.addFieldError("purchase.specification", "规格的长度在100字以内");
		if (ValidateUtils.isLengOut(purchase.getDesc(), 2000))
			this.addFieldError("purchase.desc", "产品详情的长度在2000字以内");
		
		if (StringUtils.isBlank(purchase.getTitle()))
			this.addFieldError("purchase.title", "标题不能为空");
		else if(ValidateUtils.isLengOut(purchase.getTitle(), 100))
			this.addFieldError("purchase.title", "标题的长度在100字以内");		
		if (ValidateUtils.isLengOut(purchase.getDescription(), 255))
			this.addFieldError("purchase.description", "摘要的长度在255字以内");
		if (ValidateUtils.isLengOut(purchase.getNotice(), 2000))
			this.addFieldError("purchase.notice", "求购须知的长度在2000字以内");
		if (null == purchase.getAreaId()|| purchase.getAreaId()==0)
			this.addFieldError("purchase.areaId", "请选择工作地点");				
		if (null == purchase.getContacter())
			this.addFieldError("job.contact.id", "请选择联系人");
		
		if (null == purchase.getExpireTime()){			
			this.addFieldError("purchase.expireTime", "请选择有效期至");
		}else if(purchase.getExpireTime().before(purchase.getCreateTime())){
			this.addFieldError("purchase.expireTime", "有效期必须大于创建的日期");
		}
		
		if (purchase.getCreaterUser() == null)
			purchase.setCreaterUser(ApplicationUtils.getUser());
		if (purchase.getCreateTime() == null)
			purchase.setCreateTime(new Date());
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

	public Purchase getPurchase() {
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
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
	
	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public PurchaseService getPurchaseService() {
		return purchaseService;
	}	
	
	public ContacterService getContacterService() {
		return contacterService;
	}

	@Resource
	public void setContacterService(ContacterService contacterService) {
		this.contacterService = contacterService;
	}
	
	@Resource
	public void setPurchaseService(PurchaseService purchaseService) {
		this.purchaseService = purchaseService;
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
	
	 private SiteFileService siteFileService;
	 @Resource
	public void setSiteFileService(SiteFileService siteFileService) {
		this.siteFileService = siteFileService;
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
	

}
