package com.luckystar.advent;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * We just need to calculate the total distance of each reindeer
 * and then find the max distance.
 * Created by amatveev on 03.01.2016.
 */
public class Advent141 {

    public static void main(String[] args) throws Exception {
        String input = new String(Files.readAllBytes(Paths.get(Advent141.class.getClassLoader().getResource("input_14.txt").toURI())));
        String[] rows = input.split("\r\n");
        // the total race time
        int RACE_TIME = 2503;
        System.out.println("The race begins! Race time is: " + RACE_TIME);
        for (String row : rows) {
            String[] parts = row.split(" ");
            String name = parts[0];
            int velocity = Integer.valueOf(parts[3]);
            int flyTime = Integer.valueOf(parts[6]);
            int restTime = Integer.valueOf(parts[13]);
            int distance = calculateDistance(velocity, flyTime, restTime, RACE_TIME);
            System.out.println(name + ": " + distance + " km");
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
}
