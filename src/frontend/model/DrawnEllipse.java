package frontend.model;

import backend.model.Ellipse;
import frontend.CanvasState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;
import javafx.scene.shape.ArcType;


public class DrawnEllipse<T extends Ellipse> extends DrawnFigure<T>{
    public DrawnEllipse(T figure, GraphicsContext gc, Color color, CanvasState canvasState){
        super(figure, gc, color, canvasState);
    }

    @Override
    public void draw(){
        getEffects().forEach(e -> e.apply(this));
        Ellipse ellipse = getFigure();
        getGraphicsContext().strokeOval(ellipse.getCenterPoint().getX() - (ellipse.getXAxis() / 2), ellipse.getCenterPoint().getY() - (ellipse.getYAxis() / 2), ellipse.getXAxis(), ellipse.getYAxis());
        getGraphicsContext().fillOval(ellipse.getCenterPoint().getX() - (ellipse.getXAxis() / 2), ellipse.getCenterPoint().getY() - (ellipse.getYAxis() / 2), ellipse.getXAxis(), ellipse.getYAxis());
    }

    @Override
    public void drawShadow() {
        Paint prevColor = getGraphicsContext().getFill();
        getGraphicsContext().setFill(Color.GRAY);
        getGraphicsContext().fillOval(getFigure().getCenterPoint().getX() - (getFigure().getXAxis() / 2) + 10.0,
                getFigure().getCenterPoint().getY() - (getFigure().getYAxis() / 2) + 10.0, getFigure().getXAxis(), getFigure().getYAxis());
        getGraphicsContext().setFill(prevColor);
    }

    @Override
    public void drawGradient() {
        RadialGradient radialGradient = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, getColor()),
                new Stop(1, getColor().invert()));
        getGraphicsContext().setFill(radialGradient);
    }

    @Override
    public void drawBezel() {
        double arcX = getFigure().getCenterPoint().getX() - getFigure().getXAxis()/2;
        double arcY = getFigure().getCenterPoint().getY() - getFigure().getYAxis()/2;
        getGraphicsContext().setLineWidth(10);
        getGraphicsContext().setStroke(Color.LIGHTGRAY);
        getGraphicsContext().strokeArc(arcX, arcY, getFigure().getXAxis(), getFigure().getYAxis(), 45, 180, ArcType.OPEN);
        getGraphicsContext().setStroke(Color.BLACK);
        getGraphicsContext().strokeArc(arcX, arcY, getFigure().getXAxis(), getFigure().getYAxis(), 225, 180, ArcType.OPEN);
        getGraphicsContext().setLineWidth(1);
    }
}
