package frontend.model;

import backend.model.Figure;
import backend.interfaces.Movable;
import frontend.CanvasState;
import frontend.Effects;
import frontend.interfaces.Drawable;
import frontend.interfaces.Taggable;
import javafx.scene.control.CheckBox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DrawnFiguresGroup extends ArrayList<DrawnFigure<? extends Figure>> implements Movable, Drawable, Taggable {

    private final CanvasState canvasState;

    public DrawnFiguresGroup(CanvasState canvasState){
        this(canvasState, new ArrayList<>());
    }

    public DrawnFiguresGroup(CanvasState canvasState, Collection<? extends DrawnFigure<? extends Figure>> c){
        super(c);
        this.canvasState = canvasState;
    }

    public void select(){
        forEach(canvasState::addSelectedFigure);
    }

    public void groupTags() {
        Set<String> allTags = new HashSet<>();
        forEach(drawnFigure -> allTags.addAll(drawnFigure.getTags()));
        forEach(drawnFigure -> drawnFigure.setTags(allTags));
    }

    @Override
    public void move(double diffX, double diffY){
        this.forEach(elem -> elem.getFigure().move(diffX, diffY));
    }

    @Override
    public void draw() {
        this.forEach(DrawnFigure::draw);
    }

    @Override
    public void rotate() {
        forEach(DrawnFigure::rotate);
    }

    @Override
    public void scale(double diff) {
        forEach(e->e.scale(diff));
    }

    @Override
    public void flipX() {
        forEach(DrawnFigure::flipX);
    }

    @Override
    public void flipY() {
        forEach(DrawnFigure::flipY);
    }

    @Override
    public void setTags(Set<String> tags) {
        forEach(e->e.setTags(tags));
    }

    @Override
    public boolean containsTag(String tag) {
        return false; //@todo:implement with streams
    }

    @Override
    public void bindToCheckBox(CheckBox checkBox, Effects effect) {
        checkBox.setDisable(false);
        boolean isEnabled = containsEffect(effect);
        boolean isDisabled = doesNotContainEffect(effect);
        if(!isEnabled && !isDisabled){
            checkBox.setIndeterminate(true);
        }
        if(isEnabled){
            checkBox.setSelected(true);
        }
        if(isDisabled){
            checkBox.setSelected(false);
        }

        checkBox.setOnAction(event -> forEach(e->e.changeState(effect)));
        //@TODO: MEJORAR
    }

    @Override
    public void addEffect(Effects effect) {

        System.out.println("aaa");
        this.forEach(e -> e.addEffect(effect));
    }

    @Override
    public void removeEffect(Effects effect) {
        this.forEach(e -> e.removeEffect(effect));
    }

    @Override
    public boolean containsEffect(Effects effect) {
        return this.stream().allMatch(e -> e.containsEffect(effect));
    }

    private boolean doesNotContainEffect(Effects effect) {
        return this.stream().noneMatch(e -> e.containsEffect(effect));
    }

    @Override
    public Set<String> getTags(){
        Set<String> toReturn = new HashSet<>();
        forEach((df) -> toReturn.addAll(df.getTags()));
        return toReturn;
    }
}