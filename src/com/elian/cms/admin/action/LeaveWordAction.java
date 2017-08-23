package com.elian.cms.admin.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.LeaveWord;
import com.elian.cms.admin.service.LeaveWordService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.ValidateUtils;

/**
 * 留言板
 * 
 * @author thy
 * 
 */
@Component
@Scope("prototype")
public class LeaveWordAction extends BaseAction {
	private static final long serialVersionUID = 2610310520170069L;
	private Integer siteId;
	private LeaveWord leaveWord;
	private LeaveWord reply;
	private Integer status=0;
	private boolean isEdit=false;
	
	private Pagination<LeaveWord> pagination=new Pagination<LeaveWord>();	
//	private Pagination<LeaveWord> pagination=new Pagination<LeaveWord>(
//			SearchParamUtils.getContentConditionMap());	
	private List<LeaveWord> leaveWordList=null;
	
	private LeaveWordService leaveWordService=null; //会话的list 仅在查看时使用
	
	public String list() {
		if(null !=ApplicationUtils.getSite()) siteId=ApplicationUtils.getSite().getId();
		leaveWordService.findByStatus(siteId,status,pagination);
		return LIST;
	}
	
	/**
	 * 审核
	 * @return
	 */
	public String audit(){
		if(null==leaveWord.getId()){
			return list();
		}
		leaveWord=leaveWordService.get(leaveWord.getId());
		leaveWord.setStatus(ElianUtils.STATUS_4);
		leaveWordService.save(leaveWord);
		return list();
	}
	
	public String edit() {		//后台回复留言
		if(null==leaveWord.getId()){
			return list();
		}
		leaveWord=leaveWordService.get(leaveWord.getId());
		if(isEdit)	//如果是修改
			reply=leaveWordService.getReply(leaveWord.getId());
		return EDIT;
	}
	
	public String save(){
		if(null!=reply){
			if(!isEdit){
				initReply();
				leaveWordService.save(reply);
				leaveWord.setStatus(ElianUtils.STATUS_1);
				leaveWordService.save(leaveWord);
			}else{
				String content=reply.getContent();
				reply=leaveWordService.get(reply.getId());
				reply.setContent(content);
				leaveWordService.save(reply);
			}
		}
		return list();
	}
	
	public void delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (CollectionUtils.isEmpty(idList))
			return;
		leaveWordService.deleteAllSubList(idList,ApplicationUtils.getSite().getId());
	}
	
	public void forbidden(){ //禁用
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (CollectionUtils.isEmpty(idList))
			return;
		leaveWordService.channgeStatus(idList,ElianUtils.STATUS_3);
	}
	
	public void release(){ //禁用
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (CollectionUtils.isEmpty(idList))
			return;
		leaveWordService.channgeStatus(idList,ElianUtils.STATUS_1);
	}
	
	public String show(){
		if(null==leaveWord.getId()){
			return list();
		}
		leaveWordList=leaveWordService.getByParentId(leaveWord.getId(),
				siteId==null?ApplicationUtils.getSite().getId():siteId);
		return SHOW;
	}
	
	private void initReply(){
		leaveWord=leaveWordService.get(reply.getParentId());
		reply.setReply(true);
		reply.setCreateDate(new Date());
		reply.setEmail(leaveWord.getEmail());
		reply.setGender(leaveWord.getGender());
		reply.setPhoneNumber(leaveWord.getPhoneNumber());
		reply.setRealName(leaveWord.getRealName());
		reply.setSiteId(ApplicationUtils.getSite().getId());
		reply.setStatus(ElianUtils.STATUS_1);
		reply.setTitle(leaveWord.getTitle());
	}

	public Pagination<LeaveWord> getPagination() {
		return pagination;
	}

	public void setPagination(Pagination<LeaveWord> pagination) {
		this.pagination = pagination;
	}
	
	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public LeaveWord getLeaveWord() {
		return leaveWord;
	}

	public void setLeaveWord(LeaveWord leaveWord) {
		this.leaveWord = leaveWord;
	}

	public LeaveWord getReply() {
		return reply;
	}

	public void setReply(LeaveWord reply) {
		this.reply = reply;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public boolean isEdit() {
		return isEdit;
	}

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}

	@Resource
	public void setLeaveWordService(LeaveWordService leaveWordService) {
		this.leaveWordService = leaveWordService;
	}
	
	public void validateSave() {
		if(reply!=null){
			if(StringUtils.isBlank(reply.getContent())){
				this.addFieldError("reply.content", "回复不能为空！");
			}else if(ValidateUtils.isLengOut(reply.getContent(), 3500)){
				this.addFieldError("reply.content", "最大长度为3500");
			}
		}
	}

	public List<LeaveWord> getLeaveWordList() {
		return leaveWordList;
	}
}
