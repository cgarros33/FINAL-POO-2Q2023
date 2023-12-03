package frontend.model;

import backend.model.Ellipse;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawnEllipse<T extends Ellipse> extends DrawnFigure<T> {

    public DrawnEllipse(T figure, GraphicsContext gc, Color color){
        super(figure, gc, color);
    }

    @Override
    public void draw(){
        Ellipse ellipse = getFigure();
        getGraphicsContext().strokeOval(ellipse.getCenterPoint().getX() - (ellipse.getsMayorAxis() / 2), ellipse.getCenterPoint().getY() - (ellipse.getsMinorAxis() / 2), ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
        getGraphicsContext().fillOval(ellipse.getCenterPoint().getX() - (ellipse.getsMayorAxis() / 2), ellipse.getCenterPoint().getY() - (ellipse.getsMinorAxis() / 2), ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
    }
}
