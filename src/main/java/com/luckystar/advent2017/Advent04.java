package com.luckystar.advent2017;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by ksavina on 12/4/2017.
 */
public class Advent04 {

    public static void main(String[] args) throws Exception {
        String input = new String(Files.readAllBytes(Paths.get(Advent04.class.getClassLoader().getResource("2017/input_4.txt").toURI())));
        String[] rows = input.split("\r\n");
        int wrongCount = 0;
        for (String row : rows) {
            String[] keyphrases = row.split(" ");
            Set<String> set = new HashSet<>();
            for (String keyphrase : keyphrases) {
                if (set.contains(keyphrase)) {
                    wrongCount++;
                    break;
                } else {
                    set.add(keyphrase);
                }
            }
        }
        System.out.println(rows.length - wrongCount);

        // part 2
        wrongCount = 0;
        for (String row : rows) {
            String[] keyphrases = row.split(" ");
            Set<String> set = new HashSet<>();
            here:
            for (int i=0; i < keyphrases.length-1; i++) {
                for (int j=i+1; j < keyphrases.length; j++) {
                    if (isAnagram(keyphrases[i], keyphrases[j])) {
                        wrongCount++;
                        break here;
                    }
                }
            }
        }
        System.out.println(rows.length - wrongCount);
    }

    private static boolean isAnagram(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }
        List<Character> l1 = toOrderedList(s1.toCharArray());
        List<Character> l2 = toOrderedList(s2.toCharArray());
        return l1.equals(l2);
    }

    private static List<Character> toOrderedList(char[] chars) {
        List<Character> result = new ArrayList<>();
        for (char aChar : chars) {
            result.add(aChar);
        }
        Collections.sort(result);
        return result;
    }
}
