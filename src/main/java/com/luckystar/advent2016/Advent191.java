package com.luckystar.advent2016;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * By: Alexey Matveev
 * Date: 30.12.2016
 * Time: 13:22
 */
public class Advent191 {

    public static void main(String[] args) {
        int input = 3012210;
//        Elf[] elves = new Elf[input];
//        for (int i=0; i< input; i++) {
//            elves[i] = new Elf(i+1, 1);
//        }
//        int currentIndex=0, size = elves.length;
//        int victimShift = 0;
//        Elf lastRobber = null;
//        while (victimShift != 1) {
//            while (elves[currentIndex] == null) currentIndex++;
//            Elf robber = elves[currentIndex];
//            if (size % 2 == 0) {
//                victimShift = size/2;
//            } else {
//                victimShift = size/2;
//            }
//            // roll till
//            int shifted = 0, shiftIndex = currentIndex;
//            while (shifted != victimShift) {
//                shiftIndex++;
//                if (elves[shiftIndex] != null) {
//                    shifted++;
//                }
//            }
//            Elf victim = elves[shiftIndex];
//            robber.presents = robber.presents + victim.presents;
//            lastRobber = robber;
//            elves[shiftIndex] = null;
//            if (currentIndex == elves.length) {
//                currentIndex = 0;
//            } else {
//                currentIndex++;
//            }
//            size--;
//        }
//        System.out.println("Last elf: " + lastRobber.number + " with " + lastRobber.presents + " presents");
        // hacky solution from reddit https://www.reddit.com/r/adventofcode/comments/5j4lp1/2016_day_19_solutions/?st=ixbq23es&sh=17f827d1
        int i=1;
        while (i*3 < input) i *= 3;
        System.out.println(input - i);
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
