package frontend;

import backend.model.Figure;
import frontend.interfaces.FigureModifierPane;
import frontend.model.DrawnFigure;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class MoveTagBar extends VBox implements FigureModifierPane {
    private static final int DEFAULT_SPACING_HEIGHT = 10;
    private final Button rotate, flipY, flipX, scaleUp, scaleDown, saveTag;

    private final TextArea textArea = new TextArea();
    private DrawnFigure<? extends Figure> selectedFigure;

    public MoveTagBar() {
        super(DEFAULT_SPACING_HEIGHT);
        this.unsetFigure();
        rotate = new Button("Girar D");
        flipY = new Button("Voltear H");
        flipX = new Button("Voltear V");
        scaleUp = new Button("Escala +");
        scaleDown = new Button("Escala -");
        saveTag = new Button("Guardar");
        ArrayList<Labeled> elementsList = new ArrayList<>();
        elementsList.add(rotate);
        elementsList.add(flipY);
        elementsList.add(flipX);
        elementsList.add(scaleUp);
        elementsList.add(scaleDown);
        elementsList.add(saveTag);
        this.getChildren().addAll(elementsList);
        this.getChildren().add(4, textArea);
        elementsList.forEach(element -> {
            element.setTextFill(Color.BLACK);
            element.setMinWidth(90);
        });
    }

    public void setFigure(DrawnFigure<? extends Figure> figure) {
        this.setDisable(false);
        //@todo: parte 3
        rotate.setOnAction(event -> {
        });
        flipY.setOnAction(event -> {
        });
        flipX.setOnAction(event -> {
        });
        scaleUp.setOnAction(event -> {
        });
        scaleDown.setOnAction(event -> {
        });
        //@todo: parte 4
        saveTag.setOnAction(event -> {
        });
    }

    public void unsetFigure() {
        this.setDisable(true);
    }
}
