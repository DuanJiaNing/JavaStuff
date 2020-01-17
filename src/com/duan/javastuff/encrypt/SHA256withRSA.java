package com.duan.javastuff.encrypt;

import java.security.*;
//import sun.security.rsa.RSASignature$SHA256withRSA


/**
 * Created on 2020/1/14.
 *
 * @author DuanJiaNing
 */
public class SHA256withRSA {

    public static void main(String[] args) {
        new SHA256withRSA().sign();
    }
        public void sign() {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update("test text".getBytes());
            byte[] sign = messageDigest.digest();
            System.out.println("after sha256: " + bytesToHexString(sign));

            Signature Sign = Signature.getInstance("SHA256withRSA");
            Sign.initSign(getPrivateKey());
            Sign.update(sign);
            byte[] signed = Sign.sign();
            System.out.println("after SHA256withRSA: " + bytesToHexString(signed));
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }
    }

    private PrivateKey getPrivateKey() {
//        RSA
        return null;
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}
