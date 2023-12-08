package tw23;

import aoc.BasePuzzleTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

public class TestPuzzle08 extends BasePuzzleTest {


    @SneakyThrows
    @Test
    public void testExample1() {
        var puzzle = new Puzzle2308(getStoredInput(8));
        puzzle.solvePart1();
    }
    @SneakyThrows
    @Test
    public void testExample2() {
        var puzzle = new Puzzle2308(getStoredInput(8));
        puzzle.solvePart2();
    }
}
