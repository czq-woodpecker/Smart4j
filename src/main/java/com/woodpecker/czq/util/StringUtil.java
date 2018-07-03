package com.woodpecker.czq.util;

/**
 * @Author: woodpecker
 * @Date: 2018/7/3 9:12
 *
 * 字符串工具类
 */
public class StringUtil {

    /*
    判断字符串是否为空
     */
    public static boolean isEmpty(String string){
        if(string != null){
            string = string.trim();
        }
        return StringUtil.isEmpty(string);
    }

    /*
    判断字符串是否为非空
     */
    public static boolean isNotEmpty(String string){
        return !isEmpty(string);
    }


}
