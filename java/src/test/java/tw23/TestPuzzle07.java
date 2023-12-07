package tw23;

import aoc.BasePuzzleTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

public class TestPuzzle07 extends BasePuzzleTest {


    @SneakyThrows
    @Test
    public void testExample1() {
        var puzzle = new Puzzle2307(getStoredInput(7));
        puzzle.solvePart1();
    }
    @SneakyThrows
    @Test
    public void testExample2() {
        var puzzle = new Puzzle2307(getStoredInput(7));
        puzzle.solvePart2();
    }
}
