package com.elian.cms.admin.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Content;
import com.elian.cms.syst.model.BaseStaticPageData;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.StaticPageUtils;

public class MainNewsListSideData extends BaseStaticPageData {
	public Map<String, Object> getAllDataMap() {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("1", getDataMap1(223, 224));
		dataMap.put("2", getData2(29));
		dataMap.put("3", getSubListData3(210, 222));
		return dataMap;
	}

	private Map<String, List<DataContent1>> getDataMap1(Integer... channelIds) {
		Map<String, List<DataContent1>> dataMap1 = new LinkedHashMap<String, List<DataContent1>>(
				channelIds.length);
		List<Channel> list = channelService.get(Arrays.asList(channelIds));
		if (!CollectionUtils.isEmpty(list)) {
			for (Channel channel : list) {
				String navPath = getNavPath(channel, channelService);
				dataMap1.put(channel.getChannelName(), getDataContentList(
						channel, navPath, 9));
			}
		}
		return dataMap1;
	}

	private List<DataContent1> getDataContentList(Channel channel,
			String navPath, int size) {
		List<Content> contentList = contentService.findTopHitsList(channel.getId(),
				ApplicationUtils.getSite().getId(), size);
		List<DataContent1> dtList = new ArrayList<DataContent1>(9);
		if (CollectionUtils.isEmpty(contentList))
			return dtList;
		DataContent1 dt = null;
		for (Content c : contentList) {
			dt = new DataContent1(c.getTitle(), getContentPath(channel, c.getId().toString()), null);
			dtList.add(dt);
		}
		return dtList;
	}

	private DataContent1 getData2(Integer channelId) {
		Channel channel = channelService.get(channelId);
		DataContent1 data2 = null;
		if (channel != null) {
			data2 = new DataContent1(channel.getChannelName(), channel
					.getChannelTemp() == null ? ElianCodes.SHAFT
					: (StaticPageUtils.getSiteUrl() + channel.getChannelTemp()
							.getFileName()), null);
		}
		return data2;
	}

	private List<Data1> getSubListData3(Integer... channelIds) {
		List<Data1> dataList3 = new ArrayList<Data1>(channelIds.length);
		List<Channel> list = channelService.get(Arrays.asList(channelIds));
		if (!CollectionUtils.isEmpty(list)) {
			Data1 data3 = null;
			for (Channel channel : list) {
				data3 = new Data1();
				data3.setChannelName(channel.getChannelName());
				String navPath = getNavPath(channel, channelService);
				data3.setPath(getListPath(channel));
				data3.setContentList(getDataContentList(channel, navPath, 8));
				dataList3.add(data3);
			}
		}
		return dataList3;
	}
}
	

