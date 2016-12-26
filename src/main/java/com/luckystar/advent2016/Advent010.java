package com.luckystar.advent2016;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * User: david
 * Date: 03.12.2016
 * Time: 12:38
 */
public class Advent010 {
    public static void main(String[] args) throws Exception {
        String input = new String(Files.readAllBytes(Paths.get(Advent010.class.getClassLoader().getResource("2016/input_1.txt").toURI())));
        String[] instructions = input.split(", ");
        Compass currentDirection = Compass.N;
        int x=0, y=0;
        for (int i=0; i < instructions.length; i++) {
            String instruction = instructions[i];
            Direction direction = Direction.valueOf(instruction.substring(0, 1));
            int steps = Integer.valueOf(instruction.substring(1));
            switch (currentDirection) {
                case N: {
                    if (direction == Direction.R) {
                        x += steps;
                        currentDirection = Compass.E;
                    } else {
                        x -= steps;
                        currentDirection = Compass.W;
                    }
                    break;
                }
                case E: {
                    if (direction == Direction.R) {
                        y -= steps;
                        currentDirection = Compass.S;
                    } else {
                        y += steps;
                        currentDirection = Compass.N;
                    }
                    break;
                }
                case S: {
                    if (direction == Direction.R) {
                        x -= steps;
                        currentDirection = Compass.W;
                    } else {
                        x += steps;
                        currentDirection = Compass.E;
                    }
                    break;
                }
                case W: {
                    if (direction == Direction.R) {
                        y += steps;
                        currentDirection = Compass.N;
                    } else {
                        y -= steps;
                        currentDirection = Compass.S;
                    }
                    break;
                }
            }
        }
        System.out.println("Coordinates: x=" + x + " y=" + y);
        System.out.println("Taxicab distance: " + (Math.abs(x) + Math.abs(y)));
    }

    enum Compass {
        N, S, E, W
    }

    enum Direction {
        R, L
    }
}
