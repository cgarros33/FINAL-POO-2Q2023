package frontend.interfaces;

/**
 * Interfaz que ofrece el comportamiento necesario para dibujar
 * los efectos de sombra, gradiente y biselado
 */
public interface EffectsDrawable {
    void drawShadow();
    void drawGradient();
    void drawBezel();
}
