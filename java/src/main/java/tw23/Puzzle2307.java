package tw23;

import aoc.AbstractPuzzle;
import lombok.extern.slf4j.Slf4j;
import models.Hand;

import java.util.Arrays;
import java.util.TreeMap;

@Slf4j
public class Puzzle2307 extends AbstractPuzzle {
    /**
     * Constructor which accepts the puzzle input to be solved.
     *
     * @param puzzleInput the puzzle input
     */
    public Puzzle2307(String puzzleInput) {
        super(puzzleInput);
    }


    @Override
    public String solvePart1() {
        var input = getPuzzleInput().split("\n");
        var lines = Arrays.stream(input).toList();
        long sum = 0L;
        var handsScores = new TreeMap<Hand, Integer>(Hand::compare);
        var place = 1;
        for (var line: lines) {
            var parts = line.trim().split(" ");
            handsScores.put(new Hand(parts[0], false), Integer.parseInt(parts[1]));
        }
        log.info("handsScores {}", handsScores);
        for (var handScore: handsScores.entrySet()) {
            sum += (long) place++ * handScore.getValue();
        }
        log.info("sum {}", sum);

        return String.valueOf(sum);
    }


    @Override
    public String solvePart2() {
        var lines = getPuzzleInput().split("\n");

        long sum = 0L;
        var handsScores = new TreeMap<Hand, Integer>(Hand::compare);
        var place = 1;

        for (var line: lines) {
            var parts = line.trim().split(" ");
            handsScores.put(new Hand(parts[0], true), Integer.parseInt(parts[1]));
        }

        for (var handScore: handsScores.entrySet()) {
            sum += (long) place++ * handScore.getValue();
        }

        log.info("sum {}", sum);
        return String.valueOf(sum);
    }
}
