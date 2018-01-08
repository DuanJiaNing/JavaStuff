package com.duan.javastuff.nio;

import com.duan.javastuff.P;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created on 2018/1/4.
 *
 * @author DuanJiaNing
 */
public class Io {

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

}
