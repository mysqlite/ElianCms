package com.elian.cms.admin.data;

import java.util.List;
//一个栏目信息
public class Data1 {
	private String parentChannelName;
	private String parentPath;//更多 信息路径
	private String channelName;//栏目名称
	private String path;//更多 信息路径
	private List<DataContent1> imgContentList;//图片的集合
	private List<DataContent1> contentList;//内容的集合
	private List<DataContent1> videoList;//视频的集合[仅用于资讯]
	
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}			

	public List<DataContent1> getImgContentList() {
		return imgContentList;
	}

	public void setImgContentList(List<DataContent1> imgContentList) {
		this.imgContentList = imgContentList;
	}

	public List<DataContent1> getContentList() {
		return contentList;
	}

	public void setContentList(List<DataContent1> contentList) {
		this.contentList = contentList;
	}

	public String getParentChannelName() {
		return parentChannelName;
	}

	public void setParentChannelName(String parentChannelName) {
		this.parentChannelName = parentChannelName;
	}

	public String getParentPath() {
		return parentPath;
	}

	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}
	
	public List<DataContent1> getVideoList() {
		return videoList;
	}

	public void setVideoList(List<DataContent1> videoList) {
		this.videoList = videoList;
	}

	public String toString() {
		return "[channelName="+channelName+";path="+path+";parentPath:"+parentPath+";parentChannelName:"+parentChannelName+";imgContentList="
			+(null==imgContentList?"":imgContentList.toString())+";contentList="+(null==contentList?"":contentList.toString())+"]";
	}
}