package com.luckystar.advent;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * User: david
 * Date: 12/22/2015
 * Time: 10:12 PM
 */
public class Advent912 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        String input = new String(Files.readAllBytes(Paths.get(Advent81.class.getClassLoader().getResource("input_9.txt").toURI())));
        String[] rows = input.split("\r\n");
        // create a set of city pairs
        Map<CityPair, Integer> cityPairToDistance = new HashMap<CityPair, Integer>();
        Set<CityPair> cityPairs = new HashSet<CityPair>();
        Set<String> cities = new HashSet<String>();
        for (String row : rows) {
            String[] parts = row.split(" ");
            CityPair cp = new CityPair(parts[0], parts[2]);
            Integer distance = Integer.valueOf(parts[4]);
            cityPairToDistance.put(cp, distance);
            cityPairs.add(cp);
            cities.add(parts[0]);
            cities.add(parts[2]);
        }
        String[] citiesArr = cities.toArray(new String[8]);

        // calculate all possible premutation (40320)
        ArrayList<ArrayList<String>> permutations = permuteUnique(citiesArr);
        System.out.println("Permutations: " + permutations.size());

        // calculate distances
        int min = Integer.MAX_VALUE;
        int max = 0;
        for (ArrayList<String> permutation : permutations) {
            int totalDistance = 0;
            for (int i=0; i < permutation.size() - 1; i++) {
                CityPair cp = new CityPair(permutation.get(i), permutation.get(i+1));
                totalDistance += cityPairToDistance.get(cp);
            }
            if (totalDistance < min) {
                min = totalDistance;
                System.out.println("new min: " + min);
            }
            if (totalDistance > max) {
                max = totalDistance;
            }
        }
        System.out.println("----");
        System.out.println("Min distance: " + min);
        System.out.println("Max distance: " + max);
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

//    static Set<List<CityPair>> calculatePremutations(Set<CityPair> cityPairs, List<CityPair> currentPermutation, Set<List<CityPair>> premutations) {
//        if (currentPermutation.size() == 8) {
//            premutations.add(currentPermutation);
//        }
//    }

    static class CityPair {
        String c1;
        String c2;

        public CityPair(String c1, String c2) {
            this.c1 = c1;
            this.c2 = c2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CityPair cityPair = (CityPair) o;

            if ((cityPair.c1.equals(c1) || cityPair.c1.equals(c2)) && (cityPair.c2.equals(c2) || cityPair.c2.equals(c1))) return true;
            return false;
        }

        @Override
        public int hashCode() {
            return 31;
        }
    }
}
