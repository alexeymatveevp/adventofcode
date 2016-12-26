package com.luckystar.advent2016;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: david
 * Date: 23.12.2016
 * Time: 12:53
 */
public class Advent090 {

    static Pattern p = Pattern.compile("(.*?)(\\((\\d+)x(\\d+)\\))");

    public static void main(String[] args) throws Exception {
        String input = new String(Files.readAllBytes(Paths.get(Advent090.class.getClassLoader().getResource("2016/input_9.txt").toURI())));
        input = input.trim();
        System.out.println("Original length: " + input.length());
        input = decompress(input);
        System.out.println("Decompressed length: " + input.length());
    }

    /**
     * Unsolvable with my computer memory.
     * @param input
     * @return
     */
    static String decompress(String input) {
        boolean done = false;
        StringBuilder sb = new StringBuilder();
        while (!done) {
            Matcher m = p.matcher(input);
            if (m.find()) {
                sb.append(m.group(1));
                String multiplyString = input.substring(m.end(2), m.end(2) + Integer.valueOf(m.group(3)));
                for (int i=0; i < Integer.valueOf(m.group(4)); i++) {
                    sb.append(multiplyString);
                }
                input = input.substring(m.end(2) + Integer.valueOf(m.group(3)));
            } else {
                sb.append(input);
                done = true;
            }
        }
        return sb.toString();
    }
}
