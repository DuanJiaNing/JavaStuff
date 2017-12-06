package com.duan.javastuff.lock;

import com.duan.javastuff.P;

/**
 * Created on 2017/12/6.
 *
 * @author DuanJiaNing
 */
public class TestObjectMethods {

    public static void main(String[] args) throws InterruptedException {
        new TestObjectMethods().correct();
    }

    public void correct() throws InterruptedException {
        Object ob = new Object();
        startWakeUpThread(3000, ob);
        synchronized (ob) {
            ob.wait(); //  main thread 等待并释放ob锁
        }
        P.out(" i`m wake up", true);
    }

    public void startWakeUpThread(long mill, Object lock) {
        new Thread(() -> {
            P.out("wake up main thread after " + mill / 1000 + " seconds", true);
            try {
                Thread.sleep(mill);
                // 当前线程需要成为锁的持有者，才能使用其 notifyAll 方法，否则将得到
                // IllegalMonitorStateException 异常
                synchronized (lock) {
                    lock.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }


    public void testException() throws InterruptedException {
        // 三个方法调用都将得到 IllegalMonitorException
        Object ob = new Object();
        test1(ob);
        TestObjectMethods om = new TestObjectMethods();
        om.test(ob);
        om.test2();
    }

    public synchronized void test(Object ob) throws InterruptedException {
        ob.wait(); // 锁为 this
    }

    public static synchronized void test1(Object ob) throws InterruptedException {
        ob.wait(); // 锁为 Main.class
    }

    public void test2() throws InterruptedException {
        Object ob = new Object();
        Object ob1 = new Object();
        synchronized (ob1) {
            ob.wait(); // 锁为 ob1
        }
    }


}
