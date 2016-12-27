package com.luckystar.advent2016;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * By: Alexey Matveev
 * Date: 27.12.2016
 * Time: 16:53
 */
public class Advent120 {

    public static void main(String[] args) throws Exception {
        String input = new String(Files.readAllBytes(Paths.get(Advent120.class.getClassLoader().getResource("2016/input_12.txt").toURI())));
        String[] rows = input.split("\n");
        Pattern pcpy = Pattern.compile("cpy (-?\\d+|\\D) (\\D)");
        Pattern pinc = Pattern.compile("inc (\\D)");
        Pattern pdec = Pattern.compile("dec (\\D)");
        Pattern pjnz = Pattern.compile("jnz (-?\\d+||\\D) (-?\\d+)");
        InstructionSet instructionSet = new InstructionSet();
        for (String row : rows) {
            Matcher mcpy = pcpy.matcher(row);
            Matcher minc = pinc.matcher(row);
            Matcher mdec = pdec.matcher(row);
            Matcher mjnz = pjnz.matcher(row);
            if (mcpy.find()) {
                instructionSet.addInstruction(new InstructionSet.CpyInstruction(mcpy.group(1), mcpy.group(2)));
            }
            if (minc.find()) {
                instructionSet.addInstruction(new InstructionSet.IncInstruction(minc.group(1)));
            }
            if (mdec.find()) {
                instructionSet.addInstruction(new InstructionSet.DecInstruction(mdec.group(1)));
            }
            if (mjnz.find()) {
                instructionSet.addInstruction(new InstructionSet.JnzInstruction(mjnz.group(1), mjnz.group(2)));
            }
        }
        // run processor
        // in part 2 set c=1 initially
        instructionSet.setRegister("c", 1);
        instructionSet.processInstructions();
        instructionSet.printRegisters();
    }

    static class InstructionSet {
        static int currentInstruction = 0;
        static List<Instruction> instructions = new ArrayList<>();
        static Map<String, Integer> registers = new HashMap<>();

        void addInstruction(Instruction i) {
            instructions.add(i);
        }

        void processInstructions() {
            while (currentInstruction < instructions.size()) {
                instructions.get(currentInstruction).process();
            }
        }

        void printRegisters() {
            for (String name : registers.keySet()) {
                System.out.println(name + ": " + registers.get(name));
            }

        }

        void setRegister(String name, Integer value) {
            registers.put(name, value);
        }

        interface Instruction {
            void process();
        }

        static class CpyInstruction implements Instruction {
            String arg1;
            String arg2;

            CpyInstruction(String arg1, String arg2) {
                this.arg1 = arg1;
                this.arg2 = arg2;
            }

            public void process() {
                if (isNumeric(arg1)) {
                    registers.put(arg2, Integer.valueOf(arg1));
                } else {
                    registers.put(arg2, registers.get(arg1));
                }
                currentInstruction++;
            }
        }

        static class IncInstruction implements Instruction {
            String arg1;
            public IncInstruction(String arg1) {
                this.arg1 = arg1;
            }
            public void process() {
                registers.put(arg1, registers.get(arg1) + 1);
                currentInstruction++;
            }
        }

        static class DecInstruction implements Instruction {
            String arg1;
            public DecInstruction(String arg1) {
                this.arg1 = arg1;
            }
            public void process() {
                registers.put(arg1, registers.get(arg1) - 1);
                currentInstruction++;
            }
        }

        static class JnzInstruction implements Instruction {
            String arg1;
            Integer arg2;
            public JnzInstruction(String arg1, String arg2) {
                this.arg1 = arg1;
                this.arg2 = Integer.valueOf(arg2);
            }
            public void process() {
                Integer toCompare = isNumeric(arg1) ? Integer.valueOf(arg1) : registers.get(arg1);
                if (toCompare == null) { // register not yet set
                    registers.put(arg1, 0);
                    toCompare = 0;
                }
                if (toCompare != 0) {
                    currentInstruction += arg2;
                } else {
                    currentInstruction++;
                }
            }
        }
    }




    static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }


}
