package com.luckystar.advent2018;

import com.luckystar.advent2016.Advent011;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Alexey Matveev on 26.12.2018
 */
public class Advent022 {

    public static void main(String[] args) throws Exception {
        String input = new String(Files.readAllBytes(Paths.get(Advent011.class.getClassLoader().getResource("2018/input_2.txt").toURI())));
        String[] parts = input.split("\r\n");
        for (int i=0; i < parts.length; i++) {
            for (int j=i+1; j < parts.length; j++) {
                String s1 = parts[i];
                String s2 = parts[j];
                int diffs = compare2Strings(s1, s2);
                if (diffs == 1) {
                    System.out.println("Found 2 boxes:");
                    System.out.println(s1);
                    System.out.println(s2);
                }
            }
        }
    }

    static int compare2Strings(String s1, String s2) {
        int diffs = 0;
        char[] chars1 = s1.toCharArray();
        char[] chars2 = s2.toCharArray();
        for (int i=0; i<chars1.length; i++) {
            if (chars1[i] != chars2[i]) {
                diffs++;
            }
        }
        return diffs;
    }
}
