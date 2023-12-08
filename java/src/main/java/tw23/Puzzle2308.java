package tw23;

import aoc.AbstractPuzzle;
import lombok.extern.slf4j.Slf4j;
import util.Pair;

import java.math.BigInteger;
import java.util.HashMap;
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
            if(nextPlace.equalsIgnoreCase("ZZZ")){
                counter =counter.add(BigInteger.valueOf(1));
                log.info("found + counter {}", counter);
                return counter;
            }
            counter=counter.add(BigInteger.valueOf(1));
            currentPlace = nextPlace;
            log.info("currentPlace {}, counter {} move {}", currentPlace, counter, c.toString());
        }
        log.info("currentPlace {}, counter {}", currentPlace, counter);
        getCounter(initLine, paths, currentPlace, counter);
        return counter;
    }

    @Override
    public String solvePart2() {
        return null;
    }
}
