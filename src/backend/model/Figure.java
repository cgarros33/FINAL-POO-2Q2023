package backend.model;

import backend.interfaces.Movable;

public abstract class Figure implements Movable {
    @Override
    public abstract void move(double diffX, double diffY);
    public abstract boolean belongs(Point point);
    public abstract boolean belongs(Figure figure);
}
