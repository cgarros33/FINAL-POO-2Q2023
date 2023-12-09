package frontend.model;

import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;

/**
 * Clase que permite instanciar rectangulos dibujables
 * @param <T>
 */
public class DrawnRectangle<T extends Rectangle> extends DrawnFigure<T> {
    public DrawnRectangle(T figure, GraphicsContext gc, Color color){
        super(figure, gc, color);
    }

    @Override
    public void draw(){
        getEffects().forEach(e -> e.apply(this));
        Rectangle rectangle = getFigure();
        getGraphicsContext().fillRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()), Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()));
        getGraphicsContext().strokeRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()), Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()));
    }

    @Override
    public void drawShadow() {
        Paint prevColor = getGraphicsContext().getFill();
        getGraphicsContext().setFill(Color.GRAY);
        getGraphicsContext().fillRect(getFigure().getTopLeft().getX() + 10.0,
                getFigure().getTopLeft().getY() + 10.0,
                Math.abs(getFigure().getTopLeft().getX() - getFigure().getBottomRight().getX()),
                Math.abs(getFigure().getTopLeft().getY() - getFigure().getBottomRight().getY()));

        getGraphicsContext().setFill(prevColor);
    }

    @Override
    public void drawGradient() {
       LinearGradient linearGradient = new LinearGradient(0, 0, 1, 0, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, getColor()),
                new Stop(1, getColor().invert()));
        getGraphicsContext().setFill(linearGradient);
    }

    @Override
    public void drawBezel() {
        double x = getFigure().getTopLeft().getX();
        double y = getFigure().getTopLeft().getY();
        double width = Math.abs(x - getFigure().getBottomRight().getX());
        double height = Math.abs(y - getFigure().getBottomRight().getY());
        getGraphicsContext().setLineWidth(10);
        getGraphicsContext().setStroke(Color.LIGHTGRAY);
        getGraphicsContext().strokeLine(x, y, x + width, y);
        getGraphicsContext().strokeLine(x, y, x, y + height);
        getGraphicsContext().setStroke(Color.BLACK);
        getGraphicsContext().strokeLine(x + width, y, x + width, y + height);
        getGraphicsContext().strokeLine(x, y + height, x + width, y + height);
        getGraphicsContext().setLineWidth(1);
    }
}
