package com.elian.cms.admin.data.exception;

public class LogException extends RuntimeException {
	private static final long serialVersionUID = -3774906931201535718L;
	
	public static final String MESSAGE = "日志保存出现严重错误";

	public LogException() {
		super(MESSAGE);
	}

	public LogException(String s) {
		super(s);
	}

	public LogException(String message, Throwable cause) {
		super(message, cause);
	}

	public LogException(Throwable cause) {
		super(cause);
	}
}
