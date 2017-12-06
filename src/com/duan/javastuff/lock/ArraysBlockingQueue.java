package com.duan.javastuff.lock;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created on 2017/12/6.
 *
 * @author DuanJiaNing
 */
public class ArraysBlockingQueue<E> extends AbstractQueue<E> implements BlockingQueue<E>, Serializable {

    private final int capacity;
    private final AtomicInteger count = new AtomicInteger();
    private final Node<E>[] values;

    private final ReentrantLock takeLock = new ReentrantLock();
    private final Condition notEmpty = takeLock.newCondition();
    private final ReentrantLock putLock = new ReentrantLock();
    private final Condition notFull = putLock.newCondition();

    @SuppressWarnings("unchecked")
    public ArraysBlockingQueue(Class<E> type, int size) {
        this.capacity = size;
        this.values = (Node<E>[]) Array.newInstance(type, capacity);
    }


    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public void put(E e) throws InterruptedException {

    }

    @Override
    public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public E take() throws InterruptedException {
        return null;
    }

    @Override
    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        return null;
    }

    @Override
    public int remainingCapacity() {
        return 0;
    }

    @Override
    public int drainTo(Collection<? super E> c) {
        return 0;
    }

    @Override
    public int drainTo(Collection<? super E> c, int maxElements) {
        return 0;
    }

    @Override
    public E poll() {
        return null;
    }

    @Override
    public E peek() {
        return null;
    }

    static class Node<E> {
        E item;

        Node(E item) {
            this.item = item;
        }
    }

}
