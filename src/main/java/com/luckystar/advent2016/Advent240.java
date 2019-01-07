package com.luckystar.advent2016;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by ksavina on 11/22/2017.
 */
public class Advent240 {

    static char[][] map;

    public static void main(String[] args) throws Exception {
        // pretty much the same as Advent130, took maze breadth first search from there
        String input = new String(Files.readAllBytes(Paths.get(Advent230.class.getClassLoader().getResource("2016/input_24.txt").toURI())));
        String[] rows = input.split("\n");
        map = new char[rows.length][];

        List<Point> numbers = new ArrayList<>();
        for (int i=0; i<rows.length; i++) {
            String row = rows[i];
            char[] rowChars = row.toCharArray();
            map[i] = rowChars;
            // check for digits location
            for (int j=0; j<rowChars.length; j++) {
                char rowChar = rowChars[j];
                if (Character.isDigit(rowChar)) {
                    numbers.add(new Point(rowChar + "", i, j));
                }
            }
        }

        // find all shortest paths between numbers
        List<Route> allRoutes = getAllRoutes(numbers);
        for (Route route : allRoutes) {
            Point p = breadthFirstSearch(map, route.p1, route.p2);
//            printMaze(map);
            route.path = p;
        }

        // find all available permutations
        int totalNumbers = numbers.size() - 1; // 7
        String[] numbersAlphabet = new String[totalNumbers];
        for (int i=0; i<totalNumbers; i++) {
            numbersAlphabet[i] = i+1+"";
        }
        List<List<String>> permutations = permutations(numbersAlphabet);
        // set 0 as starting point
        for (List<String> permutation : permutations) {
            permutation.add(0, "0");
        }
        // for 2nd part add 0 as last destination
        for (List<String> permutation : permutations) {
            permutation.add("0");
        }


        // for each combination of paths calculate the distance
        int minDistance = Integer.MAX_VALUE;
        List<String> shortestPath = null;
        for (List<String> path : permutations) {
            int distance = 0;
            for (int i=0; i<path.size() - 1; i++) {
                String n1 = path.get(i);
                String n2 = path.get(i+1);
                Route route = findRouteBetween(allRoutes, n1, n2);
                distance += route.path.d;
            }
            System.out.println(distance);
            if (distance < minDistance) {
                minDistance = distance;
                shortestPath = path;
            }
        }

        for (int i=0; i<shortestPath.size() - 1; i++) {
            String n1 = shortestPath.get(i);
            String n2 = shortestPath.get(i+1);
            Route route = findRouteBetween(allRoutes, n1, n2);
            List<Point> allParentPath = getPath(route.path);
            allParentPath.forEach(p -> map[p.x][p.y] = 'x');
        }
        printMaze(map);

        System.out.println("--");
        System.out.println("Min distance: " + minDistance);
    }

    public static Route findRouteBetween(List<Route> routes, String n1, String n2) {
        for (Route route : routes) {
            String name1 = route.p1.name;
            String name2 = route.p2.name;
            if (n1.equals(name1) && n2.equals(name2)
                    || n1.equals(name2) && n2.equals(name1)) {
                return route;
            }
        }
        return null;
    }

    public static List<Route> getAllRoutes(List<Point> numbers) {
        List<Route> result = new ArrayList<>();
        for (Point number : numbers) {
            for (Point n : numbers) {
                if (number != n) {
                    Route r = new Route(number, n);
                    if (!result.contains(r)) {
                        result.add(r);
                    }
                }
            }
        }
        return result;
    }

    public static List<List<String>> permutations(String[] alphabet) {
        List<List<String>> returnList = new ArrayList<>();
        returnList.add(new ArrayList<>());
        for (int i = 0; i < alphabet.length; i++) {
            Set<ArrayList<String>> currentSet = new HashSet<>();
            for (List<String> l : returnList) {
                for (int j = 0; j < l.size() + 1; j++) {
                    ArrayList<String> clone = new ArrayList<>(l);
                    clone.add(j, alphabet[i]);
                    currentSet.add(clone);
                }
            }
            returnList = new ArrayList<>(currentSet);
        }
        return returnList;
    }

    static Point breadthFirstSearch(char[][] maze, Point start, Point end) {
        Queue<Point> queue = new ArrayDeque<>();
        queue.offer(start);
        boolean[][] visited = new boolean[maze.length][];
        Point destination = null;
        here:
        while (!queue.isEmpty()) {
            Point p = queue.poll();
            List<Point> adjacent = new ArrayList<>();
            // up
            if (p.x != 0 && validPath(maze[p.x - 1][p.y]) && visitAndCheck(visited, p.x - 1, p.y)) {
                adjacent.add(new Point(p.x - 1, p.y, p.d + 1, p));
            }
            // right
            if (p.y != maze[p.x].length && validPath(maze[p.x][p.y + 1]) && visitAndCheck(visited, p.x, p.y + 1)) {
                adjacent.add(new Point(p.x, p.y + 1, p.d + 1, p));
            }
            // down
            if (p.x != maze.length && validPath(maze[p.x + 1][p.y]) && visitAndCheck(visited, p.x + 1, p.y)) {
                adjacent.add(new Point(p.x + 1, p.y, p.d + 1, p));
            }
            // left
            if (p.y != 0 && validPath(maze[p.x][p.y - 1]) && visitAndCheck(visited, p.x, p.y - 1)) {
                adjacent.add(new Point(p.x, p.y - 1, p.d + 1, p));
            }
            for (Point adj : adjacent) {
                if (adj.x == end.x && adj.y == end.y) { // end reached
                    destination = adj;
                    break here;
                }
                queue.add(adj);
            }
        }
        if (destination != null) {
            List<Point> path = getPath(destination);
            // dray path in maze
//            path.forEach(p -> maze[p.x][p.y] = '0');
        }
        return destination;
    }

    static boolean validPath(char c) {
        return Character.isDigit(c) || c == '.';
    }

    static List<Point> getPath(Point p) {
        List<Point> path = new ArrayList<>();
        p = p.parent;
        while (p.parent != null) {
            path.add(p);
            p = p.parent;
        }
        Collections.reverse(path);
        return path;
    }

    static boolean visitAndCheck(boolean[][] visited, int x, int y) {
        boolean[] row = visited[x];
        if (row == null) {
            row = new boolean[map[0].length];
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

    static void printMaze(char[][] maze) {
        for (int x=-1; x<maze.length - 1; x++) {
            for (int y=-1; y<maze[0].length - 1; y++) {
                if (x == -1 && y == -1) {
                    System.out.print("  ");
                } else if (x == -1) {
                    System.out.printf("%-2s", y);
                } else if (y == -1) {
                    System.out.printf("%-2s", x);
                } else {
                    System.out.print(maze[x][y] + " ");
                }
            }
            System.out.println();
        }
    }

    static class Point {
        String name;
        int x;
        int y;
        int d = 0; // distance
        Point parent;
        Point(String name, int x, int y) {
            this.name = name;
            this.x = x;
            this.y = y;
        }
        Point(int x, int y, int d, Point parent) {
            this.x = x;
            this.y = y;
            this.d = d;
            this.parent = parent;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "name='" + name + '\'' +
                    ", x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    static class Route {
        Point p1;
        Point p2;
        Point path;

        public Route(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Route)) {
                return false;
            }
            Route r = (Route) obj;
            return this.p1 == r.p1 && this.p2 == r.p2
                    || this.p1 == r.p2 && this.p2 == r.p1;
        }

        @Override
        public String toString() {
            return "Route{" +
                    "p1=" + p1 +
                    ", p2=" + p2 +
                    ", d=" + path.d + "}";
        }
    }
}
