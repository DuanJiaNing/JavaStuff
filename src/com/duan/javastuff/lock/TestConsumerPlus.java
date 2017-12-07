package com.duan.javastuff.lock;

import com.duan.javastuff.P;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created on 2017/12/7.
 *
 * @author DuanJiaNing
 */
public class TestConsumerPlus<T> {

    private final Lock lock = new ReentrantLock();

    //要生产的“货物”及其生产方式
    private final Supplier<T>[] suppliers;
    //消费者
    private final Consumer<T> consumer;
    //生产出的产品
    private T product;
    //生产者是否已经生产出一件产品
    private boolean isProducted = false;
    //条件：生产的产品还没有被消费者消费，需要等待消费者消费
    private Condition whenProductWaitConsume = lock.newCondition();
    //消费者是否已经消费了生产者生产的产品
    private boolean isConsumed = true;
    //条件：没有产品可以消费，需要等待生产者
    private Condition whenHaveProductToConsume = lock.newCondition();
    //生产者是否已经生产完所有的产品
    private boolean finished = false;

    public TestConsumerPlus(Consumer<T> consumer, Supplier<T>... suppliers) {
        this.suppliers = suppliers;
        this.consumer = consumer;
    }

    public void start() {
        // 开启消费者线程
        new Thread(() -> {
            try {
                consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 开启生产者线程
        new Thread(() -> {
            try {
                product();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }

    public void product() throws InterruptedException {
        // 遍历生产产品
        for (Supplier<T> supplier : suppliers) {
            doProduct(supplier);
        }
        finished = true;
    }

    private void doProduct(Supplier<T> supplier) throws InterruptedException {
        lock.lock();
        try {
            while (!isConsumed) {
                P.out("supplier wait");
                // 等待生产条件：
                // 我发现刚生产的产品还没被消费掉，等到产品被消费之后提醒我
                whenProductWaitConsume.await();
            }

            // 收到可以生产的信号
            product = supplier.get();
            TimeUnit.SECONDS.sleep(2);
            P.out("+ " + product);
            isProducted = true;
            isConsumed = false;
            // 产品生产好了，可以消费了
            whenHaveProductToConsume.signal();
        } finally {
            lock.unlock();
        }
    }

    private void consume() throws InterruptedException {
        P.out("begin consumer");
        while (!finished) {
            lock.lock();
            try {
                while (!isProducted) {
                    P.out("consumer wait");
                    // 等待消费条件：
                    // 我发现现在还没有产品可以消费，所以等有产品可以消费了就提醒我
                    whenHaveProductToConsume.await();
                }

                // 收到可以消费的信号
                consumer.accept(product);
                TimeUnit.SECONDS.sleep(2);
                P.out("- " + product);
                isConsumed = true;
                isProducted = false;
                // 产品被消费了，可以继续生产了
                whenProductWaitConsume.signal();
            } finally {
                lock.unlock();
            }
        }
    }

}
