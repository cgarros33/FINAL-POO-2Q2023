package frontend.model;

import javafx.scene.canvas.GraphicsContext;
import backend.model.*;

public class FigureDrawHelper implements DrawHelper {
    private final GraphicsContext gc;
    public FigureDrawHelper(GraphicsContext gc){this.gc=gc;}

    public void draw(Rectangle rectangle){
        draw(gc,rectangle);
    }
    public void draw(Ellipse ellipse){
        draw(gc,ellipse);
    }
    private void draw(GraphicsContext gc, Ellipse ellipse){
        gc.strokeOval(ellipse.getCenterPoint().getX() - (ellipse.getsMayorAxis() / 2), ellipse.getCenterPoint().getY() - (ellipse.getsMinorAxis() / 2), ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
        gc.fillOval(ellipse.getCenterPoint().getX() - (ellipse.getsMayorAxis() / 2), ellipse.getCenterPoint().getY() - (ellipse.getsMinorAxis() / 2), ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
    }
    private void draw(GraphicsContext gc, Rectangle rectangle){
        gc.fillRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()), Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()));
        gc.strokeRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()), Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()));
    }
}
