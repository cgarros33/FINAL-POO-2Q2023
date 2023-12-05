package frontend.interfaces;

import backend.model.Figure;
import frontend.model.DrawnFigure;

public interface FigureModifierPane {

    void setFigure(DrawnFigure<? extends Figure> figure);

    void unsetFigure();


}
