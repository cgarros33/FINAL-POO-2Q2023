package frontend;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import frontend.model.DrawnFigure;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class CanvasState extends ArrayList<DrawnFigure<? extends Figure>>{
    public static final Color LINE_COLOR = Color.BLACK;
    public static final Color DEFAULT_FILL_COLOR = Color.YELLOW;
    private final List<DrawnFigure<? extends Figure>> selectedFigures = new ArrayList<>();
    private boolean multipleSelected = false;

    public void removeSelectedFigures(){
        removeAll(selectedFigures);
        selectedFigures.clear();
    }

    public boolean isMultipleSelected(){
        return multipleSelected;
    }

    public void setMultipleSelected(boolean multipleSelected) {
        this.multipleSelected = multipleSelected;
    }

    public List<DrawnFigure<? extends Figure>> getSelectedFigures(){
        return selectedFigures;
    }

    public boolean emptyFiguresSelected(){
        return selectedFigures.isEmpty();
    }

    public boolean isFigureSelected(DrawnFigure<? extends Figure> figure){
        return selectedFigures.contains(figure);
    }

    public void addSelectedFigure(DrawnFigure<? extends Figure> drawnFigure) {
        selectedFigures.add(drawnFigure);
    }

    public void selectMultipleFigures(Point startPoint, Point endPoint){
        Figure rectangleSelection = new Rectangle(startPoint, endPoint);
        for(DrawnFigure<?> drawnFigure : this){
            if(drawnFigure.getFigure().belongs(rectangleSelection)) {
                addSelectedFigure(drawnFigure);
                setMultipleSelected(true);
            }
        }
    }

    public void setNoFiguresSelected(){
        selectedFigures.clear();
    }

    public List<DrawnFigure<?>> getFiguresForPoint(Point point) {
        List<DrawnFigure<?>> figuresForPoint = new ArrayList<>();
        for (DrawnFigure<?> drawnFigure : this) {
            if (drawnFigure.getFigure().belongs(point))
                figuresForPoint.add(drawnFigure);
        }
        return figuresForPoint;
    }

    public String buildPositionLabel(Point eventPoint, String strDefault) {
        StringBuilder label = new StringBuilder();
        List<DrawnFigure<?>> figuresForPoint = getFiguresForPoint(eventPoint);
        for(DrawnFigure<? extends Figure> drawnFigure : figuresForPoint) {
            label.append(drawnFigure);
        }
        if(figuresForPoint.isEmpty()) {
            return strDefault;
        }
        return label.toString();
    }
}
