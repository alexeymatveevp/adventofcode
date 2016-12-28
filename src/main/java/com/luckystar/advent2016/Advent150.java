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
        String input = new String(Files.readAllBytes(Paths.get(Advent120.class.getClassLoader().getResource("2016/input_15.txt").toURI())));
        String[] rows = input.split("\n");
        Pattern p = Pattern.compile("Disc #(\\d+) has (\\d+) positions; at time=0, it is at position (\\d+).");
        List<Disc> discs = new ArrayList<>();
        int startingTime = 0;
        for (String row : rows) {
            Matcher m = p.matcher(row);
            if (m.find()) {
                Integer discNumber = Integer.valueOf(m.group(1));
                Integer positions = Integer.valueOf(m.group(2));
                Integer startPosition = Integer.valueOf(m.group(3));
                if (discNumber == 1) { // put first disc at position where pressing the button will allow capsule to fall
                    startingTime = positions - startPosition - 1;
                }
                // wait till starting time
                startPosition = startPosition + startingTime;
                if (startPosition >= positions) {
                    startPosition = startPosition - positions * (startPosition % positions);
                }
                discs.add(new Disc(discNumber, positions, startPosition));
            }
        }
        // calculate period for each disc, the last period is the answer
        int currentPeriod = 0;
        for (Disc disc : discs) {
            currentPeriod = calculatePeriod(currentPeriod, disc);
        }

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
