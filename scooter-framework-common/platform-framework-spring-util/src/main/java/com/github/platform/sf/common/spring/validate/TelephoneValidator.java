package com.github.platform.sf.common.spring.validate;


import com.github.platform.sf.common.spring.validate.annotation.Telephone;
import com.github.platform.sf.common.util.ValidatorUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 手机号注解 解析器
 *
 * @author zhangjj
 * @create 2018-02-06 10:25
 **/
public class TelephoneValidator implements ConstraintValidator<Telephone, String> {

    private boolean required ;

    @Override
    public void initialize(Telephone constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (null == value || "".equals(value.trim()) ) {
            return !required;
        }
        return ValidatorUtil.isTelephone(value);
    }

}
