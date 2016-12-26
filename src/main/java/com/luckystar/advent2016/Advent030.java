package com.luckystar.advent2016;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: david
 * Date: 22.12.2016
 * Time: 17:32
 */
public class Advent030 {

    public static void main(String[] args) throws Exception {
        String input = new String(Files.readAllBytes(Paths.get(Advent030.class.getClassLoader().getResource("2016/input_3.txt").toURI())));
        Pattern p = Pattern.compile("(\\d+)");
        String[] rows = input.split("\n");
        int trianglesCount = 0;
        for (String row : rows) {
            Matcher m = p.matcher(row);
            m.find();
            int n1 = Integer.valueOf(m.group());
            m.find();
            int n2 = Integer.valueOf(m.group());
            m.find();
            int n3 = Integer.valueOf(m.group());
            if (checkTriangle(n1, n2, n3)) {
                trianglesCount++;
            }
        }
        System.out.println(trianglesCount);

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
