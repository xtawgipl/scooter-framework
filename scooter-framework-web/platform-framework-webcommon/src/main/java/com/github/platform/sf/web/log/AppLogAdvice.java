package com.github.platform.sf.web.log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.platform.sf.common.util.UUIDUtils;
import com.github.platform.sf.web.AppControllerLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;

/**
 * @description:日志拦截AOP
 *
 * @author zhangjj
 * @create 2019-01-01 17:16
**/
@Slf4j
@Configuration
@Aspect
@Order(-99)
public class AppLogAdvice {

    public AppLogAdvice() {
        log.info("启用AppLogAdvice，将拦截所有RequestMapping注释的方法!");
    }

    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping) || @annotation(org.springframework.web.bind.annotation.GetMapping) || @annotation(org.springframework.web.bind.annotation.PostMapping)")
    public Object doAround(ProceedingJoinPoint joinPoint)throws Throwable {
        String requestId = UUIDUtils.generateRandomUUID();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        AppControllerLog appControllerLog = method.getAnnotation(AppControllerLog.class);
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        GetMapping getMapping = method.getAnnotation(GetMapping.class);
        PostMapping postMapping = method.getAnnotation(PostMapping.class);
        String[] requestPaths;
        if(requestMapping != null){
            requestPaths = requestMapping.value();
        }else if(postMapping != null){
            requestPaths = postMapping.value();
        }else {
            requestPaths = getMapping.value();
        }
        log.debug("requestId = {}, 请求地址:{}", requestId, requestPaths);
        log.debug("requestId = {}, 请求方法:{}", requestId, (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
        log.debug("requestId = {}, 方法描述:{}", requestId, appControllerLog != null ? appControllerLog.description() : "");
        String argJson = null;
        try{
            argJson = JSON.toJSONString(joinPoint.getArgs(), SerializerFeature.PrettyFormat);
        }catch (Throwable e){
//            log.warn("", e);
        }
        log.debug("requestId = {}, 参数为：{}, 转换成json 为：{}", requestId, joinPoint.getArgs(), argJson);
        Object body = joinPoint.proceed();
        if(body instanceof ResponseEntity){
            ResponseEntity entity = (ResponseEntity) body;
            log.debug("requestId = {} 请求返回：\n{}", requestId, JSON.toJSONString(entity.getBody(), SerializerFeature.PrettyFormat));
        }else{
            log.debug("requestId = {}, 方法返回不为ResponseEntity对象，不输出返回结果!", requestId);
        }

        return body;
    }

}
