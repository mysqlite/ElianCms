package com.elian.cms.front.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.data.DataContent1;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Doctor;
import com.elian.cms.admin.service.ContentService;
import com.elian.cms.admin.service.DoctorService;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.FilePathUtils;
import com.elian.cms.syst.util.SpringUtils;
import com.elian.cms.syst.util.StaticPageUtils;
import com.elian.cms.syst.util.StringUtils;

@Component
public class HospitalDoctorUtil{
	private Integer siteId;
	private Integer channelId;
	private Integer departMentId;
	
	private DoctorService doctorService=null;
	private ContentService contentService=null;
	
	public HospitalDoctorUtil(){}
	
	public HospitalDoctorUtil(Integer siteId,Integer channelId,Integer departMentId){
		this.siteId=siteId;
		this.channelId=channelId;
		this.departMentId=departMentId;
		
		doctorService=SpringUtils.getBean("doctorService");
		contentService=SpringUtils.getBean("contentService");
	}
	
	/**
	 * 根据栏目id找到该栏目下所有的医生    未添加功能
	 */
	public void findByChannel(){
		
	}
	
	/**
	 * 根据科室的id找到该科室所有的医生
	 * @return 
	 */
	public List<DataContent1> findBydepartMent(){
		List<DataContent1> result=new ArrayList<DataContent1>();
		if(null==departMentId)	return result;
		List<Doctor> depaDocList=doctorService.findByPage(null,null,departMentId);
		removedontHasImgItem(depaDocList);
		List<Content> staticContentList=contentService.findBySite(siteId,depaDocList,Doctor.class,true);
		if(CollectionUtils.isEmpty(staticContentList)) return result;
		Iterator<Content> itor=staticContentList.iterator();
		while (itor.hasNext()) {
			Content content = itor.next();			
			Doctor doc=getFromListById(depaDocList, content.getEntityId());
			Channel channel=content.getChannel();
			getResult(result,doc,content,channel);
		}
		return result;
//		ApplicationUtils.sendJsonArray(result);
	}
	
	private void getResult(List<DataContent1> result, Doctor doc,
			Content content, Channel channel) {
		if(doc!=null&&content!=null&&channel!=null) {
		DataContent1 data=new DataContent1(doc.getDoctName(),getContentPath(content.getId(), channel),
				FilePathUtils.setOutFilePath(doc.getDoctImg()));
		result.add(data);	
		}else {
			System.out.println("空");
		}
	}

	private Doctor getFromListById(List<Doctor> doctorList,int docId){
		Iterator<Doctor> itor=doctorList.iterator();
		while (itor.hasNext()) {
			Doctor doc = itor.next();			
			if(doc.getId()==docId){
				itor.remove();
				return doc;
			}
		}
		return null;
	}
	
	private void removedontHasImgItem(List<Doctor> doctorList){
		Iterator<Doctor>  itor=doctorList.iterator();
		while (itor.hasNext()) {
			Doctor doc=itor.next();
			if(StringUtils.isBlank(doc.getDoctImg()))
				itor.remove();
		}
	}
	
	/*
	 * 获取内容的绝对路径
	 */
	public String getContentPath(int contentId,Channel channel) {
		return StaticPageUtils.getSiteUrl()+ channel.getPath()
				+ ElianCodes.SPRIT + contentId + ElianCodes.SUFFIX_SHTML;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Integer getDepartMentId() {
		return departMentId;
	}

	public void setDepartMentId(Integer departMentId) {
		this.departMentId = departMentId;
	}

	@Resource
	public void setDoctorService(DoctorService doctorService) {
		this.doctorService = doctorService;
	}
	
	@Resource
	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}
}
