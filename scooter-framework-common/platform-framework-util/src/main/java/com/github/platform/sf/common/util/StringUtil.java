package com.github.platform.sf.common.util;

import com.github.platform.sf.common.BaseConstants;

/**
 * @author zhangjj
 * @create 2020-02-24 13:47
 **/
public class StringUtil {

    private StringUtil(){}

    /**
     * 获取最后一个点之后的string
     * @param src
     * @return
     */
    public static String subStringByPoint(String src){
        if(src == null){
            return null;
        }
        if(!src.contains(BaseConstants.DOT_SPLIT)){
            return src;
        }
        return src.substring(src.lastIndexOf(BaseConstants.DOT_SPLIT));
    }

    public static boolean isEmpty(String src){
        return src == null || src.length() == 0;
    }


    public static boolean isNotEmpty(String src){
        return src != null && src.length() != 0;
    }


}
