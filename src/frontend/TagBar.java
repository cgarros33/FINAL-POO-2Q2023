package frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.util.StringTokenizer;

public class TagBar extends HBox {

    private static final int DEFAULT_SPACING_WIDTH = 10;

    private final TextField textField = new TextField();

    private String tagToShow = "";

    private final RadioButton all;
    public TagBar() {
        super(DEFAULT_SPACING_WIDTH);
        all = new RadioButton("Todo");
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

        all.setOnAction(event -> { textField.setDisable(true); tagToShow = ""; });

        only.setOnAction(event -> textField.setDisable(false));

        textField.setOnAction(event -> {
            StringTokenizer tokenizer = new StringTokenizer(textField.getText(), " ");
            tagToShow = "";
            if (tokenizer.hasMoreTokens())
                tagToShow = tokenizer.nextToken();
        });
    }

    public String getTagToShow(){
        return tagToShow;
    }

    public void unsetTagToShow(){
        textField.setDisable(true);
        all.setSelected(true);
        tagToShow="";
    }
}
