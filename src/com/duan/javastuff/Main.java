package com.duan.javastuff;

import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created on 2017/11/22.
 *
 * @author DuanJiaNing
 */
public class Main {

    public static void main(String[] args) {
        // 访问顺序
//        LinkedHashMap<Integer, String> map = new LinkedHashMap<>(16, 0.75f, true);
        LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
        map.put(1, "1");
        map.put(2, "2");
        map.put(3, "3");
        map.put(4, "4");
        map.put(5, "5");
        map.put(6, "6");
        map.put(7, "7");

        map.get(2);
        map.get(6);
        map.get(3);

        map.values().forEach(System.out::print);//遍历输出值: 1234567

    }

    @Test
    public void test() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "1");
        map.put(2, "2");
        map.put(22, "22");
        map.put(4332, "4332");
        map.put(5, "5");
        map.put(6, "6");
        map.put(7, "7");

        map.get(2);
        map.get(6);
        map.get(3);

        map.values().forEach(System.out::print);//遍历输出值: 1234567
    }


}

