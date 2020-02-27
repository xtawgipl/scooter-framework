package com.github.platform.sf.web.exception;

/**
 * 异常基类
 * @author 张剑军
 * @Description
 * @date 2019/1/7 10:21
 **/
public abstract class AppException extends RuntimeException{

    private ExceptionMsg exceptionType;

    public AppException() {
    }
    
    public AppException(String msg) {
        super(msg);
    }
    
    public AppException(ExceptionMsg type) {
        super(type.getMsg());
        this.exceptionType = type;
    }

    public AppException(ExceptionMsg type, Throwable cause) {
        super(type.getMsg(), cause);
        this.exceptionType = type;
    }
    
    public AppException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AppException(Throwable cause) {
        super(cause);
    }

    public AppException(ExceptionMsg type, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(type.getMsg(), cause, enableSuppression, writableStackTrace);
        this.exceptionType = type;
    }

    public ExceptionMsg getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(ExceptionMsg exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String info(){

        return exceptionType.toString();
    }
}
