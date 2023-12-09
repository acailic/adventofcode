package tw23;

import aoc.AbstractPuzzle;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
@Slf4j
public class Puzzle2309 extends AbstractPuzzle {
    /**
     * Constructor which accepts the puzzle input to be solved.
     *
     * @param puzzleInput the puzzle input
     */
    public Puzzle2309(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public String solvePart1() {
        var input = getPuzzleInput().split("\n");
        long sum = 0;
        for (var line : input) {
           //5 13 45 115 234 420 731 1329 2591 5314 11131 23388 48974 102035 211293 434077 882526 1771333 3502944 6817585 13056927
            var numbers = line.trim().split(" ");
            // numbers to array list
            List<Long> current = new ArrayList<>();
            for(var number: numbers) {
                current.add(Long.parseLong(number));
            }
            var expandedList = expandedList(current);
            // reverse a list
            java.util.Collections.reverse(expandedList);
            var sumTemp = expandedList.stream().reduce(0L, (a,b)-> a+b.get(b.size()-1), Long::sum);
            sum += sumTemp;
        }
        log.info("sum {}", sum);

        return ""+sum;
    }


    private List<List<Long>> expandedList( List<Long>  current) {
        var result = new ArrayList<List<Long>>();
        result.add(current);

        var difList = current;
        do{
            var nextDiffList = new LinkedList<Long>();
            for(var i=0; i<difList.size()-1; i++) {
                var diff = difList.get(i+1) - difList.get(i);
                nextDiffList.add(diff);
            }
            result.add(nextDiffList);
            difList = nextDiffList;

        } while (difList.stream().anyMatch(l -> l!= 0));
        // remove last element
        result.remove(result.size()-1);

        return result;
    }

    @Override
    public String solvePart2() {
        var input = getPuzzleInput().split("\n");
        long sum = 0;
        for (var line : input) {
            //5 13 45 115 234 420 731 1329 2591 5314 11131 23388 48974 102035 211293 434077 882526 1771333 3502944 6817585 13056927
            var numbers = line.trim().split(" ");
            // numbers to array list
            List<Long> current = new ArrayList<>();
            for(var number: numbers) {
                current.add(Long.parseLong(number));
            }
            var expandedList = expandedList(current);
            // reverse a list
            java.util.Collections.reverse(expandedList);
            var sumTemp = expandedList.stream().reduce(0L, (a,b)-> b.get(0)-a, Long::sum);
            sum += sumTemp;
        }
        log.info("sum {}", sum);

        return ""+sum;
    }
}
