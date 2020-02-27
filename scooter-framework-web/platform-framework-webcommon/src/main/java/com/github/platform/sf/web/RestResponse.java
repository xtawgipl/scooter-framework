package com.github.platform.sf.web;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.platform.sf.common.BaseConstants;
import com.github.platform.sf.common.ExceptionMsgConstant;
import com.github.platform.sf.web.exception.AppException;
import com.github.platform.sf.web.exception.BaseExceptionMsg;
import com.github.platform.sf.web.exception.ExceptionMsg;
import lombok.Getter;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * @author zhangjj
 * @description:统一返回消息体
 * @date 2018/2/7
 **/
@Getter
public class RestResponse<T, E extends Throwable> {
    private int ret;
    private String msg;
    private T data;
    
    @JSONField(serialize =false)
    private E errData;

    public RestResponse() {
        this.ret = 0;
        this.msg = ExceptionMsgConstant.OK_MSG;
    }

    public RestResponse(int ret, String msg, T data, E errData) {
        this.ret = ret;
        this.msg = msg;
        this.data = data;
        this.errData = errData;
    }

    /**
     * 构造正常返回
     *
     * @param data
     * @return
     */
    public static <T, E extends Throwable> RestResponse<T, E> ok(T data) {
        return new RestResponseBuilder()
                .withReturnCode(0)
                .msg(ExceptionMsgConstant.OK_MSG)
                .data(data)
                .build();
    }

    /**
     * 构造正常返回
     *
     * @return
     */
    public static RestResponseBuilder ok() {
        return new RestResponseBuilder()
                .withReturnCode(0)
                .msg(ExceptionMsgConstant.OK_MSG);
    }

    /**
     * 构造业务异常返回
     * @param ret 返回状态码
     * @param msg 错误描述
     * @param data 错误数据
     * @return
     */
    public static <T, E extends Throwable> RestResponse<T, E> fail(Integer ret, String msg, T data,E err) {
        if(ret == null) {
            ret = -1;
        }
        if(msg == null) {
            msg = ExceptionMsgConstant.FAIL_MSG;
        }
        return new RestResponseBuilder()
                .withReturnCode(ret)
                .msg(msg)
                .data(data)
                .errData(err)
                .build();
    }

    public static <T, E extends Throwable> RestResponse<T, E> fail(String msg, E err) {

        return fail(null, msg, null,err);
    }

    public static <T, E extends Throwable> RestResponse<T, E> fail(String msg) {
        return fail(null, msg, null,null);
    }

    public static <T, E extends Throwable> RestResponse<T, E> fail() {
        return fail(null, null, null,null);
    }

    /**
     * 构造应用异常返回
     *
     *  异常
     * @return
     */
    public static <T, E extends Throwable> RestResponseBuilder<T, E> createAppErrorRespBuilder(E exception) {

        if(exception instanceof AppException){
            AppException appException = (AppException) exception;
            return new RestResponseBuilder()
                    .withReturnCode(appException.getExceptionType().getCode())
                    .errData(exception)
                    .msg(appException.getExceptionType().getMsg());
        }else if (exception instanceof MethodArgumentNotValidException){
			MethodArgumentNotValidException methodExp = (MethodArgumentNotValidException) exception;
			String errMsg = methodExp.getBindingResult().getAllErrors().get(0).getDefaultMessage();
			 return new RestResponseBuilder()
	                    .withReturnCode(BaseExceptionMsg.PARAS_FORMAT_ERROR.getCode())
	                    .errData(exception)
	                    .msg(errMsg);
        }else{
            return new RestResponseBuilder()
                    .withReturnCode(BaseExceptionMsg.SYSTEM_ERROR.getCode())
                    .errData(exception)
                    .msg(exception.getMessage());
        }
    }

    @JsonIgnore
    public final boolean isOk() {
        return this.ret == 0;
    }

    public static final RestResponseBuilder create() {
        return new RestResponseBuilder();
    }

    public String concatRetMsg() {
        return new StringBuilder()
                .append(this.ret)
                .append(BaseConstants.HORIZONTAL_LINE)
                .append(this.msg)
                .toString();
    }

    public static class RestResponseBuilder<T, E extends Throwable> {
        private int ret;
        private String msg;
        private T data;
        private E errData;

        private RestResponseBuilder withReturnCode(int ret) {
            Assert.notNull(ret, "return code cannot be null");
            this.ret = ret;
            return this;
        }

        public RestResponseBuilder msg(String msg) {
            this.msg = msg;
            return this;
        }

        public RestResponseBuilder data(T data) {
            this.data = data;
            return this;
        }

        public RestResponseBuilder errData(E errData) {
            this.errData = errData;
            return this;
        }

        public RestResponse<T, E> build() {
            if (StringUtils.isEmpty(this.msg)) {
                if (!ObjectUtils.isEmpty(errData)) {
                    this.msg = ExceptionMsgConstant.FAIL_MSG;
                } else {
                    this.msg = ExceptionMsgConstant.OK_MSG;
                }
            }
            return new RestResponse(ret, msg, data, errData);
        }
        
        public RestResponseBuilder msg(ExceptionMsg msg) {
        	this.ret=msg.getCode();
            this.msg = msg.getMsg();
            return this;
        }
        
    }

}
