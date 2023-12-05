package backend.model;

public class Ellipse extends Figure {

    private final Point centerPoint;
    private final double xAxis, yAxis;

    public Ellipse(Point centerPoint, double xAxis, double yAxis) {
        this.centerPoint = centerPoint;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }

    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, DMayor: %.2f, DMenor: %.2f]", centerPoint, xAxis, yAxis);
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public double getXAxis() {
        return xAxis;
    }

    public double getYAxis() {
        return yAxis;
    }

    @Override
    public void move(double diffX, double diffY) {
        centerPoint.move(diffX, diffY);
    }

    @Override
    public boolean belongs(Point point) {
        return ((Math.pow(point.getX() - getCenterPoint().getX(), 2) / Math.pow(getXAxis(), 2)) +
                (Math.pow(point.getY() - getCenterPoint().getY(), 2) / Math.pow(getYAxis(), 2))) <= 0.30;
    }

    @Override
    public boolean belongs(Figure figure){ //@todo: chequear nombre de la funcion
        Point up, down, right, left;
        up = new Point(centerPoint.getX(), centerPoint.getY() - yAxis / 2);
        down = new Point(centerPoint.getX(), centerPoint.getY() + yAxis / 2);
        right = new Point(centerPoint.getX() + xAxis / 2, centerPoint.getY());
        left = new Point(centerPoint.getX() - xAxis / 2, centerPoint.getY());
        return figure.belongs(up) && figure.belongs(down) && figure.belongs(right) && figure.belongs(left);
    }
}
