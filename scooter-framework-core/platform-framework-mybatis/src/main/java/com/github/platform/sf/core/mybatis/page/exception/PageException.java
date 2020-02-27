package com.github.platform.sf.core.mybatis.page.exception;

public class PageException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PageException() {
		super();
	}

	public PageException(String message) {
		super(message);
	}
	
	public PageException(Throwable e) {
		super(e);
	}
	
	public PageException(String message, Throwable e) {
		super(message, e);
	}
}
