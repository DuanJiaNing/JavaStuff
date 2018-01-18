package com.duan.javastuff.test;

/**
 * Created on 2018/1/18.
 *
 * @author DuanJiaNing
 */
class Base {

    int num = 20;
    String s = "cc";

    public Base() {
        // print方法被覆写，父类调用时子类的成员变量 num 和 s 还未初始化，只有默认值：Sub.num = 0 _null
        this.print();
        num *= 2;
    }

    public void print() {
    }
}

class Sub extends Base {

    int num = 3;
    String s = "a";

    public Sub() {
        // num 和 s 已被初始化
        this.print();
        num += 2;
    }

    @Override
    public void print() {
        System.out.println("Sub.num = " + num + " _" + s);
    }
}

public class Test {
    public static void main(String[] args) {
        Base b = new Sub();

        // 覆写只针对成员方法，成员变量没有覆写
        System.out.println(b.num); // 40
        System.out.println(b.s); // cc

        // 显示调用获取子类的属性
        System.out.println(((Sub) b).s); // a
    }
}