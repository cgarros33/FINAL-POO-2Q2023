package frontend;

import backend.model.Figure;
import backend.model.Point;
import frontend.model.DrawnFigure;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class CanvasState extends ArrayList<DrawnFigure<? extends Figure>>{
    public static final Color LINE_COLOR = Color.BLACK;
    public static final Color DEFAULT_FILL_COLOR = Color.YELLOW;
    private DrawnFigure<? extends Figure> selectedFigure;

    @Override
    public boolean remove(Object o) {
        if(selectedFigure.equals(o))
            setNoFigureSelected();
        return super.remove(o);
    }

    public DrawnFigure<? extends Figure> getSelectedFigure(){
        return selectedFigure;
    }

    public boolean emptyFigureSelected(){
        return selectedFigure == null;
    }

    public boolean isFigureSelected(DrawnFigure<? extends Figure> figure){
        return !emptyFigureSelected() && selectedFigure.equals(figure);
    }

    public void setSelectedFigure(DrawnFigure<? extends Figure> selectedFigure) {
        this.selectedFigure = selectedFigure;
    }

    public void setNoFigureSelected(){
        setSelectedFigure(null);
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
