package com.github.platform.sf.common.spring.validate.annotation;

import com.github.platform.sf.common.spring.validate.IdentificationCardValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 身份证注解
 *
 * @author zhangjj
 * @create 2018-02-28 10:15
 **/
@Documented
@Target({ FIELD, ANNOTATION_TYPE, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = { IdentificationCardValidator.class })
public @interface IdentificationCard {

    boolean required() default true; //是否必须

    String message() default "身份证非法";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}