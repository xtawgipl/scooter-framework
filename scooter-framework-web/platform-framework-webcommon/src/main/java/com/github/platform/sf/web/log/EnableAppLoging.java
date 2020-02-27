package com.github.platform.sf.web.log;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启动controller接口日志输出
 *
 * @author zhangjj
 * @create 2020-02-20 17:02
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(AppLogAdvice.class)
@Documented
public @interface EnableAppLoging {

}
