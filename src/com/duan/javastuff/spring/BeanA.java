package com.duan.javastuff.spring;

import org.springframework.stereotype.Component;

/**
 * Created on 2019/8/19.
 *
 * @author DuanJiaNing
 */
@Component
public class BeanA {

    public String name = "";

    public void out(Object obj) {
        System.out.println("BeanA.out " + obj);
    }

}
