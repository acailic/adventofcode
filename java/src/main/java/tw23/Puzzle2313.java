package tw23;

import aoc.AbstractPuzzle;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
public class Puzzle2313 extends AbstractPuzzle {
    /**
     * Constructor which accepts the puzzle input to be solved.
     *
     * @param puzzleInput the puzzle input
     */

    public Puzzle2313(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public String solvePart1() {

        var sum = detectMirrors(getPuzzleInput(), 0);
        log.info("13 part1: {}", sum);
        return "" + sum;
    }

    @Override
    public String solvePart2() {
        var sum = detectMirrors(getPuzzleInput(), 1);
        log.info("13 part 2: {}", sum);
        return "" + sum;
    }


    public int detectMirrors(String input, int allowedErrors) {
        var paterns = input.split("\n\n");
        var ld = LevenshteinDistance.getDefaultInstance();
        int sum = 0;

        for (var pattern : paterns) {
            var lines = pattern.split("\n");
            var columnsList = new ArrayList<List<Character>>();

            for (var line : lines) {
                for (var x = 0; x < line.length(); x++) {
                    if (columnsList.size() < x + 1) {
                        columnsList.add(new ArrayList<>());
                    }
                    columnsList.get(x).add(line.charAt(x));
                }
            }

            /// mapp columns to to list
            var columns = columnsList.stream().map(column ->
                            column.stream().map(String::valueOf).collect(Collectors.joining()))
                    .toList();
            // lines matching
            for (var i = 0; i < lines.length - 1; i++) {
                var lDistance = 0;

                for (int j = i, k = i + 1; j >= 0 && k < lines.length && lDistance <= allowedErrors; j--, k++) {
                    lDistance += ld.apply(lines[j], lines[k]);
                }

                if (lDistance == allowedErrors) {
                    log.info("found mirror at line: {}", i + 1);
                    sum += 100 * (i + 1);
                    break;
                }
            }
            log.info("sum1: {}", sum);
            // columns matching
            for (var i = 0; i < columns.size()-1 ; i++) {
                var lDistance = 0;

                for (int j = i, k = i + 1; j >= 0 && k < columns.size()&& lDistance <= allowedErrors; j--, k++) {
                    lDistance += ld.apply(columns.get(j), columns.get(k));
                }

                if (lDistance == allowedErrors) {
                    log.info("found mirror at column: {}", i + 1);
                    sum += i + 1;
                    break;
                }
            }
            log.info("sum2: {}", sum);

        }
        return sum;
    }
}