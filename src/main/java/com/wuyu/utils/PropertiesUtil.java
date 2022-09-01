package com.wuyu.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Author:      wy
 * Create Date: 2022/8/25
 * Create Time: 21:46
 * Description:
 */
public class PropertiesUtil {

    static Properties pros = new Properties();

    static {
        try {
            //获取类加载器
            ClassLoader classLoader = PropertiesUtil.class.getClassLoader();
            //类加载器加载Properties配置文件
            InputStream is = classLoader.getResourceAsStream("druid.properties");
            pros.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties getPro() throws IOException {
//        String user = pros.getProperty("user");
//        String password = pros.getProperty("password");
//        System.out.println("user = " + user + ",password = " + password);
        return pros;
    }
}
