package tw23;

import aoc.AbstractPuzzle;
import lombok.extern.slf4j.Slf4j;
import models.Point;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Slf4j
public class Puzzle2311 extends AbstractPuzzle {
    /**
     * Constructor which accepts the puzzle input to be solved.
     *
     * @param puzzleInput the puzzle input
     */
    public Puzzle2311(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public String solvePart1() {
        var input = getPuzzleInput().split("\n");
        var expandMultiplier = 2;

        long sum = calculateDistancesSum(input, expandMultiplier);
        return ""+sum;
    }

    private static long calculateDistancesSum(String[] input, int expandMultiplier) {
        long sum = 0;
        ArrayList<Point> galaxies = new ArrayList<>();
        for(int i = 0; i< input.length; i++) {
            var line = input[i].trim();
            for(int j=0; j<line.length(); j++) {
                if(line.charAt(j) == '#') {
                    galaxies.add(new Point(j, i));
                }
            }
        }
        log.info("galaxies {}", galaxies);

        var xPos = galaxies.stream().map(g -> g.x).collect(Collectors.toSet());
        var yPos = galaxies.stream().map(g -> g.y).collect(Collectors.toSet());
        var emptyX= LongStream.rangeClosed(0, input.length-1).filter(l -> !xPos.contains(l)).boxed().collect(Collectors.toList());
        var emptyY= LongStream.rangeClosed(0, input.length-1).filter(l -> !yPos.contains(l)).boxed().collect(Collectors.toList());
        log.info("emptyX {}", emptyX);
        log.info("emptyY {}", emptyY);

        for(var galaxy: galaxies) {
            // count not empty
            var  deltaX = emptyX.stream().filter(emptyColumn-> galaxy.x>emptyColumn).count();
            var  deltaY = emptyY.stream().filter(emptyRow-> galaxy.y>emptyRow).count();
            //
            var newPos = galaxy.add(new Point(deltaX*(expandMultiplier -1), deltaY*(expandMultiplier -1)));
            galaxy.x = newPos.x;
            galaxy.y = newPos.y;
        }

        log.info("galaxies {}", galaxies);
        for( int i=0; i<galaxies.size(); i++) {
            for(int j=i+1; j<galaxies.size(); j++) {
                var distance = Point.manhattan(galaxies.get(i), galaxies.get(j));
                sum += distance;
            }
        }
        log.info("sum {}", sum);
        return sum;
    }

    @Override
    public String solvePart2() {
        var input = getPuzzleInput().split("\n");
        var expandMultiplier = 1000000;

        long sum = calculateDistancesSum(input, expandMultiplier);
        return ""+sum;
    }
}
