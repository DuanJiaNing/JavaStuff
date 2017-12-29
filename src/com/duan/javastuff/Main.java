package com.duan.javastuff;

import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on 2017/11/22.
 *
 * @author DuanJiaNing
 */
public class Main {

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("(AB)(B\\d)\\2\\1");
        Matcher m = pattern.matcher("ABB2B2AB");
        System.out.println(Pattern.matches("(AB)(B\\d)\\2\\1", "ABB2B2AB")); //true
        P.out(m.group());
    }


}

