package backend.model;

import backend.interfaces.Movable;

public class Point implements Movable {

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

    @Override
    public void move(double diffX, double diffY){
        x += diffX;
        y += diffY;
    }

    /**
     * De los siguientes 4 metodos solo se utilizan 2. No obstante, nos
     * parecio correcto que los 2 metodos no utilizados esten incluidos
     * por completitud.
     */
    public boolean isOver(Point p) {
        return Double.compare(y, p.getY()) < 0;
    }

    @SuppressWarnings("unused")
    public boolean isUnder(Point p) {
        return Double.compare(y, p.getY()) > 0;
    }

    public boolean isLeft(Point p) {
        return Double.compare(x, p.getX()) < 0;
    }
    
    @SuppressWarnings("unused")
    public boolean isRight(Point p) {
        return Double.compare(x, p.getX()) > 0;
    }
    @Override
    public String toString() {
        return String.format("{%.2f , %.2f}", x, y);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj ||
                (obj instanceof Point point &&
                x == point.x && y == point.y);
    }

    public double distanceTo(Point other){
        return Math.sqrt(Math.pow(Math.abs(x - other.x), 2) + Math.pow(Math.abs(y - other.y), 2));
    }
}
