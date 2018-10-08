package com.duan.javastuff.util;

import java.nio.charset.Charset;

/**
 * Created on 2018/3/8.
 *
 * @author DuanJiaNing
 */
public class Utils {

    /**
     * 将普通字符串转为 16 字符串
     *
     * @param str 普通字符串
     * @return 16 进制字符串
     */
    public static String stringToUnicode(String str) {
//

        StringBuilder builder = new StringBuilder();
        for (char ch : str.toCharArray()) {
            int ich = (int) ch;
            builder.append("\\u").append(Integer.toHexString(ich));
        }

        return builder.toString();
    }

    /**
     * 将 16 字符串转普通字符串
     *
     * @param unicode 16 进制字符串
     * @return 普通字符串
     */
    public static String unicodeToString(String unicode) {

        StringBuffer string = new StringBuffer();
        String[] hex = unicode.split("\\\\u");

        for (int i = 1; i < hex.length; i++) {

            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);

            // 追加成string
            string.append((char) data);
        }

        return string.toString();
    }

    // 将 blog content 中的 html 标签移除
    public static String removeHtmlFromString(String str, String replacement) {
        return str.replaceAll("<.*?>", replacement);
    }

}
