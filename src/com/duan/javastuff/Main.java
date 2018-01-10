package com.duan.javastuff;

import java.nio.ByteBuffer;

/**
 * Created on 2017/11/22.
 *
 * @author DuanJiaNing
 */
public class Main {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[]{1, 2, 3});
        byteBuffer.putChar('c');
    }




}

