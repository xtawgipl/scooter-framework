package com.github.platform.sf.web.apiversion.annotion;

import java.lang.annotation.*;

/**
 * @author 张剑军
 * @Description
 * @date 2019/6/4 16:28
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiVersion {
    int value();
}
