package backend.interfaces;

/**
 * Interfaz que ofrece el comportamiento necesario para girar, voltear y escalar
 */
public interface Manipulable {
    void rotate();
    void scale(double diff);
    void flipX();
    void flipY();

    default void scaleUp(){scale(0.25);}
    default void scaleDown(){scale(-0.25);}
}
