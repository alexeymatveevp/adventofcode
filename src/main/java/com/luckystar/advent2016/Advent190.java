package com.luckystar.advent2016;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * By: Alexey Matveev
 * Date: 30.12.2016
 * Time: 13:11
 */
public class Advent190 {

    public static void main(String[] args) {
        int input = 3012210;
        Queue<Elf> elves = new ArrayDeque<>();
        for (int i=0; i< input; i++) {
            elves.add(new Elf(i+1, 1));
        }
        while (elves.size() != 1) {
            Elf robber = elves.poll();
            Elf victim = elves.poll();
            robber.presents = robber.presents + victim.presents;
            elves.add(robber);
        }
        Elf lastOne = elves.poll();
        System.out.println("Last elf: " + lastOne.number + " with " + lastOne.presents + " presents");
    }

    static class Elf {
        int number;
        int presents;
        Elf(int number, int presents) {
            this.number = number;
            this.presents = presents;
        }
    }

}
