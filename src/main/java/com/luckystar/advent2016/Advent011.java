package com.luckystar.advent2016;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: david
 * Date: 03.12.2016
 * Time: 13:02
 */
public class Advent011 {
    public static void main(String[] args) throws Exception {
        String input = new String(Files.readAllBytes(Paths.get(Advent011.class.getClassLoader().getResource("2016/input_1.txt").toURI())));
        String[] instructions = input.split(", ");
        Compass currentDirection = Compass.N;
        int x=0, y=0, i=0;
        Set<Coord> alreadyVisited = new HashSet<>();
        alreadyVisited.add(new Coord(0,0));
        here:
        for (; i < instructions.length; i++) {
            String instruction = instructions[i];
            Direction direction = Direction.valueOf(instruction.substring(0, 1));
            int steps = Integer.valueOf(instruction.substring(1));
            // in 2nd task do iteration step by step
            Compass newCompass = null;
            for (int j = 0; j < steps; j++) {
                switch (currentDirection) {
                    case N: {
                        if (direction == Direction.R) {
                            x++;
                            newCompass = Compass.E;
                        } else {
                            x--;
                            newCompass = Compass.W;
                        }
                        break;
                    }
                    case E: {
                        if (direction == Direction.R) {
                            y--;
                            newCompass = Compass.S;
                        } else {
                            y++;
                            newCompass = Compass.N;
                        }
                        break;
                    }
                    case S: {
                        if (direction == Direction.R) {
                            x--;
                            newCompass = Compass.W;
                        } else {
                            x++;
                            newCompass = Compass.E;
                        }
                        break;
                    }
                    case W: {
                        if (direction == Direction.R) {
                            y++;
                            newCompass = Compass.N;
                        } else {
                            y--;
                            newCompass = Compass.S;
                        }
                        break;
                    }
                }

                // check already visited
                Coord newLocation = new Coord(x,y);
                if (!alreadyVisited.contains(newLocation)) {
                    alreadyVisited.add(newLocation);
                } else {
                    break here;
                }
            }
            currentDirection = newCompass;
        }
        System.out.println("Coordinates: x=" + x + " y=" + y);
        System.out.println("Twice at step=" + i + " out of " + instructions.length);
        System.out.println("Taxicab distance: " + (Math.abs(x) + Math.abs(y)));
    }

    enum Compass {
        N, S, E, W
    }

    enum Direction {
        R, L
    }

    static class Coord {
        int x;
        int y;
        Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Coord coord = (Coord) o;

            if (x != coord.x) return false;
            return y == coord.y;

        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }
    
}
