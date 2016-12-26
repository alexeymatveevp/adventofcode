package com.luckystar.advent2016;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * User: david
 * Date: 22.12.2016
 * Time: 23:34
 */
public class Advent060 {

    public static void main(String[] args) throws Exception {
        String input = new String(Files.readAllBytes(Paths.get(Advent060.class.getClassLoader().getResource("2016/input_6.txt").toURI())));
        String[] rows = input.split("\n");
        char[][] cols = new char[rows[0].length()][];
        for (int i=0; i<rows.length; i++) {
            for (int j=0; j<rows[i].length(); j++) {
                if (cols[j] == null) {
                    cols[j] = new char[rows.length];
                }
                cols[j][i] = rows[i].charAt(j);
            }
        }

        StringBuilder sb = new StringBuilder();
        for (char[] col : cols) {
            sb.append(topLetter(col));
        }
        System.out.println("message: " + sb.toString());

    }

    static Character topLetter(char[] letters) {
        Map<Character, Integer> map = new TreeMap<>();
        for (Character c : letters) {
            Integer number = map.get(c);
            if (number == null) {
                map.put(c, 1);
            } else {
                map.put(c, number+1);
            }
        }
        List<Character> sortedLetters = map.entrySet().stream().sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        return sortedLetters.get(0);
    }
}
