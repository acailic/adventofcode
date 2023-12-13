package aoc;
import com.google.common.io.Resources;

import java.nio.file.Files;
import java.nio.file.Path;
@SuppressWarnings("UnstableApiUsage")
public class BasePuzzleTest {
    public static final String FILE_NAME = "input.txt";

    public String getStoredInput(int day) throws Exception {
        int year = 2023;
        var puzzleInputResource = Resources.getResource("puzzle/"+ year+"/"+ day+ "/" + FILE_NAME);
        return Files.readString(Path.of(puzzleInputResource.toURI()));
    }
}