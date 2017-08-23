package com.elian.cms.reg.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Area;
import com.elian.cms.admin.model.UserRegister;
import com.elian.cms.admin.service.AreaService;
import com.elian.cms.admin.service.UserRegisterService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.HibernateEagerLoadingUtil;
/**
 * 
 * @author Gechuanyi
 *
 */
@Component
@Scope("prototype")
public class RegNotesAction extends BaseAction{
	private static final long serialVersionUID = -6503238799085194761L;
	private Pagination<UserRegister> pagination=new Pagination<UserRegister>();
	private Integer id;
	private UserRegisterService userRegisterService;
	private AreaService areaService ;
	private String areaName="";
	
	public String list() {
		if(ApplicationUtils.getUser()==null) {
			return "login";
		}
		userRegisterService.findByAll(pagination,null, ApplicationUtils.getUser().getId());
		return LIST;
	}
	
	public void listJson() {
		List<SelectItem> itemList = new ArrayList<SelectItem>(2);
		itemList.add(new SelectItem(0, "网上支付"));
		itemList.add(new SelectItem(1, "线下支付"));
		JSONObject obj=new JSONObject();
		if(ApplicationUtils.getUser()==null) {
			obj.put("msg", "当前用户未登录,无法获取");
			obj.put("status",false);
		}
		else {
		   userRegisterService.findByAll(pagination,null, ApplicationUtils.getUser().getId());
		   HibernateEagerLoadingUtil.eagerLoadFiled(pagination.getList());
		   obj.put("page",pagination);
		   obj.put("status",true);
		   obj.put("item", itemList);
		}
		ApplicationUtils.sendJsonpObj(obj);
	}
	
	public void getJson() {
		List<SelectItem> itemList = new ArrayList<SelectItem>(2);
		itemList.add(new SelectItem(0, "网上支付"));
		itemList.add(new SelectItem(1, "线下支付"));
		JSONObject obj=new JSONObject();
		if(ApplicationUtils.getUser()==null) {
			obj.put("msg", "当前用户未登录,无法获取");
			obj.put("status",false);
		}
		else {
			UserRegister ur=userRegisterService.get(id);
			if(ur.getUser().getId().equals(ApplicationUtils.getUser().getId())) {
    			HibernateEagerLoadingUtil.eagerLoadFiled(ur);
    			obj.put("ur", ur);
    			obj.put("status",true);
    		    obj.put("item", itemList);
    		    obj.put("area", getAreaNames(ur.getSchedulingId().getDoctor().getDept().getHospital().getAreaId()));
			}
			else {
				obj.put("msg", "非当前用户记录,无法获取");
				obj.put("status",false);
			}
		}
		ApplicationUtils.sendJsonpObj(obj);
	}
	
	public Pagination<UserRegister> getPagination() {
		return pagination;
	}
	public void setPagination(Pagination<UserRegister> pagination) {
		this.pagination = pagination;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getAreaNames(Integer areaCode) {
		if(areaCode==null)return "";
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
	
	
	@Resource
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

	@Resource
	public void setUserRegisterService(UserRegisterService userRegisterService) {
		this.userRegisterService = userRegisterService;
	}
}
