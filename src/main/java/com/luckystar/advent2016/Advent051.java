package com.luckystar.advent2016;

import java.security.MessageDigest;

/**
 * User: david
 * Date: 22.12.2016
 * Time: 23:26
 */
public class Advent051 {

    public static void main(String[] args) throws Exception {
        String input = "ffykfhsq";
        String[] rows = input.split("\n");
        MessageDigest md = MessageDigest.getInstance("MD5");
        int i = 0;
        int passwordLength = 8, lettersLeft = 8;
        String[] password = new String[passwordLength];
        while (lettersLeft != 0) {
            String anotherTry = input + i;
            String digest = hexString(md.digest(anotherTry.getBytes()));
            if (digest.substring(0,5).equals("00000")) {
                String letter = digest.substring(6, 7);
                String positionStr = digest.substring(5, 6);
                Integer position = null;
                try {
                    position = Integer.valueOf(positionStr);
                } catch (Exception e) {
                    // just ignore it
                }
                if (position != null && position < passwordLength && password[position] == null) {
                    password[position] = letter;
                    lettersLeft--;
                }
            }
            i++;
        }
        for (String s : password) {
            System.out.print(s);
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
