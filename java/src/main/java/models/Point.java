package models;

// Point in 2D space
// for example, a coordinate on a map

import lombok.AllArgsConstructor;
import lombok.Data;
import util.Direction;


@AllArgsConstructor
@Data
public class Point {
    public long x;
    public long y;

    public static long manhattan(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    public Point add(Point other) {
        return new Point(this.x+other.x, this.y+other.y);
    }

    public Point north() {
        return new Point(x, y - 1);
    }

    public Point east() {
        return new Point(x + 1, y);
    }

    public Point south() {
        return new Point(x, y + 1);
    }

    public Point west() {
        return new Point(x - 1, y);
    }

    public Point leftFromDirection(Direction direction) {
        return switch (direction) {
            case U -> left();
            case D -> right();
            case L -> down();
            case R -> up();
        };
    }

    public Point rightFromDirection(Direction direction) {
        return switch (direction) {
            case U -> right();
            case D -> left();
            case L -> up();
            case R -> down();
        };
    }

    public Point forwardFromDirection(Direction direction) {
        return switch (direction) {
            case U -> up();
            case D -> down();
            case L -> left();
            case R -> right();
        };
    }

    public Point right() {
        return new Point(x + 1, y);
    }

    public Point left() {
        return new Point(x - 1, y);
    }

    public Point up() {
        return new Point(x, y - 1);
    }

    public Point down() {
        return new Point(x, y + 1);
    }

}
