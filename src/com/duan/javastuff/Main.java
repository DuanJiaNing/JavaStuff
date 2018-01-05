package com.duan.javastuff;

/**
 * Created on 2017/11/22.
 *
 * @author DuanJiaNing
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Object obj = new Object();
        synchronized (obj) {
            P.out("wait");
            obj.wait(2000);
            P.out("wait over");
        }


    }


}

