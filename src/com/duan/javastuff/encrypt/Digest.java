package com.duan.javastuff.encrypt;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created on 2017/12/5.
 * MD5 和 SHA 加密，
 *
 * @author DuanJiaNing
 */
public class Digest {

    public static final String MD5 = "MD5";
    public static final String SHA = "SHA";
    private final MessageDigest digest;

    public Digest(String algorithm) throws NoSuchAlgorithmException {
        digest = MessageDigest.getInstance(algorithm);
    }

    public BigInteger digest(String args) {
        digest.update(args.getBytes());
        return new BigInteger(digest.digest());
    }

}

