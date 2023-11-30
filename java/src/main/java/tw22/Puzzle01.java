package tw22;
import aoc.AbstractPuzzle;
import lombok.extern.slf4j.Slf4j;

import java.util.PriorityQueue;


@Slf4j

public class Puzzle01 extends AbstractPuzzle {
    /**
     * Constructor which accepts the puzzle input to be solved.
     *
     * @param puzzleInput the puzzle input
     */

    public Puzzle01(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public String solvePart1() {
        // Elf carrying the most Calories
        // parse text into a list
        // split by empty line   for each elf

        String[] elves = getPuzzleInput().split("\n\n");
        // init pat per elf, from 0 to n
        int max = 0;
        for (int i = 0; i < elves.length; i++) {
            // split by \n
            int tempMax=0;
            String[] elf = elves[i].split("\n");
            for (String s : elf) {
                tempMax=tempMax+Integer.parseInt(s);
            }
            if (tempMax>max){
                max=tempMax;
            }
        }

        // return the max
        return String.valueOf(max);

    }

    @Override
    public String solvePart2() {
        // Elf carrying the most Calories
        // parse text into a list
        // split by empty line   for each elf

        String[] elves = getPuzzleInput().split("\n\n");
        // init pat per elf, from 0 to n
        // create a stack with top 3 max values
        PriorityQueue<Integer> heap = new PriorityQueue();

        for (int i = 0; i < elves.length; i++) {
            // split by \n
            int tempMax=0;
            String[] elf = elves[i].split("\n");
            for (String s : elf) {
                tempMax=tempMax+Integer.parseInt(s);
            }
            heap.add(tempMax);
            if (heap.size()>3){
                heap.poll();
            }

        }

        // return the max
        int sumMax = heap.stream().mapToInt(Integer::intValue).sum();
        log.info("sumMax: {}", sumMax);
        return String.valueOf(sumMax);
    }
}
