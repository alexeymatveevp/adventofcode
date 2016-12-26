package com.luckystar.advent2016;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: david
 * Date: 22.12.2016
 * Time: 17:46
 */
public class Advent031 {

    public static void main(String[] args) throws Exception {
        String input = new String(Files.readAllBytes(Paths.get(Advent031.class.getClassLoader().getResource("2016/input_3.txt").toURI())));
        Pattern p = Pattern.compile("(\\d+)");
        String[] rows = input.split("\n");
        int trianglesCount = 0;
        for (int i=0; i<rows.length; i+=3) {
            // 3 packs of triangles
            int[] pack1 = find3Numbers(p, rows[i]);
            int[] pack2 = find3Numbers(p, rows[i+1]);
            int[] pack3 = find3Numbers(p, rows[i+2]);
            if (checkTriangle(pack1[0], pack2[0], pack3[0])) {
                trianglesCount++;
            }
            if (checkTriangle(pack1[1], pack2[1], pack3[1])) {
                trianglesCount++;
            }
            if (checkTriangle(pack1[2], pack2[2], pack3[2])) {
                trianglesCount++;
            }
        }
        System.out.println(trianglesCount);

    }

    static int[] find3Numbers(Pattern p, String row) {
        Matcher m = p.matcher(row);
        m.find();
        int n1 = Integer.valueOf(m.group());
        m.find();
        int n2 = Integer.valueOf(m.group());
        m.find();
        int n3 = Integer.valueOf(m.group());
        return new int[] {n1, n2, n3};
    }

    static boolean checkTriangle(int n1, int n2, int n3) {
        int max = Math.max(Math.max(n1, n2), n3);
        if (n1 == max && n2 + n3 > n1) {
            return true;
        } else if (n2 == max && n1 + n3 > n2) {
            return true;
        } else if (n3 == max && n2 + n1 > n3) {
            return true;
        }
        return false;
    }

}
