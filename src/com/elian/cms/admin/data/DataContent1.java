package com.elian.cms.admin.data;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataContent1 {
	private String id;
	private String title;
	private String path;
	private String detial;
	private String imgPath;
	private String time;
	private String param1;
	private String param2;
	private String param3;
	private String param4;
	// private Object obj;
	private Object entity;
	
	public DataContent1(String path,Object entity) {
		this.entity=entity;
		this.path=path;
	}
	
	public DataContent1(String title,String path,Object entity) {
		this.title=title;
		this.path=path;
		this.entity=entity;
	}
	
	public DataContent1(String title, String path, String imgPath) {
		this.title = title;
		this.path = path;
		this.imgPath = imgPath;
	}

	public DataContent1(String title, String detial, String path, String imgPath) {
		this.title = title;
		this.path = path;
		this.detial = detial;
		this.imgPath = imgPath;
	}
	
	public DataContent1(String title, String detial, String path, String imgPath,Object entity) {
		this.title = title;
		this.path = path;
		this.detial = detial;
		this.imgPath = imgPath;
		this.entity=entity;
	}

	public DataContent1(String title, String detial, String path,
			String imgPath, Date date) {
		this.title = title;
		this.path = path;
		this.detial = detial;
		this.imgPath = imgPath;
		setTime(date);
	}

	public DataContent1(int id, String title, String detial, String path,
			String imgPath, Date date) {
		this.id = id + "";
		this.title = title;
		this.path = path;
		this.detial = detial;
		this.imgPath = imgPath;
		setTime(date);
	}

	public DataContent1(String id, String title, String path, String detial,
			String imgPath, Date date, String param1, String param2) {
		super();
		this.id = id;
		this.title = title;
		this.path = path;
		this.detial = detial;
		this.imgPath = imgPath;
		setTime(date);
		this.param1 = param1;
		this.param2 = param2;
	}
	public DataContent1(String id, String title, String path, String detial,
			String imgPath, Date date, String param1, String param2,Object obj) {
		super();
		this.id = id;
		this.title = title;
		this.path = path;
		this.detial = detial;
		this.imgPath = imgPath;
		setTime(date);
		this.param1 = param1;
		this.param2 = param2;
		this.entity=obj;
	}
	
	public DataContent1(String id, String title, String path, String detial,
			String imgPath,Date date, String param1, String param2,
			String param3, String param4) {
		super();
		this.id = id;
		this.title = title;
		this.path = path;
		this.detial = detial;
		this.imgPath = imgPath;
		setTime(date);
		this.param1 = param1;
		this.param2 = param2;
		this.param3 = param3;
		this.param4 = param4;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDetial() {
		return detial;
	}

	public void setDetial(String detial) {
		this.detial = detial;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getTime() {
		return time;
	}

	public void setTime(Date date) {
		this.time = new SimpleDateFormat("[yyyy-MM-dd]").format(date);
	}

	public String getParam1() {
		return param1;
	}

	public String getParam2() {
		return param2;
	}
	
	public String getParam3() {
		return param3;
	}

	public String getParam4() {
		return param4;
	}

	public void setParam3(String param3) {
		this.param3 = param3;
	}

	public void setParam4(String param4) {
		this.param4 = param4;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}

	public String toString() {
		return "[id=" + id + ";title=" + title + ";path=" + path + ";imaPath="
				+ imgPath +";param1="+param1+";param2="+param2+";param3="+param3+";param4="+param4+";entity"+entity+ "]";
	}
}