package com.duan.javastuff.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
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
        TimeUnit.SECONDS.sleep(2); // 确保服务器处于 Acceptable

        // 启动多个客户端
        for (int i = 0; i < 10; i++) {
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

    private static void out(String ops, SelectionKey key, Channel channel) {
        out(ops + " " + Thread.currentThread().getName()
                + " ops=" + key.readyOps()
                + " att=" + key.attachment()
                + " channel=" + channel.hashCode());
    }

    private static void select(Selector selector, String channelName) {
        startWithNewThread(() -> {
            try {

                while (selector.select() > 0) { // 阻塞，应在对应注册的 SelectableChannel 的子线程中运行
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        SelectableChannel channel = key.channel();
                        SocketChannel socketChannel = (SocketChannel) channel;

                        if (key.isConnectable()) { // 成功连接到服务器
                            if (socketChannel.isConnectionPending()) {
                                if (socketChannel.finishConnect()) { // 只有当连接成功后才能注册OP_READ事件
                                    out("client-select-finishConnect", key, channel);
                                    socketChannel.configureBlocking(false);

                                    socketChannel.register(selector, SelectionKey.OP_WRITE); // 重新注册为监听可读事件
                                    iterator.remove();
                                } else key.cancel();
                            }

                        } else if (key.isWritable()) {
                            out("client-select-Writable", key, channel);
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            buffer.put(channelName.getBytes());
                            buffer.flip();

                            socketChannel.write(buffer); // 发送给服务器
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }, channelName + "-SelectSubThread");

    }

    private static void serverSelect(Selector selector, String channelName) {
        startWithNewThread(() -> {
            try {

                while (selector.select() > 0) { // 阻塞，在对应注册的 SelectableChannel 的子线程中运行
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        SelectableChannel channel = key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);

                        if (key.isAcceptable()) {
                            ServerSocketChannel socketChannel = (ServerSocketChannel) channel;
                            SocketChannel acceptC = socketChannel.accept(); // 接收客户端连接请求
                            out("server-select-acceptable", key, acceptC);
                            acceptC.configureBlocking(false);
                            acceptC.register(selector, SelectionKey.OP_READ); // 注册到服务端的 selector

                            iterator.remove(); // 事件已经处理，且不会再出现
                        } else if (key.isReadable()) {
                            SocketChannel socketChannel = (SocketChannel) channel;
                            out("server-select-readable", key, socketChannel);
                            for (buffer.clear(); socketChannel.read(buffer) > 0;
                                 buffer.flip(), out(new String(buffer.array())), buffer.clear())
                                ;
                        }
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
            socketChannel.register(selector, SelectionKey.OP_CONNECT, "attr-" + name);
            select(selector, name); // 单独的线程中非阻塞处理
            socketChannel.connect(new InetSocketAddress(port));

            TimeUnit.SECONDS.sleep(30); // 1000s 后关闭客户端

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void server(String name) {
        try {

            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            channels.add(serverSocketChannel);

            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(port));

            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, "att-server");
            serverSelect(selector, name); // 单独的线程中非阻塞处理

            // 服务端主线程可以继续运行 ...

            TimeUnit.SECONDS.sleep(60); // 60s 后关闭服务器

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
