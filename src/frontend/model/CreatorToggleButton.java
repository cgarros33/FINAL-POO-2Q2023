package frontend.model;

import backend.model.Figure;
import backend.model.Point;
import frontend.interfaces.Creator;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;

public class CreatorToggleButton<T extends DrawnFigure<? extends Figure>> extends ToggleButton implements Creator<T>{

    private final Creator<T> creator;

    public CreatorToggleButton(String name, Creator<T> creator) {
        super(name);
        this.creator = creator;
    }

    @Override
    public T create(Point startPoint, Point endPoint, Color color) {
        return creator.create(startPoint, endPoint, color);
    }
}