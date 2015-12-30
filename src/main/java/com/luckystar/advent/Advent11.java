package com.luckystar.advent;

/**
 * By: Alexey Matveev
 * Date: 30.12.2015
 * Time: 16:04
 */
public class Advent11 {

    public static void main(String[] args) {
//        String input = "hxbxwxba";
        String input = "hxbxxyzz";
        // a=97, z=122
        String nextGuess = input;
        boolean correct = false;
        while (!correct) {
            // get nex guess
            nextGuess = nextGuess(nextGuess);
            // check the password
            correct = checkPassword(nextGuess);
        }

    }

    public static boolean checkPassword(String pass) {
        System.out.println("checking pass: " + pass);
        // check straight
        boolean straightPass = false;
        for (int i=0; i<pass.length()-2; i++) {
            char c = pass.charAt(i);
            if (pass.charAt(i+1) == c + 1) {
                if (pass.charAt(i+2) == c + 2) {
                    straightPass = true;
                    System.out.println("# straight pass: " + c + (char)(c+1) + (char)(c+2));
                }
            }
        }
        // check letters i,o,l
        boolean lettersPass = false;
        if (!pass.contains("i") && !pass.contains("o") && !pass.contains("l")) {
            lettersPass = true;
            System.out.println("# letters pass: true");
        }
        // check pairs
        boolean twoPairsPass = false;
        int i = 0, count = 0;
        while (i < pass.length() - 1) {
            char c = pass.charAt(i);
            if (pass.charAt(i+1) == c) {
                count++;
                i+=2;
            }
            i++;
        }
        if (count > 1) {
            twoPairsPass = true;
            System.out.println("# pairs pass: " + count + " pairs");
        }

        if (straightPass && lettersPass && twoPairsPass) {
            System.out.println("-----");
            System.out.println("new password: " + pass);
            return true;
        }
        return false;
    }

    public static String nextGuess(String currentPass) {
        StringBuilder reversedPass = new StringBuilder(currentPass).reverse();
        String reversedIncremented = incrementLetter(reversedPass.toString(), 0);
        return new StringBuilder(reversedIncremented).reverse().toString();
    }

    public static String incrementLetter(String current, int idx) {
        char newLetter;
        boolean incrementNext = false;
        if (current.charAt(idx) == 122) {
            newLetter = 'a';
            incrementNext = true;
        } else {
            newLetter = (char) (current.charAt(idx) + 1);
        }
        current = current.substring(0, idx) + newLetter + current.substring(idx + 1);
        if (incrementNext) {
            return incrementLetter(current, idx + 1);
        } else {
            return current;
        }
    }

}
