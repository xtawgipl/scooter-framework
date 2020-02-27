package com.github.platform.sf.common.spring.validate;

import com.github.platform.sf.common.spring.validate.annotation.InType;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;

/**
 * @author xn071786
 * @create 2018/01/10
 **/
@Slf4j
public class InTypeOfEnumValidator implements ConstraintValidator<InType, String> {

    private Class<? extends Enum<?>> enumClazz;

    @Override
    public void initialize(InType inType) {
        this.enumClazz = inType.enumClazz();
    }

    /**
     * 优先反射enum类中的isInType方法，如果没有该方法则使用enum.name来校验是否合法
     *
     * @param value
     * @param context
     * @return
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean isValid = false;
        try {
            Method isInTypeMethod = enumClazz.getDeclaredMethod("isInType", String.class);
            isValid = (boolean) isInTypeMethod.invoke(enumClazz.getEnumConstants()[0], value);
        } catch (Exception e) {
            log.info("{}未提供isInType方法，使用enum.name验证", enumClazz);
            return isInType(enumClazz, value);
        }
        return isValid;
    }

    private static boolean isInType(Class<? extends Enum> enumClazz, String name) {
        Enum[] enumValArr = enumClazz.getEnumConstants();
        for (Enum enumVal : enumValArr) {
            if (enumVal.name().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

}
