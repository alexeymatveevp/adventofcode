package com.luckystar.advent2021;

import com.luckystar.InputLoader;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by Alexey Matveev.
 */
public class Advent04 {
    public static void main(String[] args) {
        String input = InputLoader.readInput("2021/input_4.txt");
        String[] split = input.split("\r\n");

        int[] numbers = Arrays.stream(split[0].split(",")).mapToInt(Integer::parseInt).toArray();
        List<Board> boards = new ArrayList<>();
        for (int i=2; i<split.length; i++) {
            int k = 0;
            Board board = new Board();
            boards.add(board);
            while (k < 5) {
                String[] split1 = split[i].trim().split("\\s+");
                int[] row = Arrays.stream(split1).mapToInt(Integer::parseInt).toArray();
                board.addRow(k, row);
                k++;
                i++;
            }
        }

        // lets do this
        ArrayList<Integer> winningBoards = new ArrayList<>();
        for (int number : numbers) {
            for (Board board : boards) {
                board.newNumberCalled(number);
                if (!board.won && board.checkVictory()) {
                    winningBoards.add(board.calculateNonMatchedSum() * number);
                    board.won = true;
                }
            }
        }

        System.out.println("Winning boards size: " + winningBoards.size());
        System.out.println("First winning: " + winningBoards.get(0));
        System.out.println("Last winning: " + winningBoards.get(winningBoards.size() - 1));

    }
}

class Board {
    int[][] data = new int[5][];
    int[][] checked = new int[5][];
    boolean won = false;

    void addRow(int index, int[] row) {
        this.data[index] = row;
        this.checked[index] = new int[row.length];
    }

    void newNumberCalled(int num) {
        for (int i=0; i < data.length; i++) {
            int[] row = this.data[i];
            for (int j=0; j < row.length; j++) {
                int r = row[j];
                if (r == num) {
                    this.checked[i][j] = 1;
                }
            }
        }
    }

    boolean checkVictory() {
        // row
        for (int i=0; i < checked.length; i++) {
            int[] row = this.checked[i];
            boolean rowMatched = Arrays.stream(row).allMatch(v -> v == 1);
            if (rowMatched) {
                return true;
            }
        }
        // col
        int[] counter = new int[5];
        for (int i=0; i < checked.length; i++) {
            int[] row = this.checked[i];
            for (int j=0; j < row.length; j++) {
                if (checked[i][j] == 1) {
                    counter[j]++;
                }
            }
        }
        boolean colMatched = Arrays.stream(counter).anyMatch(v -> v == 5);
        return colMatched;
    }

    int calculateNonMatchedSum() {
        int[] checkeds = Arrays.stream(this.checked).flatMapToInt(Arrays::stream).toArray();
        int[] datas = Arrays.stream(this.data).flatMapToInt(Arrays::stream).toArray();
        return IntStream.range(0, datas.length).mapToObj(i -> checkeds[i] == 0 ? datas[i] : 0).reduce(Integer::sum).get();
    }

}
