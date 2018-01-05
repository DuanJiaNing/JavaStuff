package com.duan.javastuff.lock;

/**
 * Created on 2017/12/9.
 *
 * @author DuanJiaNing
 */
public class Test {
    public static void main(String[] args) {

        @SuppressWarnings("unchecked")
        SuppliersAndConsumerPlus<String> consumer = new SuppliersAndConsumerPlus<>(
                String::getBytes,
                () -> "a",
                () -> "b",
                () -> "c",
                () -> "d");

        consumer.start();

    }
}
