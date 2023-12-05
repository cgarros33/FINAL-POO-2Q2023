package backend.interfaces;

public interface Manipulable {


    void rotate();
    void scale(double diff);
    void flipX();
    void flipY();

    default void scaleUp(){scale(0.25);}
    default void scaleDown(){scale(-0.25);}


}
