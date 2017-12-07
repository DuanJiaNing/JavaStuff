package com.duan.javastuff;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created on 2017/12/6.
 *
 * @author DuanJiaNing
 */
public class P {
    private static final SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss-SSS");

    public static void out(Object obj) {
        System.out.println(format.format(new Date()) + " " + obj);
    }

    public static void outnl(Object obj) {
        System.out.printf(format.format(new Date()) + " " + obj);
    }

}
