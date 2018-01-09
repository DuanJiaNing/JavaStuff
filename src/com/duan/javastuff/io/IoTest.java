package com.duan.javastuff.io;

import com.duan.javastuff.P;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created on 2018/1/9.
 *
 * @author DuanJiaNing
 */
public class IoTest {
    String path = "C:\\Users\\ai\\Desktop\\tip.txt";

    @Test
    public void t1() throws IOException {
        FileReader reader = new FileReader(path);
        char[] buffer = new char[1024];
        reader.read(buffer, 0, 1024);
        P.out(new String(buffer));

        reader.close();
    }


    @Test
    public void test() throws IOException {
        FileWriter writer = new FileWriter(path);
//        writer.write("append somthing");
        writer.append("append 1");

        writer.close();
    }

    @Test
    public void test1() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\ai\\Desktop\\tip1.txt"));
        writer.write("line1");
        writer.newLine();
        writer.write("line2");
        writer.newLine();
        writer.write("line3");
        writer.flush();
        writer.close();
    }
}
