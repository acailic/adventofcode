package aoc;
import com.google.common.io.Resources;

import java.nio.file.Files;
import java.nio.file.Path;
@SuppressWarnings("UnstableApiUsage")
public class BasePuzzleTest {
    public String getStoredInput(int day) throws Exception {
        int year = 2022;
        var puzzleInputResource = Resources.getResource("puzzle/"+ year+"/"+ day+"/input.txt");
        return Files.readString(Path.of(puzzleInputResource.toURI()));
    }
}