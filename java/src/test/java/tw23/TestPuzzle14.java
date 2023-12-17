package tw23;

import aoc.BasePuzzleTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

public class TestPuzzle14 extends BasePuzzleTest {


    @SneakyThrows
    @Test
    public void testExample1() {
        var puzzle = new Puzzle2314(getStoredInput(14));
        puzzle.solvePart1();
    }
    @SneakyThrows
    @Test
    public void testExample2() {
        var puzzle = new Puzzle2314(getStoredInput(14));
        puzzle.solvePart2();
    }
}
