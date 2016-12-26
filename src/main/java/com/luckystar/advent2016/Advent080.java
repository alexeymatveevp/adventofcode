package com.luckystar.advent2016;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: david
 * Date: 23.12.2016
 * Time: 11:18
 */
public class Advent080 {

    public static void main(String[] args) throws Exception {
        String input = new String(Files.readAllBytes(Paths.get(Advent080.class.getClassLoader().getResource("2016/input_8.txt").toURI())));
        String[] rows = input.split("\n");
        char[][] screen = {
            {'.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.'}
        };
        Pattern prect = Pattern.compile("rect (\\d+)x(\\d+)");
        Pattern protr = Pattern.compile("rotate row y=(\\d+) by (\\d+)");
        Pattern protc = Pattern.compile("rotate column x=(\\d+) by (\\d+)");
        List<Instruction> instructions = new ArrayList<>();
        for (String row : rows) {
            row = row.trim();
            Matcher mrect = prect.matcher(row);
            Matcher mrotr = protr.matcher(row);
            Matcher mrotc = protc.matcher(row);
            if (mrect.find()) {
                instructions.add(new RectInstruction(Integer.valueOf(mrect.group(1)), Integer.valueOf(mrect.group(2))));
            }
            if (mrotr.find()) {
                instructions.add(new RotateRowInstruction(Integer.valueOf(mrotr.group(1)), Integer.valueOf(mrotr.group(2))));
            }
            if (mrotc.find()) {
                instructions.add(new RotateColumnInstruction(Integer.valueOf(mrotc.group(1)), Integer.valueOf(mrotc.group(2))));
            }
        }

        for (Instruction instruction : instructions) {
            System.out.println("instruction: " + instruction.toString());
            instruction.process(screen);
            printScreen(screen);
            System.out.println();
        }

        int count = 0;
        for (int i=0; i<screen.length; i++) {
            for (int j=0; j<screen[0].length; j++) {
                if (screen[i][j] == '#') {
                    count++;
                }
            }
        }
        System.out.println("Lit count: " + count) ;


    }

    static void printScreen(char[][] screen) {
        for (char[] screenRows : screen) {
            for (char pixel : screenRows) {
                System.out.print(pixel);
            }
            System.out.println();
        }
    }

    interface Instruction {
        void process(char[][] screen);
    }

    static class RectInstruction implements Instruction {
        int x,y;
        RectInstruction(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public void process(char[][] screen) {
            for (int i=0; i<y; i++) {
                for (int j=0; j<x; j++) {
                    screen[i][j] = '#';
                }
            }
        }
        public String toString() {
            return this.getClass().getSimpleName() + " " + x + "x" + y;
        }
    }
    static class RotateRowInstruction implements Instruction {
        int x,rotate;
        RotateRowInstruction(int x, int rotate) {
            this.x = x;
            this.rotate = rotate;
        }
        public void process(char[][] screen) {
            char[] row = screen[x];
            char[] newRow = new char[row.length];
            for (int i=0; i<row.length; i++) {
                char c = row[i];
                int idx = i+rotate < row.length ? i+rotate
                        : (i+rotate) - row.length * ((i+rotate) / row.length);
                newRow[idx] = c;
            }
            screen[x] = newRow;
        }
        public String toString() {
            return this.getClass().getSimpleName() + " row=" + x + " rotate=" + rotate;
        }
    }
    static class RotateColumnInstruction implements Instruction {
        int y,rotate;
        RotateColumnInstruction(int y, int rotate) {
            this.y = y;
            this.rotate = rotate;
        }
        public void process(char[][] screen) {
            char[] col = new char[screen.length];
            for (int i=0; i<screen.length; i++) {
                col[i] = screen[i][y];
            }
            char[] newCol = new char[col.length];
            for (int i=0; i<col.length; i++) {
                char c = col[i];
                int idx = i+rotate < col.length ? i+rotate
                        : (i+rotate) - col.length * ((i+rotate) / col.length);
                newCol[idx] = c;
            }
            for (int i=0; i<screen.length; i++) {
                screen[i][y] = newCol[i];
            }
        }
        public String toString() {
            return this.getClass().getSimpleName() + " col=" + y + " rotate=" + rotate;
        }
    }
}
