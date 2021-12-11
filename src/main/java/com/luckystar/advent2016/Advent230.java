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
 * Created by ksavina on 11/22/2017.
 */
public class Advent230 {

    public static void main(String[] args) throws Exception {
        // copy paste from Advent120 initially
        String input = new String(Files.readAllBytes(Paths.get(Advent230.class.getClassLoader().getResource("2016/input_23.txt").toURI())));
        String[] rows = input.split("\n");
        Pattern pcpy = Pattern.compile("cpy (-?\\d+|\\D) (\\D)");
        Pattern pinc = Pattern.compile("inc (\\D)");
        Pattern pdec = Pattern.compile("dec (\\D)");
        Pattern pjnz = Pattern.compile("jnz (-?\\d+|\\D) (-?\\d+|\\D)");
        Pattern ptgl = Pattern.compile("tgl (\\D)");
        InstructionSet instructionSet = new InstructionSet();
        for (String row : rows) {
            Matcher mcpy = pcpy.matcher(row);
            Matcher minc = pinc.matcher(row);
            Matcher mdec = pdec.matcher(row);
            Matcher mjnz = pjnz.matcher(row);
            Matcher mtgl = ptgl.matcher(row);
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
            if (mtgl.find()) {
                instructionSet.addInstruction(new InstructionSet.TglInstruction(mtgl.group(1)));
            }
        }
        // run processor
        instructionSet.setRegister("a", 12L); // 7 for part 1, 12 for part 2
        instructionSet.processInstructions();
        instructionSet.printRegisters();
    }

    static class InstructionSet {
        static int currentInstruction = 0;
        static List<Instruction> instructions = new ArrayList<>();
        static Map<String, Long> registers = new HashMap<>();

        void addInstruction(Instruction i) {
            instructions.add(i);
        }

        void processInstructions() {
            while (currentInstruction < instructions.size()) {
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
                    Instruction i1 = instructions.get(currentInstruction);
                    Instruction i2 = instructions.get(currentInstruction + 1);
                    Instruction i3 = instructions.get(currentInstruction + 2);
                    Instruction i4 = instructions.get(currentInstruction + 3);
                    Instruction i5 = instructions.get(currentInstruction + 4);
                    Instruction i6 = instructions.get(currentInstruction + 5);
                    if (i1 instanceof CpyInstruction && i2 instanceof IncInstruction && i3 instanceof DecInstruction && i4 instanceof JnzInstruction && i5 instanceof DecInstruction && i6 instanceof JnzInstruction) {
                        CpyInstruction cpy = (CpyInstruction) i1;
                        IncInstruction inc = (IncInstruction) i2;
                        DecInstruction dec1 = (DecInstruction) i3;
                        JnzInstruction jnz1 = (JnzInstruction) i4;
                        DecInstruction dec2 = (DecInstruction) i5;
                        JnzInstruction jnz2 = (JnzInstruction) i6;
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

                if (currentInstruction <= instructions.size() - 1) {
                    Instruction instruction = instructions.get(currentInstruction);
                    instruction.process();
                }
            }
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
            Instruction toggle();
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
            public Instruction toggle() {
                return new DecInstruction(arg1);
            }
            public String toString() {
                return "inc " + arg1;
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
            public Instruction toggle() {
                return new IncInstruction(arg1);
            }
            public String toString() {
                return "dec " + arg1;
            }
        }

        static class CpyInstruction implements Instruction {
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
            public Instruction toggle() {
                return new JnzInstruction(arg1, arg2);
            }
            public String toString() {
                return "cpy " + arg1 + " " + arg2;
            }
        }

        static class JnzInstruction implements Instruction {
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
            public Instruction toggle() {
                return new CpyInstruction(arg1, arg2);
            }
            public String toString() {
                return "jnz " + arg1 + " " + arg2;
            }
        }

        static class TglInstruction implements Instruction {
            String arg1;
            public TglInstruction(String arg1) {
                this.arg1 = arg1;
            }
            public void process() {
                int jump = registers.get(arg1).intValue();
                int instructionToChange = currentInstruction + jump;
                if (instructionToChange >= 0 && instructionToChange <= instructions.size() - 1) {
                    Instruction instruction = instructions.get(instructionToChange);
                    instructions.set(instructionToChange, instruction.toggle()); // set the toggled instruction instead of old one
                } else {
                    // out of bound - just skip it i guess
                }
                currentInstruction++;
            }
            public Instruction toggle() {
                return new IncInstruction(arg1);
            }
            public String toString() {
                return "tgl " + arg1;
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
