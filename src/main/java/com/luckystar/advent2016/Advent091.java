package com.luckystar.advent2016;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: david
 * Date: 26.12.2016
 * Time: 19:58
 */
public class Advent091 {

    static Pattern p = Pattern.compile("(.*?)(\\((\\d+)x(\\d+)\\))");

    public static void main(String[] args) throws Exception {
        String input = new String(Files.readAllBytes(Paths.get(Advent091.class.getClassLoader().getResource("2016/input_9.txt").toURI())));
        input = input.trim();
        System.out.println("Original length: " + input.length());
        // cannot decompress the file because the decompressed size ~ 21.5 Gb
//        while (p.matcher(input).find()) {
//            input = decompress(input);
//        }
        // calculate the length
        long length = calculateDecompressedLength(input, 0L);
        System.out.println("Decompressed length: " + length);
    }

    static long calculateDecompressedLength(String input, long accumulator) {
        List<Part> parts = new ArrayList<>();
        boolean done = false;
        while (!done) {
            Matcher m = p.matcher(input);
            if (m.find()) {
                String start = m.group(1);
                accumulator += start.length();
                String multiplyString = input.substring(m.end(2), m.end(2) + Integer.valueOf(m.group(3)));
                Integer multiply = Integer.valueOf(m.group(4));
                parts.add(new Part(multiplyString, multiply));
                input = input.substring(m.end(2) + Integer.valueOf(m.group(3)));
            } else {
                done = true;
            }
        }
        accumulator += input.length();
        if (!parts.isEmpty()) {
            for (Part part : parts) {
                accumulator += calculateDecompressedLength(part.text, 0L) * part.quantity;
            }
        } else {
            System.out.println("dead end: " + accumulator + " " + input.length());
        }
        return accumulator;
    }

    static class Part {
        int quantity;
        String text;
        Part(String text, int quantity) {
            this.quantity = quantity;
            this.text = text;
        }
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
