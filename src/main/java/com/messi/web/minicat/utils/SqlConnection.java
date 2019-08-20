package com.messi.web.minicat.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class SqlConnection {

    private static Properties properties;
    private static String driver;
    private static String url;
    private static String username;
    private static String password;
    private static Connection connection;

    static {
        properties =LoadProperties.loadProperties(LoadProperties.JDBCCONFIG);
        driver = properties.getProperty("driver");
        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");

    }

    public static Connection getInstance(){
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url,username,password);
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }

}
