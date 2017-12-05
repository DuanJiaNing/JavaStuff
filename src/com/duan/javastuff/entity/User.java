package com.duan.javastuff.entity;

import lombok.*;

import java.util.Date;

/**
 * Created on 2017/12/4.
 *
 * @author DuanJiaNing
 */
@AllArgsConstructor
@ToString
@Getter
@Setter
@NoArgsConstructor
public class User {
    private String name;
    private int age;
    private Date birthday;
}
