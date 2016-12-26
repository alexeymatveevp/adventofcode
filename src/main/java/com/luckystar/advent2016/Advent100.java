package com.luckystar.advent2016;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * User: david
 * Date: 26.12.2016
 * Time: 22:57
 */
public class Advent100 {

    public static void main(String[] args) throws Exception {
        String input = new String(Files.readAllBytes(Paths.get(Advent100.class.getClassLoader().getResource("2016/input_10.txt").toURI())));
        String[] rows = input.split("\n");
        Pattern pvalue = Pattern.compile("value (\\d+) goes to bot (\\d+)");
        Pattern pgives = Pattern.compile("bot (\\d+) gives low to (output|bot) (\\d+) and high to (output|bot) (\\d+)");
        Set<Output> outputs = new HashSet<>();
        Set<Instruction> instructions = new HashSet<>();
        Set<Bot> bots = new HashSet<>();
        for (String row : rows) {
            Matcher mvalue = pvalue.matcher(row);
            Matcher mgives = pgives.matcher(row);
            if (mvalue.find()) {
                Integer value = Integer.valueOf(mvalue.group(1));
                Integer botNumber = Integer.valueOf(mvalue.group(2));
                Optional<Bot> any = bots.stream().filter(b -> botNumber.equals(b.number)).findAny();
                Bot bot = any.isPresent() ? any.get() : new Bot(botNumber);
                bot.addValue(value);
                bots.add(bot);
            }
            if (mgives.find()) {
                Integer bot = Integer.valueOf(mgives.group(1));
                Target lowTarget = Target.valueOf(mgives.group(2).toUpperCase());
                Integer low = Integer.valueOf(mgives.group(3));
                Target highTarget = Target.valueOf(mgives.group(4).toUpperCase());
                Integer high = Integer.valueOf(mgives.group(5));
                instructions.add(new Instruction(bot, lowTarget, low, highTarget, high));
            }
        }

        System.out.println("Starting bots configuration:");
        System.out.println("--");
        bots.forEach(System.out::println);

        // find bots with low and high set and process them
        boolean done = false;
        while (!done) {
            Set<Bot> activeBots = bots.stream().filter(Bot::hasLowHigh).collect(Collectors.toSet());
            if (!activeBots.isEmpty()) {
                // solution for part1
                Optional<Bot> part1Bot = activeBots.stream().filter(b -> b.low == 17 && b.high == 61).findAny();
                if (part1Bot.isPresent()) {
                    // GOTCHA!
                    System.out.println("GOTCHA: botNumber " + part1Bot.get().number);
                }
                Set<Instruction> activeBotInstructions = instructions.stream()
                        .filter(i -> activeBots.stream().map(b -> b.number).collect(Collectors.toSet()).contains(i.botNumber))
                        .collect(Collectors.toSet());
                for (Instruction instruction : activeBotInstructions) {
                    instruction.proceed(bots, outputs);
                }
            } else {
                done = true;
            }
        }

        System.out.println();
        System.out.println("End bots configuration:");
        System.out.println("--");
        bots.forEach(System.out::println);

        System.out.println();
        System.out.println("Outputs:");
        System.out.println("--");
        outputs.forEach(System.out::println);
    }

    static class Bot {
        Integer number;
        Integer low;
        Integer high;
        Bot(int number) {
            this.number = number;
        }
        void addValue(Integer value) {
            if (low == null && high == null) {
                low = value;
            } else if (low != null && high != null) {
                throw new IllegalArgumentException();
            } else if (low != null) {
                // compare
                if (low > value) {
                    high = low;
                    low = value;
                } else {
                    high = value;
                }
            }
        }
        boolean hasLowHigh() {
            return low != null && high != null;
        }
        @Override
        public String toString() {
            return "Bot " + number + ": low=" + low + " high=" + high;
        }
    }

    static class Instruction {
        Integer botNumber;
        Target lowTarget;
        Integer lowValue;
        Target highTarget;
        Integer highValue;

        Instruction(Integer botNumber, Target lowTarget, Integer lowValue, Target highTarget, Integer highValue) {
            this.botNumber = botNumber;
            this.lowTarget = lowTarget;
            this.lowValue = lowValue;
            this.highTarget = highTarget;
            this.highValue = highValue;
        }
        void proceed(Set<Bot> bots, Set<Output> outputs) {
            Bot thisBot = bots.stream().filter(b -> botNumber.equals(b.number)).findAny().get();
            if (lowTarget == Target.BOT) {
                Bot bot;
                Optional<Bot> anyBot = bots.stream().filter(b -> lowValue.equals(b.number)).findAny();
                if (anyBot.isPresent()) {
                    bot = anyBot.get();
                } else {
                    bot = new Bot(lowValue);
                    bots.add(bot);
                }
                bot.addValue(thisBot.low);
                thisBot.low = null;
            } else {
                Optional<Output> any = outputs.stream().filter(o -> lowValue.equals(o.number)).findAny();
                if (any.isPresent()) {
                    any.get().value = thisBot.low;
                } else {
                    outputs.add(new Output(lowValue, thisBot.low));
                }
                thisBot.low = null;
            }
            // high
            if (highTarget == Target.BOT) {
                Bot bot;
                Optional<Bot> anyBot = bots.stream().filter(b -> highValue.equals(b.number)).findAny();
                if (anyBot.isPresent()) {
                    bot = anyBot.get();
                } else {
                    bot = new Bot(highValue);
                    bots.add(bot);
                }
                bot.addValue(thisBot.high);
                thisBot.high = null;
            } else {
                Optional<Output> any = outputs.stream().filter(o -> highValue.equals(o.number)).findAny();
                if (any.isPresent()) {
                    any.get().value = thisBot.high;
                } else {
                    outputs.add(new Output(highValue, thisBot.high));
                }
                thisBot.high = null;
            }
        }
    }

    enum Target {
        BOT, OUTPUT
    }

    static class Output {
        Integer number;
        Integer value;

        Output(Integer number, Integer value) {
            this.number = number;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Output{" +
                    "number=" + number +
                    ", value=" + value +
                    '}';
        }
    }
}
