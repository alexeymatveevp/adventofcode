package com.luckystar.advent2016;

/**
 * User: david
 * Date: 29.12.2016
 * Time: 11:34
 */
public class Advent160 {

    public static void main(String[] args) {
        String input = "00111101111101000";
//        int discSize = 272;
        int discSize = 35651584; // part 2
        StringBuilder a = new StringBuilder();
        a.append(input);
        while (a.length() < discSize) {
            StringBuilder copyA = new StringBuilder(a);
            copyA.reverse();
            StringBuilder b = new StringBuilder();
            for (int i=0; i < copyA.length(); i++) {
                if (copyA.charAt(i) == '1') {
                    b.append("0");
                } else {
                    b.append("1");
                }
            }
            a.append("0");
            a.append(b.toString());
        }
        String data = a.subSequence(0, discSize).toString();
        String checksum = data;
        while (checksum.length() % 2 == 0) {
            StringBuilder newChecksum = new StringBuilder();
            for (int i=0; i < checksum.length(); i+=2) {
                if (checksum.charAt(i) == checksum.charAt(i+1)) {
                    newChecksum.append("1");
                } else {
                    newChecksum.append("0");
                }
            }
            checksum = newChecksum.toString();
        }
        System.out.println(checksum);
    }

}
