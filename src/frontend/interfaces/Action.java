package frontend.interfaces;

import backend.model.Figure;
import backend.model.Point;
import frontend.model.DrawnFigure;
import javafx.scene.paint.Color;

@FunctionalInterface
public interface Action<T extends DrawnFigure<? extends Figure>> {
    T action(Point startPoint, Point endPoint, Color color);
}
