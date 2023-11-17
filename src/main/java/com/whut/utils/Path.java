package com.whut.utils;

public class Path {
    public static String getPath(String string) {
        return Path.class.getClassLoader().getResource("resources").getPath() + "/" + string;
    }
}
