package com.lansha.test;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-3-14
 * Time: 下午1:42
 * To change this template use File | Settings | File Templates.
 */
public class MainTest {

    public static void main(String[] args) {
        int arr[] = getRandom(1, 5, 2);
        System.out.println(111);

    }

    /*
     * 获得在a到b之间的n个随机数
    */
    public static int[] getRandom(int a, int b, int n) {
        if (a > b) {
            int temp = a;
            a = b;
            b = temp;
        }

        Date dt = new Date();
        Random random = new Random(dt.getSeconds());
        int res[] = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = (int) (random.nextDouble() * (Math.abs(b - a) + 1)) + a;
        }
        return res;
    }

}
