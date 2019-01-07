package com.luckystar.advent2016;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ksavina on 11/21/2017.
 */
public class Advent220 {

    public static final Pattern NAME_PATTERN = Pattern.compile("/dev/grid/node-x(\\d+)-y(\\d+)");

    public static void main(String[] args) throws Exception {
        String input = new String(Files.readAllBytes(Paths.get(Advent080.class.getClassLoader().getResource("2016/input_22.txt").toURI())));
        String[] rows = input.split("\n");
        List<Cell> cellList = new ArrayList<>();
        int maxX = 0, maxY = 0;
        for (int i=2; i<rows.length; i++) {
            String row = rows[i];
            row = row.replaceAll("( )+", " "); // /dev/grid/node-x35-y25 85T 73T 12T 85%
            String[] parts = row.split(" ");

            String name = parts[0];
            Matcher m = NAME_PATTERN.matcher(name);
            m.matches();
            int x = Integer.valueOf(m.group(1));
            int y = Integer.valueOf(m.group(2));
            if (x > maxX) maxX = x;
            if (y > maxY) maxY = y;

            int size = Integer.valueOf(parts[1].substring(0, parts[1].indexOf("T")));
            int used = Integer.valueOf(parts[2].substring(0, parts[2].indexOf("T")));
            int avail = Integer.valueOf(parts[3].substring(0, parts[3].indexOf("T")));
            int usePercent = Integer.valueOf(parts[4].substring(0, parts[4].indexOf("%")));

//            System.out.println(x + " " + y + " " + size + " " + used + " " + avail + " " + usePercent);

            cellList.add(new Cell(x, y, size, used, avail));
        }

        // part 1
        int viablePairsCount = 0;
        for (Cell cell : cellList) {
            for (Cell r : cellList) {
                if (cell.used != 0 && cell != r && cell.used <= r.avail) {
                    viablePairsCount++;
                }
            }
        }
        System.out.println(viablePairsCount);

        // part 2
        Cell[][] cellMap = new Cell[maxY + 1][];
        for (Cell cell : cellList) {
            Cell[] row = cellMap[cell.y];
            if (row == null) {
                row = new Cell[maxX + 1];
                cellMap[cell.y] = row;
            }
            row[cell.x] = cell;
        }
        for (Cell[] row : cellMap) {
            for (Cell cell : row) {
                if (cell.x == maxX && cell.y == 0) {
                    System.out.print("G");
                } else if (cell.x == 0 && cell.y == 0) {
                    System.out.print("!");
                } else if (cell.used == 0) {
                    System.out.print("=");
                } else if (cell.size > 100) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
        // calculated visually :)
        // 94 is the path from = to G
        // 170 is the path of G to ! wo the last step (1)
        // 94 + 170 + 1 = 265

    }

    static class Cell {
        int x;
        int y;
        int size;
        int used;
        int avail;

        Cell(int x, int y, int size, int used, int avail) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.used = used;
            this.avail = avail;
        }
    }
}
