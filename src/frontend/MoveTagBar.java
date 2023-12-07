package frontend;

import frontend.interfaces.FigureModifierPane;
import frontend.interfaces.Taggable;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.*;

public class MoveTagBar extends VBox implements FigureModifierPane {
    private static final int DEFAULT_SPACING_HEIGHT = 10;
    private final Button rotate, flipY, flipX, scaleUp, scaleDown, saveTag;

    private final TextArea textArea = new TextArea();

    public MoveTagBar() {
        super(DEFAULT_SPACING_HEIGHT);
        this.unsetFigure();
        rotate = new Button("Girar D");
        flipX = new Button("Voltear H");
        flipY = new Button("Voltear V");
        scaleUp = new Button("Escala +");
        scaleDown = new Button("Escala -");
        saveTag = new Button("Guardar");
        ArrayList<Labeled> elementsList = new ArrayList<>();
        elementsList.add(rotate);
        elementsList.add(flipX);
        elementsList.add(flipY);
        elementsList.add(scaleUp);
        elementsList.add(scaleDown);
        elementsList.add(saveTag);
        this.getChildren().addAll(elementsList);
        this.getChildren().add(5, textArea);
        elementsList.forEach(element -> {
            element.setTextFill(Color.BLACK);
            element.setMinWidth(90);
        });
    }

    public void setFigure(Taggable figure) {
        this.setDisable(false);
        //@todo: parte 3
        rotate.setOnAction(event -> figure.rotate());
        flipY.setOnAction(event -> figure.flipY());
        flipX.setOnAction(event -> figure.flipX());
        scaleUp.setOnAction(event -> figure.scaleUp());
        scaleDown.setOnAction(event -> figure.scaleDown());
        //@todo: parte 4
        StringBuilder tags = new StringBuilder();
        figure.getTags().forEach((str) -> tags.append(str).append(" "));
        textArea.setText(tags.toString());
        saveTag.setOnAction( event -> { Iterator<Object> tokenizer = new StringTokenizer(textArea.getText(), " ").asIterator();
                                        Set<String> tagSet = new HashSet<>();
                                        while(tokenizer.hasNext()) {
                                            tagSet.add((String) tokenizer.next());
                                        }
                                        //for (CharSequence cs : tags) {
                                          //  tagSet.add(cs.toString());
                                        //}
                                        figure.setTags(tagSet);
        } );
    }

    public void unsetFigure() {
        this.setDisable(true);
    }

    public void disableTags() {
        saveTag.setDisable(true);
    }
}
