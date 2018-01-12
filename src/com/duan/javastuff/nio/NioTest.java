package com.duan.javastuff.nio;

import com.duan.javastuff.P;
import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.ShortBuffer;
import java.nio.channels.FileChannel;
import java.util.logging.Level;

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

    @Test
    public void test() {

        byte[] res = {1, 2, 3, 4};
        ByteBuffer buffer = ByteBuffer.allocate(res.length + 3);
        P.out(buffer.limit()); // 7
        P.out(buffer.position()); // 0
        P.out(buffer.remaining()); // 7
        P.out(buffer.get(6)); // 0

        buffer.put(res);
        P.out(buffer.limit()); // 7
        P.out(buffer.position());// 4
        P.out(buffer.get()); // 0
        P.out(buffer.get(3)); // 4

        buffer.limit(4);
        P.out(buffer.limit()); // 4
        P.out(buffer.position());// 4
        P.out(buffer.get(3)); // 4
        P.out(buffer.get(1)); // 2
        //P.out(buffer.get(6)); // IndexOutOfBoundsException
        P.out(buffer.remaining());// 0
        P.out(buffer.hasRemaining());// false

        buffer.limit(6);
        P.out(buffer.hasRemaining());// true
        P.out(buffer.position());// 4
        buffer.mark(); // 标记当前位置，mark=position=4
        buffer.position(2); // if mark > position -> mark=-1，即设置的position小于mark的位置时，mark会被清除
        buffer.mark(); // 从新标记 mark=2
        P.out(buffer.get()); // 3 get当前position，并且position自增1
        P.out(buffer.position());// 3
        P.out(buffer.get(5)); // 0 不自增
        buffer.reset();
        P.out(buffer.position());// 2

        buffer.put((byte) 12);

    }

    @Test
    public void test11() {
        byte[] res = {1, 2, 3, 4};
        ByteBuffer buffer = ByteBuffer.allocate(res.length);
        buffer.put(res);
        P.out(buffer.position());
        P.out(buffer.limit());
        P.out(buffer.capacity());
        buffer.put((byte) 11);
        P.out(buffer.position());
        P.out(buffer.limit());
        P.out(buffer.capacity());
        buffer.rewind();
    }

    @Test
    public void test5() throws IOException {
        FileInputStream file = new FileInputStream(from);
        // 映射整个文件
        int size = file.available();
        MappedByteBuffer mappedByteBuffer = file.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, size);

        for (int i = size / 2; i < size / 2 + 6; i++) {
            P.out((char) mappedByteBuffer.get(i));
        }
    }

}
