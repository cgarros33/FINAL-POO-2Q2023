package frontend.model;

import backend.model.Figure;
import backend.interfaces.Movable;
import frontend.CanvasState;
import frontend.interfaces.Drawable;
import frontend.interfaces.Taggable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class DrawnFiguresGroup extends ArrayList<DrawnFigure<? extends Figure>> implements Movable, Drawable, Taggable {

    private final CanvasState canvasState;

    public DrawnFiguresGroup(CanvasState canvasState){
        this(canvasState, new ArrayList<>());
    }

    public DrawnFiguresGroup(CanvasState canvasState, Collection<? extends DrawnFigure<? extends Figure>> c){
        super(c);
        this.canvasState = canvasState;
    }

    public void select(){
        forEach(canvasState::addSelectedFigure);
    }

    @Override
    public void move(double diffX, double diffY){
        this.forEach(elem -> elem.getFigure().move(diffX, diffY));
    }

    @Override
    public void draw() {
        this.forEach(DrawnFigure::draw);
    }

    @Override
    public void rotate() {
        forEach(DrawnFigure::rotate);
    }

    @Override
    public void scale(double diff) {
        forEach(e->e.scale(diff));
    }

    @Override
    public void flipX() {
        forEach(DrawnFigure::flipX);
    }

    @Override
    public void flipY() {
        forEach(DrawnFigure::flipY);
    }

    @Override
    public void setTags(Set<String> tags) {
        forEach(e->e.setTags(tags));
    }

    @Override
    public boolean containsTag(String tag) {
        return false; //@todo:implement with streams
    }
}