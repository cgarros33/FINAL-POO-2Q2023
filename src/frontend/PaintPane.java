package frontend;

import backend.model.*;
import frontend.model.CreatorToggleButton;
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

import java.util.*;

public class PaintPane extends BorderPane {

	// BackEnd
	CanvasState canvasState;

	// Canvas y relacionados
	Canvas canvas = new Canvas(800, 600);
	GraphicsContext gc = canvas.getGraphicsContext2D();

	// Botones Barra Izquierda
	ToggleButton selectionButton = new ToggleButton("Seleccionar");
	CreatorToggleButton<DrawnRectangle<Rectangle>> rectangleButton = new CreatorToggleButton<>("Rectángulo", (startPoint, endPoint, color) -> new DrawnRectangle<>(new Rectangle(startPoint, endPoint), gc, color));
	CreatorToggleButton<DrawnEllipse<Circle>> circleButton = new CreatorToggleButton<>("Círculo", (startPoint, endPoint, color) -> {
		double circleRadius = startPoint.distanceTo(endPoint);
		return new DrawnEllipse<>(new Circle(startPoint, circleRadius), gc, color);
	});
	CreatorToggleButton<DrawnRectangle<Square>> squareButton = new CreatorToggleButton<>("Cuadrado", (startPoint, endPoint, color) -> {
		double size = Math.abs(endPoint.getX() - startPoint.getX());
		return new DrawnRectangle<>(new Square(startPoint, size), gc, color);
	});
	CreatorToggleButton<DrawnEllipse<Ellipse>> ellipseButton = new CreatorToggleButton<>("Elipse", (startPoint, endPoint, color) -> {
		Point centerPoint = new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2, (Math.abs((endPoint.getY() + startPoint.getY())) / 2));
		double sMayorAxis = Math.abs(endPoint.getX() - startPoint.getX());
		double sMinorAxis = Math.abs(endPoint.getY() - startPoint.getY());
		return new DrawnEllipse<>(new Ellipse(centerPoint, sMayorAxis, sMinorAxis), gc, color);
	});
	ToggleButton deleteButton = new ToggleButton("Borrar");

	// Selector de color de relleno
	ColorPicker fillColorPicker = new ColorPicker(CanvasState.DEFAULT_FILL_COLOR);

	// Dibujar una figura
	Point startPoint;

	// StatusBar
	StatusPane statusPane;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;
		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton};
		Set<CreatorToggleButton<?>> creatorButtons = new HashSet<>(); //@todo: check generics
		creatorButtons.add(rectangleButton);
		creatorButtons.add(circleButton);
		creatorButtons.add(squareButton);
		creatorButtons.add(ellipseButton);
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
			if(endPoint.isLeft(startPoint) || endPoint.isOver(startPoint)) {
				return ;
			}
			if (!creatorButtons.contains(tools.getSelectedToggle()))
				return;
			DrawnFigure<?> newDrawnFigure = ((CreatorToggleButton<?>) tools.getSelectedToggle()).create(startPoint, endPoint, fillColorPicker.getValue());
			canvasState.add(newDrawnFigure);
			startPoint = null;
			redrawCanvas();
		});

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			statusPane.updateStatus(canvasState.buildPositionLabel(eventPoint, eventPoint.toString()));
		});

		canvas.setOnMouseClicked(event -> {
			if(selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				List<DrawnFigure<?>> figures = canvasState.getFiguresForPoint(eventPoint);
				String label = canvasState.buildPositionLabel(eventPoint, "Ninguna figura encontrada");
				if (figures.isEmpty()) {
					canvasState.setNoFigureSelected();
				}
				else {
					canvasState.setSelectedFigure(figures.get(figures.size()-1));
				}
				statusPane.updateStatus(label);
				redrawCanvas();
			}
		});

		canvas.setOnMouseDragged(event -> {
			if(selectionButton.isSelected() && !canvasState.emptyFigureSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				double diffX = (eventPoint.getX() - startPoint.getX())/100;
				double diffY = (eventPoint.getY() - startPoint.getY())/100;
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
				gc.setStroke(CanvasState.LINE_COLOR);
			}
			gc.setFill(drawnFigure.getColor());
			drawnFigure.draw();
		}
	}



}
