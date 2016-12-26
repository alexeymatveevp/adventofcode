package com.luckystar.advent2016;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * User: david
 * Date: 23.12.2016
 * Time: 10:54
 */
public class Advent071 {

    public static void main(String[] args) throws Exception {
        String input = new String(Files.readAllBytes(Paths.get(Advent071.class.getClassLoader().getResource("2016/input_7.txt").toURI())));
        String[] rows = input.split("\n");

        int count = 0;
        for (String row : rows) {
            List<String> goodTexts = new ArrayList<>();
            List<String> badTexts = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            // parse [ ]
            boolean good = true;
            for (char c : row.toCharArray()) {
                if (good) {
                    if (c == '[') {
                        good = false;
                        goodTexts.add(sb.toString());
                        sb = new StringBuilder();
                    } else {
                        sb.append(c);
                    }
                } else {
                    if (c == ']') {
                        good = true;
                        badTexts.add(sb.toString());
                        sb = new StringBuilder();
                    } else {
                        sb.append(c);
                    }
                }
            }
            if (good) {
                goodTexts.add(sb.toString());
            } else {
                badTexts.add(sb.toString());
            }
            // find all ABA in good, check that BAB is in bad
            List<char[]> ABAs = new ArrayList<>();
            for (String text : goodTexts) {
                ABAs.addAll(findABA(text));
            }
            for (String text : badTexts) {
                if (containsBAB(text, ABAs)) {
                    count++;
                    break;
                }
            }

        }

        System.out.println("IP's count: " + count);

    }

    static List<char[]> findABA(String text) {
        List<char[]> result = new ArrayList<>();
        char[] chars = text.toCharArray();
        for (int i = 0; i< chars.length-2; i++) {
            if (chars[i] == chars[i+2]) {
                result.add(new char[] {chars[i], chars[i+1], chars[i+2]});
            }
        }
        return result;
    }

    static boolean containsBAB(String text, List<char[]> ABAs) {
        char[] chars = text.toCharArray();
        for (int i = 0; i< chars.length-2; i++) {
            for (char[] aba : ABAs) {
                if (chars[i] == aba[1] && chars[i+1] == aba[0] && chars[i+2] == aba[1]) {
                    return true;
                }
            }
        }
        return false;
    }
}
