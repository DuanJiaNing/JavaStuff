package com.duan.javastuff.nio;

import com.duan.javastuff.P;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2018/1/17.
 * 文件锁
 *
 * @author DuanJiaNing
 */
public class FileLockTest {

    private static final String DESKTOP = "C:/Users/ai/Desktop/";
    private static final String from = DESKTOP + File.separator + "tip.txt";
    private static final String to = DESKTOP + File.separator + "tip_backup1.txt";

    @Test
    public void test() throws IOException, InterruptedException {
        FileOutputStream os = new FileOutputStream(to);
        FileChannel channel = os.getChannel();
        FileLock lock = channel.tryLock();
        if (lock != null) {
            TimeUnit.SECONDS.sleep(2);
            lock.release();
        }
        os.close();

    }


    private static FileChannel channel;

    public static void main(String[] args) throws IOException {
        final int LENGTH = 1 << 10; // 1k
        channel = new RandomAccessFile(to, "rw").getChannel();

        // 映射文件 0 - 1kb 部分
        MappedByteBuffer mb = channel.map(FileChannel.MapMode.READ_WRITE, 0, LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            mb.put((byte) 'A');
        }
        // 锁住并修改文件的 0 - 1/3 部分
        new LockAndModify(mb, 0, LENGTH / 3);

        // 锁住并修改文件的 1/2 - 3/4 部分
        new LockAndModify(mb, LENGTH / 2, LENGTH / 2 + LENGTH / 4);
    }

    private static class LockAndModify extends Thread {
        private ByteBuffer buffer;
        private int start, end;

        LockAndModify(ByteBuffer buffer, int start, int end) {
            this.buffer = buffer;
            this.start = start;
            this.end = end;
            buffer.limit(end);
            buffer.position(start);

            // 基于已有buffer创建新的buffer
            this.buffer = buffer.slice();
            start();
        }

        @Override
        public void run() {
            try {

                // 阻塞锁
                FileLock lock = channel.lock(start, end, false);
                P.out("locked: " + start + " " + end);
                while (buffer.position() < buffer.limit() - 1) {
                    // 将大写字母 A 变换为小写的 a
                    byte b = (byte) (buffer.get() + 32);
                    buffer.put(b);
                }
                lock.release();
                P.out("release: " + start + " " + end);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
