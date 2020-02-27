package com.github.platform.sf.common.spring.validate;


import com.github.platform.sf.common.spring.validate.annotation.Name;
import com.github.platform.sf.common.util.ValidatorUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 姓名注解 解析器
 *
 * @author zhangjj
 * @create 2018-02-28 13:50
 **/
public class NameValidator implements ConstraintValidator<Name, String> {

    private boolean required ;

    @Override
    public void initialize(Name constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (null == value || "".equals(value.trim()) ) {
            return !required;
        }
        return ValidatorUtil.isName(value);
    }
}
