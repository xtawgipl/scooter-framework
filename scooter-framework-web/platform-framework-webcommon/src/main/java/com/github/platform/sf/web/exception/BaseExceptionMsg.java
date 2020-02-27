package com.github.platform.sf.web.exception;

import com.github.platform.sf.common.ExceptionMsgConstant;

public enum BaseExceptionMsg implements ExceptionMsg {

	SYSTEM_SUCCESS(0, ExceptionMsgConstant.OK_MSG),

	SYSTEM_FAILED(-1, ExceptionMsgConstant.FAIL_MSG),

	ENTITY_IS_EXISTS(100, ExceptionMsgConstant.ENTITY_IS_EXISTS),

	ENTITY_NOT_EXISTS(101, ExceptionMsgConstant.ENTITY_NOT_EXISTS),

	PARAS_IS_NULL(102, ExceptionMsgConstant.PARAS_IS_NULL),

	PARAS_FORMAT_ERROR(103,ExceptionMsgConstant.PARAS_FORMAT_ERROR),
	
	SYSTEM_ERROR (-1, ExceptionMsgConstant.SYSTEM_ERROR);

	private int code;
	private String msg;

	private BaseExceptionMsg(int code, String message) {
		this.code = code;
		this.msg = message;
	}

	public int getCode() {
		return this.code;
	}

	public String getMsg() {
		return this.msg;
	}
}
