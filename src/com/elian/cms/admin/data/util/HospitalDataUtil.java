package com.elian.cms.admin.data.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.data.Data1;
import com.elian.cms.admin.data.DataContent1;
import com.elian.cms.admin.model.Area;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Hospital;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.model.Type;
import com.elian.cms.admin.service.AreaService;
import com.elian.cms.admin.service.HospitalService;
import com.elian.cms.admin.service.SiteService;
import com.elian.cms.admin.service.TypeService;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.FilePathUtils;
import com.elian.cms.syst.util.FreemarkerCodes;
import com.elian.cms.syst.util.SpringUtils;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.SysXmlUtils;

public class HospitalDataUtil extends BaseDataUtil {
	protected HospitalService hospitalService = null;
	private TypeService typeService = null;
	protected SiteService siteService = null;
	protected AreaService areaService = null;

	private String actionName = null;

	public HospitalDataUtil() {
		super();
		hospitalService = (HospitalService) SpringUtils
				.getEntityService(Hospital.class);
		typeService = (TypeService) SpringUtils.getEntityService(Type.class);
		siteService = (SiteService) SpringUtils.getEntityService(Site.class);
		areaService = (AreaService) SpringUtils.getEntityService(Area.class);
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
		Hospital hosp = hospitalService.findStaticSpageData(site.getId(),
				channel.getId(), hasImg);
		if (hosp == null)
			return null;
		data.setChannelName(channel.getChannelName());
		data.setPath(getPath(hosp.getId(), false));
		data.setContentList(getSpageDataContent(hosp));
		return data;
	}

	@Override
	public Data1 getTopHitsDatas(Integer size) {
		if (size == 0)
			return null;
		Data1 data = new Data1();
		List<Content> contentList = contentService.findTopHitsList(channel
				.getId(), site.getId(), size);
		ArrayList<DataContent1> list = getDataContentFromContList(contentList,
				true, true);
		data.setContentList(list);
		return data;
	}

	private Data1 createData(int contentListSize, int imgListSize,
			boolean isContDetial) {
		Data1 data = new Data1();
		if (channel == null)
			return data;
		// 获取内容列表和图片列表的值
		List<Hospital> imgList = getImgContentList(imgListSize);
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
		data.setContentList(getDataContentFromContList(contentList,
				isContDetial, false));
		return data;
	}

	private List<Hospital> getImgContentList(int size) {
		if (size == 0)
			return null;
		List<Hospital> imgList = hospitalService.findStaticImgList(
				site.getId(), channel.getId(), size);
		if (CollectionUtils.isEmpty(imgList))
			return null;
		return imgList;
	}

	private List<Content> getContentList(List<Hospital> imgList,
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
			List<Hospital> imgList, boolean isDynamicChannel) {
		if (CollectionUtils.isEmpty(imgList)) {
			return null;
		}
		ArrayList<DataContent1> dataContentList = new ArrayList<DataContent1>();
		for (Hospital hosp : imgList) {
			String path = getPath(hosp.getId(), false);
			String hospRank = null;
			if (null != hosp.getRank())
				hospRank = typeService.get(hosp.getRank()).getTypeName();
			dataContentList.add(new DataContent1(hosp.getId().toString(), hosp
					.getHospName(), path, hosp.getHospDesc(), getImgPath(hosp
					.getHospImg()), hosp.getCreateTime(), hospRank, hosp
					.getPhone(), hosp.getAddress(), hosp.getEmergencyPhone()));
		}
		return dataContentList;
	}

	private ArrayList<DataContent1> getSpageDataContent(Hospital hosp) {
		if (null == hosp)
			return null;

		ArrayList<DataContent1> dataContentList = new ArrayList<DataContent1>();
		String path = getPath(hosp.getId(), false);
		String hospRank = null;
		if (null != hosp.getRank())
			hospRank = typeService.get(hosp.getRank()).getTypeName();
		dataContentList.add(new DataContent1(hosp.getEmergencyPhone(), hosp
				.getHospName(), path, FilePathUtils.setOutFilePath(hosp
				.getHospImg()), hosp.getHospDesc(), hosp.getCreateTime(),
				hospRank, hosp.getPhone(), FilePathUtils.setOutFilePath(hosp
						.getMapImg()), hosp.getAddress()));
		Hospital h=new Hospital();
		BeanUtils.copyProperties(hosp,h);
		h.setHospImg(FilePathUtils.setOutFilePath(h.getHospImg()));
		dataContentList.add(new DataContent1(path, hosp));
		return dataContentList;
	}

	private ArrayList<DataContent1> getDataContentFromContList(
			List<Content> contentList, boolean isDetial,
			boolean isDynamicChannel) {
		if (CollectionUtils.isEmpty(contentList)) {
			return null;
		}
		ArrayList<DataContent1> dataContentList = new ArrayList<DataContent1>();
		for (Content c : contentList) {
			String path = null;
			if (isDynamicChannel)
				path = getDynamicPath(c.getEntityId());
			else
				path = getPath(c.getEntityId(), false);
			Hospital hosp = null;
			if (isDetial) {
				hosp = hospitalService.get(c.getEntityId());
			}
			if (null != hosp) {
				String hospRank = null;
				if (null != hosp.getRank())
					hospRank = typeService.get(hosp.getRank()).getTypeName();
				Area area = areaService.get(hosp.getAreaId());
				dataContentList.add(new DataContent1(hosp.getId().toString(),
						hosp.getHospName(), path, hosp.getHospDesc(), "#", c
								.getCreateTime(), hospRank, hosp.getPhone(),
						(area == null ? "全国" : area.getAreaName()), hosp
								.getEmergencyPhone()));
			}
			else {
				dataContentList.add(new DataContent1(c.getTitle(), "", path,
						"#", c.getCreateTime()));
			}
		}
		return dataContentList;
	}

	public String getPath(Integer entityId, boolean isChannelPath) {
		if (!ElianUtils.COMP_TYPE_HOSP.equals(site.getComType())) {
			return getReferencePath(entityId);
		}
		return super.getPath(entityId, isChannelPath);
	}

	private String getDynamicPath(int entityId) {
		if (actionName == null)
			actionName = SysXmlUtils.getContentActionName("contentType",
					Hospital.class.getSimpleName()).getValue();
		channel = channelService.findByEntityId(site.getId(), actionName,
				entityId);
		setChannel(channel);
		String path = getPath(entityId, false);
		return path;
	}

	private String getReferencePath(Integer entityId) {
		Site site = siteService.findByByComp(ElianUtils.COMP_TYPE_HOSP,
				entityId);
		if (site == null)
			return "#";
		if (StringUtils.isNotBlank(site.getAlias())) {
			if (site.getAlias().startsWith("http://"))
				return site.getAlias();
		}
		StringBuffer path = new StringBuffer("http://hosp").append(
				FreemarkerCodes.DOMAIN_SUFFIX).append(ElianCodes.SPRIT).append(
				site.getId());
		return path.toString();
	}

}
