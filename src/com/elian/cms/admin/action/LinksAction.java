package com.elian.cms.admin.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Links;
import com.elian.cms.admin.service.SiteFileService;
import com.elian.cms.admin.service.LinksService;
import com.elian.cms.admin.service.UserService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.SpringUtils;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.ValidateUtils;

/**
 * 友情链接
 * 
 * @author Gechuanyi
 * @version 0
 */
@Component
@Scope("prototype")
public class LinksAction extends BaseAction {
	private static final long serialVersionUID = -7124134287126165774L;
	private Integer id = Integer.valueOf(0);
	private boolean edit;
	private Links links;
	/** 树节点传递过来的是否叶子节点 */
	private boolean leaf = false;
	/** 树节点传递过来的栏目ID */
	private Integer channelId = Integer.valueOf(0);
	/** 树节点传递过来的action名称 */
	private String action;
	/** 内容状态 */
	private Integer status = Integer.valueOf(1);
	private LinksService linksService;
	private SiteFileService siteFileService;
	
	public String list() {
		return LIST;
	}

	public String edit() {
		if (!edit)
			createLinks();
		else {
			links=linksService.get(id);
		}
		return EDIT;
	}

	public String show() {
		if (id > 0) {
			links = linksService.get(id);
		}
		return SHOW;
	}
	
	private void createLinks() {
		links = new Links();
		links.setCreater(ApplicationUtils.getUser());
		links.setCreateTime(new Date());
	}

	public void delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (CollectionUtils.isEmpty(idList))
			return;
		List<Links> linksList = linksService.findByContentId(idList,ApplicationUtils.getSite().getId());
		if (CollectionUtils.isEmpty(linksList))
			return;
		for (Links links : linksList) {
			linksService.delete(links);
		}
	}
	
	public String save() {
		links.setActionName(action);
		links.setChannelId(channelId);
		links.setContentStatus(status);
		if (links.getCreater().getId() != null) {
			links.setCreater(((UserService) SpringUtils.getBean("userService")).get(links.getCreater().getId()));
		}
		Integer controlId=0;
		if(isPublish())
			controlId=linksService.save(links, edit, isPublish());
		else
		    linksService.save(links,edit);
		if(controlId!=0)
			setControlId(controlId);
		siteFileService.saveFileToFtp(links, getPrevFile(), links.getLogoImg());
		return SAVE;
	}
	
	public void validateSave() {
		if (StringUtils.isBlank(links.getTitle()))
			this.addFieldError("links.title", "请填写链接标题");
		else {
			if (ValidateUtils.isLengOut(links.getTitle(), 50))
				this.addFieldError("links.title", "链接标题长度在50字以内");
		}
		if(ValidateUtils.isLengOut(links.getLinkUrl(), 255))
			this.addFieldError("links.linkUrl", "链接Url长度在50字以内");
		if(ValidateUtils.isLengOut(links.getLogoImg(), 200))
			this.addFieldError("links.logoImg", "logo图片长度在200字以内");
		if(ValidateUtils.isLengOut(links.getDescription(), 255))
			this.addFieldError("links.description", "简要描述在255字以内");
		if(StringUtils.isNotBlank(links.getEmail())) {
    		if(!ValidateUtils.isEmail(links.getEmail(),5,95))
    			this.addFieldError("links.email", "邮箱格式不正确");
		}
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

	public Links getLinks() {
		return links;
	}

	public void setLinks(Links links) {
		this.links = links;
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

	@Resource
	public void setSiteFileService(SiteFileService siteFileService) {
		this.siteFileService = siteFileService;
	}

	@Resource
	public void setLinksService(LinksService linksService) {
		this.linksService = linksService;
	}
	
}
