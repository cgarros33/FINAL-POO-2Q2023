package frontend;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import frontend.model.DrawnFigure;
import frontend.model.DrawnFiguresGroup;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class CanvasState extends ArrayList<DrawnFigure<? extends Figure>>{
    public static final Color LINE_COLOR = Color.BLACK;
    public static final Color DEFAULT_FILL_COLOR = Color.YELLOW;
    private final DrawnFiguresGroup selectedFigures = new DrawnFiguresGroup(this);
    private boolean multipleSelected = false;
    private Runnable unsetTagAction;

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

    public DrawnFiguresGroup getSelectedFigures(){
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

    public void groupSelectedFigures(){
        if(selectedFigures.size() > 1) {
            DrawnFiguresGroup newGroup = new DrawnFiguresGroup(this, selectedFigures);
            for(DrawnFigure<?> drawnFigure : newGroup){
                drawnFigure.setGroup(newGroup);
            }
            newGroup.groupTags();
        }
    }

    public void ungroupSelectedFigures(){
        if(selectedFigures.size() > 1){
            for(DrawnFigure<?> drawnFigure : selectedFigures){
                drawnFigure.setNoGroup();
            }
        }
    }

    public void selectMultipleFigures(Point startPoint, Point endPoint){
        Figure rectangleSelection = new Rectangle(startPoint, endPoint);
        for(DrawnFigure<?> drawnFigure : this){
            if(drawnFigure.getFigure().belongs(rectangleSelection)) {
                drawnFigure.select();
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
    public void unsetTag(){
        unsetTagAction.run();
    }
    public void setUnsetTag(Runnable action){
        unsetTagAction=action;
    }
}
