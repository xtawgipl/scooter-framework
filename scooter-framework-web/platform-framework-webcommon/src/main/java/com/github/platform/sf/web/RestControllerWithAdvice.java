package com.github.platform.sf.web;

import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * 带通知的restController，统一返回消息
 * @author zhangjj
 * @create 2019-01-01 17:16
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
public @interface RestControllerWithAdvice {
}
