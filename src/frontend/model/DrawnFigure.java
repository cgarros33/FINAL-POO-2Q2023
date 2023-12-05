package frontend.model;

import backend.interfaces.Movable;
import backend.model.Figure;
import frontend.CanvasState;
import frontend.interfaces.Drawable;
import frontend.interfaces.Taggable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Set;

public abstract class DrawnFigure<T extends Figure> implements Movable, Drawable, Taggable {
    private final T figure;
    private final GraphicsContext gc;
    private final Color color;
    private DrawnFiguresGroup group = null;
    private final CanvasState canvasState;

    private Set<String> tags;

    public DrawnFigure(T figure, GraphicsContext gc, Color color, CanvasState canvasState){
        this.figure = figure;
        this.gc = gc;
        this.color = color;
        this.canvasState = canvasState;
    }

    public T getFigure(){
        return figure;
    }

    protected GraphicsContext getGraphicsContext(){
        return gc;
    }

    public Color getColor(){
        return color;
    }

    public DrawnFiguresGroup getGroup() {
        return group;
    }

    public void select(){
        if(hasGroup())
            group.select();
        else
            canvasState.addSelectedFigure(this);
    }

    public void setGroup(DrawnFiguresGroup group) {
        this.group = group;
    }

    public void setNoGroup() {
        setGroup(null);
    }

    public boolean hasGroup(){
        return group != null;
    }

    @Override
    public void move(double diffX, double diffY){
        figure.move(diffX, diffY);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj ||
                (obj instanceof DrawnFigure<?> drawnFigure &&
                figure.equals(drawnFigure.figure) &&
                gc.equals(drawnFigure.gc) &&
                color.equals(drawnFigure.color));
    }

    @Override
    public String toString() {
        return figure.toString();
    }

    @Override
    public void setTags(Set<String> tags) {
        this.tags=tags;
    }

    @Override
    public boolean containsTag(String tag) {
        return tags.contains(tag);
    }

    @Override
    public void rotate() {
        getFigure().rotate();
    }

    @Override
    public void scale(double diff) {
        getFigure().scale(diff);
    }

    @Override
    public void flipX() {
        getFigure().flipX();
    }

    @Override
    public void flipY() {
        getFigure().flipY();
    }
}
