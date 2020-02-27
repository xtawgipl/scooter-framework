package com.github.platform.sf.common.spring.validate.annotation;


import com.github.platform.sf.common.spring.validate.NameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 姓名注解
 *
 * @author zhangjj
 * @create 2018-02-28 13:50
 **/
@Documented
@Target({ FIELD, ANNOTATION_TYPE, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = { NameValidator.class })
public @interface Name {

    boolean required() default true; //是否必须

    String message() default "姓名格式不正确，仅包含·.和中文";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}