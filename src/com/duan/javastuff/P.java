package com.duan.javastuff;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created on 2017/12/6.
 *
 * @author DuanJiaNing
 */
public class P {

    public static void out(Object obj, boolean newLine) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss-SSS");
        System.out.printf(format.format(new Date()) + " " + obj + (newLine ? "\n" : ""));
    }

}
