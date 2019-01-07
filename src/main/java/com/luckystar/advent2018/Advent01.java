package com.luckystar.advent2018;

import com.luckystar.InputLoader;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alexey Matveev on 12/9/2018.
 */
public class Advent01 {
    public static void main(String[] args) {
        String input = InputLoader.readInput("2018/input_1.txt");
        String[] split = input.split("\r\n");
        int sum = 0;
        for (String s : split) {
            char sign = s.charAt(0);
            int inc = Integer.valueOf(s.substring(1));
            if (sign == '+') {
                sum += inc;
            } else {
                sum -= inc;
            }
        }
        System.out.println(sum);
        part2(split);
    }

    static void part2(String[] split) {
        int sum = 0;
        Set<Integer> frequencies = new HashSet<>();
        frequencies.add(0);
        here:
        while (true) {
            for (String s : split) {
                char sign = s.charAt(0);
                int inc = Integer.valueOf(s.substring(1));
                if (sign == '+') {
                    sum += inc;
                } else {
                    sum -= inc;
                }
                if (frequencies.contains(sum)) {
                    System.out.println("Duplicate: " + sum);
                    break here;
                }
                frequencies.add(sum);
            }
        }
    }
}
