package com.elian.cms.admin.data.exception;

import com.elian.cms.admin.model.Channel;

public class TemplateConfigException extends RuntimeException {
	private static final long serialVersionUID = 3968832843126431176L;	
	private String Msg="栏目没有完全配置模板上的区域值";
	
	public TemplateConfigException() {
		super();
	}

	public TemplateConfigException(Channel channel) {
		Msg="请配置[ "+channel.getChannelName()+" ]栏目上的区域，再生成！";
	}
	
	public TemplateConfigException(String message, Throwable cause) {
		super(message, cause);
	}

	public TemplateConfigException(String message) {
		super(message);
	}

	public TemplateConfigException(Throwable cause) {
		super(cause);
	}

	public String getMessage() {		
		return Msg;
	}
}
