package frontend.model;

import backend.interfaces.Movable;
import backend.model.Figure;
import frontend.CanvasState;
import frontend.interfaces.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class DrawnFigure<T extends Figure> implements Movable, Drawable {
    private final T figure;
    private final GraphicsContext gc;
    private final Color color;
    private DrawnFiguresGroup group = null;
    private final CanvasState canvasState;

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
}
