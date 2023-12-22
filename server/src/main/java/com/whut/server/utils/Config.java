package com.whut.server.utils;

import java.io.FileInputStream;
import java.io.InputStream;

import java.util.Properties;

public class Config {

    private Properties properties = new Properties();

    public Config() { 
    }

    public String get(String key) {
        Object value = properties.get(key);
        if(value instanceof String) return (String)value;
        else return null;
    }

    public void init(String[] args){
        InputStream in;
        try{
            if(args.length == 0) in = Config.class.getClassLoader().getResourceAsStream("resources/config.properties");
            else in = new FileInputStream(args[0]);
            properties.load(in);
        }catch(Exception e){
            System.out.println("配置文件加载失败！");
        }
       
    }
}
