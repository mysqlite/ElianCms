package com.elian.cms.reg.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.UserRegister;
import com.elian.cms.admin.service.UserRegisterService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.util.ApplicationUtils;
/**
 * 
 * @author Gechuanyi
 *
 */
@Component
@Scope("prototype")
public class RegConsumptionAction extends BaseAction{
	private static final long serialVersionUID = 1309191282601458195L;
	private Pagination<UserRegister> pagination=new Pagination<UserRegister>();
	private Integer id;
	private Integer consumption=0;
	private UserRegisterService userRegisterService;
	public String list() {
		if(ApplicationUtils.getUser()==null) {
			return "login";
		}
		userRegisterService.findByAll(pagination, null, ApplicationUtils
				.getUser().getId());
		if(!CollectionUtils.isEmpty( pagination.getList()))
		for (UserRegister ur : pagination.getList()) {
			consumption+=ur.getAmount();
		}
		return LIST;
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
	
	public Integer getConsumption() {
		return consumption;
	}

	public void setConsumption(Integer consumption) {
		this.consumption = consumption;
	}

	public static List<SelectItem> getPayTypeList() {
		List<SelectItem> itemList = new ArrayList<SelectItem>(2);
		itemList.add(new SelectItem(0, "网上支付"));
		itemList.add(new SelectItem(1, "线下支付"));
		return itemList;
	}

	@Resource
	public void setUserRegisterService(UserRegisterService userRegisterService) {
		this.userRegisterService = userRegisterService;
	}
}
