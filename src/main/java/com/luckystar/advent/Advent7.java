package com.luckystar.advent;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * By: Alexey Matveev
 * Date: 23.12.2015
 * Time: 13:31
 */
public class Advent7 {

    static Map<String, Node> nodesMap = new HashMap<>();
    static Map<String, Integer> evaluatedMap = new HashMap<>();

    public static void main(String[] args) throws IOException, URISyntaxException {
        String input = new String(Files.readAllBytes(Paths.get(Advent7.class.getClassLoader().getResource("input_7.txt").toURI())));
        String[] rows = input.split("\r\n");

        for (String row : rows) {
            String[] parts = row.split(" ");
            if (parts.length == 3) {
                nodesMap.put(parts[2], new ResultNode(parts[2], fromString(parts[0])));
            } else if (parts[1].equals("AND")) {
                // y AND ae -> ag
                nodesMap.put(parts[4], new AndNode(parts[4], fromString(parts[0]), fromString(parts[2])));
            } else if (parts[1].equals("OR")) {
                // y OR ae -> ag
                nodesMap.put(parts[4], new OrNode(parts[4], fromString(parts[0]), fromString(parts[2])));
            } else if (parts[1].equals("RSHIFT")) {
                // lf RSHIFT 2 -> lg
                nodesMap.put(parts[4], new RshiftNode(parts[4], fromString(parts[0]), fromString(parts[2])));
            } else if (parts[1].equals("LSHIFT")) {
                // lf LSHIFT 15 -> lg
                nodesMap.put(parts[4], new LshiftNode(parts[4], fromString(parts[0]), fromString(parts[2])));
            }  else if (parts[0].equals("NOT")) {
                // NOT di -> dj
                nodesMap.put(parts[3], new NotNode(parts[3], fromString(parts[1])));
            }
        }

        String nodeToEvaluate = "a";
        // evaluate
        Node node = nodesMap.get(nodeToEvaluate);
        int value = node.evaluate();
//        System.out.println(value + 32768);
        for (String s : evaluatedMap.keySet()) {
            System.out.printf("%s%7s", s, evaluatedMap.get(s));
            System.out.println();
        }

        System.out.println("------------");
        System.out.println("a wire is: " + value);
    }

    static int encodeInt(String s) {
        Integer intValue = Integer.valueOf(s);
        return intValue.intValue();
    }

    static NodePart fromString(String s) {
        try {
            int value = encodeInt(s);
            return new NodePart(value);
        } catch (Throwable t) {
            return new NodePart(s);
        }
    }

    static abstract class Node {
        String name;
        NodePart left;
        NodePart right;
        abstract int evaluate();
    }

    static class ResultNode extends Node {
        public ResultNode(String name, NodePart left) {
            this.name = name;
            this.left = left;
        }
        int evaluate() {
            int value = left.isLeaf() ? left.number : nodesMap.get(left.nodeName).evaluate();
            evaluatedMap.put(name, value);
            return value;
        }
    }

    static class AndNode extends Node {
        public AndNode(String name, NodePart left, NodePart right) {
            this.name = name;
            this.left = left;
            this.right = right;
        }
        int evaluate() {
            Integer value = evaluatedMap.get(name);
            if (value == null) {
                int l = left.isLeaf() ? left.number : nodesMap.get(left.nodeName).evaluate();
                int r = right.isLeaf() ? right.number : nodesMap.get(right.nodeName).evaluate();
                value = r & l;
                evaluatedMap.put(name, value);
            }
            return value;
        }
    }

    static class OrNode extends Node {
        public OrNode(String name, NodePart left, NodePart right) {
            this.name = name;
            this.left = left;
            this.right = right;
        }
        int evaluate() {
            Integer value = evaluatedMap.get(name);
            if (value == null) {
                int l = left.isLeaf() ? left.number : nodesMap.get(left.nodeName).evaluate();
                int r = right.isLeaf() ? right.number : nodesMap.get(right.nodeName).evaluate();
                value = r | l;
                evaluatedMap.put(name, value);
            }
            return value;
        }
    }

    static class RshiftNode extends Node {
        public RshiftNode(String name, NodePart left, NodePart right) {
            this.name = name;
            this.left = left;
            this.right = right;
        }
        int evaluate() {
            Integer value = evaluatedMap.get(name);
            if (value == null) {
                int l = left.isLeaf() ? left.number : nodesMap.get(left.nodeName).evaluate();
                int r = right.isLeaf() ? right.number : nodesMap.get(right.nodeName).evaluate();;
                value = l >> r;
                evaluatedMap.put(name, value);
            }
            return value;
        }
    }

    static class LshiftNode extends Node {
        public LshiftNode(String name, NodePart left, NodePart right) {
            this.name = name;
            this.left = left;
            this.right = right;
        }
        int evaluate() {
            Integer value = evaluatedMap.get(name);
            if (value == null) {
                int l = left.isLeaf() ? left.number : nodesMap.get(left.nodeName).evaluate();
                int r = right.isLeaf() ? right.number : nodesMap.get(right.nodeName).evaluate();
                value = (l << r) & 0xffff; // cut off bigger then 16 bit
                evaluatedMap.put(name, value);
            }
            return value;
        }
    }

    static class NotNode extends Node {
        public NotNode(String name, NodePart right) {
            this.name = name;
            this.right = right;
        }
        int evaluate() {
            Integer value = evaluatedMap.get(name);
            if (value == null) {
                int r = right.isLeaf() ? right.number : nodesMap.get(right.nodeName).evaluate();
                value = ~r;
                evaluatedMap.put(name, value);
            }
            return value;
        }
    }

    static class NodePart {
        int number;
        String nodeName;

        public NodePart(int number) {
            this.number = number;
        }

        public NodePart(String nodeName) {
            this.nodeName = nodeName;
        }

        public boolean isLeaf() {
            return nodeName == null;
        }
    }

}
