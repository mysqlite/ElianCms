package com.elian.cms.admin.data.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.data.Data1;
import com.elian.cms.admin.data.DataContent1;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Information;
import com.elian.cms.admin.model.Medicine;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.service.MedicineService;
import com.elian.cms.admin.service.SiteService;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.FreemarkerCodes;
import com.elian.cms.syst.util.SpringUtils;
import com.elian.cms.syst.util.SysXmlUtils;

public class MedicineDataUtil extends BaseDataUtil {
	protected MedicineService medicineService;
	protected SiteService siteService;
	private String actionName=null;	
	
	public MedicineDataUtil() {
		super();
		medicineService = SpringUtils.getBean("medicineService");
		siteService =SpringUtils.getBean("siteService");
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
		Medicine med = medicineService.findStaticSpageData(site.getId(),
				channel.getId(), hasImg);
		if (med == null)
			return null;
		data.setChannelName(channel.getChannelName());
		data.setPath(getPath(med.getId(), false));
		data.setContentList(getSpageDataContent(med));
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
		List<Medicine> imgList = getImgContentList(imgListSize);
		List<Content> contentList = getContentList(imgList, contentListSize,
				imgListSize);
		if (imgList == null && contentList == null)
			return null;
		// 获取栏目路径
		String channelPath = null;
		if (!CollectionUtils.isEmpty(contentList)) {
			channelPath = getPath(contentList.get(0).getEntityId(), true);
		}
		else if (!CollectionUtils.isEmpty(imgList)) {
			channelPath = getPath(imgList.get(0).getId(), true);
		}
		else {
			channelPath = getPath(null, true);
		}

		// 填充data
		data.setPath(channelPath);
		data.setChannelName(channel.getChannelName());
		data.setImgContentList(getDataContentFromImgList(imgList, false));
		return data;
	}

	private List<Medicine> getImgContentList(int size) {
		if (size == 0) {
			return null;
		}
		List<Medicine> medList = medicineService.findStaticImgList(
				site.getId(), channel.getId(), size);
		if (CollectionUtils.isEmpty(medList))
			return null;
		return medList;
	}

	private List<Content> getContentList(List<Medicine> imgList,
			int contentListSize, int imgListSize) {
		if (0 == contentListSize || contentListSize == imgListSize)
			return null;

		List<Content> contentList = contentService.findStaticPages(channel
				.getId(), ApplicationUtils.getSite().getId(), contentListSize);
		// 从内容列表中去掉在图片列表中的数据
		checkRepeat(imgList, contentList, contentListSize, imgListSize);
		if (CollectionUtils.isEmpty(contentList))
			return null;
		return contentList;
	}

	private ArrayList<DataContent1> getDataContentFromImgList(
			List<Medicine> imgList, boolean isDynamicChannel) {
		if (CollectionUtils.isEmpty(imgList)) {
			return null;
		}
		ArrayList<DataContent1> dataContentList = new ArrayList<DataContent1>();
		for (Medicine medicine : imgList) {
			String path=null;
			if(isDynamicChannel)path=getDynamicPath(medicine.getId());
			else path=getPath(medicine.getId(), false);
			dataContentList.add(new DataContent1(path,medicine));
		}
		return dataContentList;
	}
	
	private String getDynamicPath(int entityId){
		if(actionName==null)
			actionName=SysXmlUtils.getContentActionName("contentType", Information.class.getSimpleName()).getValue();
		channel=channelService.findByEntityId(site.getId(), actionName, entityId);
		setChannel(channel);		
		String path=getPath(entityId,false);
		return path;
	}

	private ArrayList<DataContent1> getSpageDataContent(Medicine medicine) {
		if (null == medicine)
			return null;

		ArrayList<DataContent1> dataContentList = new ArrayList<DataContent1>();
		String path = getPath(medicine.getId(), false);
		DataContent1 d1 = new DataContent1(medicine.getCnName(), path, medicine
				.getFrontImg());
		d1.setEntity(medicine);
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
		Integer siteId=medicineService.get(entityId).getCompany().getId();
		Site site=siteService.findByByComp(ElianUtils.COMP_TYPE_COMP,siteId);
		if(site==null)		
			site=siteService.findByByComp(ElianUtils.COMP_TYPE_MEDICINE_COMP,siteId);
		if(site==null)	
			site=siteService.findByByComp(ElianUtils.COMP_TYPE_INSTRUMENT_COMP,siteId);
		
		if(site==null) return "#";
		Content content=contentService.getByEntityId(site,Medicine.class, entityId);
		if(content==null) return "#";
		Channel channel=content.getChannel();
		StringBuffer path=new StringBuffer("http://comp").append(FreemarkerCodes.DOMAIN_SUFFIX)
			.append(ElianCodes.SPRIT).append(site.getId()).append(channel.getPath())
				.append(ElianCodes.SPRIT).append(content.getId()).append(ElianCodes.SUFFIX_SHTML);
		return path.toString();
	}
}
