package com.wuyu.entity;

/**
 * Author:      wy
 * Create Date: 2022/8/25
 * Create Time: 23:02
 * Description:
 */
public class Student {

    static {
        System.out.println("静态代码块执行了!");
    }

    public static void show() {
        System.out.println("show()");
    }
}
