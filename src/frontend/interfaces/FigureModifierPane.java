package frontend.interfaces;

/**
 * Interfaz que ofrece el comportamiento necesario para asociar
 * y desasociar una figura a un panel
 */
public interface FigureModifierPane {
    void setFigure(EffectApplicableWithTags figure);
    void unsetFigure();
}
