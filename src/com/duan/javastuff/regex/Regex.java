package com.duan.javastuff.regex;

import com.duan.javastuff.P;
import com.sun.deploy.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on 2018/2/2.
 *
 * @author DuanJiaNing
 */
public class Regex {


    public static void main(String[] args) {
        Regex m = new Regex();
        P.out(m.parsePwd("aaaa1"));


    }

    // 解析博文中引用的相册图片
    private int[] parseContentForImageIds(String content, int bloggerId) {
        //http://localhost:8080/image/1/type=public/523?default=5
        //http://localhost:8080/image/1/type=private/1
        String regex = "http://localhost:8080/image/" + bloggerId + "/.*?/(\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        List<String> res = new ArrayList<>();
        while (matcher.find()) {
            String str = matcher.group();
            P.out(str);
            int index = str.lastIndexOf("/");
            res.add(str.substring(index + 1));
        }

        return res.stream()
                .mapToInt(Integer::valueOf)
                .distinct()
                .toArray();
    }

    /**
     * 校验密码：6-12 为，由字母和数字组成
     */
    private boolean parsePwd(String pwd) {

        String regex = "^(?:(?=.*[A-z])(?=.*[0-9])).{6,12}$";
        return pwd.matches(regex);
    }

}
