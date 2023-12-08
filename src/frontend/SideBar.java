package frontend;

import backend.model.*;
import frontend.interfaces.Taggable;
import frontend.model.*;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class SideBar extends VBox {

    private static final int DEFAULT_SPACING_HEIGHT = 10;
    private ActionToggleButton<?> selectionButton;
    private ActionToggleButton<DrawnRectangle<Rectangle>> rectangleButton;
    private ActionToggleButton<DrawnEllipse<Circle>> circleButton;
    private ActionToggleButton<DrawnRectangle<Square>> squareButton;
    private ActionToggleButton<DrawnEllipse<Ellipse>> ellipseButton;
    private final ActionToggleButton<?> groupButton;
    private final ActionToggleButton<?> ungroupButton = new ActionToggleButton<>("Desagrupar", (a, b, c) -> null);
    private final ActionToggleButton<?> deleteButton = new ActionToggleButton<>("Borrar", (a, b, c) -> null);

    // Selector de color de relleno
    private final ColorPicker fillColorPicker = new ColorPicker(CanvasState.DEFAULT_FILL_COLOR);

    private final GraphicsContext gc;

    private final CanvasState canvasState;

    private final ToggleGroup tools = new ToggleGroup();

    private final MoveTagBar moveTagBar = new MoveTagBar();

    public SideBar(GraphicsContext gc, CanvasState canvasState) {
        super(DEFAULT_SPACING_HEIGHT);
        this.gc = gc;
        this.canvasState = canvasState;
        this.groupButton = new ActionToggleButton<>("Agrupar", (a, b, c) -> { canvasState.getSelectedFigures().groupTags(); return null;});
        setButtonFunctionality();

        ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, groupButton, ungroupButton, deleteButton};

        for (ToggleButton tool : toolsArr) {
            tool.setMinWidth(90);
            tool.setToggleGroup(tools);
            tool.setCursor(Cursor.HAND);
        }

        getChildren().addAll(toolsArr);
        getChildren().add(moveTagBar);
        setButtonsBoxStyle();
    }

    private void setButtonsBoxStyle() {
        getChildren().add(fillColorPicker);
        setPadding(new Insets(5));
        setStyle("-fx-background-color: #999");
        setPrefWidth(100);
    }

    public void unselectFigure() {
        moveTagBar.unsetFigure();
    }

    public void setSelectedFigure(Taggable figure) {
        //if (!canvasState.isMultipleSelected()) moveTagBar.setFigure(figure);
        moveTagBar.setFigure(figure);
    }

    private void setButtonFunctionality() {
        selectionButton = new ActionToggleButton<>("Selección", (startPoint, endPoint, color) -> {
            canvasState.selectMultipleFigures(startPoint, endPoint);
            setSelectedFigure(canvasState.getSelectedFigures());
            return null;
        });
        rectangleButton = new ActionToggleButton<>("Rectángulo", (startPoint, endPoint, color) -> new DrawnRectangle<>(new Rectangle(startPoint, endPoint), gc, color, canvasState));
        circleButton = new ActionToggleButton<>("Círculo", (startPoint, endPoint, color) -> {
            double circleRadius = startPoint.distanceTo(endPoint);
            return new DrawnEllipse<>(new Circle(startPoint, circleRadius), gc, color, canvasState);
        });
        squareButton = new ActionToggleButton<>("Cuadrado", (startPoint, endPoint, color) -> {
            double size = Math.abs(endPoint.getX() - startPoint.getX());
            return new DrawnRectangle<>(new Square(startPoint, size), gc, color, canvasState);
        });
        ellipseButton = new ActionToggleButton<>("Elipse", (startPoint, endPoint, color) -> {
            Point centerPoint = new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2, (Math.abs((endPoint.getY() + startPoint.getY())) / 2));
            double xAxis = Math.abs(endPoint.getX() - startPoint.getX());
            double yAxis = Math.abs(endPoint.getY() - startPoint.getY());
            return new DrawnEllipse<>(new Ellipse(centerPoint, xAxis, yAxis), gc, color, canvasState);
        });
        rectangleButton.setOnAction(event -> {
            canvasState.setNoFiguresSelected();
            canvasState.unsetTag();
        });
        circleButton.setOnAction(event -> {
            canvasState.setNoFiguresSelected();
            canvasState.unsetTag();
        });
        squareButton.setOnAction(event -> {
            canvasState.setNoFiguresSelected();
            canvasState.unsetTag();
        });
        ellipseButton.setOnAction(event -> {
            canvasState.setNoFiguresSelected();
            canvasState.unsetTag();
        });
        groupButton.setOnAction(event -> canvasState.groupSelectedFigures());
        ungroupButton.setOnAction(event -> canvasState.ungroupSelectedFigures());
        deleteButton.setOnAction(event -> canvasState.removeSelectedFigures());
    }

    public boolean noToggleSelected() {
        return tools.getSelectedToggle() == null;
    }

    public DrawnFigure<?> onRelease(Point startPoint, Point endPoint) {
        return ((ActionToggleButton<?>) tools.getSelectedToggle()).action(startPoint, endPoint, fillColorPicker.getValue());
    }

    public boolean inSelectMode() {
        return selectionButton.isSelected();
    }
}
