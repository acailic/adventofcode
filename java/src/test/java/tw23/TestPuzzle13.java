package tw23;

import aoc.BasePuzzleTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

public class TestPuzzle13 extends BasePuzzleTest {


    @SneakyThrows
    @Test
    public void testExample1() {
        var puzzle = new Puzzle2313(getStoredInput(13));
        puzzle.solvePart1();
    }
    @SneakyThrows
    @Test
    public void testExample2() {
        var puzzle = new Puzzle2313(getStoredInput(13));
        puzzle.solvePart2();
    }
}
