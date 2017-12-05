package com.duan.javastuff.encrypt;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * Created on 2017/12/5.
 * Base加密：把任意序列的位字节描述为一种不易被人直接识别的形式。
 *
 * @author DuanJiaNing
 */
public class Base {

    private final BASE64Encoder encoder;
    private final BASE64Decoder decoder;

    public Base() {
        encoder = new BASE64Encoder();
        decoder = new BASE64Decoder();
    }

    //解密
    public byte[] decrypt(String arg) throws IOException {
        return decoder.decodeBuffer(arg);
    }

    // 加密
    public String encrypt(byte[] args) {
        return encoder.encodeBuffer(args);
    }

}
