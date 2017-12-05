package com.duan.javastuff;

import com.duan.javastuff.encrypt.Base;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.function.Consumer;

/**
 * Created on 2017/11/22.
 *
 * @author DuanJiaNing
 */
public class Main {


    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        String s = "信息传输完整一致。是计算机广泛使用的杂凑算法之一（又译摘要算法、哈希算法），主流编程语言普遍已有MD实现。";
        Base b = new Base();
        String s1 = b.encrypt(s.getBytes());
        p.accept(s1);
        p.accept(new String(b.decrypt(s1)));
    }

    public static Consumer<Object> p = System.out::println;

}

