package com.luckystar.advent2016;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * By: Alexey Matveev
 * Date: 30.12.2016
 * Time: 14:54
 */
public class Advent210 {

    public static void main(String[] args) throws Exception {
        String input = new String(Files.readAllBytes(Paths.get(Advent080.class.getClassLoader().getResource("2016/input_21.txt").toURI())));
        String message = "abcdefgh";
        String scrambledPswd = "fbgdceah";
        Pattern pswapp = Pattern.compile("swap position (\\d+) with position (\\d+)");
        Pattern pswapl = Pattern.compile("swap letter (\\D) with letter (\\D)");
        Pattern protrl = Pattern.compile("rotate (right|left) (\\d+) step[s]?");
        Pattern protp = Pattern.compile("rotate based on position of letter (\\D)");
        Pattern prev = Pattern.compile("reverse position[s]? (\\d+) through (\\d+)");
        Pattern pmov = Pattern.compile("move position (\\d+) to position (\\d+)");
        String[] rows = input.split("\n");
        List<Instruction> instructions = new ArrayList<>();
        for (String row : rows) {
            row = row.trim();
            Matcher mswapp = pswapp.matcher(row);
            Matcher mswapl = pswapl.matcher(row);
            Matcher mrotrl = protrl.matcher(row);
            Matcher mrotp = protp.matcher(row);
            Matcher mrev = prev.matcher(row);
            Matcher mmov = pmov.matcher(row);
            if (mswapp.find()) {
                instructions.add(new SwapPositionInstruction(Integer.valueOf(mswapp.group(1)), Integer.valueOf(mswapp.group(2))));
            } else if (mswapl.find()) {
                instructions.add(new SwapLetterInstruction(mswapl.group(1), mswapl.group(2)));
            } else if (mrotrl.find()) {
                instructions.add(new RotateRightLeftInstruction(mrotrl.group(1).equals("right"), Integer.valueOf(mrotrl.group(2))));
            } else if (mrotp.find()) {
                instructions.add(new RotateBasedOnPositionInstruction(mrotp.group(1).charAt(0)));
            } else if (mrev.find()) {
                instructions.add(new ReversePositionInstruction(Integer.valueOf(mrev.group(1)), Integer.valueOf(mrev.group(2))));
            } else if (mmov.find()) {
                instructions.add(new MovePositionInstruction(Integer.valueOf(mmov.group(1)), Integer.valueOf(mmov.group(2))));
            }
        }

        StringBuilder sb = new StringBuilder(message);
        for (Instruction instruction : instructions) {
            sb = instruction.process(sb);
        }
        System.out.println(sb.toString());

        // part 2 - reversed instructions
        Collections.reverse(instructions);
        StringBuilder sb2 = new StringBuilder(scrambledPswd);
        for (Instruction instruction : instructions) {
            sb2 = instruction.processReverse(sb2);
        }
        System.out.println(sb2.toString());

    }

    interface Instruction {
        StringBuilder process(StringBuilder s);
        StringBuilder processReverse(StringBuilder s);
    }

    static class SwapPositionInstruction implements Instruction {
        int x;
        int y;

        public SwapPositionInstruction(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public StringBuilder process(StringBuilder s) {
            char c = s.charAt(x);
            s.setCharAt(x, s.charAt(y));
            s.setCharAt(y, c);
            return s;
        }
        public StringBuilder processReverse(StringBuilder s) {
            return process(s);
        }
    }

    static class SwapLetterInstruction implements Instruction {
        String x;
        String y;

        public SwapLetterInstruction(String x, String y) {
            this.x = x;
            this.y = y;
        }

        public StringBuilder process(StringBuilder s) {
            int xi = s.indexOf(x);
            int yi = s.indexOf(y);
            s.setCharAt(xi, y.charAt(0));
            s.setCharAt(yi, x.charAt(0));
            return s;
        }
        public StringBuilder processReverse(StringBuilder s) {
            return process(s);
        }
    }

    static class RotateRightLeftInstruction implements Instruction {
        boolean right;
        int steps;

        public RotateRightLeftInstruction(boolean right, int steps) {
            this.right = right;
            this.steps = steps;
        }

        private StringBuilder rotateDirection(StringBuilder s, boolean right) {
            int times = steps % s.length();
            if (right) {
                String rotated = s.substring(s.length() - times) + s.substring(0, s.length() - times);
                return new StringBuilder(rotated);
            } else {
                String rotated = s.substring(times) + s.substring(0, times);
                return new StringBuilder(rotated);
            }
        }
        public StringBuilder process(StringBuilder s) {
            return rotateDirection(s, right);
        }
        public StringBuilder processReverse(StringBuilder s) {
            return rotateDirection(s, !right);
        }
    }

    static class RotateBasedOnPositionInstruction implements Instruction {
        char letter;

        public RotateBasedOnPositionInstruction(char letter) {
            this.letter = letter;
        }

        public StringBuilder process(StringBuilder s) {
            int times = s.indexOf(letter+"");
            if (times >= 4) times++;
            times++;
            times = times % s.length();
            String rotated = s.substring(s.length() - times) + s.substring(0, s.length() - times);
            return new StringBuilder(rotated);
        }
        public StringBuilder processReverse(StringBuilder s) {
            // tricky - rotate left index times, then 1 more time then get index and if >= 4 - one more time
            // dont know formula - just iterate all possible solutions
//            0 - 1
//            1 - 3
//            2 - 5
//            3 - 7
//            4 - 2 (4+2)
//            5 - 4 (5+2)
//            6 - 6 (6+2)
//            7 - 0 (7+2)
            int i = s.indexOf(letter+"");
            int timesLeft = 0;
            if (i == 1) timesLeft = 1;
            else if (i == 3) timesLeft = 2;
            else if (i == 5) timesLeft = 3;
            else if (i == 7) timesLeft = 4;
            else if (i == 2) timesLeft = 6;
            else if (i == 4) timesLeft = 6;
            else if (i == 6) timesLeft = 0;
            else if (i == 0) timesLeft = 1;
            String rotated = s.substring(timesLeft) + s.substring(0, timesLeft);
            return new StringBuilder(rotated);
        }
    }

    static class ReversePositionInstruction implements Instruction {
        int x;
        int y;

        public ReversePositionInstruction(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public StringBuilder process(StringBuilder s) {
            String s1 = s.substring(0, x);
            String s2 = s.substring(x, y+1);
            StringBuilder s2b = new StringBuilder(s2);
            s2b.reverse();
            String s3 = s.substring(y+1);
            return new StringBuilder(s1 + s2b.toString() + s3);
        }
        public StringBuilder processReverse(StringBuilder s) {
            return process(s);
        }
    }

    static class MovePositionInstruction implements Instruction {
        int x;
        int y;

        public MovePositionInstruction(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public StringBuilder process(StringBuilder s) {
            char c = s.charAt(x);
            s.deleteCharAt(x);
            s.insert(y, c);
            return s;
        }
        public StringBuilder processReverse(StringBuilder s) {
            char c = s.charAt(y);
            s.deleteCharAt(y);
            s.insert(x, c);
            return s;
        }
    }

}
