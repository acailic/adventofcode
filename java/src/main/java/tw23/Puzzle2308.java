package tw23;

import aoc.AbstractPuzzle;
import lombok.extern.slf4j.Slf4j;
import util.MathUtil;
import util.Pair;
import util.Regex;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Puzzle2308 extends AbstractPuzzle {
    /**
     * Constructor which accepts the puzzle input to be solved.
     *
     * @param puzzleInput the puzzle input
     */
    public Puzzle2308(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public String solvePart1() {
        var input = getPuzzleInput().split("\n");
        var firstLine = input[0].trim();
        var thirdLine = input[2].trim();
        Map<String, Pair<String,String>> paths = new HashMap<>();
        for (var line : input) {
            // regex to match string
            // AAA = (BBB, CCC)
            var regex = "([A-Z]+) = \\(([A-Z]+), ([A-Z]+)\\)";
            Matcher matcher = Pattern.compile(regex).matcher(line.trim());
            if (matcher.find()) {
                var first = matcher.group(1);
                var second = matcher.group(2);
                var third = matcher.group(3);
                paths.put(first, new Pair<>(second, third));
            }
        }
        // get first element of map
        log.info("Started");
        String currentPlace="AAA";

        BigInteger counter = getCounter(firstLine.trim(), paths, currentPlace, BigInteger.valueOf(0));
        log.info("counter end {}", counter);
        return ""+counter;
    }

    private BigInteger getCounter(String firstLine, Map<String, Pair<String, String>> paths, String currentPlace, BigInteger counter) {
        String initLine = new String(firstLine);
        for(Character c: firstLine.toCharArray()) {
            String nextPlace = null;
            if ("L".equalsIgnoreCase(c.toString())) {
                nextPlace = paths.get(currentPlace).a;
            } else if ("R".equalsIgnoreCase(c.toString())) {
                nextPlace = paths.get(currentPlace).b;
            }
            /// last letter in string is Z, like AAZ, ABZ, ACZ
            if(nextPlace.matches(".*Z$")){
                counter =counter.add(BigInteger.valueOf(1));
                log.info("found + counter {}", counter);
                return counter;
            }
            counter=counter.add(BigInteger.valueOf(1));
            currentPlace = nextPlace;
          //  log.info("currentPlace {}, counter {} move {}", currentPlace, counter, c.toString());
        }
        //log.info("currentPlace {}, counter {}", currentPlace, counter);
        return getCounter(initLine, paths, currentPlace, counter);
    }

    @Override
    public String solvePart2() {
        var input = getPuzzleInput();
        var parts = input.trim().split("\n\n");
        var moves = parts[0].trim();
        var nodes = parseNodes(parts[1]);
        var ghosts = new LinkedList<Ghost>();

        for (var node: nodes.entrySet()) {
            if (node.getKey().endsWith("A")) {
                var ghostNode = new Ghost();
                ghostNode.position = node.getValue();
                ghosts.add(ghostNode);
            }
        }

        var index = 0L;
        var allOnZ = false;
        var ghostCycleInterval = new HashMap<Ghost, Long>();
        log.info("Started");
        do {
            var move = moves.charAt((int) (index++ % moves.length()));
            allOnZ = true;

            for (var ghost: ghosts) {
                ghost.position = move == 'L' ? ghost.position.left : ghost.position.right;

                if (ghost.position.name.endsWith("Z")) {
                    if (ghost.lastMatchIndex != null) {
                        ghostCycleInterval.put(ghost, index - ghost.lastMatchIndex);
                    }

                    ghost.lastMatchIndex = index;
                } else {
                    allOnZ = false;
                }
            }

            if (ghostCycleInterval.size() == ghosts.size()) {
                String res = String.valueOf(MathUtil.lcm(ghostCycleInterval.values()));
                log.info("res {}", res);
                return res;
            }
        } while (!allOnZ);

        log.info("index {}", index);
        return String.valueOf(index);
    }

    public static class Ghost {
        public Node position;
        public Long lastMatchIndex = null;
    }

    public static class Node {
        public String name;
        public String leftName;
        public String rightName;
        public Node left;
        public Node right;

        public Node(String name, String leftName, String rightName) {
            this.name = name;
            this.leftName = leftName;
            this.rightName = rightName;
        }
    }


    private HashMap<String, Node> parseNodes(String input) {
        var nodes = new HashMap<String, Node>();

        for (var map: input.trim().split("\n")) {
            var matches = Regex.matchAll("\\w+", map);
            var node = new Node(matches.get(0), matches.get(1), matches.get(2));
            nodes.put(node.name, node);
        }

        for (var nodeEntrySet: nodes.entrySet()) {
            var node = nodeEntrySet.getValue();
            node.left = nodes.get(node.leftName);
            node.right = nodes.get(node.rightName);
        }

        return nodes;
    }
}
