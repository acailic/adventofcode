package tw23;

import aoc.AbstractPuzzle;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Triple;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static util.StringUtilities.ints;

@Slf4j
public class Puzzle2312 extends AbstractPuzzle {
    /**
     * Constructor which accepts the puzzle input to be solved.
     *
     * @param puzzleInput the puzzle input
     */
    public Puzzle2312(String puzzleInput) {
        super(puzzleInput);
    }
    private final HashMap<Triple<String, List<Integer>, Boolean>, Long> memory = new HashMap<>();

    @Override
    public String solvePart1() {
        var lines = getPuzzleInput().split("\n");
        var sum = 0L;
        for (var line : lines) {
            log.info("line: {}", line);
            var parts = line.split(" ");
            var springLine = parts[0].chars().mapToObj(Character::toString).collect(Collectors.joining());
            var numbers = ints(parts[1]);
            sum += combinationsCount(springLine, numbers, false);
        }
        log.info("sum: {}", sum);
        return ""+sum;
    }

    private long combinationsCount(String line, List<Integer> damaged, boolean inGroup) {
        var state = Triple.of(line, damaged, inGroup);
        var cached = cacheRead(state);

        if (cached != null) {
            return cached;
        }

        if (line.isEmpty()) {
            return damaged.isEmpty() || (damaged.size() == 1 && damaged.get(0) == 0) ? 1 : 0;
        }

        var head = line.charAt(0);
        var remainingLine = line.substring(1);

        switch (head) {
            case '.' -> {
                if (inGroup) {
                    if (damaged.get(0) != 0) {
                        return 0;
                    }

                    return cache(state, combinationsCount(remainingLine, damaged.subList(1, damaged.size()), false));
                }

                return cache(state, combinationsCount(remainingLine, damaged, false));
            }
            case '#' -> {
                if (!damaged.isEmpty() && damaged.get(0) > 0) {
                    var reducedDamaged = new LinkedList<>(damaged){{ set(0, damaged.get(0) - 1); }};

                    return cache(state, combinationsCount(remainingLine, reducedDamaged, true));
                }
            }
            case '?' -> {
                return cache(
                        state,
                        combinationsCount('.' + remainingLine, damaged, inGroup)
                                + combinationsCount('#' + remainingLine, damaged, inGroup)
                );
            }
        }

        return 0;
    }

    private long cache(Triple<String, List<Integer>, Boolean> state, long result) {
        memory.put(state, result);
        return result;
    }

    public Long cacheRead(Triple<String, List<Integer>, Boolean> state) {
        return memory.getOrDefault(state, null);
    }

    @Override
    public String solvePart2() {
        var lines = getPuzzleInput().trim().split("\n");
        var sum = 0L;

        for (var line: lines) {
            var parts = line.split(" ");
            var springsLine = parts[0].chars().mapToObj(Character::toString).collect(Collectors.joining());
            var numbers = ints(parts[1]);
            var newSpringsLine = springsLine + ('?' + springsLine).repeat(4);
            var newNumbers = new LinkedList<>(numbers){{
                addAll(numbers); addAll(numbers); addAll(numbers); addAll(numbers);
            }};

            sum += combinationsCount(newSpringsLine, newNumbers, false);
        }
        log.info("sum: {}", sum);
        return String.valueOf(sum);
    }
}
