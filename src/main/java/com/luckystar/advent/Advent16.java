package com.luckystar.advent;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Part 1 can be guessed visually.
 * Part 2:
 */
public class Advent16 {

    static Map<String, Integer> checkingCompounds = new HashMap<>();

    public static void main(String[] args) throws Exception {
        String input = new String(Files.readAllBytes(Paths.get(Advent16.class.getClassLoader().getResource("input_16.txt").toURI())));
        String[] rows = input.split("\r\n");
        // hardcoded matching MFCSAM input

        checkingCompounds.put("children", 3);
        checkingCompounds.put("cats", 7);
        checkingCompounds.put("samoyeds", 2);
        checkingCompounds.put("pomeranians", 3);
        checkingCompounds.put("akitas", 0);
        checkingCompounds.put("vizslas", 0);
        checkingCompounds.put("goldfish", 5);
        checkingCompounds.put("trees", 3);
        checkingCompounds.put("cars", 2);
        checkingCompounds.put("perfumes", 1);

        for (String row : rows) {
            String[] parts = row.split(" ");
            int auntNumber = Integer.valueOf(parts[1].substring(0, parts[1].indexOf(":")));
            Map<String, Integer> currentAuntCompounds = new HashMap<>();
            for (int i=2; i<parts.length; i+=2) {
                String compound = parts[i].substring(0, parts[i].indexOf(":"));
                int quantity;
                if (i == parts.length - 2) {
                    // the last one
                    quantity = Integer.valueOf(parts[i+1]);
                } else {
                    quantity = Integer.valueOf(parts[i+1].substring(0, parts[i+1].indexOf(",")));
                }
                currentAuntCompounds.put(compound, quantity);
            }
            // check the aunt
            System.out.println("checking aunt " + auntNumber + "...");
//            boolean theRealAunt = checkAuntPart1(currentAuntCompounds);
            boolean theRealAunt = checkAuntPart2(currentAuntCompounds);

            if (theRealAunt) {
                System.out.println("The real one is: " + auntNumber);
                break;
            }
        }
    }

    static boolean checkAuntPart1(Map<String, Integer> currentAuntCompounds) {
        boolean theRealAunt = true;
        for (String compound : currentAuntCompounds.keySet()) {
            Integer checkQuantity = currentAuntCompounds.get(compound);
            Integer standardQuantity = checkingCompounds.get(compound);
            if (!checkQuantity.equals(standardQuantity)) {
                theRealAunt = false;
                break;
            }
        }
        return theRealAunt;
    }

    static boolean checkAuntPart2(Map<String, Integer> currentAuntCompounds) {
        boolean theRealAunt = true;
        for (String compound : currentAuntCompounds.keySet()) {
            Integer checkQuantity = currentAuntCompounds.get(compound);
            Integer standardQuantity = checkingCompounds.get(compound);
            if ((compound.equals("cats") || compound.equals("trees"))) {
                if (checkQuantity <= standardQuantity) {
                    System.out.println("# " + compound + " is only " + checkQuantity);
                    theRealAunt = false;
                    break;
                }
            } else if ((compound.equals("pomeranians") || compound.equals("goldfish"))) {
                if (checkQuantity >= standardQuantity) {
                    System.out.println("# " + compound + " is too much: " + checkQuantity);
                    theRealAunt = false;
                    break;
                }
            } else {
                if (!checkQuantity.equals(standardQuantity)) {
                    System.out.println("# " + compound + " is of not correct quantity: " + checkQuantity);
                    theRealAunt = false;
                    break;
                }
            }
        }
        return theRealAunt;
    }

}
