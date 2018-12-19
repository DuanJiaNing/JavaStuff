package com.duan.javastuff.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2018/12/19.
 *
 * @author DuanJiaNing
 */
public class SelectorTest {

    private static int port = 8067;
    private static List<Channel> channels = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException, IOException {
        Thread server = startWithNewThread(() -> server("server"), "server");

        for (int i = 0; i < 3; i++) {
            Thread.sleep(2 * 1000);
            String name = "client-" + i;
            startWithNewThread(() -> client(name), name);
        }

        server.join(); // 等待服务器关闭

        if (channels.size() > 0) {
            for (Channel channel : channels) {
                channel.close();
            }
        }

    }

    private static void out(Object obj) {
        System.out.println(obj);
    }

    private static void select(Selector selector, String channelName) {
        startWithNewThread(() -> {
            try {

                while (selector.select() > 0) { // 阻塞，应在对应注册的 SelectableChannel 的子线程中运行
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        out("select: " + Thread.currentThread().getName() + " ops=" + key.readyOps());
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }, channelName + "-SelectSubThread");

    }

    private static Thread startWithNewThread(Runnable runnable, String name) {
        Thread thread = new Thread(runnable, name);
        thread.start();
        out("thread: [" + name + "] started");
        return thread;
    }

    private static void client(String name) {

        try {
            SocketChannel socketChannel = SocketChannel.open();
            channels.add(socketChannel);

            Selector selector = Selector.open();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            select(selector, name); // 单独的线程中非阻塞处理

            socketChannel.connect(new InetSocketAddress(port));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void server(String name) {
        out(new Date().getSeconds());

        try {

            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            channels.add(serverSocketChannel);

            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(port));

            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            select(selector, name); // 单独的线程中非阻塞处理

            // 服务端主线程可以继续运行 ...

            TimeUnit.SECONDS.sleep(10); // 10s 后关闭服务器

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        out(new Date().getSeconds());
    }
}
