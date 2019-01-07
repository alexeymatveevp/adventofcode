package com.luckystar.advent2017;

import com.luckystar.advent2016.Advent250;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by ksavina on 12/2/2017.
 */
public class Advent01 {

    public static void main(String[] args) throws Exception {
        String input = new String(Files.readAllBytes(Paths.get(Advent01.class.getClassLoader().getResource("2017/input_1.txt").toURI())));
        int sum = 0;
        for (int i=0; i<input.length() - 1; i++) {
            char c = input.charAt(i);
            char next = input.charAt(i+1);
            if (c == next) {
                sum += Integer.valueOf(c + "");
            }
        }
        if (input.substring(0,1).equals(input.substring(input.length()-1, input.length()))) {
            sum += Integer.valueOf(input.substring(0,1));
        }
        System.out.println(sum);

        // part 2
        sum = 0;
        int shift = input.length() / 2;
        for (int i = 0; i< input.length(); i++) {
            char c = input.charAt(i);
            int index = i + shift;
            if (index >= input.length()) {
                index -= input.length();
            }
            char toCompare = input.charAt(index);
            if (c == toCompare) {
                sum += Integer.valueOf(c + "");
            }
        }
        System.out.println(sum);
    }
}
