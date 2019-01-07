package com.luckystar.advent2016;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * By: Alexey Matveev
 * Date: 28.12.2016
 * Time: 17:55
 */
public class Advent150 {

    public static void main(String[] args) throws Exception {
//        String input = new String(Files.readAllBytes(Paths.get(Advent120.class.getClassLoader().getResource("2016/input_15.txt").toURI())));
//        String[] rows = input.split("\n");
//        Pattern p = Pattern.compile("Disc #(\\d+) has (\\d+) positions; at time=0, it is at position (\\d+).");
//        List<Disc> discs = new ArrayList<>();
//        int startingTime = 0;
//        for (String row : rows) {
//            Matcher m = p.matcher(row);
//            if (m.find()) {
//                Integer discNumber = Integer.valueOf(m.group(1));
//                Integer positions = Integer.valueOf(m.group(2));
//                Integer startPosition = Integer.valueOf(m.group(3));
//                if (discNumber == 1) { // put first disc at position where pressing the button will allow capsule to fall
//                    startingTime = positions - startPosition - 1;
//                }
//                // wait till starting time
//                startPosition = startPosition + startingTime;
//                if (startPosition >= positions) {
//                    startPosition = startPosition - positions * (startPosition % positions);
//                }
//                discs.add(new Disc(discNumber, positions, startPosition));
//            }
//        }
//        // calculate period for each disc, the last period is the answer
//        int currentPeriod = 0;
//        for (Disc disc : discs) {
//            currentPeriod = calculatePeriod(currentPeriod, disc);
//        }

//        int d1 = 12;
//        int d2 = 12;
//        int d3 = 5;
//        int d4 = 3;
//        int d5 = 0;
//        int d6 = 1;
//        int x = 0;
//        while (!(d1 == d2 && d2 == d3 && d3 == d4 && d4 == d5 && d5 == d6)) {
//            if (x % 17 == 0) {
//                d1+=17 * x/17;
//            }
//            if (x % 19 == 0) {
//                d2+=19 * x/19;
//            }
//            if (x % 7 == 0) {
//                d3+=7 * x/7;
//            }
//            if (x % 13 == 0) {
//                d4+=13 * x/13;
//            }
//            if (x % 5 == 0) {
//                d5+=5 * x/5;
//            }
//            if (x % 3 == 0) {
//                d6+=3 * x/3;
//            }
//            x++;
//        }
//        System.out.println(x);

        int x = 0;
        while (!((x-11) % 17 == 0 // pos 5 -> pos 16
                && (x-9) % 19 == 0 // pos 8 -> pos 17
                && (x-3) % 7 == 0 // pos 1 -> pos 4
                && (x-2) % 13 == 0 // pos 7 -> pos 9
                && (x-4) % 5 == 0 // pos 1 -> pos 0
                && (x-0) % 3 == 0 // pos 0 -> pos 0
                && (x-4) % 11 == 0 // pos 0 -> pos 0 (for part 2)
        )) {
            x++;
        }
        System.out.println(x);

    }

    static int calculatePeriod(int existingPeriod, Disc disc) {
        return 0;
    }

    static class Disc {
        int number;
        int positions;
        int start;
        Disc(int number, int positions, int start) {
            this.number = number;
            this.positions = positions;
            this.start = start;
        }
    }

}
