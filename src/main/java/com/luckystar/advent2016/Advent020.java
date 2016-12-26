package com.luckystar.advent2016;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * User: david
 * Date: 22.12.2016
 * Time: 12:03
 */
public class Advent020 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        String input = new String(Files.readAllBytes(Paths.get(Advent020.class.getClassLoader().getResource("2016/input_2.txt").toURI())));
        String[] rows = input.split("\n");
        String code = "";
        for (String row : rows) {
            CodeFace cf = new CodeFace();
            for (char c : row.toCharArray()) {
                if (c == 'U') {
                    cf.moveUp();
                } else if (c == 'D') {
                    cf.moveDown();
                } else if (c == 'R') {
                    cf.moveRight();
                } else if (c == 'L') {
                    cf.moveLeft();
                }
            }
            code = code + cf.currentState;
        }
        System.out.println(code);
    }

    static class CodeFace {
        int currentState = 5;
        void moveUp() {
            if (currentState - 3 > 0) {
                currentState = currentState - 3;
            }
        }
        void moveDown() {
            if (currentState + 3 <= 9) {
                currentState = currentState + 3;
            }
        }
        void moveRight() {
            if (currentState != 3 && currentState != 6 && currentState != 9) {
                currentState = currentState + 1;
            }
        }
        void moveLeft() {
            if (currentState != 1 && currentState != 4 && currentState != 7) {
                currentState = currentState - 1;
            }
        }
    }

}
