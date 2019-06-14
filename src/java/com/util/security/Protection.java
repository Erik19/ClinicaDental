/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util.security;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Protection {
    public Protection() {
    }

    public static byte[] makeDigest(String var0, String var1) throws NoSuchAlgorithmException {
        MessageDigest var2 = MessageDigest.getInstance("SHA");
        var2.update(var0.getBytes());
        var2.update(var1.getBytes());
        return var2.digest();
    }

    public static byte[] makeDigest(String var0, String var1, long var2, double var4) throws NoSuchAlgorithmException {
        MessageDigest var6 = MessageDigest.getInstance("SHA");
        var6.update(var0.getBytes());
        var6.update(var1.getBytes());
        var6.update(makeBytes(var2, var4));
        return var6.digest();
    }

    public static byte[] makeDigest(String var0, long var1, double var3) throws NoSuchAlgorithmException {
        MessageDigest var5 = MessageDigest.getInstance("SHA");
        var5.update(var0.getBytes());
        var5.update(makeBytes(var1, var3));
        return var5.digest();
    }

    public static byte[] makeDigest(byte[] var0, long var1, double var3) throws NoSuchAlgorithmException {
        MessageDigest var5 = MessageDigest.getInstance("SHA");
        var5.update(var0);
        var5.update(makeBytes(var1, var3));
        return var5.digest();
    }

    public static byte[] makeBytes(long var0, double var2) {
        try {
            ByteArrayOutputStream var4 = new ByteArrayOutputStream();
            DataOutputStream var5 = new DataOutputStream(var4);
            var5.writeLong(var0);
            var5.writeDouble(var2);
            return var4.toByteArray();
        } catch (IOException var6) {
            return new byte[0];
        }
    }
}
