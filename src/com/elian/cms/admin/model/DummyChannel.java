package com.elian.cms.admin.model;

import java.util.List;

/**
	 * 前台数据源虚拟Bean
	 */
	public class DummyChannel {
		/** 栏目名称 */
		private String channelName;
		/** 访问路径 */
		private String path;
		/** 栏目类型 */
		private String channelType;
		/**栏目ID*/
		private String id;
		/**栏目父ID*/
		private String pId;
		/**子类集合*/
		private  List<DummyChannel> chirds;
		
		
	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++) {
			for (int j = 0; j < args.length; j++) {
				
			}
		}
	}	
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

		public String getChannelType() {
			return channelType;
		}

		public void setChannelType(String channelType) {
			this.channelType = channelType;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getpId() {
			return pId;
		}

		public void setpId(String pId) {
			this.pId = pId;
		}
		
		public List<DummyChannel> getChirds() {
			return chirds;
		}

		public void setChirds(List<DummyChannel> chirds) {
			this.chirds = chirds;
		}

		public String toString() {
			return this.getClass().getName()+"[id="+id+"],[pid="+pId+"],[channelName="+channelName+"],[path="+path+"]";
		}
	}