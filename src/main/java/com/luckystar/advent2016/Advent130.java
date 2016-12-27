package com.luckystar.advent2016;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * By: Alexey Matveev
 * Date: 27.12.2016
 * Time: 19:03
 */
public class Advent130 {

    public static void main(String[] args) {
        Integer officeDesignerFavoriteNumber = 1364;
        Point startingPoint = new Point(1, 1);
        Point destination = new Point(31, 39);
        int width = 50, height = 50;
        String[][] maze = new String[height][];
        generateMaze(maze, officeDesignerFavoriteNumber, width, height); // generate maze bigger then destination end to give it some space
        breadthFirstSearch(maze, startingPoint, destination);
        printMaze(maze);
    }

    static void generateMaze(String[][] maze, int officeDesignerFavoriteNumber, int width, int height) {
        for (int x=0; x<height; x++) {
            maze[x] = new String[width];
            for (int y=0; y<width; y++) {
                Integer step1 = x*x + 3*x + 2*x*y + y + y*y;
                Integer step2 = step1 + officeDesignerFavoriteNumber;
                String step3 = Integer.toBinaryString(step2);
                int count = 0;
                for (char c : step3.toCharArray()) {
                    if (c == '1') count++;
                }
                // even = . or odd = #
                if (count%2 == 0) {
                    maze[x][y] = ".";
                } else {
                    maze[x][y] = "#";
                }
            }
        }
    }

    static void breadthFirstSearch(String[][] maze, Point start, Point end) {
        Queue<Point> queue = new ArrayDeque<>();
        queue.offer(start);
        boolean[][] visited = new boolean[maze.length][];
        here:
        while (!queue.isEmpty()) {
            Point p = queue.poll();
            List<Point> adjacent = new ArrayList<>();
            // up
            if (p.x != 0 && maze[p.x - 1][p.y].equals(".") && visitAndCheck(visited, p.x - 1, p.y)) {
                adjacent.add(new Point(p.x - 1, p.y, p.d + 1));
            }
            // right
            if (p.y != maze[p.x].length && maze[p.x][p.y + 1].equals(".") && visitAndCheck(visited, p.x, p.y + 1)) {
                adjacent.add(new Point(p.x, p.y + 1, p.d + 1));
            }
            // down
            if (p.x != maze.length && maze[p.x + 1][p.y].equals(".") && visitAndCheck(visited, p.x + 1, p.y)) {
                adjacent.add(new Point(p.x + 1, p.y, p.d + 1));
            }
            // left
            if (p.y != 0 && maze[p.x][p.y - 1].equals(".") && visitAndCheck(visited, p.x, p.y - 1)) {
                adjacent.add(new Point(p.x, p.y - 1, p.d + 1));
            }
            for (Point adj : adjacent) {
                if (adj.x == end.x && adj.y == end.y) { // end reached
                    System.out.println("Distance: " + adj.d);
                    break here;
                }
                queue.add(adj);
            }
        }
    }

    static boolean visitAndCheck(boolean[][] visited, int x, int y) {
        boolean[] row = visited[x];
        if (row == null) {
            row = new boolean[50];
            row[y] = true;
            visited[x] = row;
            return true;
        } else {
            if (!row[y]) {
                row[y] = true;
                return true;
            } else {
                return false;
            }
        }
    }

    static class Point {
        int x;
        int y;
        int d = 0; // distance
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        Point(int x, int y, int d) {
            this.x = x;
            this.y = y;
            this.d = d;
        }
    }

    static void printMaze(String[][] maze) {
        for (int x=-1; x<maze.length; x++) {
            for (int y=-1; y<maze[0].length; y++) {
                if (x == -1 && y == -1) {
                    System.out.print(" ");
                } else if (x == -1) {
                    if (y<10) System.out.print(y + " ");
                    else System.out.print(y);
                } else if (y == -1) {
                    System.out.print(x);
                } else {
                    System.out.print(maze[x][y] + " ");
                }
            }
            System.out.println();
        }
    }

}
