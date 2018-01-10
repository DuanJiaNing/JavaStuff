package com.duan.javastuff.nio;

import com.duan.javastuff.P;
import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * Created on 2018/1/4.
 *
 * @author DuanJiaNing
 */
public class NioTest {

    private static final String DESKTOP = "C:/Users/ai/Desktop/";
    private static final String from = DESKTOP + File.separator + "tip.txt";
    private static final String to = DESKTOP + File.separator + "tip_backup1.txt";

    public static void main(String[] args) throws IOException {
        String path = "C:/Users/ai/Desktop/java集合框架.jpg";
        FileChannel channel = new FileOutputStream(path).getChannel();
        channel.write(ByteBuffer.wrap("somthing write".getBytes()));
        channel.close();

        channel = new RandomAccessFile(path, "rw").getChannel();
        channel.position(channel.size());
        channel.write(ByteBuffer.wrap("somthing append in the end".getBytes()));
        channel.close();

        channel = new FileInputStream(path).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(20000);
        channel.read(buffer);
        buffer.flip();

        while (buffer.hasRemaining())
            System.out.print((char) buffer.get());

    }

    @Test
    public void test1() throws IOException {

        FileChannel inChannel = new FileInputStream(from).getChannel();
        FileChannel outChannel = new FileOutputStream(to).getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (inChannel.read(buffer) != -1) {
            buffer.flip(); // 准备好被别人读取
            outChannel.write(buffer);//write之后信息仍在缓冲区
            buffer.clear(); // 为下一次读取做准备
        }

        close(inChannel, outChannel);

    }

    @Test
    public void test2() throws IOException {

        FileChannel inChannel = new FileInputStream(from).getChannel();
        FileChannel outChannel = new FileOutputStream(to).getChannel();

        inChannel.transferTo(0, inChannel.size(), outChannel);

        close(inChannel, outChannel);
    }

    private void close(FileChannel... channels) throws IOException {
        if (channels != null) {
            for (FileChannel channel : channels) {
                if (channel != null && channel.isOpen()) channel.close();
            }
        }
    }

    @Test
    public void test3() throws IOException {
        FileChannel channel = new FileOutputStream(to).getChannel();
//        buffer.asCharBuffer().put("新的字符串"); // 以字符串的形式填充缓冲器
        channel.write(ByteBuffer.wrap("指定字符集".getBytes("UTF-8")));
        close(channel);

        // 读取
        FileChannel read = new FileOutputStream(to).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(24);
        buffer.clear();
        read.read(buffer); // java.nio.channels.NonReadableChannelException 异常 ？？
        buffer.flip();
        P.out(buffer.asCharBuffer());

    }

    @Test
    public void test4() {

        byte[] byt = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1};
        ByteBuffer buffer = ByteBuffer.wrap(byt);
        buffer.order(ByteOrder.BIG_ENDIAN);
        ShortBuffer shortBuffer1 = buffer.asShortBuffer();
        while (buffer.hasRemaining())
            P.out(shortBuffer1.get());

        buffer.rewind();
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        ShortBuffer shortBuffer = buffer.asShortBuffer();
        while (buffer.hasRemaining())
            P.out(shortBuffer.get());

    }

}
