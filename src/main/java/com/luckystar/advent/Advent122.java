package com.luckystar.advent;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * User: david
 * Date: 1/1/2016
 * Time: 7:48 PM
 */
public class Advent122 {

    public static void main(String[] args) throws Exception {
        String input = new String(Files.readAllBytes(Paths.get(Advent81.class.getClassLoader().getResource("input_12.txt").toURI())));
        System.out.println("start length: " + input.length());
        System.out.println("----");
        // cut the red objects
        int redIdx;
        StringBuilder parts = new StringBuilder();
        while ((redIdx = input.indexOf("red")) != -1) {
            int[] arrayBounds = getBoundIndices(redIdx, input, '[', ']');
            int[] objectBounds = getBoundIndices(redIdx, input, '{', '}');
            if (objectBounds == null && arrayBounds == null) {
                // object is not wrapped by anything - shouldn't happen
                System.out.println("alert");
                break;
            } else if (objectBounds != null && arrayBounds == null) {
                // object bound - cut the red
                System.out.println("# red cut: " + objectBounds[0] + " - " + objectBounds[1]);
                input = input.substring(0, objectBounds[0]) + input.substring(objectBounds[1]+1);
            } else if (objectBounds == null && arrayBounds != null) {
                // only array bound - skip the red
                System.out.println("# array bound: " + arrayBounds[0] + " - " + arrayBounds[1]);
                parts.append(input.substring(arrayBounds[0], arrayBounds[1]+1));
                input = input.substring(0, arrayBounds[0]) + input.substring(arrayBounds[1]+1);
            } else if (objectBounds != null && arrayBounds != null) {
                // find who is the master
                if (objectBounds[0] < arrayBounds[0] && objectBounds[1] > arrayBounds[1]) {
                    // array is the master
                    System.out.println("# array is the master: " + arrayBounds[0] + " - " + arrayBounds[1]);
                    String newPart = input.substring(arrayBounds[0], arrayBounds[1] + 1);
                    parts.append(newPart);
                    System.out.println(newPart);
                    input = input.substring(0, arrayBounds[0]) + input.substring(arrayBounds[1]+1);
                } else {
                    // object is the master
                    System.out.println("# object is the master: " + objectBounds[0] + " - " + objectBounds[1]);
                    if (objectBounds[1] == input.length()) {
                        input = input.substring(0, objectBounds[0]);
                    } else {
                        input = input.substring(0, objectBounds[0]) + input.substring(objectBounds[1]+1);
                    }
                }
            }

        }
        System.out.println("----");
        System.out.println("length after red cut: " + input.length());

        // look for digit (or minus), parse number till next digit
        // 0=48, 9=57
        int inputSum = calculateNumbers(input);
        System.out.println("----");
        System.out.println("input sum: " + inputSum);

        // calculate the parts
        int partsSum = calculateNumbers(parts.toString());
        System.out.println("parts sum: " + partsSum);

        System.out.println("----");
        System.out.println("total: " + (inputSum + partsSum));
    }

    static int calculateNumbers(String input) {
        int i=0;
        int total = 0;
        while (i < input.length()) {
            char c = input.charAt(i);
            if (checkDigit(c) || c == '-') {
                int j = i;
                boolean digit = true;
                while (digit) {
                    j++;
                    digit = checkDigit(input.charAt(j));
                }
                // parse
                Integer number = Integer.valueOf(input.substring(i, j));
//                System.out.println("# number found: " + number);
                total += number;
                i = j;
            } else {
                i++;
            }
        }
        return total;
    }

    static int[] getBoundIndices(int idx, String input, char boundStart, char boundEnd) {
        int startIdx = idx;
        int startFat = 1;
        while (startFat != 0 && startIdx >= 0) {
            if (input.charAt(startIdx) == boundStart) {
                startFat--;
            } else if (input.charAt(startIdx) == boundEnd) {
                startFat++;
            }
            startIdx--;
        }
        int endIdx = idx;
        int endFat = 1;
        while (endFat != 0 && endIdx < input.length()) {
            if (input.charAt(endIdx) == boundStart) {
                endFat++;
            } else if (input.charAt(endIdx) == boundEnd) {
                endFat--;
            }
            endIdx++;
        }
        if (startIdx < 0 || (endFat != 0 && endIdx >= input.length())) {
            // no bounds
            return null;
        }
        return new int[]{startIdx, endIdx};
    }

    static boolean checkDigit(char c) {
        return c >=48 && c <= 57;
    }

}
