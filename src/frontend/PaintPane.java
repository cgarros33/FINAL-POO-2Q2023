package frontend;

import backend.model.*;
import frontend.interfaces.Creator;
import frontend.model.DrawnEllipse;
import frontend.model.DrawnFigure;
import frontend.model.DrawnRectangle;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public class PaintPane extends BorderPane {

	// BackEnd
	CanvasState canvasState;

	// Canvas y relacionados
	Canvas canvas = new Canvas(800, 600);
	GraphicsContext gc = canvas.getGraphicsContext2D();
	Color lineColor = Color.BLACK;
	Color defaultFillColor = Color.YELLOW;

	// Botones Barra Izquierda
	ToggleButton selectionButton = new ToggleButton("Seleccionar");
	ToggleButton rectangleButton = new ToggleButton("Rectángulo");
	ToggleButton circleButton = new ToggleButton("Círculo");
	ToggleButton squareButton = new ToggleButton("Cuadrado");
	ToggleButton ellipseButton = new ToggleButton("Elipse");
	ToggleButton deleteButton = new ToggleButton("Borrar");
	Map<ToggleButton, Creator<?>> creatorsMap = new HashMap<>();

	// Selector de color de relleno
	ColorPicker fillColorPicker = new ColorPicker(defaultFillColor);

	// Dibujar una figura
	Point startPoint;

	// StatusBar
	StatusPane statusPane;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;
		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton};
		putFigureCreators();
		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}
		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(toolsArr);
		setButtonsBoxStyle(buttonsBox);
		gc.setLineWidth(1);

		canvas.setOnMousePressed(event -> startPoint = new Point(event.getX(), event.getY()));

		canvas.setOnMouseReleased(event -> {
			Point endPoint = new Point(event.getX(), event.getY());
			if(startPoint == null) {
				return ;
			}
			if(endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) {
				return ;
			}
			DrawnFigure<?> newDrawnFigure = creatorsMap.get((ToggleButton) tools.getSelectedToggle()).create(startPoint, endPoint, fillColorPicker.getValue()); // @todo: ver si sacar o no el casting
			if(newDrawnFigure == null)
				return;
			canvasState.add(newDrawnFigure);
			startPoint = null;
			redrawCanvas();
		});

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			StringBuilder label = new StringBuilder();
			for(DrawnFigure<? extends Figure> drawnFigure : canvasState) {
				Figure figure = drawnFigure.getFigure();
				if(figure.belongs(eventPoint)) {
					found = true;
					label.append(figure);
				}
			}
			if(found) {
				statusPane.updateStatus(label.toString());
			} else {
				statusPane.updateStatus(eventPoint.toString());
			}
		});

		canvas.setOnMouseClicked(event -> {
			if(selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				boolean found = false;
				StringBuilder label = new StringBuilder("Se seleccionó: ");
				for (DrawnFigure<? extends Figure> drawnFigure : canvasState) {
					Figure figure = drawnFigure.getFigure();
					if(figure.belongs(eventPoint)) {
						found = true;
						canvasState.setSelectedFigure(drawnFigure);
						label.append(figure);
					}
				}
				if (found) {
					statusPane.updateStatus(label.toString());
				} else {
					canvasState.setNoFigureSelected();
					statusPane.updateStatus("Ninguna figura encontrada");
				}
				redrawCanvas();
			}
		});

		canvas.setOnMouseDragged(event -> {
			if(selectionButton.isSelected() && !canvasState.emptyFigureSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
				double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
				canvasState.getSelectedFigure().getFigure().move(diffX, diffY);
				redrawCanvas();
			}
		});

		deleteButton.setOnAction(event -> {
			if (!canvasState.emptyFigureSelected()) {
				canvasState.remove(canvasState.getSelectedFigure());
				redrawCanvas();
			}
		});

		setLeft(buttonsBox);
		setRight(canvas);
	}

	private void putFigureCreators() {
		creatorsMap.put(selectionButton, (startPoint, endPoint, color) -> null);
		creatorsMap.put(rectangleButton, (startPoint, endPoint, color) -> new DrawnRectangle<>(new Rectangle(startPoint, endPoint), gc, color));
		creatorsMap.put(circleButton, (startPoint, endPoint, color) -> {
			double circleRadius = startPoint.distanceTo(endPoint);
			return new DrawnEllipse<>(new Circle(startPoint, circleRadius), gc, color);
		});
		creatorsMap.put(squareButton, (startPoint, endPoint, color) -> {
			double size = Math.abs(endPoint.getX() - startPoint.getX());
			return new DrawnRectangle<>(new Square(startPoint, size), gc, color);
		});
		creatorsMap.put(ellipseButton, (startPoint, endPoint, color) -> {
			Point centerPoint = new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2, (Math.abs((endPoint.getY() + startPoint.getY())) / 2));
			double sMayorAxis = Math.abs(endPoint.getX() - startPoint.getX());
			double sMinorAxis = Math.abs(endPoint.getY() - startPoint.getY());
			return new DrawnEllipse<>(new Ellipse(centerPoint, sMayorAxis, sMinorAxis), gc, color);
		});
		creatorsMap.put(deleteButton, (startPoint, endPoint, color) -> null);
	}

	private void setButtonsBoxStyle(VBox buttonsBox) {
		buttonsBox.getChildren().add(fillColorPicker);
		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100);
	}

	void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(DrawnFigure<? extends Figure> drawnFigure : canvasState) {
			if(canvasState.isFigureSelected((drawnFigure))) {
				gc.setStroke(Color.RED);
			} else {
				gc.setStroke(lineColor);
			}
			gc.setFill(drawnFigure.getColor());
			drawnFigure.draw();
		}
	}

}
