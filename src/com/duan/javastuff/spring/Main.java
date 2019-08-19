package com.duan.javastuff.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created on 2019/8/19.
 *
 * @author DuanJiaNing
 */
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.duan.javastuff.spring");
        BeanA a = context.getBean(BeanA.class);
        a.out("this is out");

    }
}
