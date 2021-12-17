package com.luckystar.advent2021;

import com.luckystar.InputLoader;

import java.util.*;

/**
 * Created by Alexey Matveev.
 */
public class Advent03 {
    public static void main(String[] args) {
        String input = InputLoader.readInput("2021/input_3.txt");
        String[] split = input.split("\r\n");
        int sum = 0;
        // 0 0 -2 -1 5
        int[] result = new int[split[0].toCharArray().length];

        for (String s : split) {
            char[] chars = s.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char c = chars[i];
                int b = Character.getNumericValue(c);
                int increment = b == 1 ? 1 : -1;
                result[i] = result[i] + increment;
            }
        }

        Optional<String> gamma$ = Arrays.stream(result).mapToObj(n -> n > 0 ? "1" : "0").reduce((left, right) -> left + right);
        Optional<String> epsilon$ = Arrays.stream(result).mapToObj(n -> n > 0 ? "0" : "1").reduce((left, right) -> left + right);
        int gamma = Integer.parseInt(gamma$.get(), 2);
        int epsilon = Integer.parseInt(epsilon$.get(), 2);

        System.out.println(gamma);
        System.out.println(epsilon);
        System.out.println(gamma * epsilon);

        System.out.println("\n\nPart 2");
        int oxygen = calculatePart2Rating(split, 1, 0);
        int co2Scrubber = calculatePart2Rating(split, 0, 1);
        System.out.println(oxygen);
        System.out.println(co2Scrubber);
        System.out.println(oxygen*co2Scrubber);
    }

    private static int calculatePart2Rating(String[] split, int mainCharacter, int nonMainCharacter) {
        List<String> list = new LinkedList<>(Arrays.asList(split));
        for (int i = 0; i < split[0].toCharArray().length; i++) {
            final int ii = i;
            Optional<Integer> reduce = list.stream().map(s -> Character.getNumericValue(s.charAt(ii))).reduce(Integer::sum);
            int ones = reduce.get();
            int zeros = list.size() - ones;
            int mc = ones >= zeros ? mainCharacter : nonMainCharacter;
            if (mainCharacter == 0) {
                mc = zeros <= ones ? mainCharacter : nonMainCharacter;
            }
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                String s = it.next();
                if (Character.getNumericValue(s.charAt(i)) != mc) {
                    it.remove();
                }
            }
            if (list.size() == 1) {
                break;
            }
        }
        System.out.println(list.size());
        System.out.println(list.get(0));
        return Integer.parseInt(list.get(0), 2);
    }

}
