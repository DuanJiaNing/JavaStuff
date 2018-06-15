package com.duan.javastuff.util;

import nl.flotsam.xeger.Xeger;

import java.util.Random;

/**
 * Created on 2018/6/15.
 *
 * @author DuanJiaNing
 */
public class DataConstructUtil {

    private String[] newEmail(int count) {

        Random r = new Random();
        String[] ems = {"163", "qq", "17m", "club", "gmail"};
        String[] emc = {"com", "cn"};

        String[] res = new String[count];
        for (int i = 0; i < count; i++) {

            int len = r.nextInt(5) + 5;
            char[] cs = new char[len];
            for (int j = 0; j < len; j++) {
                char low = (char) (r.nextInt(26) + 65);
                char upp = (char) (r.nextInt(26) + 97);

                cs[j] = r.nextBoolean() ? low : upp;
            }

            res[i] = new String(cs) + "@" + ems[r.nextInt(ems.length)] + "." + emc[r.nextInt(emc.length)];
        }

        return res;
    }

    public String[] newPhone(int count) {

        String phoneRegx = "(^((13[0-9])|(14[5-8])|(15([0-3]|[5-9]))|(16[6])|(17[0|4|6|7|8])|(18[0-9])|(19[8-9]))\\\\d{8}$)|(^((170[0|5])|(174[0|1]))\\\\d{7}$)|(^(14[1|4])\\\\d{10}$)";
        Xeger generator = new Xeger(phoneRegx, new Random());

        String[] ps = new String[count];
        Random r = new Random();

        for (int i = 0; i < count; i++) {
            String result = generator.generate();

            char[] cs = new char[result.length()];
            char[] chars = result.toCharArray();
            for (int j = 1; j < chars.length; j++) {
                if (j == 12) break;
                char c = chars[j];
                if ((byte) c >= 48 && (byte) c < 57) {
                    cs[j] = c;
                } else {
                    cs[j] = (r.nextInt(10) + 1 + "").toCharArray()[0];
                }
            }

            ps[i] = new String(cs);
        }

        return ps;

    }
}
