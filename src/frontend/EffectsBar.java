package frontend;

import frontend.interfaces.FigureModifierPane;
import frontend.interfaces.Taggable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.HashSet;
import java.util.Set;

public class EffectsBar extends HBox implements FigureModifierPane {
    private static final int DEFAULT_SPACING_WIDTH = 10;
    private final CheckBox shadowBox, gradientBox, bezelBox;
    public EffectsBar() {
        super(DEFAULT_SPACING_WIDTH);
        this.unsetFigure();
        shadowBox = new CheckBox("Sombra");
        gradientBox = new CheckBox("Gradiente");
        bezelBox = new CheckBox("Biselado");
        Set<CheckBox> checkboxes = new HashSet<>();
        checkboxes.add(shadowBox);
        checkboxes.add(gradientBox);
        checkboxes.add(bezelBox);
        checkboxes.forEach(checkbox -> checkbox.setTextFill(Color.BLACK));
        this.getChildren().add(new Text("Efectos: "));
        this.getChildren().addAll(checkboxes);

        setPadding(new Insets(5));
        setStyle("-fx-background-color: #999");
        this.setAlignment(Pos.CENTER);}

    @Override
    public void setFigure(Taggable figure) {
        setDisable(false);
        figure.bindToCheckBox(shadowBox, Effects.SHADOW);
        figure.bindToCheckBox(gradientBox, Effects.GRADIENT);
        figure.bindToCheckBox(bezelBox, Effects.BEZEL);
        /*shadowBox.setOnAction(event -> figure.bindToCheckBox(shadowBox, Effects.SHADOW));
        gradientBox.setOnAction(event -> figure.bindToCheckBox(gradientBox, Effects.GRADIENT));
        bezelBox.setOnAction(event -> figure.bindToCheckBox(bezelBox, Effects.BEZEL));*/
    }

    @Override
    public void unsetFigure() {
        this.setDisable(true);
    }
}
