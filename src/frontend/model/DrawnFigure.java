package frontend.model;

import backend.interfaces.Movable;
import backend.model.Figure;
import frontend.CanvasState;
import frontend.Effects;
import frontend.interfaces.Drawable;
import frontend.interfaces.EffectsDrawable;
import frontend.interfaces.Taggable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.paint.Color;
import java.util.EnumSet;
import java.util.Set;

public abstract class DrawnFigure<T extends Figure> implements Movable, Drawable, Taggable, EffectsDrawable {
    private final T figure;
    private final GraphicsContext gc;
    private final Color color;
    private DrawnFiguresGroup group = null;
    private final CanvasState canvasState;
    private final Set<Effects> effects = EnumSet.noneOf(Effects.class);

    private Set<String> tags;

    public DrawnFigure(T figure, GraphicsContext gc, Color color, CanvasState canvasState){
        this.figure = figure;
        this.gc = gc;
        this.color = color;
        this.canvasState = canvasState;
    }

    public T getFigure(){
        return figure;
    }

    protected GraphicsContext getGraphicsContext(){
        return gc;
    }

    public Color getColor(){
        return color;
    }

    public DrawnFiguresGroup getGroup() {
        return group;
    }

    public void select(){
        if(hasGroup())
            group.select();
        else
            canvasState.addSelectedFigure(this);
    }

    public void setGroup(DrawnFiguresGroup group) {
        this.group = group;
    }

    public void setNoGroup() {
        setGroup(null);
    }

    public boolean hasGroup(){
        return group != null;
    }

    protected Iterable<Effects> getEffects(){
        return effects;
    }

    @Override
    public void move(double diffX, double diffY){
        figure.move(diffX, diffY);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj ||
                (obj instanceof DrawnFigure<?> drawnFigure &&
                figure.equals(drawnFigure.figure) &&
                gc.equals(drawnFigure.gc) &&
                color.equals(drawnFigure.color));
    }

    @Override
    public String toString() {
        return figure.toString();
    }

    @Override
    public void setTags(Set<String> tags) {
        this.tags=tags;
    }

    @Override
    public boolean containsTag(String tag) {
        return tags.contains(tag);
    }

    @Override
    public void rotate() {
        getFigure().rotate();
    }

    @Override
    public void scale(double diff) {
        getFigure().scale(diff);
    }

    @Override
    public void flipX() {
        getFigure().flipX();
    }

    @Override
    public void flipY() {
        getFigure().flipY();
    }

    @Override
    public void bindToCheckBox(CheckBox checkBox, Effects effect){
        checkBox.setIndeterminate(false);
        checkBox.setDisable(!effects.contains(effect));
        checkBox.setOnAction(event -> changeState(effect));
    }

    private void changeState(Effects effect){
        if(containsEffect(effect))
            removeEffect(effect);
        else
            addEffect(effect);
    }

    @Override
    public void addEffect(Effects effect){
        effects.add(effect);
    }

    @Override
    public void removeEffect(Effects effect){
        effects.remove(effect);
    }

    @Override
    public boolean containsEffect(Effects effect){
        return effects.contains(effect);
    }
}
