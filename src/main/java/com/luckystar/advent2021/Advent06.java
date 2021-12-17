package com.luckystar.advent2021;

import com.luckystar.InputLoader;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Advent06 {

    public static void main(String[] args) {
        part1();
        part2();
    }

    static void part1() {
        String input = InputLoader.readInput("2021/input_6.txt");
        Integer[] fishes = Arrays.stream(input.split(",")).map(Integer::parseInt).toArray(Integer[]::new);

        for (int i=0; i<80; i++) {
            fishes = Arrays.stream(fishes).map(f -> f-1).toArray(Integer[]::new);
            int newFish = (int) Arrays.stream(fishes).filter(f -> f == -1).count();
            Stream<Integer> fishStream = Arrays.stream(fishes).map(f -> {
                if (f == -1) {
                    return 6;
                }
                return f;
            });
            Stream<Integer> newFishStream = IntStream.range(0, newFish).mapToObj(n -> 8);
            fishes = Stream.concat(fishStream, newFishStream).toArray(Integer[]::new);
        }

        System.out.println(fishes.length);
    }

    static void part2() {
        String input = InputLoader.readInput("2021/input_6.txt");
        Integer[] fishes = Arrays.stream(input.split(",")).map(Integer::parseInt).toArray(Integer[]::new);

        LinkedList<Long> generations = new LinkedList<>();
        Map<Integer, List<Integer>> groupedFish = Arrays.stream(fishes).collect(Collectors.groupingBy(Function.identity()));

        for (int i=0; i<9; i++) {
            List<Integer> ff = groupedFish.get(i);
            if (ff != null) {
                generations.add((long)ff.size());
            } else {
                generations.add(0L);
            }
        }

        for (int i=0; i<256; i++) {
            // move and add 6
            Long zero = generations.get(0);
            for (int j = 1; j < generations.size(); j++) {
                Long cur = generations.get(j);
                generations.set(j - 1, cur);
            }
            generations.set(generations.size() - 1, zero);
            Long six = generations.get(6);
            generations.set(6, six + zero);
        }

        System.out.println(generations.stream().reduce(Long::sum));

    }

}