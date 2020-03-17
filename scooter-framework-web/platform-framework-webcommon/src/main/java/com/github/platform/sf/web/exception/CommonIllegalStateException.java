package com.github.platform.sf.web.exception;

/**
 * 通用 状态异常
 * @author 张剑军
 * @Description
 * @date 2019/1/8 11:45
 * @Copyright: ©China software and Technical service Co.Ltd
 **/
public class CommonIllegalStateException extends AppException {

    public CommonIllegalStateException(ExceptionMsg type) {
        super(type);
    }
}
