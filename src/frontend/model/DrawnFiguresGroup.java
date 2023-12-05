package frontend.model;

import backend.model.Figure;
import backend.interfaces.Movable;
import frontend.interfaces.Drawable;

import java.util.ArrayList;

public class DrawnFiguresGroup extends ArrayList<DrawnFigure<? extends Figure>> implements Movable, Drawable {

    @Override
    public void move(double diffX, double diffY){
        this.forEach(elem -> elem.getFigure().move(diffX, diffY));
    }

    @Override
    public void draw() {
        this.forEach(DrawnFigure::draw);
    }
}