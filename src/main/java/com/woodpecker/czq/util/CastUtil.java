package com.woodpecker.czq.util;

/**
 * @Author: woodpecker
 * @Date: 2018/7/3 0:04
 *
 * 转型工具类
 */
public final class CastUtil {

    /*
    转为String类型(默认值为空字符串)
     */
    public static String castString(Object object){
        return CastUtil.castString(object,"");
    }

    /*
    转为String类型，可指定默认值。
     */
    public static String castString(Object object,String defaultValue){
        return object != null ? String.valueOf(object) : defaultValue;
    }

    /*
    转为double类型（默认值为0d）
     */
    public static double  castDouble(Object object){
        return CastUtil.castDouble(object,0);
    }

    /*
    转为double类型，可指定默认值。
     */
    public static double castDouble(Object object,double defaultValue){
        double doubleValue = defaultValue;
        if(object != null){
            String strValue = CastUtil.castString(object);
            if(StringUtil.isNotEmpty(strValue)){
                try {
                    doubleValue = Double.parseDouble(strValue);
                } catch (NumberFormatException e) {
                    doubleValue = defaultValue;
                }
            }
        }
        return doubleValue;
    }

    /*
    转为long类型（默认值为0L）
     */
    public static long castLong(Object object){
        return CastUtil.castLong(object,0);
    }

    /*
    转为long类型，可指定默认值。
     */
    public static long castLong(Object object,long defaultValue){
        long longValue = defaultValue;
        if(object != null){
            String strValue = CastUtil.castString(object);
            if(StringUtil.isNotEmpty(strValue)){
                try {
                    longValue = Long.parseLong(strValue);
                } catch (NumberFormatException e) {
                    longValue = defaultValue;
                }
            }
        }
        return longValue;
    }



    /*
    转为int类型（默认为0）
     */
    public static int castInt(Object object){
        return CastUtil.castInt(object,0);
    }

    /*
    转为int类型，可指定默认值
     */
    public static int castInt(Object object,int defaultValue){
        int intValue = defaultValue;
        if(object != null){
            String strValue = castString(object);
            if(StringUtil.isNotEmpty(strValue)){
                try {
                    intValue = Integer.parseInt(strValue);
                } catch (NumberFormatException e) {
                    intValue = defaultValue;;
                }
            }
        }
        return intValue;
    }

    /*
    转为boolean类型（默认值为false）
     */
    public static boolean castBoolean(Object object){
        return CastUtil.castBoolean(object,false);
    }

    /*
    转为boolean类型（可指定默认值）
     */
    public static boolean castBoolean(Object object,Boolean defaultValue){
        boolean booleanValue = defaultValue;
        if(object != null){
            booleanValue = Boolean.parseBoolean(castString(object));
        }
        return booleanValue;
    }


    ///////思考？能不能使用泛型解决代码冗余？

}
