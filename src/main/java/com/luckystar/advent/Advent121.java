package com.luckystar.advent;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * User: david
 * Date: 1/1/2016
 * Time: 6:49 PM
 */
public class Advent121 {

    public static void main(String[] args) throws Exception {
        String input = new String(Files.readAllBytes(Paths.get(Advent81.class.getClassLoader().getResource("input_12.txt").toURI())));
        // look for digit (or minus), parse number till next digit
        // 0=48, 9=57
        int i=0;
        int total = 0;
        while (i < input.length()) {
            char c = input.charAt(i);
            if (checkDigit(c) || c == '-') {
                int j = i;
                boolean digit = true;
                while (digit) {
                    j++;
                    digit = checkDigit(input.charAt(j));
                }
                // parse
                Integer number = Integer.valueOf(input.substring(i, j));
                System.out.println("# number found: " + number);
                total += number;
                i = j;
            } else {
                i++;
            }
        }
        System.out.println("----");
        System.out.println("total: " + total);
    }

    static boolean checkDigit(char c) {
        return c >=48 && c <= 57;
    }
}
