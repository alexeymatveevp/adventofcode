package com.luckystar.advent2016;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: david
 * Date: 23.12.2016
 * Time: 10:21
 */
public class Advent070 {

    public static void main(String[] args) throws Exception {
        String input = new String(Files.readAllBytes(Paths.get(Advent070.class.getClassLoader().getResource("2016/input_7.txt").toURI())));
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
            // add one last sentence
            if (good) {
                goodTexts.add(sb.toString());
            } else {
                badTexts.add(sb.toString());
            }
            // check which are good but not bad
            boolean bad = false;
            for (String text : badTexts) {
                if (checkPolyndrom(text)) {
                    bad = true;
                    break;
                }
            }
            if (!bad) {
                for (String text : goodTexts) {
                    if (checkPolyndrom(text)) {
                        count++;
                        break;
                    }
                }
            }

        }

        System.out.println("IP's count: " + count);

    }

    static boolean checkPolyndrom(String text) {
        char[] chars = text.toCharArray();
        for (int i = 0; i< chars.length-3; i++) {
            if (chars[i] != chars[i+1]
                && chars[i] == chars[i+3] && chars[i+1] == chars[i+2]) {
                return true;
            }
        }
        return false;
    }
}
