package com.duan.javastuff.io_nio;

import com.duan.javastuff.P;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created on 2018/1/4.
 *
 * @author DuanJiaNing
 */
public class Io {

    public static void main(String[] args) {
        File txt = new File("C:/Users/ai/Desktop/tip.txt");
        byte[] buffer = new byte[1024 * 1024];
        try (RandomAccessFile file = new RandomAccessFile(txt, "r")) {
            file.seek(0);
            file.read(buffer);
            P.out(new String(buffer));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
