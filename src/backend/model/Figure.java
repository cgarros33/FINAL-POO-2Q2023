package backend.model;

public abstract class Figure {
    public abstract void move(double diffX, double diffY);
    public abstract boolean belongs(Point point);

    public abstract void draw(DrawHelper figureDrawHelper);
}
