package frontend;

import backend.model.Figure;
import frontend.model.DrawnFigure;

import java.util.ArrayList;

public class CanvasState extends ArrayList<DrawnFigure<? extends Figure>>{
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
}
