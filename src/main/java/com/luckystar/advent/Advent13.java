package com.luckystar.advent;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * The same as advent 9: permutations + each pair has a metric - their sum of happiness to each other.
 * Created by amatveev on 03.01.2016.
 */
public class Advent13 {

    public static void main(String[] args) throws Exception {
        // for the 1st input
//        String input = new String(Files.readAllBytes(Paths.get(Advent13.class.getClassLoader().getResource("input_13.txt").toURI())));
        // for input with self
        String input = new String(Files.readAllBytes(Paths.get(Advent13.class.getClassLoader().getResource("input_13_self.txt").toURI())));
        String[] rows = input.split("\r\n");
        // map the input to person pairs metrics
        Map<PersonPair, Integer> personPairToHappiness = new HashMap<>();
        Set<String> persons = new HashSet<>();
        for (String row : rows) {
            String[] parts = row.split(" ");
            String person1 = parts[0];
            String person2 = parts[10].substring(0, parts[10].indexOf("."));
            PersonPair pp = new PersonPair(person1, person2);
            if (!personPairToHappiness.containsKey(pp)) {
                // find happiness
                Integer happiness = Integer.valueOf(parts[3]);
                if (parts[2].equals("lose")) {
                    happiness = -happiness;
                }
                // find mirror
                for (String row2 : rows) {
                    String[] parts2 = row2.split(" ");
                    if (parts2[0].equals(person2) && parts2[10].substring(0, parts2[10].indexOf(".")).equals(person1)) {
                        Integer happiness2 = Integer.valueOf(parts2[3]);
                        if (parts2[2].equals("lose")) {
                            happiness2 = -happiness2;
                        }
                        happiness += happiness2;
                        break;
                    }
                }
                personPairToHappiness.put(pp, happiness);
                persons.add(person1);
                persons.add(person2);
            }
        }
        // find person permutations
        ArrayList<ArrayList<String>> permutations = permuteUnique(persons.toArray(new String[8]));
        System.out.println("Permutations: " + permutations.size());
        System.out.println("----");
        System.out.println("Guest happiness:");
        for (PersonPair pp : personPairToHappiness.keySet()) {
            Integer h = personPairToHappiness.get(pp);
            System.out.println("# " + pp.p1 + " and " + pp.p2 + ": " + h);
        }
        // calculate min and max happiness
        int min = Integer.MAX_VALUE;
        int max = 0;
        for (ArrayList<String> permutation : permutations) {
            int totalHappiness = 0;
            for (int i=0; i < permutation.size() - 1; i++) {
                PersonPair pp = new PersonPair(permutation.get(i), permutation.get(i+1));
                totalHappiness += personPairToHappiness.get(pp);
            }
            // add the 1st and last pair
            PersonPair pp = new PersonPair(permutation.get(0), permutation.get(permutation.size()-1));
            totalHappiness += personPairToHappiness.get(pp);
            if (totalHappiness < min) {
                min = totalHappiness;
            }
            if (totalHappiness > max) {
                max = totalHappiness;
            }
        }
        System.out.println("----");
        System.out.println("Min happiness: " + min);
        System.out.println("Max happiness: " + max);
        System.out.println("Total change: " + (max - min));
    }

    public static ArrayList<ArrayList<String>> permuteUnique(String[] num) {
        ArrayList<ArrayList<String>> returnList = new ArrayList<ArrayList<String>>();
        returnList.add(new ArrayList<String>());

        for (int i = 0; i < num.length; i++) {
            Set<ArrayList<String>> currentSet = new HashSet<ArrayList<String>>();
            for (List<String> l : returnList) {
                for (int j = 0; j < l.size() + 1; j++) {
                    l.add(j, num[i]);
                    ArrayList<String> T = new ArrayList<String>(l);
                    l.remove(j);
                    currentSet.add(T);
                }
            }
            returnList = new ArrayList<ArrayList<String>>(currentSet);
        }

        return returnList;
    }

    static class PersonPair {
        String p1;
        String p2;

        public PersonPair(String p1, String p2) {
            this.p1 = p1;
            this.p2 = p2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PersonPair cityPair = (PersonPair) o;

            if ((cityPair.p1.equals(p1) || cityPair.p1.equals(p2)) && (cityPair.p2.equals(p2) || cityPair.p2.equals(p1))) return true;
            return false;
        }

        @Override
        public int hashCode() {
            return 31;
        }
    }
}
