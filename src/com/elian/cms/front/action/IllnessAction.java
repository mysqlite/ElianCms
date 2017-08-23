package com.elian.cms.front.action;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.BodyPosition;
import com.elian.cms.admin.model.Illness;
import com.elian.cms.admin.service.BodyPositionService;
import com.elian.cms.admin.service.IllnessService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.StringUtils;

@Component
@Scope("prototype")
public class IllnessAction extends BaseAction {
	private static final long serialVersionUID = -5570009052114373482L;
	private BodyPositionService bodyPositionService=null;
	private IllnessService illnessService=null;
	
	private Integer illId;
	private Illness illness=null;
	private String departmentName=null;
	
	//总站疾病大全列表页
	public void listJson(){
		if(StringUtils.isNotBlank(departmentName)){
			listByDepartmentJson();
			return;
		}
		List<BodyPosition> bodyPositionList=bodyPositionService.getAll();
		List<Object> list=new ArrayList<Object>(bodyPositionList.size());
		
		for(BodyPosition p:bodyPositionList){
			List<Illness> illnessList= illnessService.getByIds(p.getIllLink());
			if(!CollectionUtils.isEmpty(illnessList)){
				dto item=new dto(p.getBodyName(), illnessList);
				list.add(item);
			}
		}
		ApplicationUtils.sendJsonpList(list);
	}
	
	public void listByDepartmentJson(){
		List<Object> list=new ArrayList<Object>(1);
		List<Illness> illnessList= illnessService.getByDepartmentName(departmentName,false);
		if(!CollectionUtils.isEmpty(illnessList)){
			dto item=new dto(departmentName, illnessList);
			list.add(item);
		}
		ApplicationUtils.sendJsonpList(list);
	}

	
	public String detial(){
		if(illId!=null)
			illness=illnessService.get(illId);
		return SHOW;
	}
	
	@Resource
	public void setBodyPositionService(BodyPositionService bodyPositionService) {
		this.bodyPositionService = bodyPositionService;
	}

	@Resource
	public void setIllnessService(IllnessService illnessService) {
		this.illnessService = illnessService;
	}
	
	public class dto{
		private String name;
		private Object value;
		
		public dto(String name, Object value) {
			super();
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public Object getValue() {
			return value;
		}
		
		public void setValue(Object value) {
			this.value = value;
		}
	}

	public Integer getIllId() {
		return illId;
	}

	public void setIllId(Integer illId) {
		this.illId = illId;
	}

	public Illness getIllness() {
		return illness;
	}

	public void setIllness(Illness illness) {
		this.illness = illness;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		try {
			this.departmentName =URLDecoder.decode(departmentName,"utf-8");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
