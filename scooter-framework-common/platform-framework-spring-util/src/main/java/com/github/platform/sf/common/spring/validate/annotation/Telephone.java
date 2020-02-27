package com.github.platform.sf.common.spring.validate.annotation;

import com.github.platform.sf.common.spring.validate.TelephoneValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 手机号码注解
 *
 * @author zhangjj
 * @create 2018-02-06 10:16
 **/
@Documented
@Target({ FIELD, ANNOTATION_TYPE, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = { TelephoneValidator.class })
public @interface Telephone {

    boolean required() default true; //是否必须

    String message() default "手机号非法";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
