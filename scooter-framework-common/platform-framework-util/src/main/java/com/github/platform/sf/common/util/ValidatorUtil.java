package com.github.platform.sf.common.util;

import java.util.regex.Pattern;

/**
 * 验证工具
 *
 * @author zhangjj
 * @create 2018-03-26 15:48
 **/
public class ValidatorUtil {

    private static final Pattern TELEPHONE_PATTERN = Pattern.compile("^(\\+86)?1\\d{10}$");

    private static final Pattern NAME_PATTERN = Pattern.compile("^[\\u4E00-\\u9FA5]{1,20}([·.][\\u4E00-\\u9FA5]{0,20})*[\\u4E00-\\u9FA5]$");

    private static final Pattern IDENTIFICATIONCARD_PATTERN = Pattern.compile("^(\\d{14}|\\d{17})(\\d|[xX])$");

    private static final Pattern NUMBER_PATTERN = Pattern.compile("^\\d+(\\.\\d+)?$");

    private static final Pattern SPECIAL_CHARACTER_PATTERN = Pattern.compile("[~!#$%^&*+<>?:'\"\\|]");


    /**
     * 验证 是否包含特殊字符
     * @param 
     * @author zhangjj
     * @Date 2018/3/30 9:26
     * @return 
     * @exception 
     * 
     */
    public static boolean isIncludeSpecialCharacter(String str){
        if (null == str || "".equals(str.trim()) ) {
            return false;
        }
        return SPECIAL_CHARACTER_PATTERN.matcher(str).find();
    }


    public static boolean validateLen(String str, Integer max){

        return validateLen(str, 0, max);
    }

    /**
     * 验证字符串长度 合法性
     * @param 
     * @author zhangjj
     * @Date 2018/3/26 18:01
     * @return 
     * @exception 
     * 
     */
    public static boolean validateLen(String str, Integer min, Integer max){
        if(min == null){
            min = 0;
        }
        if(max == null){
            min = Integer.MAX_VALUE;
        }
        if(str == null){
            return false;
        }
        if("".equals(str) && min == 0){
            return true;
        }
        return str.length() >= min && str.length() <= max;

    }

    /**
     * 判断 src是否与args数组 中的任何一个等值
     * @param
     * @author zhangjj
     * @Date 2018/3/26 16:46
     * @return 
     * @exception 
     * 
     */
    public static boolean equalsAny(Object src, Object... args){
        if(src == null && args == null){
            return true;
        } else if("".equals(src)){
            if(args == null){
                return false;
            }
            if(args.length != 1){
                return false;
            }
            return "".equals(args[0]);
        } else {
            for(Object arg : args){
                if(src.equals(arg)){
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean numberValidate(String number) {
       return numberValidate(number, false, 10, 10);
    }

    /**
     * 带条件的数据验证
     * @param number 数字
     * @param negative 是否允许为负值
     * @param length 总长度
     * @param decimal 小数最长长度
     * @author zhangjj
     * @Date 2018/3/26 16:05
     * @return 
     * @exception 
     * 
     */
    public static boolean numberValidate(String number, boolean negative, int length, int decimal) {
        if (null == number || "".equals(number.trim()) ) {
            return false;
        }
        if(!negative && number.startsWith("-")){
            return false;
        }
        if(number.startsWith("-")){
            number = number.substring(1);
        }
        if(NUMBER_PATTERN.matcher(number).find()){
            int len = number.length();
            if(number.contains(".")){//为小数
                if(len > length + 1){
                    return false;
                }
                String decimalValue = number.substring(number.indexOf(".") + 1);
                return decimalValue.length() >= 1 && decimalValue.length() <= decimal;
            }else{
                return number.length() <= length;
            }
        }else{
            return false;
        }
    }



    /**
     * 验证 身份证 合法性
     * @param 
     * @author zhangjj
     * @Date 2018/3/26 15:55
     * @return 
     * @exception 
     * 
     */
    public static boolean isIdentificationCard(String identificationCard) {
        if (null == identificationCard || "".equals(identificationCard.trim()) ) {
            return false;
        }
        return IDENTIFICATIONCARD_PATTERN.matcher(identificationCard).find();
    }

    /**
     * 验证姓名合法性
     * @param 
     * @author zhangjj
     * @Date 2018/3/26 15:52
     * @return 
     * @exception 
     * 
     */
    public static boolean isName(String name) {
        if (null == name || "".equals(name.trim()) ) {
            return false;
        }
        return NAME_PATTERN.matcher(name).find();
    }

    /**
     * 验证手机号合法性
     * @param
     * @author zhangjj
     * @Date 2018/3/26 15:51
     * @return 
     * @exception 
     * 
     */
    public static boolean isTelephone(String telephone){
        if (null == telephone || "".equals(telephone.trim()) ) {
            return false;
        }
        return TELEPHONE_PATTERN.matcher(telephone).find();
    }

}
