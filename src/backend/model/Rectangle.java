package backend.model;

public class Rectangle extends Figure {

    private final Point topLeft, bottomRight;

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    @Override
    public String toString() {
        return String.format("Rectángulo [ %s , %s ]", topLeft, bottomRight);
    }

    @Override
    public void move(double diffX, double diffY) {
        topLeft.move(diffX, diffY);
        bottomRight.move(diffX, diffY);
    }

    @Override
    public boolean belongs(Point point) {
        return point.getX() > getTopLeft().getX() && point.getX() < getBottomRight().getX() &&
                point.getY() > getTopLeft().getY() && point.getY() < getBottomRight().getY();
    }

    @Override
    public boolean belongs(Figure figure) { //@todo: chequear nombre de la funcion
        Point topRight, bottomLeft;
        topRight = new Point(bottomRight.getX(), topLeft.getY());
        bottomLeft = new Point(topLeft.getX(), bottomRight.getY());
        return figure.belongs(topRight) && figure.belongs(topLeft) && figure.belongs(bottomRight) && figure.belongs(bottomLeft);
    }
}
