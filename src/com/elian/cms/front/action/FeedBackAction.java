package com.elian.cms.front.action;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.LeaveWord;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.service.LeaveWordService;
import com.elian.cms.admin.service.SiteService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.ValidateUtils;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 留言板s
 * @author thy
 */
@Component
@Scope("prototype")
public class FeedBackAction extends ActionSupport{
	private static final long serialVersionUID = 2610310520170069L;
	private LeaveWord leaveWord=null;
	
	private Map<String,String> filedError=new HashMap<String, String>();
	private LeaveWordService leaveWordService=null; //会话的list 仅在查看时使用
	
	private Pagination<LeaveWord> pagination=new Pagination<LeaveWord>();	
	private List<List<LeaveWord>> subList=new ArrayList<List<LeaveWord>>();
	
	private final String MSG_LIST="list";
	private final String MSG_ERROR="error";
	private final String MSG_PAGINATION="pagination";
	
	private SiteService siteService=null;
	
	public void list() {//页面加载
		pagination.setRowSize(5);
		if(leaveWord!=null && leaveWord.getSiteId()!=null){
			leaveWordService.findFrontList(leaveWord.getSiteId(),pagination);
			if(!CollectionUtils.isEmpty(pagination.getList())){
				orderSubList();
				JSONObject obj = new JSONObject();
				obj.put(MSG_LIST, subList);
				obj.put(MSG_PAGINATION, pagination);
				ApplicationUtils.sendJsonpObj(obj);		
			}
		}
	}

	public void save(){//保存
		validateData();
		if(filedError.size()>0){
			JSONObject obj = new JSONObject();
			obj.put(MSG_ERROR, filedError);
			ApplicationUtils.sendJsonpObj(obj);
			return;
		}
		
		leaveWord.setStatus(0);
		leaveWord.setCreateDate(new Date());
		leaveWordService.save(leaveWord);
		
		JSONObject obj = new JSONObject();
		obj.put("msg", "ok");
		ApplicationUtils.sendJsonpObj(obj);
		return;
	}

	public void report(){	//举报
		if(leaveWord!=null && leaveWord.getId()!=null){
			leaveWord=leaveWordService.get(leaveWord.getId());
			if(!leaveWord.getStatus().equals(ElianUtils.STATIC_STATUS_2)){
				leaveWord.setStatus(2);
				leaveWordService.save(leaveWord);
			}
		}
	}

	public void asked() { // 追问
		//System.out.println("请求来了！");
		LeaveWord temp =validateAsked();
		if(!CollectionUtils.isEmpty(filedError)){
			JSONObject obj = new JSONObject();
			obj.put("error", filedError);
			//System.out.println("验证失败："+filedError);
			ApplicationUtils.sendJsonpObj(obj);
			return;
		}
	
		leaveWord.setId(null);
		leaveWord.setParentId(temp.getId());
		leaveWord.setReply(false);
		leaveWord.setCreateDate(new Date());
		leaveWord.setEmail(temp.getEmail());
		leaveWord.setGender(temp.getGender());
		leaveWord.setPhoneNumber(temp.getPhoneNumber());
		leaveWord.setRealName(temp.getRealName());
		leaveWord.setSiteId(temp.getSiteId());
		leaveWord.setStatus(ElianUtils.CONTENT_STATUS_0);
		leaveWord.setTitle(leaveWord.getContent().length() > 120 ? leaveWord
						.getContent().substring(0, 120)
						: leaveWord.getContent());
		leaveWordService.save(leaveWord);
		//System.out.println("验证失败："+filedError);
		
		JSONObject obj = new JSONObject();
		obj.put("msg", "ok");
		ApplicationUtils.sendJsonpObj(obj);
		return;
	}

	public LeaveWord getLeaveWord() {
		return leaveWord;
	}

	public void setLeaveWord(LeaveWord leaveWord)  {
			this.leaveWord = leaveWord;
	}

	public Map<String, String> getFiledError() {
		return filedError;
	}

	public void setFiledError(Map<String, String> filedError) {
		this.filedError = filedError;
	}

	public List<List<LeaveWord>> getSubList() {
		return subList;
	}

	public void setSubList(List<List<LeaveWord>> subList) {
		this.subList = subList;
	}

	public Pagination<LeaveWord> getPagination() {
		return pagination;
	}

	public void setPagination(Pagination<LeaveWord> pagination) {
		this.pagination = pagination;
	}

	@Resource
	public void setLeaveWordService(LeaveWordService leaveWordService) {
		this.leaveWordService = leaveWordService;
	}
	
	private void validateData() {
		decodeLeaveWord(leaveWord);
		if(StringUtils.isBlank(leaveWord.getRealName())){
			this.filedError.put("realNameError", "姓名不能为空");
		}else {
			if (ValidateUtils.isLengOut(leaveWord.getRealName(), 16))
				this.filedError.put("realNameError", "真实名字在16字以内");
//			else if(!ValidateUtils.isChina(leaveWord.getRealName())){
//				this.filedError.put("realNameError", "请输入真实姓名");
//			}
		}
		
//		if(StringUtils.isBlank(leaveWord.getPhoneNumber())){
//			this.filedError.put("phoneNumberError", "请输入手机号码");
//		}else if(!ValidateUtils.isMobile(leaveWord.getPhoneNumber())){
//			this.filedError.put("phoneNumberError", "请输入正确的手机号码");
//		}
		/*Site site= siteService.get(leaveWord.getSiteId());
		if(ElianUtils.COMP_TYPE_HOSP.equals(site.getComType())){
    		if(StringUtils.isBlank(leaveWord.getEmail())){
    			this.filedError.put("emailError", "请输入邮箱号码");
    		}else if(!ValidateUtils.isEmail(leaveWord.getEmail(), 6, 40)){
    			this.filedError.put("emailError", "请输入正确的邮箱号码");
    		}
		}*/
		
		if(StringUtils.isBlank(leaveWord.getTitle())){
			this.filedError.put("titleError", "标题不能为空");
		}else if(ValidateUtils.isLengOut(leaveWord.getTitle(), 120)){
			this.filedError.put("titleError", "标题最大长度为120");
		}
		
		if(StringUtils.isBlank(leaveWord.getContent())){
			this.filedError.put("contentError", "内容不能为空");
		}else if(ValidateUtils.isLengOut(leaveWord.getContent(), 3500)){
			this.filedError.put("contentError", "内容最大长度为3500");
		}
	}
	
	private LeaveWord validateAsked() {
		decodeLeaveWord(leaveWord);
		LeaveWord temp =null;
		if (leaveWord == null) {
				filedError.put("idError", "失败，请尝试刷新页面重试！");
				//System.out.println("为空");
		}else{
			//System.out.println("不为空阿，id等于下面");
			//System.out.println(leaveWord.getId());
			//System.out.println("leaveWordService="+leaveWordService);
			//System.out.println("对象:"+leaveWordService.get(leaveWord.getId()));
			temp=leaveWordService.get(leaveWord.getId());
			
			//System.out.println("临时对象"+temp);
			//System.out.println("电话号码"+temp.getPhoneNumber());
			
			if(StringUtils.isBlank(leaveWord.getPhoneNumber())){
				filedError.put("phoneNumberError", "手机号码不能为空");
			}else if(!temp.getPhoneNumber().equals(leaveWord.getPhoneNumber()))
				filedError.put("phoneNumberError", "手机验证失败");
			
			if(StringUtils.isBlank(leaveWord.getContent())){
				this.filedError.put("contentError", "内容不能为空");
			}else if(ValidateUtils.isLengOut(leaveWord.getContent(), 3500)){
				this.filedError.put("contentError", "内容最大长度为3500");
			}
		}
		return temp;
	}

	private void orderSubList() {
		for(LeaveWord l:pagination.getList()){
			subList.add(leaveWordService.getFrontListByParent(l.getId(), l.getSiteId()));
			for(List<LeaveWord> list:subList){
				for(int i=0;i<list.size();i++){
					LeaveWord current=list.get(i);
					if(i+1==list.size()) break;
					LeaveWord next=list.get(i+1);
					if(!next.getParentId().equals(current.getId())){
						LeaveWord sub=null;
						for(int j=i+1;j<list.size();j++){
							if(list.get(j).getParentId().equals(current.getId())){
								sub=list.get(j);list.remove(j);
								list.add(i+1,sub);
							}
						}
					}
				}
			}
		}
	}
	
	private void decodeLeaveWord(LeaveWord l) {
		try{
			if(l==null) return ;
			if(StringUtils.isNotBlank(l.getRealName()))			
				l.setRealName(URLDecoder.decode(l.getRealName(),"utf-8"));
			if(StringUtils.isNotBlank(l.getTitle()))			
				l.setTitle(URLDecoder.decode(l.getTitle(),"utf-8"));
			if(StringUtils.isNotBlank(l.getContent()))			
				l.setContent(URLDecoder.decode(l.getContent(),"utf-8"));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Resource
	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}
}
