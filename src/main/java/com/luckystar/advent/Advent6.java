package com.luckystar.advent;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * By: Alexey Matveev
 * Date: 23.12.2015
 * Time: 13:26
 */
public class Advent6 {

    public static void main(String[] args) throws IOException, URISyntaxException {
        String input = new String(Files.readAllBytes(Paths.get(Advent6.class.getClassLoader().getResource("input_6.txt").toURI())));
        String[] rows = input.split("\r\n");
        List<Command> commands = new ArrayList<>();
        for (String row : rows) {
            String[] parts = row.split(" ");
            if (parts[0].equals("toggle")) {
                commands.add(new ToggleCommand(getCoord(parts[1]), getCoord(parts[3])));
            } else if (parts[1].equals("off")) {
                commands.add(new OffCommand(getCoord(parts[2]), getCoord(parts[4])));
            } else {
                commands.add(new OnCommand(getCoord(parts[2]), getCoord(parts[4])));
            }
        }

        int[][] lights = new int[1000][1000];
        for (Command command : commands) {
            for (int i=0; i<1000; i++) {
                for (int j=0; j<1000; j++) {
                    if (i >= command.start.x && j >= command.start.y && i <=command.end.x && j <= command.end.y) {
                        lights[i][j] = command.getNewValue(lights[i][j]);
                    }
                }
            }
        }

        int count = 0;
        for (int i=0; i<1000; i++) {
            for (int j=0; j<1000; j++) {
                System.out.print(lights[i][j]);
                count += lights[i][j];
            }
            System.out.println();
        }
        System.out.println("--------");
        System.out.println("Lights: " + count);

    }

    public static Coord getCoord(String part) {
        String[] c = part.split(",");
        return new Coord(Integer.valueOf(c[0]), Integer.valueOf(c[1]));
    }

    static class Coord {
        int x;

        int y;

        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static abstract class Command {
        Coord start;
        Coord end;

        abstract int getNewValue(int current);
    }

    static class ToggleCommand extends Command {
        public ToggleCommand(Coord start, Coord end) {
            this.start = start;
            this.end = end;
        }

        @Override
        int getNewValue(int current) {
            return current + 2;
        }
    }

    static class OffCommand extends Command {
        public OffCommand(Coord start, Coord end) {
            this.start = start;
            this.end = end;
        }

        @Override
        int getNewValue(int current) {
            if (current == 0) {
                return 0;
            } else {
                return current - 1;
            }
        }
    }

    static class OnCommand extends Command {
        public OnCommand(Coord start, Coord end) {
            this.start = start;
            this.end = end;
        }

        @Override
        int getNewValue(int current) {
            return current + 1;
        }
    }

}
