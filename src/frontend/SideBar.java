package frontend;

import backend.model.*;
import frontend.model.ActionToggleButton;
import frontend.model.DrawnEllipse;
import frontend.model.DrawnFigure;
import frontend.model.DrawnRectangle;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class SideBar extends VBox {

    private static final int DEFAULT_SPACING_HEIGHT = 10;
    private final ActionToggleButton<?> selectionButton = new ActionToggleButton<>("Seleccionar", (a, b, c) -> null); //@todo: Leo Optional
    private ActionToggleButton<DrawnRectangle<Rectangle>> rectangleButton;
    private ActionToggleButton<DrawnEllipse<Circle>> circleButton;
    private ActionToggleButton<DrawnRectangle<Square>> squareButton;
    private ActionToggleButton<DrawnEllipse<Ellipse>> ellipseButton;
    private final ActionToggleButton<?> deleteButton = new ActionToggleButton<>("Borrar", (a, b, c) -> null); //@todo: Leo Optional

    // Selector de color de relleno
    private final ColorPicker fillColorPicker = new ColorPicker(CanvasState.DEFAULT_FILL_COLOR);

    private final GraphicsContext gc;



    ToggleGroup tools = new ToggleGroup();

    public SideBar(GraphicsContext gc) {
        super(DEFAULT_SPACING_HEIGHT);
        this.gc = gc;
        setButtonFunctionality();

        ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton};

        for (ToggleButton tool :  toolsArr) {
            tool.setMinWidth(90);
            tool.setToggleGroup(tools);
            tool.setCursor(Cursor.HAND);
        }

        getChildren().addAll(toolsArr);
        setButtonsBoxStyle();
    }

    private void setButtonsBoxStyle() {
        getChildren().add(fillColorPicker);
        setPadding(new Insets(5));
        setStyle("-fx-background-color: #999");
        setPrefWidth(100);
    }

    private void setButtonFunctionality() {
        rectangleButton = new ActionToggleButton<>("Rectángulo", (startPoint, endPoint, color) -> new DrawnRectangle<>(new Rectangle(startPoint, endPoint), gc, color));
        circleButton = new ActionToggleButton<>("Círculo", (startPoint, endPoint, color) -> {
            double circleRadius = startPoint.distanceTo(endPoint);
            return new DrawnEllipse<>(new Circle(startPoint, circleRadius), gc, color);
        });
        squareButton = new ActionToggleButton<>("Cuadrado", (startPoint, endPoint, color) -> {
            double size = Math.abs(endPoint.getX() - startPoint.getX());
            return new DrawnRectangle<>(new Square(startPoint, size), gc, color);
        });
        ellipseButton = new ActionToggleButton<>("Elipse", (startPoint, endPoint, color) -> {
            Point centerPoint = new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2, (Math.abs((endPoint.getY() + startPoint.getY())) / 2));
            double sMayorAxis = Math.abs(endPoint.getX() - startPoint.getX());
            double sMinorAxis = Math.abs(endPoint.getY() - startPoint.getY());
            return new DrawnEllipse<>(new Ellipse(centerPoint, sMayorAxis, sMinorAxis), gc, color);
        });

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

    public ActionToggleButton<?> getDeleteButton() {
        return deleteButton;
    }
}
