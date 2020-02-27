package com.github.platform.sf.common.spring.validate;


import com.github.platform.sf.common.spring.validate.annotation.IdentificationCard;
import com.github.platform.sf.common.util.ValidatorUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 身份证注解 解析器
 * @author zhangjj
 * @create 2018-02-28 10:17
 **/
public class IdentificationCardValidator implements ConstraintValidator<IdentificationCard, String> {

    private boolean required ;

    @Override
    public void initialize(IdentificationCard constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (null == value || "".equals(value.trim()) ) {
            return !required;
        }
        return ValidatorUtil.isIdentificationCard(value);
    }


}
