package com.luckystar.advent2016;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * User: david
 * Date: 22.12.2016
 * Time: 17:08
 */
public class Advent021 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        String input = new String(Files.readAllBytes(Paths.get(Advent021.class.getClassLoader().getResource("2016/input_2.txt").toURI())));
        String[] rows = input.split("\n");
        String code = "";
        for (String row : rows) {
            RhombusFace rf = new RhombusFace();
            for (char c : row.toCharArray()) {
                if (c == 'U') {
                    rf.moveUp();
                } else if (c == 'D') {
                    rf.moveDown();
                } else if (c == 'R') {
                    rf.moveRight();
                } else if (c == 'L') {
                    rf.moveLeft();
                }
            }
            code = code + rf.getCurrent();
        }
        System.out.println(code);
    }

    static class RhombusFace {
        String[][] matrix = {
            {null, null, "1", null, null},
            {null, "2" , "3",  "4", null},
            {"5" , "6" , "7",  "8",  "9"},
            {null, "A" , "B",  "C", null},
            {null, null, "D", null, null}
        };
        int x = 2, y = 0;

        void moveUp() {
            if (x != 0 && matrix[x-1][y] != null) {
                x = x - 1;
            }
        }
        void moveDown() {
            if (x != matrix.length-1 && matrix[x+1][y] != null) {
                x = x + 1;
            }
        }
        void moveRight() {
            if (y != matrix[0].length-1 && matrix[x][y+1] != null) {
                y = y + 1;
            }
        }
        void moveLeft() {
            if (y != 0 && matrix[x][y-1] != null) {
                y = y - 1;
            }
        }
        String getCurrent() {
            return matrix[x][y];
        }
    }

}
