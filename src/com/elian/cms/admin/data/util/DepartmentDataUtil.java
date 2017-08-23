package com.elian.cms.admin.data.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.data.Data1;
import com.elian.cms.admin.data.DataContent1;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Department;
import com.elian.cms.admin.service.DepartmentService;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.FilePathUtils;
import com.elian.cms.syst.util.SpringUtils;
import com.elian.cms.syst.util.StringUtils;

public class DepartmentDataUtil extends BaseDataUtil{
	protected DepartmentService departmentService=null;	
	
	public DepartmentDataUtil() {
		super();
		departmentService =(DepartmentService) SpringUtils.getEntityService(Department.class);
	}
	
	/**用于获取列表页数据*/
	@Override
	public Object getListDatas(int contentSize, int imgSize,
			boolean isContentDetial) {
		Data1 data = createData(contentSize,imgSize,isContentDetial,false);
		return data;
	}
	
	/** 用于获取单页的数据*/
	@Override
	public Data1 getSpageDatas(Boolean hasImg) {
		Data1 data=new Data1();	
		Department dept = departmentService.findStaticSpageData(site.getId(), channel
				.getId(),hasImg);
		if(null==dept) return null;
		data.setChannelName(channel.getChannelName());
		data.setPath(getPath(dept.getId(),false));
		data.setContentList(getSpageDataContent(dept));
		return data;
	}	
	
	private List<DataContent1> getSpageDataContent(Department dept) {
		ArrayList<DataContent1> dataContentList = new ArrayList<DataContent1>();
		Department d=new Department();
		BeanUtils.copyProperties(dept, d);
		d.setDeptImg(FilePathUtils.getImgPath(dept.getDeptImg()));
		String path = getPath(dept.getId(),false);
		DataContent1 dataContent1 = new DataContent1(d.getDeptName(),d.getShortDesc(),
				path,getImgPath(d.getDeptImg()),d);
		dataContentList.add(dataContent1);
		return dataContentList;
	}

	/**
	 * 用于获取列表页数据
	 * @param contentListSize 内容列表的大小      该数据取自内容表
	 * @param imgListSize  图片列表的大小		该数据取自T_department表
	 * @param isContDetial  是否获取内容详细信息
	 * @param isImgDetial  是否获取图片详细信息
	 * @return
	 */
	public Data1 getListDatas(int contentListSize, int imgListSize, boolean isContDetial,boolean isImgDetial) {
		Data1 data = createData(contentListSize,imgListSize,isContDetial,isImgDetial);
		return data;
	}
	
	/**
	 * 用于获取单页的数据
	 * @return
	 */
	public Data1 getSpageDatas() {
		Data1 data=new Data1();	
		Department departMent=departmentService.getSingleData(site, ApplicationUtils.getHospital(),
				channel, ElianUtils.STATUS_3, true,null);
		if(null==departMent) return null;
		
		data.setChannelName(channel.getChannelName());
		data.setImgContentList(getSpageDataContentFromDepartment(departMent));
		data.setPath(getPath(null,true));
		return data;
	}
	
	private List<DataContent1> getSpageDataContentFromDepartment(Department departMent) {
		if(null==departMent) return null;
		
		ArrayList<DataContent1> dataContentList = new ArrayList<DataContent1>();
		
		String path=getPath(departMent.getId(),true);
		
		dataContentList.add(new DataContent1(departMent.getDeptName(),StringUtils.replaceHtml(departMent.getDescription()),path,
				getImgPath(departMent.getDeptImg())));
		
		return dataContentList;
	}

	private Data1 createData(int contentListSize, int imgListSize,
			boolean isContDetial, boolean hasImg) {
		if(0==contentListSize && contentListSize==imgListSize) return null;
		Data1 data = new Data1();
		if (channel == null)
			return data;	
		
		//获取内容列表和图片列表的值
		List<Department> imgList=getImgList(imgListSize,true);
		List<Content> contentList=getContentList(imgList,contentListSize,imgListSize);		
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

		//填充data
		data.setPath(channelPath);
		data.setChannelName(channel.getChannelName());
		data.setImgContentList(getDataContentFromImgList(imgList));
		data.setContentList(getDataContentFromcontList(contentList,isContDetial));
		return data;
	}	

	/*
	 * 获取显示图片的list 大小由size决定
	 */
	private List<Department> getImgList(int size,Boolean hasImg) {	
		if(size==0) return null;		
		// 从information表中取出前几条有图片的记录//////
		List<Department>  imgList = departmentService.getListData(site,ApplicationUtils.getHospital(),
				channel,size,ElianUtils.STATUS_3, true, hasImg);		
		if (CollectionUtils.isEmpty(imgList))
			return null;
		return imgList;
	}
	
	/*
	 * 获取内容列表 大小由size决定
	 */
	private List<Content> getContentList(List<Department> imgList,
			int contentListSize,int imgListSize) {			
		if(0==contentListSize) return null;		
		if(0!=imgListSize && imgListSize>=contentListSize) return null; //保证contentList比imgList大
		
		// 从控制表中取出前10条记录（可能不包含有图片的）
		List<Content> contentList = contentService.findStaticPages(channel
				.getId(), ApplicationUtils
				.getSite().getId(), contentListSize);

		// 从内容列表中去掉在图片列表中的数据
		checkRepeat(imgList, contentList, contentListSize, imgListSize);
		if (CollectionUtils.isEmpty(contentList))
			return null;
		return contentList;
	}	
	
	/*
	 * 将Content List转换为DataContent1 List
	 */
	private List<DataContent1> getDataContentFromcontList(
			List<Content> contentList,boolean isDetial) {		
		if (CollectionUtils.isEmpty(contentList)) {
			return null;
		}			
		ArrayList<DataContent1> dataContentList = new ArrayList<DataContent1>();
		for (Content c : contentList) {		
			String path = getContentPath(c.getId());
			String detial=null;
			String imgPath=null;
			if(isDetial){
				Department d=departmentService.get(c.getEntityId());
				if(null != d){
					detial=StringUtils.replaceHtml(d.getDescription());					
					imgPath=d.getDeptImg();
				}
			}				
			dataContentList.add(new DataContent1(c.getTitle(),detial, path, getImgPath(imgPath),c.getCreateTime()));			
		}
		return dataContentList;
	}		
	
	/*
	 * 将img List转换为DataContent1 List
	 */
	private List<DataContent1> getDataContentFromImgList(List<Department> imgList) {
		if (CollectionUtils.isEmpty(imgList)) {
			return null;
		}			
		ArrayList<DataContent1> dataContentList = new ArrayList<DataContent1>();
		String path="";
		for (Department d : imgList) {
			path=getPath(d.getId(), false);
			dataContentList.add(new DataContent1(d.getDeptName(), path, FilePathUtils.getImgPath(d.getDeptImg())));
		}
		return dataContentList;
	}
}
