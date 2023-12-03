package backend.model;

public class Point {

    private double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void move(double diffX, double diffY){
        x += diffX;
        y += diffY;
    }


    public boolean isOver(Point p) {
        return Double.compare(y, p.getY()) < 0;
    }

    public boolean isUnder(Point p) {
        return Double.compare(y, p.getY()) > 0;
    }

    public boolean isLeft(Point p) {
        return Double.compare(x, p.getX()) < 0;
    }

    public boolean isRight(Point p) {
        return Double.compare(x, p.getX()) > 0;
    }
    @Override
    public String toString() {
        return String.format("{%.2f , %.2f}", x, y);
    }

    public double distanceTo(Point other){
        return Math.sqrt(Math.pow(Math.abs(x - other.x), 2) + Math.pow(Math.abs(y - other.y), 2));
    }
}
