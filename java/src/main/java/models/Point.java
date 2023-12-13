package models;

// Point in 2D space
// for example, a coordinate on a map

import lombok.AllArgsConstructor;
import lombok.Data;


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

}
