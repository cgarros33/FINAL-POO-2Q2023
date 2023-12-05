package backend.interfaces;

public interface Manipulable {

    //@todo: add point 2 functions

    public abstract void rotate();
    void scale(double diff);
    void flipX();
    void flipY();

    default void scaleUp(){scale(0.25);}
    default void scaleDown(){scale(-0.25);}

    //@todo: add point 4 functions

}
