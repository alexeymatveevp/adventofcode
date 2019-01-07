package com.luckystar.advent2018_1;

import com.luckystar.advent2016.Advent011;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Alexey Matveev on 26.12.2018
 */
public class Advent02 {

    public static void main(String[] args) throws Exception {
        String input = new String(Files.readAllBytes(Paths.get(Advent011.class.getClassLoader().getResource("2018/input_2.txt").toURI())));
        String[] parts = input.split("\r\n");
        int twoCount = 0;
        int threeCount = 0;
        for (String part : parts) {
            char[] chars = part.toCharArray();
            Map<Character, Integer> map = map(chars);
            Optional<Integer> two = map.values().stream().filter(count -> count == 2).findAny();
            if (two.isPresent()) {
                twoCount++;
            }
            Optional<Integer> three = map.values().stream().filter(count -> count == 3).findAny();
            if (three.isPresent()) {
                threeCount++;
            }
        }
        System.out.println(twoCount * threeCount);
    }

    static Map<Character, Integer> map(char[] chars) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : chars) {
            map.putIfAbsent(c, 0);
            map.put(c, map.get(c)+1);
        }
        return map;
    }
}
