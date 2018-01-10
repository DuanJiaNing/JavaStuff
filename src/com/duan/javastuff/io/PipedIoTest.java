package com.duan.javastuff.io;

import com.duan.javastuff.P;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * Created on 2018/1/10.
 *
 * @author DuanJiaNing
 */
public class PipedIoTest {

    private static class Sender implements Runnable {

        private PipedOutputStream outputStream = new PipedOutputStream();

        PipedOutputStream getOutputStream() {
            return outputStream;
        }

        @Override
        public void run() {
            try {
                outputStream.write("sender send message".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class Receiver implements Runnable {

        PipedInputStream inputStream = new PipedInputStream();

        PipedInputStream getInputStream() {
            return inputStream;
        }

        @Override
        public void run() {
            try {
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = inputStream.read(buffer)) > 0) {
                    P.out(Thread.currentThread().getName() + " " + new String(buffer, 0, len));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) throws IOException {

        Sender sender = new Sender();
        Receiver receiver = new Receiver();

        Thread senderThread = new Thread(sender);
        Thread receiverThread = new Thread(receiver);

        PipedOutputStream outputStream = sender.getOutputStream();
        PipedInputStream inputStream = receiver.getInputStream();
        //关联两个流
        outputStream.connect(inputStream);

        senderThread.start();
        receiverThread.start();

    }

}
