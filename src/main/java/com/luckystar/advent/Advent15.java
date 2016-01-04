package com.luckystar.advent;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by amatveev on 03.01.2016.
 */
public class Advent15 {

    public static void main(String[] args) throws Exception {
        String input = new String(Files.readAllBytes(Paths.get(Advent15.class.getClassLoader().getResource("input_15.txt").toURI())));
        String[] rows = input.split("\r\n");
        // parse the ingredients
        List<Ingredient> ingredients = new ArrayList<>();
        for (String row : rows) {
            String[] parts = row.split(" ");
            String name = parts[0].substring(0, parts[0].indexOf(":"));
            int capacity = Integer.valueOf(parts[2].substring(0, parts[2].indexOf(",")));
            int durability = Integer.valueOf(parts[4].substring(0, parts[4].indexOf(",")));
            int flavor = Integer.valueOf(parts[6].substring(0, parts[6].indexOf(",")));
            int texture = Integer.valueOf(parts[8].substring(0, parts[8].indexOf(",")));
            int calories = Integer.valueOf(parts[10]);
            ingredients.add(new Ingredient(name, capacity, durability, flavor, texture, calories));
        }
        System.out.println("There are " + ingredients.size() + " ingredients, lets find the best recipe");
        System.out.println("----");
        Sum100Array arr = new Sum100Array(4);
        int i = 1;
        boolean end = false;
        long maxPleasure = 0;
        int[] bestRecipe = null;
        while (!end) {
            end = arr.inc();
//            System.out.println(Arrays.toString(arr.num));
            // calculate cookie recipe
            int[] quantity = arr.num;
            int j = 0;
            int totalCapacity = 0;
            int totalDurability = 0;
            int totalFlavor = 0;
            int totalTexture = 0;
            int totalCalories = 0;
            for (Ingredient ingredient : ingredients) {
                totalCapacity += quantity[j] * ingredient.capacity;
                totalDurability += quantity[j] * ingredient.durability;
                totalFlavor += quantity[j] * ingredient.flavor;
                totalTexture += quantity[j] * ingredient.texture;
                totalCalories += quantity[j] * ingredient.calories;
                j++;
            }
            long pleasure;
            if (totalCapacity > 0 && totalDurability > 0 && totalFlavor > 0 && totalTexture > 0) {
                pleasure = totalCapacity * totalDurability * totalFlavor * totalTexture;
            } else {
                pleasure = 0;
            }
            // for part 2
            boolean withCalories = true;
            if (pleasure > maxPleasure) {
                if (!withCalories || totalCalories == 500) {
                    System.out.println("# new max pleasure: " + pleasure);
                    System.out.println(Arrays.toString(arr.num));
                    maxPleasure = pleasure;
                    bestRecipe = quantity.clone();
                }
            }
            i++;
        }
        System.out.println("----");
        System.out.println("total: " + i + " iterations");
        System.out.println("max pleasure: " + maxPleasure);
        System.out.println("Best recipe:");
        int j = 0;
        for (Ingredient ingredient : ingredients) {
            System.out.println(bestRecipe[j] + " spoons of " + ingredient.name);
            j++;
        }
    }

    static class Sum100Array {
        int[] num;
        int rank;

        Sum100Array(int rank) {
            this.rank = rank;
        }

        boolean inc() {
            // init first state
            if (num == null) {
                num = new int[rank];
                num[0] = 100;
                return false;
            }
            // from left to right: decrement if not 0, +1 to next
            // if current idx != 0 - move current value to idx = 0
            // repeat
            for (int i=0; i<num.length; i++) {
                if (num[i] == 0) continue;
                if (i == num.length-1 && num[i] == 100) {
                    return true;
                }
                num[i] = num[i] - 1;
                num[i+1] = num[i+1] + 1;
                // shift num[i] to the very left
                num[0] = num[i];
                if (i != 0) {
                    num[i] = 0;
                }
                break;
            }
            return false;
        }
    }

    static class Ingredient {
        String name;
        int capacity;
        int durability;
        int flavor;
        int texture;
        int calories;

        public Ingredient(String name, int capacity, int durability, int flavor, int texture, int calories) {
            this.name = name;
            this.capacity = capacity;
            this.durability = durability;
            this.flavor = flavor;
            this.texture = texture;
            this.calories = calories;
        }
    }
}
