package com.luckystar.advent2016;

/**
 * By: Alexey Matveev
 * Date: 30.12.2016
 * Time: 12:37
 */
public class Advent180 {

    public static void main(String[] args) {
        String input = ".^^^^^.^^.^^^.^...^..^^.^.^..^^^^^^^^^^..^...^^.^..^^^^..^^^^...^.^.^^^^^^^^....^..^^^^^^.^^^.^^^.^^";
//        int rowsNumber = 40;
        int rowsNumber = 400000; // part 2
        String[] rows = new String[rowsNumber];
        rows[0] = input;
        for (int i=1; i<rowsNumber; i++) {
            String prevRow = rows[i-1];
            StringBuilder row = new StringBuilder();
            for (int j=0; j<input.length(); j++) {
                char c1 = j-1 == -1 ? '.' : prevRow.charAt(j-1);
                char c2 = prevRow.charAt(j);
                char c3 = j+1 == input.length() ? '.' : prevRow.charAt(j+1);
                row.append(checkSafe(c1, c2, c3) ? '.' : '^');
            }
            rows[i] = row.toString();
        }
        // count safe tiles
        int safeTiles = 0;
        for (String row : rows) {
            for (char c : row.toCharArray()) {
                if (c == '.') safeTiles++;
            }
        }
        System.out.println(safeTiles);

    }

    static boolean checkSafe(char c1, char c2, char c3) {
        if (c1 == '^' && c2 == '^' && c3 == '.') return false;
        else if (c1 == '.' && c2 == '^' && c3 == '^') return false;
        else if (c1 == '^' && c2 == '.' && c3 == '.') return false;
        else if (c1 == '.' && c2 == '.' && c3 == '^') return false;
        else return true;
    }

}
