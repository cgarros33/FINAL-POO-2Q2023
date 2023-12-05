package frontend.model;

import backend.model.Ellipse;
import frontend.CanvasState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawnEllipse<T extends Ellipse> extends DrawnFigure<T>{

    public DrawnEllipse(T figure, GraphicsContext gc, Color color, CanvasState canvasState){
        super(figure, gc, color, canvasState);
    }

    @Override
    public void draw(){
        Ellipse ellipse = getFigure();
        getGraphicsContext().strokeOval(ellipse.getCenterPoint().getX() - (ellipse.getXAxis() / 2), ellipse.getCenterPoint().getY() - (ellipse.getYAxis() / 2), ellipse.getXAxis(), ellipse.getYAxis());
        getGraphicsContext().fillOval(ellipse.getCenterPoint().getX() - (ellipse.getXAxis() / 2), ellipse.getCenterPoint().getY() - (ellipse.getYAxis() / 2), ellipse.getXAxis(), ellipse.getYAxis());
    }
}
