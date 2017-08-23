package com.elian.cms.admin.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Company;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Department;
import com.elian.cms.admin.model.Hospital;
import com.elian.cms.admin.model.Information;
import com.elian.cms.admin.model.Instrument;
import com.elian.cms.admin.model.Medicine;
import com.elian.cms.admin.model.Model;
import com.elian.cms.admin.service.ChannelService;
import com.elian.cms.admin.service.CompanyService;
import com.elian.cms.admin.service.ContentService;
import com.elian.cms.admin.service.DepartmentService;
import com.elian.cms.admin.service.HospitalService;
import com.elian.cms.admin.service.InformationService;
import com.elian.cms.admin.service.InstrumentService;
import com.elian.cms.admin.service.MedicineService;
import com.elian.cms.admin.service.impl.StaticServiceImpl;
import com.elian.cms.front.action.HospitalDoctorUtil;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.FilePathUtils;
import com.elian.cms.syst.util.FreemarkerCodes;
import com.elian.cms.syst.util.SpringUtils;
import com.elian.cms.syst.util.StaticPageUtils;
import com.elian.cms.syst.util.StringUtils;

public class HospitalDataBean {
	/**
	 * 获取数据库导航下所有启用的子栏目
	 */
	public List<HospitalChannelData> getDbAllSubChannel(Channel channel,
			List<HospitalContentData> contentList,List<List<DataContent1>> specialContent) {
		List<Channel> subList = ((ChannelService) SpringUtils.getBean("channelService")).findAllSubByParentId(channel.getId(), ApplicationUtils.getSite().getId());
		if (subList == null)return null;
		List<HospitalChannelData> channelDataList = new ArrayList<HospitalChannelData>();
		for (Channel c : subList) {
			HospitalChannelData data = new HospitalChannelData();
			data.setChannelName(c.getChannelName());
			data.setParentId(c.getParentId());
			data.setId(c.getId());
			if (ElianUtils.CHANNEL_PARENT.equals(c.getChannelType()))
				data.setType(ElianUtils.CHANNEL_PARENT);
			else if (ElianUtils.CHANNEL_CONTENT.equals(c.getChannelType())) {
				if (ElianUtils.CONTENT_LIST.equals(c.getContentType())) {
					data.setType(ElianUtils.CONTENT_LIST);
					data.setTableUrl(StaticServiceImpl.getTableUrl(c));
				}else if(ElianUtils.CONTENT_SINGLE.equals(c.getContentType())) {
					data.setType(ElianUtils.CONTENT_SINGLE);
					List<Content> list = getDbContentList(c);
					if (list != null && list.size() == 1) {
						Content content = list.get(0);
						doSpecial(c,content,specialContent);
						contentList.add(createHospitalData(content,c));
						data.setContentId(content.getId().toString());
					}
				}
				data.setPathUrl(StaticServiceImpl.getTableUrl(c,
						"pageLevel", "list").concat("&siteUrl=").concat(
						StaticPageUtils.getSiteUrl()).concat("&companyType=")
						.concat(ApplicationUtils.getSite().getComType()));
			}else if(ElianUtils.CHANNEL_OUT.equals(c.getChannelType())) {
				data.setType(ElianUtils.CHANNEL_OUT);
				data.setPathUrl(c.getOutLinkUrl());
				System.out.println("==========数据已加入");
			}
			channelDataList.add(data);
		}
		return channelDataList;
	}
	
	private void doSpecial(Channel c, Content content,List<List<DataContent1>> specialContentList) {
		if(c.getModel().getContentModel().getActionName().contains("department")){
			HospitalDoctorUtil docUtil=new HospitalDoctorUtil(c.getSite().getId(),c.getId(),content.getEntityId());
			specialContentList.add(docUtil.findBydepartMent());
		}
	}

	private HospitalContentData createHospitalData(Content content,Channel c) {
		HospitalContentData data = new HospitalContentData();
		data.setContentId(content.getId().toString());
		data.setEntityId(content.getEntityId().toString());
		data.setTitle(content.getTitle());
		data.setCreater(content.getCreater());
		data.setCreateTime(new SimpleDateFormat("yyyy-MM-dd").format(content
				.getCreateTime()));
		data.setTableUrl(getTableUrl(c));
		if ("information_c".equals(content.getActionName())) {
			Information info = ((InformationService) SpringUtils
					.getBean("informationService")).get(content.getEntityId());
			if (info != null){
				data.setContent(FilePathUtils.setEditorOutPath(info.getContent()));
				data.setCreater(info.getAuthor());
				data.setEntity(info);
			}
		}else if("department_c".equals(content.getActionName())){
			Department depa=((DepartmentService)SpringUtils.
					getBean("departmentService")).get(content.getEntityId());
			if(depa!=null) {
				data.setContent(FilePathUtils.setEditorOutPath(depa.getDescription()));
				data.setEntity(depa);
			}
		}else if("hospital_c".equals(content.getActionName())){
			Hospital hospital=((HospitalService)SpringUtils.
    				getBean("hospitalService")).get(content.getEntityId());
    		if(hospital!=null) {
    			data.setContent(FilePathUtils.setEditorOutPath(hospital.getHospDesc()));
    			data.setEntity(hospital);
    		}
    	}else if("company_c".equals(content.getActionName())){
			Company company=((CompanyService)SpringUtils.
    				getBean("companyService")).get(content.getEntityId());
    		if(company!=null) {
    			data.setContent(FilePathUtils.setEditorOutPath(company.getFrontIntroduce()));
    			data.setEntity(company);
    		}
    	}else if("instrument_c".equals(content.getActionName())) {
    		Instrument instrument=((InstrumentService)SpringUtils.getBean("instrumentService")).get(content.getEntityId());
    		data.setContent(instrument.getFrontDesc());
    		data.setEntity(instrument);
    		data.setEntityType(StringUtils.getENL(instrument));
    	}else if("medicine_c".equals(content.getActionName())) {
    		Medicine medicine=((MedicineService)SpringUtils.getBean("medicineService")).get(content.getEntityId());
    		data.setContent(medicine.getFrontDesc());
    		data.setEntity(medicine);
    		data.setEntityType(StringUtils.getENL(medicine));
    	}
		return data;
	}
	
	/**
	 * 获取数据库所有状态为：通过的内容数据
	 */
	private List<Content> getDbContentList(Channel channel) {
		List<Content> contentList = ((ContentService) SpringUtils
				.getBean("contentService")).findLeafByStatus(channel
				.getId(), ElianUtils.CONTENT_STATUS_3, ApplicationUtils
				.getSite().getId());
		if (contentList == null)
			contentList = new ArrayList<Content>(0);
		return contentList;
	}
	
	
	/**
	 * 获取列表页数据链接
	 */
	public static String getTableUrl(Channel channel) {
		return getTableUrl(channel, getActionName(), getActionMethod(channel));
	}
	
	/**
	 * 获取列表页数据链接
	 */
	public static String getTableUrl(Channel channel, String actionName,
			String actionMethod) {
		return new StringBuilder().append(FreemarkerCodes.CMS_URL).append(
				ElianCodes.SPRIT).append("front/").append(actionName).append(
				"!").append(actionMethod).append(".action?siteId=").append(
				ApplicationUtils.getSite().getId()).append("&channelId=")
				.append(channel.getId()).append("&path=").append(
						StaticPageUtils.getSiteUrl()).append(channel.getPath())
				.toString();
	}
	/**
	 * 获取ActionName，组织类型的前四位+List，例如：hospList
	 */
	private static String getActionName() {
		return ApplicationUtils.getSite().getComType().substring(0, 4) + "List";
	}
	
	/**
	 * 获取ActionMethod名称，模型action的名称截取后两位，例如：job_c——>job
	 */
	private static String getActionMethod(Channel sub) {
		Model model = sub.getModel();
		if (model != null && model.getContentModel() != null) {
			String objName = model.getContentModel().getActionName();
			return objName.substring(0, objName.length() - 2);
		}
		return "";
	}
}