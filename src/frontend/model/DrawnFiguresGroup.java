package frontend.model;

import backend.model.Figure;
import backend.interfaces.Movable;
import frontend.Effects;
import frontend.interfaces.Drawable;
import frontend.interfaces.EffectApplicableWithTags;
import javafx.scene.control.CheckBox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Clase que permite instanciar grupos de figuras dibujables
 */
public class DrawnFiguresGroup extends ArrayList<DrawnFigure<? extends Figure>> implements Movable, Drawable, EffectApplicableWithTags {
    private Consumer<DrawnFigure<? extends Figure>> addSelectedFigures;

    public DrawnFiguresGroup(Collection<? extends DrawnFigure<? extends Figure>> c){
        super(c);
    }

    public void setAddSelectedFigures(Consumer<DrawnFigure<? extends Figure>> addSelectedFigures) {
        this.addSelectedFigures = addSelectedFigures;
    }

    public void select(){
        forEach(figure -> addSelectedFigures.accept(figure));
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
    public void bindToCheckBox(CheckBox checkBox, Effects effect) {
        checkBox.setDisable(false);
        boolean isEnabled = containsEffect(effect);
        boolean isDisabled = doesNotContainEffect(effect);
        checkBox.setSelected(isEnabled);
        checkBox.setIndeterminate(!isEnabled && !isDisabled);
        checkBox.setOnAction(event -> forEach(e->e.changeState(effect)));
    }

    @Override
    public void addEffect(Effects effect) {
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

    @Override
    public Set<String> getTags(){
        Set<String> toReturn = new HashSet<>();
        forEach((df) -> toReturn.addAll(df.getTags()));
        return toReturn;
    }

    private boolean doesNotContainEffect(Effects effect) {
        return this.stream().noneMatch(e -> e.containsEffect(effect));
    }
}