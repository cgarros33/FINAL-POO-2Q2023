package frontend.model;

import backend.model.Rectangle;
import frontend.CanvasState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawnRectangle<T extends Rectangle> extends DrawnFigure<T> {

    public DrawnRectangle(T figure, GraphicsContext gc, Color color, CanvasState canvasState){
        super(figure, gc, color, canvasState);
    }

    @Override
    public void draw(){
        Rectangle rectangle = getFigure();
        getGraphicsContext().fillRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()), Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()));
        getGraphicsContext().strokeRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()), Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()));
    }
}
