package com.luckystar.advent2021;

import com.luckystar.InputLoader;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Advent07 {

    public static void main(String[] args) {
        String input = InputLoader.readInput("2021/input_7.txt");
        Integer[] crabs = Arrays.stream(input.split(",")).map(Integer::parseInt).toArray(Integer[]::new);

        int maxCrab = Arrays.stream(crabs).max(Integer::compareTo).get();
        ArrayList<Long> fuels = new ArrayList<>();
        for (int i = 0; i < maxCrab; i++) {
            long totalFuel = 0L;
            for (Integer crab : crabs) {
//                int fuel = fuelCalculatorPart1(i, crab);
                long fuel = fuelCalculatorPart2(i, crab);
                totalFuel += fuel;
            }
            fuels.add(totalFuel);
        }

        Long minFuel = fuels.stream().min(Long::compareTo).get();
        System.out.println(minFuel);
    }

    static int fuelCalculatorPart1(int start, int end) {
        return Math.abs(start - end);
    }

    static long fuelCalculatorPart2(int start, int end) {
        // 8, 3 = 5
        // (1 + 5) / 2 * 5 = 10
        int n = Math.abs(start - end);
        return ((1 + n)*n) / 2;
    }

}