package frontend.model;

import backend.model.Figure;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class DrawnFigure<T extends Figure> {
    private final T figure;
    private final GraphicsContext gc;
    private final Color color;

    public DrawnFigure(T figure, GraphicsContext gc, Color color){
        this.figure = figure;
        this.gc = gc;
        this.color = color;
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

    public abstract void draw();

    @Override
    public boolean equals(Object obj) {
        return this == obj ||
                (obj instanceof DrawnFigure<?> drawnFigure &&
                figure.equals(drawnFigure.figure) &&
                gc.equals(drawnFigure.gc) &&
                color.equals(drawnFigure.color));
    }
}
