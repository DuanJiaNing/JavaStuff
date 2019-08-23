package com.duan.javastuff.spring;

import org.springframework.context.support.AbstractApplicationContext;

/**
 * Created on 2019/8/19.
 *
 * @author DuanJiaNing
 */
public class Main {

    public static void main(String[] args) {
        startIOC();

//        Integer a = 10;
//        Type t = a.getClass();
//        Class<? extends Integer> aClass = a.getClass();
//        P.out(t);
//        P.out(aClass);
//        P.out(aClass == t);
    }

    private static void startIOC() {
        AbstractApplicationContext context = new AAContext("com.duan.javastuff.spring");
        BeanA a = context.getBean(BeanA.class);
        a.out(a.name);
    }
}
