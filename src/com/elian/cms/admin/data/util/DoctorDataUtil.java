package com.elian.cms.admin.data.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.data.Data1;
import com.elian.cms.admin.data.DataContent1;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Doctor;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.service.DoctorService;
import com.elian.cms.admin.service.SiteService;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.FreemarkerCodes;
import com.elian.cms.syst.util.SpringUtils;
import com.elian.cms.syst.util.StringUtils;

public class DoctorDataUtil extends BaseDataUtil {
	protected DoctorService doctorService = null;
	protected SiteService siteService=null;
	
	public DoctorDataUtil() {
		doctorService = (DoctorService) SpringUtils.getEntityService(Doctor.class);		
		siteService = (SiteService) SpringUtils.getEntityService(Site.class);		
	}
	
	@Override
	public Data1 getListDatas(int contentSize, int imgSize,
			boolean isContentDetial) {
		Data1 data = createData(contentSize,imgSize,isContentDetial);
		return data;
	}

	@Override
	public Data1 getSpageDatas(Boolean hasImg) {
		Data1 data = new Data1();
		Doctor doctor = doctorService.findStaticSpageData(site.getId(), channel.getId(),hasImg);
		if (doctor == null)
			return null;
		data.setChannelName(channel.getChannelName());
		data.setPath(getPath(doctor.getId(), true));
		data.setContentList(getSpageDataContent(doctor));
		return data;
	}

	private Data1 createData(int contentSize, int imgListSize,
			boolean isContentDetial) {
		if (channel == null)
			return null;
		Data1 data = new Data1();
		List<Doctor> imgList = getImgContentList(imgListSize);
		List<Content> contentList=getContentList(imgList,contentSize,imgListSize);
		
		if(imgList==null && contentList==null) return null;
		//获取栏目路径
		String channelPath=null;
		if(!CollectionUtils.isEmpty(contentList)){
			channelPath=getPath(contentList.get(0).getEntityId(),true);
		}else if(!CollectionUtils.isEmpty(imgList)){
			channelPath=getPath(imgList.get(0).getId(),true);
		}else{
			channelPath=getPath(null,true);
		}
		
		// 填充data
		data.setPath(channelPath);
		data.setChannelName(channel.getChannelName());
		data.setImgContentList(getDataContentFromImgList(imgList));
		data.setContentList(getDataContentFromcontList(contentList,isContentDetial));
		return data;
	}
	/**
	 * @param isContentDetial 该参数暂时没用
	 */
	private List<DataContent1> getDataContentFromcontList(
			List<Content> contentList, boolean isContentDetial) {
		if (CollectionUtils.isEmpty(contentList)) {
			return null;
		}			
		ArrayList<DataContent1> dataContentList = new ArrayList<DataContent1>();
		DataContent1 dataContent1=null;
		for (Content c : contentList) {		
			String path = getPath(c.getEntityId(),false);
			Doctor doct=doctorService.get(c.getEntityId());
			if(null != doct){
				dataContent1=new DataContent1(doct.getId().toString(),doct.getDoctName(),path,doct.getSpeciality(),getImgPath(doct.getDoctImg())
						,doct.getCreateTime(),doct.getDept().getHospital().getHospName(),doct.getDept().getDeptName(),doct.getJobTitle(),null);
				dataContent1.setEntity(doct);
				dataContentList.add(dataContent1);					
				
			}else{
				dataContent1=new DataContent1(c.getTitle(),null, path, "#",c.getCreateTime());
				dataContent1.setEntity(doct);
				dataContentList.add(new DataContent1(c.getTitle(),null, path, "#",c.getCreateTime()));			
			}			
		}
		return dataContentList;
	}

	private List<Content> getContentList(List<Doctor> imgList, int contentSize,
			int imgListSize) {
		if(0==contentSize || contentSize==imgListSize) return null;
		// 从控制表中取出前n条记录（可能不包含有图片的）
		List<Content> contentList = contentService.findStaticPages(channel.getId(), ApplicationUtils
				.getSite().getId(), contentSize);
		// 从内容列表中去掉在图片列表中的数据
		checkRepeat(imgList, contentList, contentSize,imgListSize);
		if (CollectionUtils.isEmpty(contentList))
			return null;
		return contentList;
	}

	/*
	 * 获取显示图片的list 大小由size决定
	 */
	private List<Doctor> getImgContentList(int size) {
		if (size == 0)
			return null;
		// 从医生表中取出前几条有图片的记录
		List<Doctor> doctorList = doctorService.findStaticImgList(site.getId(),channel.getId(), size);
		if (CollectionUtils.isEmpty(doctorList))
			return null;
		return doctorList;
	}

	/*
	 * 将Doctor List转换为DataContent1 List
	 */
	private ArrayList<DataContent1> getDataContentFromImgList(
			List<Doctor> imgDoctortList) {
		if (CollectionUtils.isEmpty(imgDoctortList)) {
			return null;
		}
		ArrayList<DataContent1> dataContentList = new ArrayList<DataContent1>();
		for (Doctor d : imgDoctortList) {				
			String path = getPath(d.getId(),false);
			DataContent1 dataContent1 = new DataContent1(d.getId().toString(),d.getDoctName(),path,d.getSpeciality(),getImgPath(d.getDoctImg())
					,d.getCreateTime(),d.getDept().getHospital().getHospName(),d.getDept().getDeptName(),d.getJobTitle(),null);
			dataContent1.setParam1(d.getJobTitle());
			dataContent1.setEntity(d);
			dataContentList.add(dataContent1);
		}
		return dataContentList;
	}

	private List<DataContent1> getSpageDataContent(Doctor doctor) {
		ArrayList<DataContent1> dataContentList = new ArrayList<DataContent1>();
		String path = getPath(doctor.getId(),false);
		DataContent1 dataContent1 = new DataContent1(doctor.getDoctName(),
				doctor.getSpeciality(), path, getImgPath(doctor.getDoctImg()));
		dataContent1.setParam1(doctor.getJobTitle());
		dataContentList.add(dataContent1);
		return dataContentList;
	}
	
	public String getPath(Integer entityId, boolean isChannelPath) {
		if (!ElianUtils.COMP_TYPE_HOSP.equals(site.getComType())) {
			return getReferencePath(entityId);
		}
		return super.getPath(entityId, isChannelPath);
	}

	private String getReferencePath(Integer entityId) {		
		Site site=siteService.findByByComp(ElianUtils.COMP_TYPE_HOSP,
				doctorService.get(entityId).getDept().getHospital().getId());
		if(site==null) return "#";
		Content content=contentService.getByEntityId(site,Doctor.class, entityId);
		if(content==null) return "#";
		Channel channel=content.getChannel();
		StringBuffer sitePath=new StringBuffer();
		if(StringUtils.isNotBlank(site.getAlias())){
			if(site.getAlias().startsWith("http://"))
				sitePath.append(site.getAlias());
			else
				sitePath.append("http://hosp").append(FreemarkerCodes.DOMAIN_SUFFIX)
				.append(ElianCodes.SPRIT).append(site.getId());
				
		}
		StringBuffer path=sitePath.append(channel.getPath())
				.append(ElianCodes.SPRIT).append(content.getId()).append(ElianCodes.SUFFIX_SHTML);
		return path.toString();
	}
}
