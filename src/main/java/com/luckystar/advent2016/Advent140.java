package com.luckystar.advent2016;

import java.nio.CharBuffer;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

/**
 * By: Alexey Matveev
 * Date: 28.12.2016
 * Time: 14:26
 */
public class Advent140 {

    public static void main(String[] args) throws Exception {
//        String input = "ihaygndm";
        String input = "abc";
        boolean part2 = true;
        int padsFound = 0;
        int index = 0;
        int lastTripleIndex = 0;
        int padThreshold = 64;
        MessageDigest md = MessageDigest.getInstance("MD5");
        Map<String, String> hashes = new HashMap<>();
        here:
        while (padsFound < padThreshold) {
            String salt = input + index;
            String digest;
            if (!part2) {
                digest = hexString(md.digest(salt.getBytes()));
            } else {
                digest = hashNTimes(md, salt, 2017);
            }
            // find triple in current hash
            Character triple = checkContainTriple(digest);
            if (triple != null) {
                // check next 1000 hashes for 5-lets
                for (int j=index+1; j < index + 1001; j++) {
                    String salt1000 = input + j;
                    String hash = hashes.get(salt1000);
                    if (hash == null) {
                        if (!part2) {
                            hash = hexString(md.digest(salt1000.getBytes()));
                        } else {
                            hash = hashNTimes(md, salt1000, 2017);
                        }
                        hashes.put(salt1000, hash);
                    }
                    if (checkContain5InARow(hash, triple)) {
                        padsFound++;
                        if (padsFound == padThreshold) { // threshold reached
                            lastTripleIndex = index;
                            break here;
                        }
                    }
                }
            }
            index++;
        }
        System.out.println("Part 2? " + part2);
        System.out.println("Found " + padsFound + " pads");
        System.out.println("Last triple index: " + lastTripleIndex);
    }

    static String hashNTimes(MessageDigest md, String s, int times) throws Exception {
        String result = s;
        for (int i=0; i < times; i++) {
            result = hexString(md.digest(result.getBytes()));
        }
        return result;
    }

    static Character checkContainTriple(String s) {
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length - 2; i++) {
            if (chars[i] == chars[i+1] && chars[i] == chars[i+2]) {
                return chars[i];
            }
        }
        return null;
    }

    static boolean checkContain5InARow(String s, Character c) {
        return s.contains(CharBuffer.wrap(new char[] {c, c, c, c, c}));
    }

    static String hexString(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (byte aData : data) {
            sb.append(Integer.toString((aData & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

}
