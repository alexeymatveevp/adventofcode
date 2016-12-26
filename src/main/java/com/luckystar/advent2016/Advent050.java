package com.luckystar.advent2016;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

/**
 * User: david
 * Date: 22.12.2016
 * Time: 22:25
 */
public class Advent050 {

    public static void main(String[] args) throws Exception {
        String input = "ffykfhsq";
        String[] rows = input.split("\n");
        MessageDigest md = MessageDigest.getInstance("MD5");
        int i = 0;
        int passwordLength = 8;
        while (passwordLength != 0) {
            String anotherTry = input + i;
            String digest = hexString(md.digest(anotherTry.getBytes()));
            if (digest.substring(0,5).equals("00000")) {
                System.out.print(digest.substring(5,6));
                passwordLength--;
            }
            i++;
        }
    }

    static String hexString(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (byte aData : data) {
            sb.append(Integer.toString((aData & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
