package tw22;

import aoc.AbstractPuzzle;
import aoc.PuzzleInputFetcher;

public class Main {
    public static final int USED_YEAR = 2022;

    private static final PuzzleInputFetcher PUZZLE_INPUT_FETCHER = new PuzzleInputFetcher(String.valueOf(USED_YEAR));
    private static final AbstractPuzzle[] PUZZLES = new AbstractPuzzle[]{
            new Puzzle01(PUZZLE_INPUT_FETCHER.getPuzzleInput(USED_YEAR, 1))};

    public static void main(String[] args) {
       // PUZZLES[0].solvePart1();
        PUZZLES[0].solvePart2();
    }
}