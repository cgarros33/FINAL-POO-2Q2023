package frontend.interfaces;

import backend.model.Figure;
import backend.model.Point;

@FunctionalInterface
public interface FigureCreator {
    Figure create(Point startPoint, Point endPoint);
}
