package com.luckystar.advent2016;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * By: Alexey Matveev
 * Date: 30.12.2016
 * Time: 14:41
 */
public class Advent200 {

    public static void main(String[] args) throws Exception {
        String input = new String(Files.readAllBytes(Paths.get(Advent080.class.getClassLoader().getResource("2016/input_20.txt").toURI())));
        String[] rows = input.split("\n");
        Set<Range> ranges = new HashSet<>();
        Long max = 4294967295L;
        for (String row : rows) {
            row = row.trim();
            int i = row.indexOf("-");
            Long start = Long.valueOf(row.substring(0, i));
            Long end = Long.valueOf(row.substring(i+1));
            ranges.add(new Range(start, end));
        }
        // find the least IP not in deny lists
        Long i=0L;
        Long leastIP = null;
        int ipsFound = 0;
        while (i<max) {
            final Long guess = i;
            Optional<Range> any = ranges.stream().filter(r -> r.start <= guess && guess <= r.end).findAny();
            if (any.isPresent()) {
                Range range = any.get();
                i = range.end;
            } else {
                // found
                if (leastIP == null) {
                    leastIP = i;
                }
                ipsFound++;
            }
            i++;
        }
        System.out.println("Found least IP: " + leastIP); // part 1
        System.out.println("IPs count: " + ipsFound); // part 2
    }

    static class Range {
        Long start;
        Long end;

        public Range(Long start, Long end) {
            this.start = start;
            this.end = end;
        }
    }

}
