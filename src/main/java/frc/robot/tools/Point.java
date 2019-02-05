package frc.robot.tools;

public class Point {
    public double x;
    public double y;

    public static final Point origin = new Point(0, 0);

    public Point() {}

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Point other) {
        return Math.sqrt(Math.pow(other.x, 2) + Math.pow(other.y, 2));
    }

    public Point midpoint(Point other) {
        return new Point((this.x - other.x) / 2, (this.y - other.y) / 2);
    }
}