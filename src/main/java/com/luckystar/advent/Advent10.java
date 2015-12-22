package com.luckystar.advent;

/**
 * Conway and the game of life.
 *
 * User: david
 * Date: 12/22/2015
 * Time: 11:07 PM
 */
public class Advent10 {
    public static void main(String[] args) {
        String input = "1113122113";
        // 40 or 50
        for (int i=0; i < 50; i++) {
            input = lookAndSay(input);
        }
        System.out.println(input.length());
    }

    static String lookAndSay(String input) {
        StringBuilder result = new StringBuilder();
        int idx = 0;
        while (idx < input.length()) {
            char c = input.charAt(idx);
            int mult = 0;
            char next = c;
            while (next == c) {
                mult++;
                if (idx != input.length() - 1) {
                    idx++;
                    next = input.charAt(idx);
                } else {
                    idx++;
                    break;
                }
            }
            result.append(mult + "");
            result.append(c + "");
        }
        return result.toString();
    }
}
