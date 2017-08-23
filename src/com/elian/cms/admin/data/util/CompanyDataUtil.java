package com.elian.cms.admin.data.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.data.Data1;
import com.elian.cms.admin.data.DataContent1;
import com.elian.cms.admin.model.Area;
import com.elian.cms.admin.model.Company;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.service.AreaService;
import com.elian.cms.admin.service.CompanyService;
import com.elian.cms.admin.service.SiteService;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.FilePathUtils;
import com.elian.cms.syst.util.FreemarkerCodes;
import com.elian.cms.syst.util.SpringUtils;

public class CompanyDataUtil extends BaseDataUtil {
	protected CompanyService companyService = null;
	protected SiteService siteService=null;
	protected AreaService areaService=null;

	public CompanyDataUtil() {
		super();
		companyService = (CompanyService) SpringUtils.
				getEntityService(Company.class);
		siteService = (SiteService) SpringUtils
				.getEntityService(Site.class);	
		areaService = (AreaService) SpringUtils
			.getEntityService(Area.class);	
	}

	@Override
	public Object getListDatas(int contentSize, int imgSize,
			boolean isContentDetial) {
		Data1 data = createData(contentSize, imgSize, isContentDetial);
		return data;
	}

	@Override
	public Data1 getSpageDatas(Boolean hasImg) {
		Data1 data = new Data1();
		Company company = companyService.findStaticSpageData(site.getId(),
				channel.getId(),hasImg);
		if (company == null) return null;
		data.setChannelName(channel.getChannelName());
		data.setPath(getPath(company.getId(), false));
		data.setContentList(getSpageDataContent(company));
		return data;
	}

	@Override
	public Data1 getTopHitsDatas(Integer size) {
		return null;
	}

	private Data1 createData(int contentListSize, int imgListSize,
			boolean isContDetial) {
		Data1 data = new Data1();
		if (channel == null)
			return data;
		// 获取内容列表和图片列表的值
		List<Company> imgList = getImgContentList(imgListSize);
		List<Content> contentList = getContentList(imgList, contentListSize,
				imgListSize);
		if(imgList==null && contentList==null) return null;

		// 获取栏目路径
		String channelPath = null;
		if (!CollectionUtils.isEmpty(contentList)) {
			channelPath = getPath(contentList.get(0).getEntityId(), true);
		}
		else if (!CollectionUtils.isEmpty(imgList)) {
			channelPath = getPath(imgList.get(0).getId(), false);
		}
		else {
			channelPath = getPath(null, true);
		}
		

		// 填充data
		data.setPath(channelPath);
		data.setChannelName(channel.getChannelName());
		data.setImgContentList(getDataContentFromImgList(imgList, false));
		data.setContentList(getDataContentFromContList(contentList,isContDetial,false));
		return data;
	}

	private List<DataContent1> getDataContentFromContList(
			List<Content> contentList, boolean isDetial, boolean isDynamicChannel) {
		if (CollectionUtils.isEmpty(contentList)) {
			return null;
		}
		ArrayList<DataContent1> dataContentList = new ArrayList<DataContent1>();
		for (Content c : contentList) {
			String path = null;
//			if(isDynamicChannel) path=getDynamicPath(c.getEntityId());
//			else	
				path=getPath(c.getEntityId(), false);	
			Company comp=null;
			if (isDetial) {
				comp = companyService.get(c.getEntityId());
			}
			if (null != comp){
				//Area area=areaService.get(hosp.getAreaId());				
				dataContentList.add(new DataContent1(comp.getId().toString(), comp.getName(),
						path, null,getImgPath(comp.getCompanyImg()),comp.getCreateTime(),null,null,comp));
			}else{
				dataContentList.add(new DataContent1(c.getTitle(), "", path,
						"#", c.getCreateTime()));
			}
		}
		return dataContentList;
		
	}


	private List<Company> getImgContentList(int size) {
		if (size == 0)
			return null;
		List<Company> imgList = companyService.findStaticImgList(
				site.getId(), channel.getId(), size);
		if (CollectionUtils.isEmpty(imgList))
			return null;
		return imgList;
	}

	private List<Content> getContentList(List<Company> imgList,
			int contentListSize, int imgListSize) {
		if (0 == contentListSize || contentListSize==imgListSize)	return null;

		List<Content> contentList = contentService.findStaticPages(channel.getId(), ApplicationUtils.getSite().getId(), contentListSize);
		// 从内容列表中去掉在图片列表中的数据
		checkRepeat(imgList, contentList, contentListSize, imgListSize);
		if (CollectionUtils.isEmpty(contentList))
			return null;
		return contentList;
	}

	private ArrayList<DataContent1> getDataContentFromImgList(
			List<Company> imgList, boolean isDynamicChannel) {
		if (CollectionUtils.isEmpty(imgList)) {
			return null;
		}
		ArrayList<DataContent1> dataContentList = new ArrayList<DataContent1>();
		for (Company comp : imgList) {
			String path = getPath(comp.getId(),false);
			dataContentList.add(new DataContent1(comp.getId().toString(), comp.getName(),
					path, null,getImgPath(comp.getCompanyImg()),comp.getCreateTime(),null,null,comp));
		}
		
		return dataContentList;
	}

	private ArrayList<DataContent1> getSpageDataContent(Company company) {
		if (null == company)
			return null;

		ArrayList<DataContent1> dataContentList = new ArrayList<DataContent1>();
		String path = getPath(company.getId(),false);
		DataContent1 d1= new DataContent1(
				company.getName(), 
        		path,
        		FilePathUtils.setOutFilePath(company.getCompanyImg())
        		);
		d1.setEntity(company);
        dataContentList.add(d1);
		return dataContentList;
	}

	public String getPath(Integer entityId, boolean isChannelPath) {
		if (!site.getComType().startsWith(ElianUtils.COMP_TYPE_COMP)) {
			return getReferencePath(entityId);
		}
		return super.getPath(entityId, isChannelPath);
	}
	
	private String getReferencePath(Integer entityId) {		
		Site site=siteService.findByByComp(ElianUtils.COMP_TYPE_COMP,entityId);
		if(site==null)		
			site=siteService.findByByComp(ElianUtils.COMP_TYPE_MEDICINE_COMP,entityId);
		if(site==null)	
			site=siteService.findByByComp(ElianUtils.COMP_TYPE_INSTRUMENT_COMP,entityId);
		
		if(site==null) return "#";
		StringBuffer path=new StringBuffer("http://comp").append(FreemarkerCodes.DOMAIN_SUFFIX)
			.append(ElianCodes.SPRIT).append(site.getId());
		return path.toString();
	}

}
