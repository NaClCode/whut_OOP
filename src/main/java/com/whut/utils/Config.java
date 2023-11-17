package com.whut.utils;

import java.io.InputStream;

import java.util.Properties;

public class Config {

    private Properties properties = new Properties();

    public Config() {
        
        try{
            InputStream in = Config.class.getClassLoader().getResourceAsStream("resources/config.properties");
            properties.load(in);
        }catch(Exception e ){
            System.out.println("没有找到配置文件config.properties");
        }
        
    }

    public String get(String key) {
        Object value = properties.get(key);
        if(value instanceof String) return (String)value;
        else return null;
    }
}
