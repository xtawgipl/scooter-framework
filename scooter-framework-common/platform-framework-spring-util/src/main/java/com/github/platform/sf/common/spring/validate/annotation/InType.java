package com.github.platform.sf.common.spring.validate.annotation;


import com.github.platform.sf.common.spring.validate.InTypeOfEnumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 类型判断，属于enum中的一个
 *
 * @author zhangjj
 * @create 2018-02-28 10:15
 **/
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = InTypeOfEnumValidator.class)
@Documented
public @interface InType {
    String message() default "类型错误";

    Class<?>[] groups() default {};

    Class<? extends Enum<?>> enumClazz();

    Class<? extends Payload>[] payload() default {};
}
