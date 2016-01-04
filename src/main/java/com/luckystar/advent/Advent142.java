package com.luckystar.advent;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This time for 14.2 we need the full emulation of race
 * to calculate distance each step and grant reindeers stars.
 *
 * Created by amatveev on 03.01.2016.
 */
public class Advent142 {

    public static void main(String[] args) throws Exception {
        String input = new String(Files.readAllBytes(Paths.get(Advent142.class.getClassLoader().getResource("input_14.txt").toURI())));
        String[] rows = input.split("\r\n");
        // the total race time
        int RACE_TIME = 2503;
        System.out.println("The race begins! Race time is: " + RACE_TIME);
        // first get the reindeer properties
        Set<Reindeer> reindeers = new HashSet<>();
        for (String row : rows) {
            String[] parts = row.split(" ");
            String name = parts[0];
            int velocity = Integer.valueOf(parts[3]);
            int flyTime = Integer.valueOf(parts[6]);
            int restTime = Integer.valueOf(parts[13]);
            reindeers.add(new Reindeer(name, velocity, flyTime, restTime));
        }
        // initial state
        Map<Reindeer, Result> scoringTable = new HashMap<>();
        for (Reindeer reindeer : reindeers) {
            scoringTable.put(reindeer, new Result());
        }
        // this time we need full emulation
        for (int i=1; i<RACE_TIME+1; i++) {
            int maxDistance = 0;
            for (Reindeer reindeer : scoringTable.keySet()) {
                Result result = scoringTable.get(reindeer);
                if (result.ft == reindeer.ft) {
                    result.ft = 0;
                    result.rt = 1;
                    result.flying = false;
                } else if (result.rt == reindeer.rt) {
                    result.rt = 0;
                    result.ft = 1;
                    result.flying = true;
                } else if (result.flying) {
                    result.ft++;
                } else {
                    result.rt++;
                }
                // get the current distance of the reindeer
                result.currentDistance = calculateDistance(reindeer.v, reindeer.ft, reindeer.rt, i);
                if (result.currentDistance > maxDistance) {
                    maxDistance = result.currentDistance;
                }
            }
            // grant all leading reindeers a star
            for (Reindeer reindeer : scoringTable.keySet()) {
                Result result = scoringTable.get(reindeer);
                if (result.currentDistance == maxDistance) {
                    result.stars = result.stars + 1;
                }
            }
        }
        // finish
        System.out.println("The race is finished!");
        for (Reindeer reindeer : scoringTable.keySet()) {
            Result result = scoringTable.get(reindeer);
            System.out.println(reindeer.name + " gets " + result.stars + " stars");
        }

    }

    static int calculateDistance(int velocity, int flyTime, int restTime, int raceTime) {
        int flyRestUnits = raceTime / (flyTime + restTime);
        int distanceLeft = raceTime - flyRestUnits * (flyTime + restTime);
        int totalDistance = flyRestUnits * velocity * flyTime;
        if (distanceLeft < flyTime) {
            totalDistance += distanceLeft * velocity;
        } else {
            totalDistance += flyTime * velocity;
        }
        return totalDistance;
    }

    static class Reindeer {
        String name;
        int v;
        int ft;
        int rt;

        public Reindeer(String name, int v, int ft, int rt) {
            this.name = name;
            this.v = v;
            this.ft = ft;
            this.rt = rt;
        }
    }

    static class Result {
        int ft;
        int rt;
        boolean flying = true;
        int stars;
        int currentDistance;
    }

}
