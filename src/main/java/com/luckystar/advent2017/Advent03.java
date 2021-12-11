package com.luckystar.advent2017;

/**
 * Created by ksavina on 12/3/2017.
 */
public class Advent03 {

    public static void main(String[] args) {
        // 1st part answer is 475
        // the right bottom sequence is 1,3*3, 5*5, 7*7, ...
        // so the first > 277678 is 527*527 - 1 - 51 = 475

        // part 2
        int[][] maze = new int[100][];
        // init maze with zeros
        for (int i=0; i<maze.length; i++) {
            maze[i] = new int[maze.length];
            for (int j=0; j<maze.length; j++) {
                maze[i][j] = 0;
            }
        }
        maze[50][50] = 1;
        int x = 51, y = 50, step = 2;
        int currentNumber = 1;
        int targetNumber = 277678;
        here:
        while (true) {
            // go up
            for (int i=0; i<step; i++) {
                currentNumber = sumNeighbors(maze, x, y);
                maze[x][y] = currentNumber;
                if (i < step - 1) y++;
                if (currentNumber > targetNumber) break here;
            }
            // go left
            for (int i=0; i<step; i++) {
                currentNumber = sumNeighbors(maze, x, y);
                maze[x][y] = currentNumber;
                x--;
                if (currentNumber > targetNumber) break here;
            }
            // go bottom
            for (int i=0; i<step; i++) {
                currentNumber = sumNeighbors(maze, x, y);
                maze[x][y] = currentNumber;
                y--;
                if (currentNumber > targetNumber) break here;
            }
            // go right
            for (int i=0; i<step+1; i++) {
                currentNumber = sumNeighbors(maze, x, y);
                maze[x][y] = currentNumber;
                x++;
                if (currentNumber > targetNumber) break here;
            }
            step += 2;
        }
        System.out.println(currentNumber);
    }

    private static int sumNeighbors(int[][] maze, int x, int y) {
        return maze[x+1][y] + maze[x+1][y+1] + maze[x-1][y] + maze[x-1][y-1]
                + maze[x][y+1] + maze[x][y-1] + maze[x+1][y-1] + maze[x-1][y+1];
    }
}
