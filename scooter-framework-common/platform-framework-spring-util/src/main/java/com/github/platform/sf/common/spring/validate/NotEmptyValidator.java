package com.github.platform.sf.common.spring.validate;

import com.github.platform.sf.common.spring.validate.annotation.NotEmpty;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @author zhangjj
 * @create 2018-04-26 16:43
 **/
public class NotEmptyValidator implements ConstraintValidator<NotEmpty, String> {

    private static final Pattern pattern = Pattern.compile("^\\d+(\\.\\d+)?$");


    @Override
    public void initialize(NotEmpty constraintAnnotation) {

    }


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !"".equals(value);
    }

}