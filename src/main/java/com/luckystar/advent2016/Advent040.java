package com.luckystar.advent2016;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * User: david
 * Date: 22.12.2016
 * Time: 18:00
 */
public class Advent040 {

    public static void main(String[] args) throws Exception {
        String input = new String(Files.readAllBytes(Paths.get(Advent040.class.getClassLoader().getResource("2016/input_4.txt").toURI())));
        Pattern sectorP = Pattern.compile("(\\d+)");
        Pattern checksumP = Pattern.compile("\\[(\\w+)\\]");
        String[] rows = input.split("\n");
        int sectorIdSum = 0;
        for (String row : rows) {
            // calculate top letters
            String letters = row.substring(0, row.lastIndexOf('-')).replaceAll("-", "");
            List<Character> topLetters = topLetters(letters);
            // get and check the checksum
            Matcher checksumM = checksumP.matcher(row);
            checksumM.find();
            String checksum = checksumM.group(1);
            if (checkChecksum(checksum, topLetters)) {
                // find sectorId
                Matcher sectorM = sectorP.matcher(row);
                sectorM.find();
                Integer sectorId = Integer.valueOf(sectorM.group());
                sectorIdSum += sectorId;
            }
        }
        System.out.println("SectorId's sum: " + sectorIdSum);
    }

    static List<Character> topLetters(String letters) {
        Map<Character, Integer> map = new TreeMap<>();
        for (Character c : letters.toCharArray()) {
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
        return sortedLetters;
    }

    static boolean checkChecksum(String checksum, List<Character> topLetters) {
        for (int i=0; i < checksum.length(); i++) {
            Character c = topLetters.get(i);
            if (checksum.charAt(i) != c) {
                return false;
            }
        }
        return true;
    }
}
