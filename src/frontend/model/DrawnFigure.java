package frontend.model;

import backend.interfaces.Movable;
import backend.model.Figure;
import frontend.Effects;
import frontend.interfaces.Drawable;
import frontend.interfaces.EffectsDrawable;
import frontend.interfaces.EffectApplicableWithTags;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.paint.Color;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Clase que permite instanciar figuras dibujables
 * @param <T>
 */
public abstract class DrawnFigure<T extends Figure> implements Movable, Drawable, EffectApplicableWithTags, EffectsDrawable {
    private final T figure;
    private final GraphicsContext gc;
    private final Color color;
    private DrawnFiguresGroup group = null;
    private Consumer<DrawnFigure<? extends Figure>> addSelectedFigure;
    private final Set<Effects> effects = EnumSet.noneOf(Effects.class);

    private Set<String> tags = new HashSet<>();

    public DrawnFigure(T figure, GraphicsContext gc, Color color){
        this.figure = figure;
        this.gc = gc;
        this.color = color;
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

    public void setAddSelectedFigure(Consumer<DrawnFigure<? extends Figure>> addSelectedFigure) {
        this.addSelectedFigure = addSelectedFigure;
    }

    public void select(){
        if(hasGroup())
            group.select();
        else
            addSelectedFigure.accept(this);
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
        checkBox.setSelected(effects.contains(effect));
        checkBox.setOnAction(event -> setState(effect, checkBox.isSelected()));
    }

    protected void setState(Effects effect, boolean isSet){
        if(isSet) addEffect(effect);
        else removeEffect(effect);
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

    @Override
    public Set<String> getTags() {
        return tags;
    }

    public void draw(String s) {
        if (s.isEmpty() || tags.contains(s))
            draw();
    }
}
