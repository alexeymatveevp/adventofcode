package com.luckystar.advent2016;

import java.security.MessageDigest;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

/**
 * By: Alexey Matveev
 * Date: 30.12.2016
 * Time: 12:06
 */
public class Advent170 {

    public static void main(String[] args) throws Exception {
        String input = "pxxbnzuo";
        MessageDigest md = MessageDigest.getInstance("MD5");
        Queue<Position> queue = new ArrayDeque<>();
        queue.add(new Position(input));
        Set<Position> pathsToVault = new HashSet<>();
        while (!queue.isEmpty()) {
            Position p = queue.poll();
            if (vaultReached(p)) {
                pathsToVault.add(p);
            } else {
                String hash = hexString(md.digest(p.path.getBytes()));
                if (!p.directionBlocked(Direction.U) && checkDoorOpen(Direction.U, hash)) {
                    Position newPosition = new Position(p.x - 1, p.y, p.path + Direction.U);
                    queue.add(newPosition);
                }
                if (!p.directionBlocked(Direction.D) && checkDoorOpen(Direction.D, hash)) {
                    Position newPosition = new Position(p.x + 1, p.y, p.path + Direction.D);
                    queue.add(newPosition);
                }
                if (!p.directionBlocked(Direction.L) && checkDoorOpen(Direction.L, hash)) {
                    Position newPosition = new Position(p.x, p.y - 1, p.path + Direction.L);
                    queue.add(newPosition);
                }
                if (!p.directionBlocked(Direction.R) && checkDoorOpen(Direction.R, hash)) {
                    Position newPosition = new Position(p.x, p.y + 1, p.path + Direction.R);
                    queue.add(newPosition);
                }
            }
        }
        // find the shortest
        Optional<Position> min = pathsToVault.stream().min((o1, o2) -> Integer.valueOf(o1.path.length()).compareTo(o2.path.length()));
        if (min.isPresent()) {
            System.out.println("The shortest path: " + min.get().path.substring(input.length()));
        } else {
            System.out.println("There is no min path!");
        }
        // the longest
        Optional<Position> max = pathsToVault.stream().max((o1, o2) -> Integer.valueOf(o1.path.length()).compareTo(o2.path.length()));
        if (max.isPresent()) {
            System.out.println("The longest path length: " + max.get().path.substring(input.length()).length());
        } else {
            System.out.println("There is no max path!");
        }
    }

    static class Position {
        int x = 0;
        int y = 0;
        String path;
        Position(int x, int y, String path) {
            this.x = x;
            this.y = y;
            this.path = path;
        }
        Position(String path) {
            this.path = path;
        }
        boolean directionBlocked(Direction d) {
            if (d == Direction.U) return x == 0;
            else if (d == Direction.R) return y == 3;
            else if (d == Direction.D) return x == 3;
            else if (d == Direction.L) return y == 0;
            return false;
        }
    }

    enum Direction {
        U,D,L,R
    }

    static boolean checkDoorOpen(Direction d, String hash) {
        char[] chars = hash.substring(0, 4).toCharArray();
        if (d == Direction.U) return openDoorChar(chars[0]);
        else if (d == Direction.D) return openDoorChar(chars[1]);
        else if (d == Direction.L) return openDoorChar(chars[2]);
        else if (d == Direction.R) return openDoorChar(chars[3]);
        return false;
    }

    static boolean openDoorChar(char c) {
        return c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f';
    }

    static boolean vaultReached(Position p) {
        return p.x == 3 && p.y == 3;
    }

    static String hexString(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (byte aData : data) {
            sb.append(Integer.toString((aData & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

}
