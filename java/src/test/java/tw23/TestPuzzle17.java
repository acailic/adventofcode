package tw23;

import aoc.BasePuzzleTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

public class TestPuzzle17 extends BasePuzzleTest {


    @SneakyThrows
    @Test
    public void testExample1() {
        var puzzle = new Puzzle2317(getStoredInput(17));
        puzzle.solvePart1();
    }
    @SneakyThrows
    @Test
    public void testExample2() {
        var puzzle = new Puzzle2317(getStoredInput(17));
        puzzle.solvePart2();
    }
}
