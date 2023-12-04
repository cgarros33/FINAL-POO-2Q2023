package frontend;

import backend.model.*;
import frontend.model.DrawnFigure;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.util.*;

public class PaintPane extends BorderPane {

	// FrontEnd
	CanvasState canvasState;

	// Canvas y relacionados
	Canvas canvas = new Canvas(800, 600);
	GraphicsContext gc = canvas.getGraphicsContext2D();


	// Dibujar una figura
	Point startPoint;

	// StatusBar
	StatusPane statusPane;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;

		SideBar sideBar = new SideBar(gc);

		gc.setLineWidth(1);

		setCanvasActions(sideBar);
		sideBar.getDeleteButton().setOnAction(event -> {
			if (!canvasState.emptyFigureSelected()) {
				canvasState.remove(canvasState.getSelectedFigure());
				redrawCanvas();
			}
		});

		setLeft(sideBar);
		setRight(canvas);
	}

	private void setCanvasActions(SideBar sideBar) {
		canvas.setOnMousePressed(event -> startPoint = new Point(event.getX(), event.getY()));

		canvas.setOnMouseReleased(event -> {

			Point endPoint = new Point(event.getX(), event.getY());
			if(startPoint == null) {
				return ;
			}
			if(endPoint.isLeft(startPoint) || endPoint.isOver(startPoint)) {
				return ;
			}
			if (sideBar.noToggleSelected()) return;
			DrawnFigure<?> newDrawnFigure = sideBar.onRelease(startPoint, endPoint);
			if (newDrawnFigure!= null) canvasState.add(newDrawnFigure); //@todo: leo Optional
			startPoint = null;
			redrawCanvas();
		});

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			statusPane.updateStatus(canvasState.buildPositionLabel(eventPoint, eventPoint.toString()));
		});

		canvas.setOnMouseClicked(event -> {
			if(sideBar.inSelectMode()) {
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
			if(sideBar.inSelectMode() && !canvasState.emptyFigureSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				double diffX = (eventPoint.getX() - startPoint.getX())/100;
				double diffY = (eventPoint.getY() - startPoint.getY())/100; //@todo: cambiar como tomo el movimiento del mouse
				canvasState.getSelectedFigure().getFigure().move(diffX, diffY);
				redrawCanvas();
			}
		});

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
