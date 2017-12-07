package com.duan.javastuff;

import com.duan.javastuff.lock.TestConsumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created on 2017/11/22.
 *
 * @author DuanJiaNing
 */
public class Main {

    final ReentrantLock reentrantLock = new ReentrantLock();

//    public static void main(String[] args) throws InterruptedException {
//
//        Main m = new Main();
//        Condition c1 = m.reentrantLock.newCondition();
//        Condition c2 = m.reentrantLock.newCondition();
//        Condition c3 = m.reentrantLock.newCondition();
//        Thread t1 = m.get("t1", c1);
//        Thread t2 = m.get("t2", c2);
//        Thread t3 = m.get("t3", c3);
//
//        t1.start();
//        t2.start();
//        t3.start();
//
//        m.reentrantLock.lock();
//        c2.signal();
//        m.reentrantLock.unlock();
//
//    }

    private Thread get(String name, Condition condition) {
        return new Thread(() -> {
            try {
                reentrantLock.lock();
                P.out(name + " await " + reentrantLock.getHoldCount() + " " + reentrantLock.isHeldByCurrentThread());
                condition.await();
                P.out(name + " wakeup");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
        }, name);
    }


    public static void main(String[] args) {
        @SuppressWarnings("unchecked")
        TestConsumer<String> consumer = new TestConsumer<>(
                String::getBytes,
                () -> "a",
                () -> "b",
                () -> "c",
                () -> "d");

        consumer.start();
    }

}

