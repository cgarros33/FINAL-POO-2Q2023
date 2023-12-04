package frontend.model;

import backend.model.Figure;
import backend.model.Point;
import frontend.interfaces.Action;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;

public class ActionToggleButton<T extends DrawnFigure<? extends Figure>> extends ToggleButton implements Action<T> {

    private final Action<T> action;

    public ActionToggleButton(String name, Action<T> action) {
        super(name);
        this.action = action;
    }

    @Override
    public T action(Point startPoint, Point endPoint, Color color) {
        return action.action(startPoint, endPoint, color);
    }
}