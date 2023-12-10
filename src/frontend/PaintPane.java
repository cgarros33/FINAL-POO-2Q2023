package frontend;

import backend.model.*;
import frontend.model.DrawnFigure;
import frontend.model.DrawnFiguresGroup;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.util.*;

/**
 * Gestiona las acciones del mouse y muestra
 * el estado del canvas.
 */
public class PaintPane extends BorderPane {

    private final CanvasState canvasState;

    // Canvas y relacionados
    private final Canvas canvas = new Canvas(800, 600);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();

    // Dibujar una figura
    private Point startPoint;

    // StatusBar
    private final StatusPane statusPane;
    private final EffectsBar fxBar = new EffectsBar();
    private final TagBar tagBar = new TagBar();

    public PaintPane(CanvasState canvasState, StatusPane statusPane) {
        this.canvasState = canvasState;
        this.statusPane = statusPane;
        canvasState.setUnsetTag(tagBar::unsetTagToShow);
        SideBar sideBar = new SideBar(gc, canvasState);
        sideBar.setDisableTagsAction(() -> fxBar.setDisable(true));
        sideBar.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> redrawCanvas());
        sideBar.setGetTag(tagBar::getTagToShow);
        tagBar.setUnselectFiguresAction(() -> {
            canvasState.setNoFiguresSelected();
            sideBar.unselectFigure();
            fxBar.unsetFigure();
        });
        fxBar.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> redrawCanvas());
        tagBar.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> redrawCanvas());
        tagBar.addEventFilter(KeyEvent.ANY, event -> redrawCanvas());
        gc.setLineWidth(1);

        setCanvasActions(sideBar);
        setTop(fxBar);
        setLeft(sideBar);
        setRight(canvas);
        setBottom(tagBar);
    }

    private void setCanvasActions(SideBar sideBar) {
        canvas.setOnMousePressed(event -> startPoint = new Point(event.getX(), event.getY()));

        canvas.setOnMouseClicked(event -> {
            if (canvasState.isMultipleSelected()) {
                canvasState.setMultipleSelected(false);
                return;
            }
            if (canvasState.inMovingFiguresMode()) {
                canvasState.setInMovingFiguresMode(false);
                return;
            }
            if (sideBar.inSelectMode()) {
                canvasState.setNoFiguresSelected();
                Point eventPoint = new Point(event.getX(), event.getY());
                List<DrawnFigure<?>> figures = canvasState.getFiguresForPoint(eventPoint);
                String label = canvasState.buildPositionLabel(eventPoint, "Ninguna figura encontrada");
                if (figures.isEmpty()) {
                    canvasState.setNoFiguresSelected();
                    sideBar.unselectFigure();
                    fxBar.unsetFigure();
                } else {
                    DrawnFigure<?> topFigure = figures.get(figures.size() - 1);
                    topFigure.select();

                    if (topFigure.hasGroup()) {
                        fxBar.setFigure(topFigure.getGroup());
                        sideBar.setSelectedFigure(topFigure.getGroup());
                    } else {
                        fxBar.setFigure(topFigure);
                        sideBar.setSelectedFigure(topFigure);
                    }
                }
                statusPane.updateStatus(label);
                redrawCanvas();
            }
        });

        canvas.setOnMouseReleased(event -> {
            Point endPoint = new Point(event.getX(), event.getY());
            if (startPoint == null || endPoint.isLeft(startPoint) || endPoint.isOver(startPoint) || endPoint.equals(startPoint)) {
                return;
            }
            if (sideBar.noToggleSelected()) return;
            DrawnFigure<?> newDrawnFigure = sideBar.onRelease(startPoint, endPoint);
            if (newDrawnFigure != null) {
                canvasState.add(newDrawnFigure);
                newDrawnFigure.setAddSelectedFigure(canvasState::addSelectedFigure);
            } else {
                DrawnFiguresGroup selected = canvasState.getSelectedFigures();
                if (!selected.isEmpty()) {
                    fxBar.setFigure(selected);
                    sideBar.setSelectedFigure(selected);
                }
            }
            startPoint = null;
            redrawCanvas();
        });

        canvas.setOnMouseMoved(event -> {
            Point eventPoint = new Point(event.getX(), event.getY());
            statusPane.updateStatus(canvasState.buildPositionLabel(eventPoint, eventPoint.toString()));
        });

        canvas.setOnMouseDragged(event -> {
            if (sideBar.inSelectMode() && !canvasState.emptyFiguresSelected()) {
                Point eventPoint = new Point(event.getX(), event.getY());
                double diffX = (eventPoint.getX() - startPoint.getX());
                double diffY = (eventPoint.getY() - startPoint.getY());
                canvasState.getSelectedFigures().forEach(elem -> elem.move(diffX, diffY));
                startPoint = eventPoint;
                canvasState.setInMovingFiguresMode(true);
                redrawCanvas();
            }
        });
    }

    void redrawCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (DrawnFigure<? extends Figure> drawnFigure : canvasState) {
            if (canvasState.isFigureSelected((drawnFigure))) {
                gc.setStroke(Color.RED);
            } else {
                gc.setStroke(CanvasState.LINE_COLOR);
            }
            gc.setFill(drawnFigure.getColor());
            drawnFigure.draw(tagBar.getTagToShow());
        }
    }
}