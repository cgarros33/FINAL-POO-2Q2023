package backend.model;

import backend.interfaces.Manipulable;
import backend.interfaces.Movable;

public abstract class Figure implements Movable, Manipulable {
    @Override
    public abstract void move(double diffX, double diffY);

    /**
     * Analiza si un punto pertenece a una figura
     * @param point
     * @return true si el punto pertenece, false en caso contrario
     */
    public abstract boolean belongs(Point point);

    /**
     * Analiza si la figura instanciada pertenece a la figura recibida
     * @param figure
     * @return true si la figura instanciada pertenece a la figura recibida, false si no
     */
    public abstract boolean belongs(Figure figure);

    protected abstract double getWidth();
    protected abstract double getHeight();

    @Override
    public void flipX(){
        move(getWidth(),0);
    }

    @Override
    public void flipY(){
        move(0, getHeight());
    }
}
