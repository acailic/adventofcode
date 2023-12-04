package tw23;
import aoc.BasePuzzleTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

public class TestPuzzle04 extends BasePuzzleTest {

    @SneakyThrows
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle2304(getStoredInput(4));
        puzzle.solvePart1();
    }

    @SneakyThrows
    @Test
    public void testPart2Example1() {
        var puzzle = new Puzzle2304(getStoredInput(4));
        puzzle.solvePart2();
    }
}