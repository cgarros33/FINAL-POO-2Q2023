package frontend.interfaces;

import backend.interfaces.Manipulable;
import backend.model.Figure;
import frontend.model.DrawnFigure;

public interface FigureModifierPane {

    void setFigure(Manipulable figure);

    void unsetFigure();


}
