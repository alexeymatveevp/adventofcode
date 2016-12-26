package com.luckystar.advent2016;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: david
 * Date: 22.12.2016
 * Time: 19:10
 */
public class Advent041 {

    public static void main(String[] args) throws Exception {
        String input = new String(Files.readAllBytes(Paths.get(Advent040.class.getClassLoader().getResource("2016/input_4.txt").toURI())));
        Pattern sectorP = Pattern.compile("(\\d+)");
        String[] rows = input.split("\n");
        System.out.println("a: " + (int)'a');
        System.out.println("z: " + (int)'z');
        System.out.println("A: " + (int)'A');
        System.out.println("Z: " + (int)'Z');
        System.out.println("-: " + (int)'-');
        System.out.println(" : " + (int)' ');
        int A = 65, Z = 90, a = 97, z = 122;
        int gapSize = a - Z - 1; // 6
        int phantomEndIndex = z - gapSize;
        int cycle = phantomEndIndex - A + 1;
        // calculate index from 65 to 115 (phantom end) and then add gap size to result
        Integer sectorId = null;
        for (String row : rows) {
            Matcher sectorM = sectorP.matcher(row);
            sectorM.find();
            Integer caesarShift = Integer.valueOf(sectorM.group());

            String encoded = row.substring(0, row.indexOf(caesarShift+""));
            StringBuilder sb = new StringBuilder();
            for (int code : encoded.toCharArray()) {
                if (code == (int)'-') {
                    code = 32;
                } else {
                    // normalize the gap between Z - a
                    if (code > Z) {
                        code = code - gapSize;
                    }
                    int left = caesarShift - cycle * (caesarShift / cycle);
                    if ((code - A) + left >= cycle) {
                        code = A + (left - (phantomEndIndex - code) - 1);
                    } else {
                        code = code + left;
                    }
                    // return the gap back
                    if (code > Z) {
                        code = code + gapSize;
                    }
                }
//                System.out.print(code + " ");
                sb.append((char)code);
            }
            System.out.println(sb.toString());
            if (sb.toString().toLowerCase().contains("northpole object")) {
                sectorId = caesarShift;
            }
        }
        System.out.println("---");
        System.out.println("SectorId with North Pole objects: " + sectorId);
    }
}
