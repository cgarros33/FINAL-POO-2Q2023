package frontend;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import frontend.model.DrawnFigure;
import frontend.model.DrawnFiguresGroup;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Permite gestionar el estado del canvas.
 * Guarda el estado de todas las figuras
 * dibujadas y cuales se encuentran seleccionadas.
 */
public class CanvasState extends ArrayList<DrawnFigure<? extends Figure>>{
    public static final Color LINE_COLOR = Color.BLACK;
    public static final Color DEFAULT_FILL_COLOR = Color.YELLOW;
    private final DrawnFiguresGroup selectedFigures = new DrawnFiguresGroup(this);
    private boolean multipleSelected = false;
    private boolean movingMode = false;
    private Runnable unsetTagAction;

    public void removeSelectedFigures(){
        removeAll(selectedFigures);
        selectedFigures.clear();
    }

    /**
     * @return true si hay una seleccion multiple en curso, false en caso contrario.
     */
    public boolean isMultipleSelected(){
        return multipleSelected;
    }

    /**
     * Permite establecer si hay o no una seleccion multiple en curso.
     * @param multipleSelected true si hay una seleccion multiple en curso, false si no.
     */
    public void setMultipleSelected(boolean multipleSelected) {
        this.multipleSelected = multipleSelected;
    }

    /**
     * @return true si se movieron figuras de una seleccion, false si no.
     */
    public boolean inMovingFiguresMode(){
        return movingMode;
    }

    /**
     * Permite establecer si se movieron o no figuras de una seleccion.
     * @param movingMode true si se esta en modo de movimiento, false si no
     */
    public void setInMovingFiguresMode(boolean movingMode){
        this.movingMode = movingMode;
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
            DrawnFiguresGroup newGroup = new DrawnFiguresGroup(selectedFigures);
            for(DrawnFigure<?> drawnFigure : newGroup){
                drawnFigure.setGroup(newGroup);
            }
            newGroup.groupTags();
            newGroup.setAddSelectedFigures(this::addSelectedFigure);
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

    /**
     * Permite obtener las figuras a las cuales pertenece un punto
     * @param point punto para el cual se quiere obtener las figuras a las cuales pertenece
     * @return las figuras a las cuales pertenece el punto
     */
    public List<DrawnFigure<?>> getFiguresForPoint(Point point) {
        List<DrawnFigure<?>> figuresForPoint = new ArrayList<>();
        for (DrawnFigure<?> drawnFigure : this) {
            if (drawnFigure.getFigure().belongs(point))
                figuresForPoint.add(drawnFigure);
        }
        return figuresForPoint;
    }

    /**
     * Construye la etiqueta a mostrar en StatusPane.
     * @param eventPoint punto para el cual se quiere construir la etiqueta.
     * @param strDefault string a retornar en caso de no haber figuras a las cuales el punto pertenezca.
     * @return la etiqueta correspondiente.
     */
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
