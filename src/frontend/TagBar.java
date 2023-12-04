package frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class TagBar extends HBox {

    private static final int DEFAULT_SPACING_WIDTH = 10;

    private final TextField textField = new TextField();

    private final Set<String> tags = new HashSet<>();

    public TagBar() {

        super(DEFAULT_SPACING_WIDTH);

        RadioButton all = new RadioButton("Todo");
        all.setTextFill(Color.BLACK);
        RadioButton only = new RadioButton("Sólo:");
        only.setTextFill(Color.BLACK);
        ToggleGroup toggleGroup = new ToggleGroup();
        all.setToggleGroup(toggleGroup);
        only.setToggleGroup(toggleGroup);
        all.setSelected(true);
        textField.setDisable(true);
        Text text = new Text("Mostrar Etiquetas");
        this.getChildren().add(text);
        this.getChildren().add(all);
        this.getChildren().add(only);
        this.getChildren().add(textField);
        setPadding(new Insets(5));
        setStyle("-fx-background-color: #999");
        this.setAlignment(Pos.CENTER);

        all.setOnAction(event -> {
            textField.setDisable(true);
            tags.clear();
        });

        only.setOnAction(event -> textField.setDisable(false));

        textField.setOnAction(event -> {
            tags.clear();
            tags.addAll(Collections.list(new StringTokenizer(textField.getText(), " ")).stream().map(token -> (String) token).toList());
            System.out.println(tags);
        });

    }
}
