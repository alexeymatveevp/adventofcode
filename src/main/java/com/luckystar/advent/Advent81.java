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
public class Advent81 {

    public static void main(String[] args) throws IOException, URISyntaxException {
        String input = new String(Files.readAllBytes(Paths.get(Advent81.class.getClassLoader().getResource("input_8.txt").toURI())));
        String[] rows = input.split("\r\n");
        int totalCode = 0;
        int totalMemory = 0;
        for (String row : rows) {
            int length = row.length();
            totalCode += length;
            int currentIndex = 0;
            int currentMemory = 0;
            while (currentIndex < length) {
                char c = row.charAt(currentIndex);
                if (c == '\\') {
                    if (currentIndex != length - 1) {
                        char next = row.charAt(currentIndex + 1);
                        if (next == '\\' || next == '\"') {
                            currentIndex += 2;
                            currentMemory++;
                        } else if (next == 'x') {
                            currentIndex += 4;
                            currentMemory++;
                        }
                    } else {
                        currentIndex++;
                    }
                } else {
                    currentIndex++;
                    currentMemory++;
                }
            }
            currentMemory -= 2; // for the surrounding ""
            totalMemory += currentMemory;
            System.out.printf("%-50s %5s", row, currentMemory);
            System.out.println();
        }
        System.out.println("---------------");
        System.out.printf("%-15s %4s", "Total code: ", totalCode);
        System.out.println();
        System.out.printf("%-15s %4s", "Total memory: ", totalMemory);
        System.out.println();
        System.out.printf("%-15s %4s", "Diff: ", totalCode - totalMemory);
    }

}
