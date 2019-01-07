package com.luckystar.advent2018_1;

import com.luckystar.advent2016.Advent011;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Alexey Matveev on 26.12.2018
 */
public class Advent03 {

    public static void main(String[] args) throws Exception {
        String input = new String(Files.readAllBytes(Paths.get(Advent011.class.getClassLoader().getResource("2018/input_3.txt").toURI())));
        String[] parts = input.split("\r\n");

        int[][] fabric = new int[1000][];
        for (int i=0; i < fabric.length; i++) {
            fabric[i] = new int[1000];
        }

        for (String part : parts) {
            Claim c = parseClaim(part);
            for (int i=c.offsetLeft; i < c.offsetLeft + c.width; i++) {
                for (int j=c.offsetTop; j < c.offsetTop + c.height; j++) {
                    if (fabric[i][j] == 0) {
                        fabric[i][j] = 1;
                    } else if (fabric[i][j] == 1) {
                        fabric[i][j] = 2;
                    }
                }
            }
        }

        int result = 0;
        for (int i=0; i < fabric.length; i++) {
            int[] fabricRow = fabric[i];
            for (int j=0; j < fabricRow.length; j++) {
                if (fabricRow[j] == 2) {
                    result++;
                }
            }
        }

        System.out.println("part1: " + result);

        for (int i=0; i<parts.length; i++) {
            Claim c1 = parseClaim(parts[i]);
            boolean intersectsWithOthers = false;
            for (int j=0; j<parts.length; j++) {
                if (i == j) {
                    continue;
                }
                Claim c2 = parseClaim(parts[j]);
                int intersectFabricCount = getIntersectFabricCount(c1, c2);
                if (intersectFabricCount != 0) {
                    intersectsWithOthers = true;
                    break;
                }
            }
            if (!intersectsWithOthers) {
                System.out.println("part2: id=" + c1.id);
            }
        }

    }

    static int getIntersectFabricCount(Claim c1, Claim c2) {
        // find rightmost left border
        int x1 = c1.offsetLeft > c2.offsetLeft ? c1.offsetLeft : c2.offsetLeft;
        int x2 = c1.offsetLeft + c1.width < c2.offsetLeft + c2.width ? c1.offsetLeft + c1.width : c2.offsetLeft + c2.width;
        int y1 = c1.offsetTop > c2.offsetTop ? c1.offsetTop : c2.offsetTop;
        int y2 = c1.offsetTop + c1.height < c2.offsetTop + c2.height ? c1.offsetTop + c1.height : c2.offsetTop + c2.height;

        if (x1 >= x2 || y1 >= y2) {
            return 0;
        }

        return (x2 - x1) * (y2 - y1);
    }

    static Claim parseClaim(String claimString) {
        String[] asdf = claimString.split(" ");
        String id = asdf[0];
        int offsetLeft = Integer.valueOf(asdf[2].substring(0, asdf[2].length()-1).split(",")[0]);
        int offsetTop = Integer.valueOf(asdf[2].substring(0, asdf[2].length()-1).split(",")[1]);
        int width = Integer.valueOf(asdf[3].split("x")[0]);
        int height = Integer.valueOf(asdf[3].split("x")[1]);
        return new Claim(id, offsetLeft, offsetTop, width, height);
    }

    static class Claim {
        String id;
        int offsetLeft;
        int offsetTop;
        int width;
        int height;

        Claim(String id, int offsetLeft, int offsetTop, int width, int height) {
            this.id = id;
            this.offsetLeft = offsetLeft;
            this.offsetTop = offsetTop;
            this.width = width;
            this.height = height;
        }
    }

}
