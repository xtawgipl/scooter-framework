package com.github.platform.sf.web.exception;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.platform.sf.web.RestControllerWithAdvice;
import com.github.platform.sf.web.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * @author zhangjj
 * @description:统一消息/异常处理，如果你不希望被这个class拦截到你的exception，可以选择以下两种方式
 * 1、在自己的controller中定义@ExceptionHandler
 * 2、不要在自己的controller上使用@RestControllerWithAdvice
 * @date 2018/2/7
 **/
@RestControllerAdvice(annotations = RestControllerWithAdvice.class)
@Slf4j
public class GlobalExceptionHandler implements ResponseBodyAdvice {

    @ResponseBody
    @ExceptionHandler(value = {ServletRequestBindingException.class })
    public ResponseEntity<RestResponse> handleParameterException(ServletRequestBindingException e) {
        log.warn("", e);
        return new ResponseEntity(RestResponse.createAppErrorRespBuilder(e).build(), HttpStatus.BAD_REQUEST);
    }


    /**
     * Validation框架异常处理
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception,
                                                                 ServletWebRequest webRequest) {
        BindingResult result = exception.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        log.error(JSONArray.toJSONString(fieldErrors, SerializerFeature.PrettyFormat));
        return new ResponseEntity(RestResponse.createAppErrorRespBuilder(exception).build(), HttpStatus.BAD_REQUEST);
    }

    /**
     *
     * Validation框架异常处理
     * 参数异常
     *
     * @param
     * @author zhangjj
     * @Date 2018/6/1 16:22
     * @return
     * @exception
     *
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<RestResponse> bindExceptionExceptionHandler(BindException exception, ServletWebRequest webRequest) {
        StringBuffer sb = new StringBuffer();
        List<ObjectError> allErrors = exception.getAllErrors();
        StringBuffer errorMsg = new StringBuffer();
        for(ObjectError error : allErrors){
            sb.append(error.getDefaultMessage()).append("；");

            if(error instanceof FieldError){
                FieldError fieldError = (FieldError) error;
                errorMsg.append(fieldError.getField())
                        .append("[")
                        .append(fieldError.getRejectedValue())
                        .append("]")
                        .append(":");
            }
            errorMsg.append(error.getDefaultMessage()).append("；");
        }

        log.error(JSONArray.toJSONString(errorMsg, SerializerFeature.PrettyFormat));
        return new ResponseEntity(RestResponse.createAppErrorRespBuilder(exception).build(), HttpStatus.BAD_REQUEST);
    }

    /**
     *
     * Validation框架异常处理
     * 参数异常
     *
     * @param
     * @author zhangjj
     * @Date 2018/6/1 16:22
     * @return
     * @exception
     *
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<RestResponse> constraintViolationExceptionExceptionHandler(ConstraintViolationException exception, ServletWebRequest webRequest) {
        StringBuffer sb = new StringBuffer();
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        for(ConstraintViolation cons : constraintViolations){
            sb.append(cons.getMessage()).append("；");
            log.error(String.format("%s[%s]---> %s", cons.getPropertyPath().toString(), cons.getInvalidValue(), cons.getMessage()));
        }

        log.error(JSONArray.toJSONString(sb, SerializerFeature.PrettyFormat));
        return new ResponseEntity(RestResponse.createAppErrorRespBuilder(exception).build(), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(value = {AppException.class})
    public ResponseEntity<RestResponse> handleAppException(AppException e) {
        log.error(e.getExceptionType().toString(), e);

        return new ResponseEntity(RestResponse.createAppErrorRespBuilder(e).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * 其他异常处理
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<RestResponse> handleException(ServletRequestBindingException e) {
        log.error(e.getMessage(),e);

        return new ResponseEntity(RestResponse.createAppErrorRespBuilder(e).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {

        return true;//全部使用统一异常处理，以后扩展
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        return body;//TODO 扩展
    }
}
