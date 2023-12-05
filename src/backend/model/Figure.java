package backend.model;

import backend.interfaces.Manipulable;
import backend.interfaces.Movable;

public abstract class Figure implements Movable, Manipulable {
    @Override
    public abstract void move(double diffX, double diffY);
    public abstract boolean belongs(Point point);
    public abstract boolean belongs(Figure figure);

    protected abstract double getWidth();
    protected abstract double getHeight();

    public void flipX(){
        move(getWidth(),0);
    }
    public void flipY(){
        move(0, getHeight());
    }
}
