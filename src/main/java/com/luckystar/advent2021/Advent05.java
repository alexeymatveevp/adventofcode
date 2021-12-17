package com.luckystar.advent2021;

import com.luckystar.InputLoader;
import kotlin.ranges.IntRange;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Advent05 {

    public static void main(String[] args) {
        String input = InputLoader.readInput("2021/input_5.txt");
        String[] lines = input.split("\n");
        Line[] steamLines = Arrays.stream(lines).map(l -> l.split(" -> ")).map(p -> {
            String[] from = p[0].split(",");
            String[] to = p[1].split(",");
            Line line = new Line();
            line.from = new Coord(Integer.parseInt(from[0]), Integer.parseInt(from[1]));
            line.to = new Coord(Integer.parseInt(to[0]), Integer.parseInt(to[1]));
            return line;
        }).toArray(Line[]::new);

        int maxX = Arrays.stream(steamLines).flatMapToInt(line -> IntStream.of(line.to.x, line.from.x)).max().getAsInt();
        int maxY = Arrays.stream(steamLines).flatMapToInt(line -> IntStream.of(line.to.y, line.from.y)).max().getAsInt();

        int[][] data = new int[maxX + 1][];
        IntStream.range(0, maxY + 1).forEach(i -> data[i] = new int[maxY + 1]);

        Arrays.stream(steamLines).forEach(line -> {
            if (line.isHorizontal()) {
                int[] row = data[line.from.y];
                int min = Math.min(line.from.x, line.to.x);
                int max = Math.max(line.from.x, line.to.x);
                IntStream.range(min, max + 1).forEach(x -> row[x]++);
            } else if (line.checkDiagonal()) {
                int[] xes = bidirectionalRange(line.from.x, line.to.x).toArray();
                int[] yes = bidirectionalRange(line.from.y, line.to.y).toArray();
                if (xes.length != yes.length) {
                    throw new RuntimeException("Ahhhh");
                }
                for (int i=0; i<xes.length; i++) {
                    data[yes[i]][xes[i]]++;
                }
            } else {
                int min = Math.min(line.from.y, line.to.y);
                int max = Math.max(line.from.y, line.to.y);
                IntStream.range(min, max + 1).forEach(y -> data[y][line.to.x]++);
            }
        });


        long count = Arrays.stream(data).flatMapToInt(Arrays::stream).filter(n -> n >= 2).count();
        System.out.println(count);
    }

    static IntStream bidirectionalRange(int start, int end) {
        if (start >= end) {
            return IntStream.range(end, start + 1)
                    .map(i -> start + 1 + (end - 1 - i));
        } else {
            return IntStream.range(start, end + 1);
        }
    }

}

class Line {
    Coord from;
    Coord to;

    boolean checkDiagonal() {
        return from.x != to.x && from.y != to.y;
    }

    boolean isHorizontal() {
        return from.y == to.y;
    }

    @Override
    public String toString() {
        return "Line{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}

class Coord {
    int x;
    int y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coord{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}