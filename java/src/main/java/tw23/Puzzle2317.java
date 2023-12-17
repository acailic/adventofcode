package tw23;

import aoc.AbstractPuzzle;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import models.Point;
import util.Direction;
import util.Pair;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;


@Slf4j
public class Puzzle2317 extends AbstractPuzzle {
    /**
     * Constructor which accepts the puzzle input to be solved.
     *
     * @param puzzleInput the puzzle input
     */

    public Puzzle2317(String puzzleInput) {
        super(puzzleInput);
    }

    @SneakyThrows
    @Override
    public String solvePart1() {
        var input = getPuzzleInput();
        int solved = solve(input, 1, 3);
        log.info("23 part 1: {}", solved);
        return String.valueOf(solved);
    }

    @SneakyThrows
    @Override
    public String solvePart2() {
        var input = getPuzzleInput();
        int solved = solve(input, 4, 10);
        log.info("23 part 2: {}", solved);
        return String.valueOf(solved);
    }

    private int solve(String input, int minStraight, int maxStraight) {
        var lines = input.trim().split("\n");
        var grid = new HashMap<Point, Node>();

        for (var y = 0; y < lines.length; y++) {
            var line = lines[y];

            for (var x = 0; x < line.length(); x++) {
                grid.put(new Point(x, y), new Node(line.charAt(x) - '0'));
            }
        }

        var target = new Point(lines[0].length() - 1, lines.length - 1);

        var queue = new PriorityQueue<Player>(Comparator.comparingInt(a -> a.heatLoss));
        queue.add(new Player(new Point(0, 0), Direction.R, 0, 0));
        queue.add(new Player(new Point(0, 0), Direction.D, 0, 0));

        while (!queue.isEmpty()) {
            var player = queue.poll();
            var node = grid.get(player.position);

            if (player.position.equals(target) && player.chainSteps >= minStraight) {
                return player.heatLoss;
            }

            if (node.visited.contains(Pair.of(player.direction, player.chainSteps))) {
                continue;
            }

            node.visited.add(Pair.of(player.direction, player.chainSteps));

            if (player.chainSteps < maxStraight) {
                var nextPoint = player.position.forwardFromDirection(player.direction);
                var nextNode = grid.get(nextPoint);

                if (nextNode != null && !nextNode.visited.contains(Pair.of(player.direction, player.chainSteps + 1))) {
                    queue.add(new Player(nextPoint, player.direction, player.chainSteps + 1, player.heatLoss + nextNode.heatLoss));
                }
            }

            if (player.chainSteps >= minStraight) {
                var rightPoint = player.position.rightFromDirection(player.direction);
                var rightDirection = player.direction.turnRight();
                var rightNode = grid.get(rightPoint);

                if (rightNode != null && !rightNode.visited.contains(Pair.of(rightDirection, 1))) {
                    queue.add(new Player(rightPoint, rightDirection, 1, player.heatLoss + rightNode.heatLoss));
                }

                var leftPoint = player.position.leftFromDirection(player.direction);
                var leftDirection = player.direction.turnLeft();
                var leftNode = grid.get(leftPoint);

                if (leftNode != null && !leftNode.visited.contains(Pair.of(leftDirection, 1))) {
                    queue.add(new Player(leftPoint, leftDirection, 1, player.heatLoss + leftNode.heatLoss));
                }
            }
        }

        throw new RuntimeException("No solution found");
    }

    private record Player(Point position, Direction direction, int chainSteps, int heatLoss) {
    }

    private static class Node {
        public final int heatLoss;
        public HashSet<Pair<Direction, Integer>> visited = new HashSet<>();

        public Node(int heatLoss) {
            this.heatLoss = heatLoss;
        }
    }
}