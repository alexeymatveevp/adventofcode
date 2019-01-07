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
 * Created by ksavina on 11/23/2017.
 */
public class Advent250 {

    public static void main(String[] args) throws Exception {
        // copy paste from Advent230 initially
        String input = new String(Files.readAllBytes(Paths.get(Advent250.class.getClassLoader().getResource("2016/input_25.txt").toURI())));
        String[] rows = input.split("\n");

        Pattern pcpy = Pattern.compile("cpy (-?\\d+|\\D) (\\D)");
        Pattern pinc = Pattern.compile("inc (\\D)");
        Pattern pdec = Pattern.compile("dec (\\D)");
        Pattern pjnz = Pattern.compile("jnz (-?\\d+|\\D) (-?\\d+|\\D)");
        Pattern pout = Pattern.compile("out (\\D)");
        InstructionSet instructionSet = new InstructionSet();
        for (String row : rows) {
            Matcher mcpy = pcpy.matcher(row);
            Matcher minc = pinc.matcher(row);
            Matcher mdec = pdec.matcher(row);
            Matcher mjnz = pjnz.matcher(row);
            Matcher mout = pout.matcher(row);
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
            if (mout.find()) {
                instructionSet.addInstruction(new InstructionSet.OutInstruction(mout.group(1)));
            }
        }
        // run processor
        long a = 0L;
        instructionSet.setRegister("a", a);
        while (!instructionSet.processInstructions()) {
            instructionSet.reset();
            a++;
            instructionSet.setRegister("a", a);
        }
        System.out.println("--");
        System.out.println("final out: " + instructionSet.out);
        System.out.println("final a: " + a);
        instructionSet.printRegisters();
    }

    static class InstructionSet {
        static int currentInstruction = 0;
        static List<InstructionSet.Instruction> instructions = new ArrayList<>();
        static Map<String, Long> registers = new HashMap<>();
        static String checkString = "010101010101010101010101";
        static String out = "";

        void reset() {
            out = "";
            currentInstruction = 0;
            registers.clear();
        }

        void addInstruction(InstructionSet.Instruction i) {
            instructions.add(i);
        }

        boolean processInstructions() {
            Long initialAValue = registers.get("a");
            while (currentInstruction < instructions.size() && checkString.startsWith(out)) {
                // try to optimize
                // calculate this pattern:
                //   cpy b c
                //   inc a
                //   dec c
                //   jnz c -2
                //   dec d
                //   jnz d -5
                // into:
                //   a = a + b * d
                if (currentInstruction + 6 <= instructions.size()) {
                    InstructionSet.Instruction i1 = instructions.get(currentInstruction);
                    InstructionSet.Instruction i2 = instructions.get(currentInstruction + 1);
                    InstructionSet.Instruction i3 = instructions.get(currentInstruction + 2);
                    InstructionSet.Instruction i4 = instructions.get(currentInstruction + 3);
                    InstructionSet.Instruction i5 = instructions.get(currentInstruction + 4);
                    InstructionSet.Instruction i6 = instructions.get(currentInstruction + 5);
                    if (i1 instanceof InstructionSet.CpyInstruction && i2 instanceof InstructionSet.IncInstruction && i3 instanceof InstructionSet.DecInstruction && i4 instanceof InstructionSet.JnzInstruction && i5 instanceof InstructionSet.DecInstruction && i6 instanceof InstructionSet.JnzInstruction) {
                        InstructionSet.CpyInstruction cpy = (InstructionSet.CpyInstruction) i1;
                        InstructionSet.IncInstruction inc = (InstructionSet.IncInstruction) i2;
                        InstructionSet.DecInstruction dec1 = (InstructionSet.DecInstruction) i3;
                        InstructionSet.JnzInstruction jnz1 = (InstructionSet.JnzInstruction) i4;
                        InstructionSet.DecInstruction dec2 = (InstructionSet.DecInstruction) i5;
                        InstructionSet.JnzInstruction jnz2 = (InstructionSet.JnzInstruction) i6;
                        if (cpy.arg2.equals(dec1.arg1) && cpy.arg2.equals(jnz1.arg1)
                                && dec2.arg1.equals(jnz2.arg1)
                                && getOrZero(jnz1.arg2) == -2 && getOrZero(jnz2.arg2) == -5) {

                            long increment = getOrZero(cpy.arg1);
                            long multiply = getOrZero(dec2.arg1);
                            long value1 = getOrZero(inc.arg1);

                            value1 = value1 + increment * multiply; // output value of "a"

                            registers.put(inc.arg1, value1);
                            registers.put(dec1.arg1, 0L);
                            registers.put(dec2.arg1, 0L);
                            currentInstruction += 6;
                        }
                    }
                }

                // optimization 2
                if (currentInstruction == 12) {
                    long b = getOrZero("b");
                    long a = getOrZero("a");
                    registers.put("a", a + b/2);
                    if (b == 0) {
                        registers.put("c", 2L);
                    } else if (b % 2 != 0) {
                        registers.put("c", 1L);
                    } else {
                        registers.put("c", 2L);
                    }
                    registers.put("b", 0L);
                    currentInstruction = 20;
                }

                if (currentInstruction <= instructions.size() - 1) {
                    InstructionSet.Instruction instruction = instructions.get(currentInstruction);
                    instruction.process();
                }
                if (checkString.equals(out)) {
                    return true;
                }
            }
            System.out.println("a: " + initialAValue + ", out: " + out);
            return false;
        }

        void printRegisters() {
            for (String name : registers.keySet()) {
                System.out.println(name + ": " + registers.get(name));
            }
        }

        void setRegister(String name, Long value) {
            registers.put(name, value);
        }

        interface Instruction {
            void process();
        }

        static class IncInstruction implements InstructionSet.Instruction {
            String arg1;
            public IncInstruction(String arg1) {
                this.arg1 = arg1;
            }
            public void process() {
                registers.put(arg1, registers.get(arg1) + 1);
                currentInstruction++;
            }
            public String toString() {
                return "inc " + arg1;
            }
        }

        static class DecInstruction implements InstructionSet.Instruction {
            String arg1;
            public DecInstruction(String arg1) {
                this.arg1 = arg1;
            }
            public void process() {
                registers.put(arg1, registers.get(arg1) - 1);
                currentInstruction++;
            }
            public String toString() {
                return "dec " + arg1;
            }
        }

        static class CpyInstruction implements InstructionSet.Instruction {
            String arg1;
            String arg2;

            CpyInstruction(String arg1, String arg2) {
                this.arg1 = arg1;
                this.arg2 = arg2;
            }

            public void process() {
                if (!isNumeric(arg2)) { // instruction is not valid - skip it
                    if (isNumeric(arg1)) {
                        registers.put(arg2, Long.valueOf(arg1));
                    } else {
                        registers.put(arg2, registers.get(arg1));
                    }
                }
                currentInstruction++;
            }
            public String toString() {
                return "cpy " + arg1 + " " + arg2;
            }
        }

        static class JnzInstruction implements InstructionSet.Instruction {
            String arg1;
            String arg2;
            public JnzInstruction(String arg1, String arg2) {
                this.arg1 = arg1;
                this.arg2 = arg2;
            }
            public void process() {
                Long toCompare = isNumeric(arg1) ? Long.valueOf(arg1) : registers.get(arg1);
                if (toCompare == null) { // register not yet set
                    registers.put(arg1, 0L);
                    toCompare = 0L;
                }
                if (toCompare != 0) {
                    Long jump = isNumeric(arg2) ? Long.valueOf(arg2) : registers.get(arg2);
                    currentInstruction += jump;
                } else {
                    currentInstruction++;
                }
            }
            public String toString() {
                return "jnz " + arg1 + " " + arg2;
            }
        }

        static class OutInstruction implements InstructionSet.Instruction {
            String arg1;
            public OutInstruction(String arg1) {
                this.arg1 = arg1;
            }
            public void process() {
                out += getOrZero(arg1);
                currentInstruction++;
            }
            public String toString() {
                return "out " + arg1;
            }
        }

        static long getOrZero(String register) {
            if (isNumeric(register)) return Long.valueOf(register);
            Long value = registers.get(register);
            return value == null ? 0 : value;
        }
    }




    static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

}
