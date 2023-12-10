package frontend;

import backend.model.*;
import frontend.interfaces.EffectApplicableWithTags;
import frontend.model.*;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

/**
 * Gestiona los botones de selecccion, borrado, agrupamiento y creacion de figuras.
 */
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

    private Runnable disableTags;

    public SideBar(GraphicsContext gc, CanvasState canvasState) {
        super(DEFAULT_SPACING_HEIGHT);
        this.gc = gc;
        this.canvasState = canvasState;
        this.groupButton = new ActionToggleButton<>("Agrupar", (a, b, c) -> {
            canvasState.getSelectedFigures().groupTags();
            return null;
        });
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

    public void setSelectedFigure(EffectApplicableWithTags figure) {
        moveTagBar.setFigure(figure);
    }

    private void setButtonFunctionality() {
        selectionButton = new ActionToggleButton<>("Selección", (startPoint, endPoint, color) -> {
            canvasState.selectMultipleFigures(startPoint, endPoint);
            setSelectedFigure(canvasState.getSelectedFigures());
            return null;
        });
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
            double xAxis = Math.abs(endPoint.getX() - startPoint.getX());
            double yAxis = Math.abs(endPoint.getY() - startPoint.getY());
            return new DrawnEllipse<>(new Ellipse(centerPoint, xAxis, yAxis), gc, color);
        });
        rectangleButton.setOnAction(event -> figureButtonUnselectTagAction());
        circleButton.setOnAction(event -> figureButtonUnselectTagAction());
        squareButton.setOnAction(event -> figureButtonUnselectTagAction());
        ellipseButton.setOnAction(event -> figureButtonUnselectTagAction());
        groupButton.setOnAction(event -> canvasState.groupSelectedFigures());
        ungroupButton.setOnAction(event -> canvasState.ungroupSelectedFigures());
        deleteButton.setOnAction(event -> {
            canvasState.removeSelectedFigures();
            figureButtonUnselectTagAction();
        });
    }

    private void figureButtonUnselectTagAction() {
        canvasState.setNoFiguresSelected();
        canvasState.unsetTag();
        unselectFigure();
        disableTags.run();
    }

    public boolean noToggleSelected() {
        return tools.getSelectedToggle() == null;
    }

    /**
     * Metodo a ejecutarse en el modo "onRelease" de movimientos del mouse segun el boton seleccionado.
     *
     * @param topLeft     Punto superior izquierdo.
     * @param bottomRight Punto inferior derecho.
     * @return una nueva figura si corresponde. En caso contrario, retorna null.
     */
    public DrawnFigure<?> onRelease(Point topLeft, Point bottomRight) {
        return ((ActionToggleButton<?>) tools.getSelectedToggle()).action(topLeft, bottomRight, fillColorPicker.getValue());
    }

    public void setDisableTagsAction(Runnable action) {
        this.disableTags = action;
    }

    public boolean inSelectMode() {
        return selectionButton.isSelected();
    }
}
