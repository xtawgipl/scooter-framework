package com.github.platform.sf.web;

import java.lang.annotation.*;

/**
 * @description:Controller层AOP拦截
 * @author zhangjj
 * @create 2019-01-01 17:16
 **/
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AppControllerLog {
    String description() default "";
}
