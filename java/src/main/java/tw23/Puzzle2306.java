package tw23;

import aoc.AbstractPuzzle;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;



@Slf4j
public class Puzzle2306   extends AbstractPuzzle {
    /**
     * Constructor which accepts the puzzle input to be solved.
     *
     * @param puzzleInput the puzzle input
     */
    public Puzzle2306(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public String solvePart1() {
        var lines = getPuzzleInput().trim().split("\n");
        long product = 1L;
        var times =  extractUnsignedIntegers(lines[0]);
        var records =  extractUnsignedIntegers(lines[1]);

        for (var i = 0; i < times.size(); i++) {
            product *= winningCombinations(times.get(i), records.get(i));
        }
        log.info("product {}", product);
        return String.valueOf(product);
    }

    /**
     * d - total record
     * t - total time
     * other way
     * Problem can be solved using a quadratic equation.
     */
    private int winningCombinations(long t, long d) {
        int count = 0;
        for (int i = 0; i < t; i++) {
            // Hold down for i seconds, speed up for t - i seconds
            if ((t - i) * i > d) {
                count++;
            }
        }
        return count;
    }

    public static List<Integer> extractUnsignedIntegers(String s) {
        var list = new ArrayList<Integer>();

        var p = Pattern.compile("\\d+");
        var m = p.matcher(s);

        while (m.find()) {
            list.add(Integer.parseInt(m.group()));
        }

        return list;
    }


    @Override
    public String solvePart2() {
        var lines = getPuzzleInput().trim().split("\n");
        var times = extractUnsignedIntegers(lines[0]);
        var records = extractUnsignedIntegers(lines[1]);
        var totalTime = Long.parseLong(Joiner.on("").join(times));
        var totalRecord = Long.parseLong(Joiner.on("").join(records));
        int res = winningCombinations(totalTime, totalRecord);
        log.info("res {}", res);
        return String.valueOf(res);
        //35961505
    }
}
