package com.woodpecker.czq.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author: woodpecker
 * @Date: 2018/7/2 22:24
 * <p>
 * 属性文件工具类
 */
public final class PropertiesUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtil.class);
    private String load_properties_file_failure;

    /*
    加载属性文件
     */
    public static Properties loadProperties(String fileName) {
        Properties props = null;
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (is == null) {
                throw new FileNotFoundException(fileName + " file is not found");
            }
            props = new Properties();
            props.load(is);
        } catch (IOException e) {
            LOGGER.error("load_properties_file_failure", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("close inputstream failure", e);
                }
            }
        }
        return props;
    }

    /*
    获取字符型属性（默认为空字符串）
     */
    public static String getString(Properties properties,String key){
        return getString(properties,key,"");
    }

    /*
    获取字符型属性（可指定默认值）
     */
    public static String getString(Properties properties,String key,String defaultValue){
        String value = defaultValue;
        if(properties.containsKey(key)){
            value = properties.getProperty(key);
        }
        return value;
    }

    /*
    获取数值型属性（默认值为0）
     */
    public static int getInt(Properties properties,String key){
        return getInt(properties,key,0);
    }

    /*
    获取数值型属性（可指定默认值）
     */
    public static int getInt(Properties properties,String key,int defaultValue){
        int value = defaultValue;
        if(properties.containsKey(key)){
            value = CastUtil.castInt(properties.getProperty(key));
        }
        return value;
    }

    /*
    获取布尔型属性的值（默认为false）
     */
    public static boolean getBoolean(Properties properties,String key){
        return getBoolean(properties,key,false);
    }

    /*
    获取布尔型属性的值，可指定默认值
     */
    public static boolean getBoolean(Properties properties,String key,Boolean defaultValue){
        return (properties.containsKey(key)?CastUtil.castBoolean(properties.getProperty(key)):defaultValue);
    }




}
