package com.github.platform.sf.common.util;


import org.apache.commons.lang3.StringUtils;

public class SQLFilter {

    /**
     * SQL注入过滤
     * @param str  待验证的字符串
     */
    public static String sqlInject(String str)  {
        if(StringUtils.isEmpty(str)){
            return null;
        }
        //去掉'|"|;|\字符
        str = str.replaceAll("'", "");
        str = str.replaceAll("\"", "");
        str = str.replaceAll(";", "");
        str = str.replaceAll("\\", "");

        //转换成小写
        str = str.toLowerCase();

        //非法字符
        String[] keywords = {"master", "truncate", "insert", "select", "delete", "update", "declare", "alert", "create", "drop"};

        //判断是否包含非法字符
        for(String keyword : keywords){
            if(str.indexOf(keyword) != -1){
                throw new IllegalArgumentException("sql contans illeagal string word= "+keyword)  ;
            }
        }
        return str;
    }
}
