package tw23;

import aoc.AbstractPuzzle;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.OptionalLong;
import java.util.regex.Pattern;

@Slf4j
public class Puzzle2305 extends AbstractPuzzle {
    /**
     * Constructor which accepts the puzzle input to be solved.
     *
     * @param puzzleInput the puzzle input
     */
    public Puzzle2305(String puzzleInput) {
        super(puzzleInput);
    }
    protected String[] lines;

    @Override
    public String solvePart1() {
        log.info("starting part 1");
        // part 1
        lines = getPuzzleInput().split("\n");
        ArrayList<Long> seeds = new ArrayList<>();
        var matcher = Pattern.compile("\\d+").matcher(lines[0]);
        while (matcher.find()) {
            seeds.add(Long.parseLong(matcher.group(0)));
        }
        ArrayList<Long> next = new ArrayList<>();
        for (int i = 2; i < lines.length; i++) {
            var l = lines[i].trim();
            if (l.isBlank()) {
                next.addAll(seeds);
                seeds = next;
                next = new ArrayList<>();
            } else if (Character.isDigit(l.charAt(0))) {
                var ll = Arrays.stream(l.split("\\s+")).mapToLong(Long::parseLong).toArray();
                log.info("ll {}", ll);
                var it = seeds.iterator();
                while (it.hasNext()) {
                    var seed = it.next();
                    if (seed >= ll[1] && seed < (ll[1] + ll[2])) {
                        log.info("match: {}", seed);
                        log.info("ll: ", ll);
                        next.add(seed - ll[1] + ll[0]);
                        it.remove();
                    }
                }
            }
        }
        next.addAll(seeds);
        seeds = next;
        log.info("seeds {}", seeds);
        log.info("min {}", getMin(seeds));
        return getMin(seeds) +"";
    }

    @NotNull
    private static OptionalLong getMin(ArrayList<Long> seeds) {
        return seeds.stream().mapToLong(Long::longValue).min();
    }

    @Override
    public String solvePart2() {
        lines = getPuzzleInput().split("\n");
        ArrayList<Pair<Long,Long>> ranges = new ArrayList<>();
        var matcher = Pattern.compile("(\\d+)\\s+(\\d+)").matcher(lines[0]);
        while (matcher.find()) {
            ranges.add(Pair.of(Long.parseLong(matcher.group(1)), Long.parseLong(matcher.group(2))));
        }
        ArrayList<Pair<Long,Long>> next = new ArrayList<>();
        for (int i = 2; i < lines.length; i++) {
            var l = lines[i].trim();
            if (l.isBlank()) {
                next.addAll(ranges);
                ranges = next;
                next = new ArrayList<>();
            } else if (Character.isDigit(l.charAt(0))) {
                var ll = Arrays.stream(l.split("\\s+")).mapToLong(Long::parseLong).toArray();
                for (int j = 0; j < ranges.size(); j++) {
                    var seed = ranges.get(j);
                    if (seed.a < ll[1]) {
                        // if seed is contained in range
                        if ((seed.a + seed.b) > ll[1]) {
                            long len = ll[1] - seed.a;
                            ranges.set(j, Pair.of(seed.a, len));
                            ranges.add(j + 1, Pair.of(seed.a + len, seed.b - len));
                            j--;
                        }
                    } else {
                        // seed.a >= ll[1]
                        // ll[1]
                        if (seed.a < (ll[1] + ll[2])) {
                            if ((seed.a + seed.b) <= (ll[1] + ll[2])) {
                                // seeds are contained in range
                                next.add(Pair.of(seed.a - ll[1] + ll[0], seed.b));
                                ranges.remove(seed);
                                j--;
                            } else {
                                // split
                                long len = (ll[1] + ll[2]) - seed.a;
                                ranges.set(j, Pair.of(seed.a, len));
                                ranges.add(j + 1, Pair.of(seed.a + len, seed.b - len));
                                j--;
                            }
                        }
                    }
                }
            }
        }
        next.addAll(ranges);
        ranges = next;
        OptionalLong min = ranges.stream().mapToLong(s -> s.a).min();
        log.info("min {}", min);
        return min + "";
    }
}
