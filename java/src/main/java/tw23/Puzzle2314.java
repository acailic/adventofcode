package tw23;

import aoc.AbstractPuzzle;
import lombok.extern.slf4j.Slf4j;
import models.Point;
import util.CompassDirection;

import java.util.HashMap;


@Slf4j
public class Puzzle2314 extends AbstractPuzzle {
    /**
     * Constructor which accepts the puzzle input to be solved.
     *
     * @param puzzleInput the puzzle input
     */

    public Puzzle2314(String puzzleInput) {
        super(puzzleInput);
    }

    private Point firstFreeSpot(HashMap<Point, Character> grid, CompassDirection direction, Point point) {
        Point lastFree = null;
        var previousPoint = point;

        while (true) {
            previousPoint = switch (direction) {
                case N -> previousPoint.north();
                case S -> previousPoint.south();
                case E -> previousPoint.east();
                case W -> previousPoint.west();
                default -> throw new RuntimeException("Unknown direction: " + direction);
            };

            var charAt = grid.getOrDefault(previousPoint, null);

            if (charAt == null || charAt != '.') {
                break;
            }

            lastFree = previousPoint;
        }

        return lastFree;
    }
    @Override
    public String solvePart1() {
        var lines = getPuzzleInput().split("\n");
        var grid = new HashMap<Point, Character>();

        for (var y = 0; y < lines.length; y++) {
            var line = lines[y];

            for (var x = 0; x < line.length(); x++) {
                grid.put(new Point(x, y), line.charAt(x));
            }
        }

        // North tilt
        for (var x = 0; x < lines[0].length(); x++) {
            for (var y = 0; y < lines.length; y++) {
                var point = new Point(x, y);
                var charAt = grid.get(point);

                if (charAt == 'O') {
                    var lastFree = firstFreeSpot(grid, CompassDirection.N, point);
                    if (lastFree != null) {
                        grid.put(lastFree, 'O');
                        grid.put(point, '.');
                    }
                }
            }
        }

        var sum = 0L;
        var maxWeight = lines.length;

        for (var a: grid.entrySet()) {
            if (a.getValue() == 'O') {
                sum += maxWeight - a.getKey().y;
            }
        }
        log.info("sum: {}", sum);
        return String.valueOf(sum);
    }

    @Override
    public String solvePart2() {
        var lines = getPuzzleInput().split("\n");
        var grid = new HashMap<Point, Character>();

        for (var y = 0; y < lines.length; y++) {
            var line = lines[y];

            for (var x = 0; x < line.length(); x++) {
                grid.put(new Point(x, y), line.charAt(x));
            }
        }

        var memory = new HashMap<HashMap<Point, Character>, Integer>();
        boolean enableMemory = true;

        for (var cycle = 1; cycle <= 1000000000; cycle++) {
            // North tilt
            for (var x = 0; x < lines[0].length(); x++) {
                for (var y = 0; y < lines.length; y++) {
                    var point = new Point(x, y);
                    var charAt = grid.get(point);

                    if (charAt == 'O') {
                        var lastFree = firstFreeSpot(grid, CompassDirection.N, point);
                        if (lastFree != null) {
                            grid.put(lastFree, 'O');
                            grid.put(point, '.');
                        }
                    }
                }
            }

            // West tilt
            for (var y = 0; y < lines.length; y++) {
                for (var x = 0; x < lines[0].length(); x++) {
                    var point = new Point(x, y);
                    var charAt = grid.get(point);

                    if (charAt == 'O') {
                        var lastFree = firstFreeSpot(grid, CompassDirection.W, point);
                        if (lastFree != null) {
                            grid.put(lastFree, 'O');
                            grid.put(point, '.');
                        }
                    }
                }
            }

            // South tilt
            for (var x = 0; x < lines[0].length(); x++) {
                for (var y = lines.length - 1; y >= 0; y--) {
                    var point = new Point(x, y);
                    var charAt = grid.get(point);

                    if (charAt == 'O') {
                        var lastFree = firstFreeSpot(grid, CompassDirection.S, point);
                        if (lastFree != null) {
                            grid.put(lastFree, 'O');
                            grid.put(point, '.');
                        }
                    }
                }
            }

            // East tilt
            for (var y = 0; y < lines.length; y++) {
                for (var x = lines[0].length() - 1; x >= 0; x--) {
                    var point = new Point(x, y);
                    var charAt = grid.get(point);

                    if (charAt == 'O') {
                        var lastFree = firstFreeSpot(grid, CompassDirection.E, point);
                        if (lastFree != null) {
                            grid.put(lastFree, 'O');
                            grid.put(point, '.');
                        }
                    }
                }
            }

            if (enableMemory) {
                if (memory.containsKey(grid)) {
                    var firstSeenIndex = memory.get(grid);
                    var repeatingWindow = cycle - firstSeenIndex;
                    cycle = 1000000000 - ((1000000000 - firstSeenIndex) % repeatingWindow);
                    enableMemory = false;
                } else {
                    memory.put(new HashMap<>(grid), cycle);
                }
            }
        }

        var sum = 0L;
        var maxWeight = lines.length;

        for (var a: grid.entrySet()) {
            if (a.getValue() == 'O') {
                sum += maxWeight - a.getKey().y;
            }
        }
        log.info("sum: {}", sum);
        return String.valueOf(sum);
    }


}