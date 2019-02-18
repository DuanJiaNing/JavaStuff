package com.duan.javastuff;

import com.duan.javastuff.test.TestB;

/**
 * Created on 2017/11/22.
 *
 * @author DuanJiaNing
 */
public class Main {

    final int a = 1;

    public static void main(String[] args) {
        final char a = 2, b = 4;
        double d = 1;
        float f = 1;
        long l = 1;
        short s = a + b;

        TestB tb = new TestB();
        System.out.println("tb.add() = " + tb.add());

    }


}