package com.luckystar.advent2018;

import com.luckystar.InputLoader;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Advent04 {

    // [1518-05-31 00:27] falls asleep
    static Pattern LINE_PATTERN = Pattern.compile("^\\[(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2})\\] (.*)$");
    static Pattern TEXT_GUARD_BEGINS_SHIFT_PATTERN = Pattern.compile("^Guard #(\\d+) begins shift$");

    public static void main(String[] args) {
        String input = InputLoader.readInput("2018/input_4.txt");
        String[] lines = input.split("\r\n");
        List<Event> events = new ArrayList<>();
        for (String line : lines) {
            Matcher m = LINE_PATTERN.matcher(line);
            if (m.matches()) {
                Integer year = Integer.valueOf(m.group(1));
                Integer month = Integer.valueOf(m.group(2));
                Integer day = Integer.valueOf(m.group(3));
                Integer hour = Integer.valueOf(m.group(4));
                Integer minute = Integer.valueOf(m.group(5));
                LocalDateTime time = LocalDateTime.of(year, month, day, hour, minute);
                String text = m.group(6);
                if (text.equals("falls asleep")) {
                    events.add(new Event(EventType.FALLS_ASLEEP, time, null));
                } else if (text.equals("wakes up")) {
                    events.add(new Event(EventType.WAKES_UP, time, null));
                } else {
                    Matcher matcher = TEXT_GUARD_BEGINS_SHIFT_PATTERN.matcher(text);
                    if (matcher.matches()) {
                        Integer guard = Integer.valueOf(matcher.group(1));
                        events.add(new Event(EventType.BEGINS_SHIFT, time, guard));
                    }
                }
            } else {
                throw new RuntimeException("Incorrect pattern " + line);
            }
        }

        events.sort(Comparator.comparing(e -> e.time));

        Map<Integer, Integer> guard2Minutes = new HashMap<>();
        Iterator<Event> it = events.iterator();
        Event event = it.next();
        List<Event> guard997 = new ArrayList<>();
        while (it.hasNext()) {
            boolean guard997Yes = false;
            if (event.guard == 997) {
                guard997.add(event);
                guard997Yes = true;
            }
            LocalDateTime lastAwakeTime = event.time;
            Event e = null;
            while (it.hasNext() && (e = it.next()).type != EventType.BEGINS_SHIFT) {
                if (guard997Yes) {
                    guard997.add(e);
                }
                if (e.type == EventType.FALLS_ASLEEP) {
                    Duration duration = Duration.between(lastAwakeTime, e.time);
                    long minutes = duration.toMinutes();
                    guard2Minutes.putIfAbsent(event.guard, 0);
                    Integer asleepMinutes = guard2Minutes.get(event.guard);
                    guard2Minutes.put(event.guard, asleepMinutes + (int) minutes);
                } else if (e.type == EventType.WAKES_UP) {
                    lastAwakeTime = e.time;
                }
            }

            event = e;

        }

        System.out.println(guard2Minutes.size());

        System.out.println(guard997.size());


        List<Event> sleepEvents = guard997.stream().filter((e) -> e.type != EventType.BEGINS_SHIFT).collect(Collectors.toList());
        // 60 -> 30
        List<TimeSleep> durations = new ArrayList<>();
        for (int i = 0; i < sleepEvents.size(); i+=2) {
            Event fallsAsleep = sleepEvents.get(i);
            Event wakesUp = sleepEvents.get(i + 1);
            if (fallsAsleep.type != EventType.FALLS_ASLEEP) throw new RuntimeException("HUEVIY INPUT");
            if (wakesUp.type != EventType.WAKES_UP) throw new RuntimeException("HUEVIY INPUT BLYAD!");
            TimeSleep duration = new TimeSleep(fallsAsleep.time, wakesUp.time);
            durations.add(duration);
        }

        System.out.println(durations);

        List<TimeSleep2> durations2 = new ArrayList<>();
        for (int i = 1; i < durations.size(); i++) {
            TimeSleep ts1 = durations.get(i-1);
            TimeSleep ts2 = durations.get(i);
            int md1 = ts1.end.get(ChronoField.MINUTE_OF_DAY);
            int md2 = ts2.start.get(ChronoField.MINUTE_OF_DAY);
            durations2.add(new TimeSleep2(md1, md2));
        }

        durations2.sort(Comparator.comparing(ts -> ts.start));

        System.out.println(durations2.size());

//        List<Integer> list = new ArrayList<>();
//        for (TimeSleep2 ts : durations2) {
//            list.add
//        }


    }

    static class Event {
        EventType type;
        LocalDateTime time;
        Integer guard;

        public Event(EventType type, LocalDateTime time, Integer guard) {
            this.type = type;
            this.time = time;
            this.guard = guard;
        }
    }

    enum EventType {
        FALLS_ASLEEP, BEGINS_SHIFT, WAKES_UP
    }

    static class GuardSleep {
        Integer guard;
        Integer totalMinutes;
        LocalDateTime lastTime;

        public GuardSleep(Integer guard, Integer totalMinutes, LocalDateTime lastTime) {
            this.guard = guard;
            this.totalMinutes = totalMinutes;
            this.lastTime = lastTime;
        }
    }

    static class TimeSleep {
        LocalDateTime start;
        LocalDateTime end;

        public TimeSleep(LocalDateTime start, LocalDateTime end) {
            this.start = start;
            this.end = end;
        }
    }

    static class TimeSleep2 {
        int start;
        int end;

        public TimeSleep2(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}
