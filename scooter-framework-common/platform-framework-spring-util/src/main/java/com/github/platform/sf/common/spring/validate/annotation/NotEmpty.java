package com.github.platform.sf.common.spring.validate.annotation;


import com.github.platform.sf.common.spring.validate.NotEmptyValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 非空
 *
 * @author zhangjj
 * @create 2018-04-26 16:40
 **/
@Documented
@Target({ FIELD, ANNOTATION_TYPE, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = { NotEmptyValidator.class })
public @interface NotEmpty {

    String message() default "不能为空";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
