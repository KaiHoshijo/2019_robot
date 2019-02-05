package frc.robot.tools;

public class Contour {
    public Point center = new Point();

    public double area;

    public Contour() {}

    public Contour(Point center, double area) {
        this.center = center;
        this.area = area;
    }
}