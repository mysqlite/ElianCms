package com.elian.cms.admin.data.exception;

public class TemplateNotFindException extends RuntimeException {
	private static final long serialVersionUID = -3774906931201535718L;
	
	public static final String MESSAGE = "栏目没有配置模板!";

	public TemplateNotFindException() {
		super(MESSAGE);
	}

	public TemplateNotFindException(String s) {
		super(s);
	}

	public TemplateNotFindException(String message, Throwable cause) {
		super(message, cause);
	}

	public TemplateNotFindException(Throwable cause) {
		super(cause);
	}
}
