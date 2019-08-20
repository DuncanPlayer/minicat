package com.messi.web.minicat.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class LoadProperties {

    public static String MAPPINGURL = "src/main/resources/config/mapping.properties";
    public static String JDBCCONFIG = "src/main/resources/config/jdbc.properties";
    /**
     * 加载映射文件
     * @return
     */
    public static Properties loadProperties(String path) {
        Properties properties = new Properties();
        try{
            InputStream in = new BufferedInputStream(new FileInputStream(path));
            properties.load(in);
        }catch (Exception e){
            e.printStackTrace();
        }
        return properties;
    }

}
