package com.luckystar.advent2017;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by ksavina on 12/3/2017.
 */
public class Advent02 {

    public static void main(String[] args) throws Exception {
        String input = new String(Files.readAllBytes(Paths.get(Advent01.class.getClassLoader().getResource("2017/input_2.txt").toURI())));
        String[] rows = input.split("\n");
        int total = 0;
        for (String row : rows) {
            String[] numbers = row.split("\t");
            int min = Integer.MAX_VALUE, max = 0;
            for (String aNumber : numbers) {
                aNumber = aNumber.trim();
                Integer num = Integer.valueOf(aNumber);
                if (num > max) {
                    max = num;
                }
                if (num < min) {
                    min = num;
                }
            }
            total += max - min;
        }
        System.out.println(total);

        // part 2
        total = 0;
        for (String row : rows) {
            String[] numbers = row.split("\t");
            int min = Integer.MAX_VALUE, max = 0;
            here:
            for (int i=0; i<numbers.length; i++) {
                Integer num1 = Integer.valueOf(numbers[i].trim());
                for (int j=0; j<numbers.length; j++) {
                    Integer num2 = Integer.valueOf(numbers[j].trim());
                    if (i != j && num1 % num2 == 0) {
                        total += num1 / num2;
                        break here;
                    }
                }
            }
        }
        System.out.println(total);

    }

}
