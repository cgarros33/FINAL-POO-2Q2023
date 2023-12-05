package backend.model;

public class Circle extends Ellipse {
    
    public Circle(Point centerPoint, double radius) {
        super(centerPoint, 2*radius, 2*radius);
    }

    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s, Radio: %.2f]", getCenterPoint(), getRadius());
    }

    public double getRadius() {
        return getXAxis()/2;
    }

    @Override
    public boolean belongs(Point point) {
        // Sobreescrito pues tiene precision exacta a diferencia de la implementacion de Ellipse
        return Math.sqrt(Math.pow(getCenterPoint().getX() - point.getX(), 2) +
                Math.pow(getCenterPoint().getY() - point.getY(), 2)) < getRadius();
    }
}
