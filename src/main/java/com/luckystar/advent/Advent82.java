package com.luckystar.advent;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * User: david
 * Date: 12/21/2015
 * Time: 11:52 PM
 */
public class Advent82 {

    public static void main(String[] args) throws IOException, URISyntaxException {
        String input = new String(Files.readAllBytes(Paths.get(Advent82.class.getClassLoader().getResource("input_8.txt").toURI())));
        String[] rows = input.split("\r\n");
        int totalCode = 0;
        int totalEncoded = 0;
        for (String row : rows) {
            int length = row.length();
            totalCode += length;
            int specialLength = length - row.replace("\\", "").replace("\"", "").length();
            totalEncoded += length + specialLength + 2; // 2 for surrounding ""
        }
        System.out.println("---------------");
        System.out.printf("%-15s %4s", "Total code: ", totalCode);
        System.out.println();
        System.out.printf("%-15s %4s", "Total encoded: ", totalEncoded);
        System.out.println();
        System.out.printf("%-15s %4s", "Diff: ", totalEncoded - totalCode);
    }

}
