package com.elian.cms.admin.data.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.data.Data1;
import com.elian.cms.admin.data.DataContent1;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Information;
import com.elian.cms.admin.model.Instrument;
import com.elian.cms.admin.service.InstrumentService;
import com.elian.cms.admin.service.SiteService;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.SpringUtils;
import com.elian.cms.syst.util.SysXmlUtils;

public class InstrumentDataUtil extends BaseDataUtil {
	protected InstrumentService instrumentService;
	protected SiteService siteService;
	private String actionName=null;	
	public InstrumentDataUtil() {
		super();
		instrumentService =SpringUtils.getBean("instrumentService");
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
		Instrument ins = instrumentService.findStaticSpageData(site.getId(),channel.getId(), hasImg);
		if (ins == null)
			return null;
		data.setChannelName(channel.getChannelName());
		data.setPath(getPath(ins.getId(), false));
		data.setContentList(getSpageDataContent(ins));
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
		List<Instrument> imgList = getImgContentList(imgListSize);
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
			channelPath = getPath(imgList.get(0).getId(), false);
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

	private List<Instrument> getImgContentList(int size) {
		if (size == 0) {
			return null;
		}
		List<Instrument> insList = instrumentService.findStaticImgList(
				site.getId(), channel.getId(), size);
		if (CollectionUtils.isEmpty(insList))
			return null;
		return insList;
	}

	private List<Content> getContentList(List<Instrument> imgList,
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

	private ArrayList<DataContent1> getDataContentFromImgList(List<Instrument> imgList, boolean isDynamicChannel) {
		if (CollectionUtils.isEmpty(imgList)) {
			return null;
		}
		ArrayList<DataContent1> dataContentList = new ArrayList<DataContent1>();
		for (Instrument instrument : imgList) {
			String path=null;
			if(isDynamicChannel)path=getDynamicPath(instrument.getId());
			else path=getPath(instrument.getId(), false);
			dataContentList.add(new DataContent1(path,instrument));
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

	private ArrayList<DataContent1> getSpageDataContent(Instrument instrument) {
		if (null == instrument)
			return null;

		ArrayList<DataContent1> dataContentList = new ArrayList<DataContent1>();
		String path = getPath(instrument.getId(), false);
		DataContent1 d1 = new DataContent1(instrument.getCnName(), path, instrument
				.getFrontImg());
		d1.setEntity(instrument);
		dataContentList.add(d1);
		return dataContentList;
	}

}
