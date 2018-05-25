package de.simonmeusel.vector.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import de.simonmeusel.vector.board.Board;
import de.simonmeusel.vector.board.BoundingBox;
import de.simonmeusel.vector.board.Point;
import de.simonmeusel.vector.board.Screen;
import de.simonmeusel.vector.board.shape.Arrow;
import de.simonmeusel.vector.board.shape.Dot;
import de.simonmeusel.vector.board.shape.Ellipse;
import de.simonmeusel.vector.board.shape.Line;
import de.simonmeusel.vector.board.shape.Rectangle;
import de.simonmeusel.vector.board.shape.Shape;
import de.simonmeusel.vector.io.svg.SVGCreator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class DocumentController implements Initializable {
	public static final double ZOOM_BUTTON_FACTOR = 0.2;
	public static final double ZOOM_GESTURE_EXPONENT = 0.1;

	@FXML
	private Pane boardContainer;
	@FXML
	private VBox toolBox;
	@FXML
	private ColorPicker colorPicker;
	@FXML
	private VBox propertyBox;
	@FXML
	private TextField lowerLeftPointXCoordinate;
	@FXML
	private TextField lowerLeftPointYCoordinate;
	@FXML
	private TextField upperRigthPointXCoordinate;
	@FXML
	private TextField upperRigthPointYCoordinate;
	@FXML
	private ComboBox<String> shapeSelection;

	private Board board;

	private boolean dragStarted = false;
	private Shape dragShape = null;
	private Point dragStartPoint;
	private double realDragStartX;
	private double realDragStartY;
	private double realDragLastX;
	private double realDragLastY;
	private MouseButton dragButton;

	private Shape selectedShape;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		board = new Board();

		// Add default shapes
		board.addShape(new Rectangle(board, new Point(10, 10, board), new Point(30, 30, board)));
		board.addShape(new Dot(board, new Point(500, 300, board)));
		board.addShape(new Line(board, new Point(0, 0, board), new Point(400, 400, board), false));
		board.addShape(new Ellipse(board, new Point(0, 0, board), new Point(100, 100, board)));

		board.addShape(new Arrow(board, new Point(0, 300, board), new Point(400, 500, board), true, true, 30));
		board.addShape(new Arrow(board, new Point(100, 300, board), new Point(500, 500, board), false, true, 30));
		board.addShape(new Arrow(board, new Point(200, 300, board), new Point(600, 500, board), true, false, 30));
		board.addShape(new Arrow(board, new Point(300, 300, board), new Point(700, 500, board), false, false, 30));

		// Add board to pane
		boardContainer.getChildren().add(board);

		// Automatically resize canvas with pane
		boardContainer.widthProperty().addListener(observable -> handleResize());
		boardContainer.heightProperty().addListener(observable -> handleResize());

		// Initialize values in combo box
		shapeSelection
				.setItems(FXCollections.observableArrayList("Move", "Arrow", "Dot", "Ellipse", "Line", "Rectangle"));
		// Select rectangle
		shapeSelection.getSelectionModel().select("Move");

		boardContainer.setOnMouseClicked(event -> {
			Point p = new Point(event.getX(), event.getY(), board, true);
			selectShape(board.getShapeAtPoint(p));
		});

		boardContainer.setOnDragDetected(event -> {
			event.consume();
			dragStarted = true;
			realDragStartX = event.getX();
			realDragStartY = event.getY();
			realDragLastX = event.getX();
			realDragLastY = event.getY();
			dragButton = event.getButton();
			dragStartPoint = new Point(realDragStartX, realDragStartY, board, true);

			if (shapeSelection.getSelectionModel().getSelectedItem().equals("Move")) {
				dragShape = board.getShapeAtPoint(dragStartPoint);
			}
		});

		boardContainer.setOnMouseDragged(event -> {
			handleDrag(event);
			realDragLastX = event.getX();
			realDragLastY = event.getY();
		});

		boardContainer.setOnMouseReleased(event -> {
			handleDrag(event);
			dragStarted = false;
			dragShape = null;
		});

		board.redraw();
	}


	@FXML
	private void moveTowardsForeground() {
		if (selectedShape != null) {
			board.moveTowardsForeground(selectedShape);
			board.redraw();
		}
	}


	@FXML
	private void moveTowardsBackground() {
		if (selectedShape != null) {
			board.moveTowardsBackground(selectedShape);
			board.redraw();
		}
	}


	@FXML
	private void moveToForeground() {
		if (selectedShape != null) {
			board.moveToForeground(selectedShape);
			board.redraw();
		}
	}


	@FXML
	private void moveToBackground() {
		if (selectedShape != null) {
			board.moveToBackground(selectedShape);
			board.redraw();
		}
	}

	/**
	 * Handles a change in the color picker
	 */
	@FXML
	private void handleColorChange() {
		if (selectedShape != null) {
			selectedShape.setColor(colorPicker.getValue());
			board.redraw();
		}
	}

	/**
	 * Handles a scroll event from the board container
	 * 
	 * @param event
	 *            Scroll event
	 */
	@FXML
	private void handleScroll(ScrollEvent event) {
		Screen screen = board.getScreen();

		double rdx = event.getDeltaX();
		double rdy = event.getDeltaY();

		double dx = -Math.signum(rdx) * screen.getUnrealDistance(rdx);
		double dy = Math.signum(rdy) * screen.getUnrealDistance(rdy);

		board.getScreen().move(dx, dy);
		board.redraw();
	}

	/**
	 * Handles the progression of a mouse drag event
	 * 
	 * @param event
	 *            Mouse drag event
	 */
	private void handleDrag(MouseEvent event) {
		if (dragStarted) {
			Screen screen = board.getScreen();
			double rdx = event.getX() - realDragLastX;
			double rdy = event.getY() - realDragLastY;

			double dx = -Math.signum(rdx) * screen.getUnrealDistance(rdx);
			double dy = Math.signum(rdy) * screen.getUnrealDistance(rdy);
			switch (dragButton) {
			case PRIMARY:
				// Create a shape or move a shape
				if (shapeSelection.getSelectionModel().getSelectedItem().equals("Move")) {
					if (dragShape != null) {
						dragShape.getBoundingBox().move(-dx, -dy);
						updatePositionTextFields();
						board.redraw();
					}

				} else {
					Point newPoint = new Point(event.getX(), event.getY(), board, true);
					if (dragShape == null) {
						try {
							Class<? extends Shape> shapeClass = Shape
									.getShapeSubclassByName(shapeSelection.getSelectionModel().getSelectedItem());
							dragShape = Shape.createShapeByBoundingBox(shapeClass, board,
									new BoundingBox(dragStartPoint, newPoint));
							
							board.addShape(dragShape);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						dragShape.getBoundingBox().setPoints(dragStartPoint, newPoint);
					}
					board.redraw();
				}
				break;
			case SECONDARY:
				// Move the screen
				board.getScreen().move(dx, dy);
				board.redraw();
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Handles a resize of the board container
	 */
	private void handleResize() {
		board.setWidth(boardContainer.getWidth());
		board.setHeight(boardContainer.getHeight());
	}

	/**
	 * Selects a shape
	 * 
	 * @param shape
	 *            Shape to select
	 */
	private void selectShape(Shape shape) {
		if (shape == null) {
			return;
		}
		selectedShape = shape;
		colorPicker.setValue(selectedShape.getColor());

		updatePositionTextFields();
	}

	/**
	 * Updates the coordinate text fields to the shape's position
	 */
	private void updatePositionTextFields() {
		if (selectedShape == null) {
			lowerLeftPointXCoordinate.setText("");
			lowerLeftPointYCoordinate.setText("");

			upperRigthPointXCoordinate.setText("");
			upperRigthPointYCoordinate.setText("");
			return;
		}

		Point ll = selectedShape.getBoundingBox().getLowerLeftPoint();
		Point ur = selectedShape.getBoundingBox().getUpperRigthPoint();

		lowerLeftPointXCoordinate.setText("" + ll.getX());
		lowerLeftPointYCoordinate.setText("" + ll.getY());

		upperRigthPointXCoordinate.setText("" + ur.getX());
		upperRigthPointYCoordinate.setText("" + ur.getY());
	}

	/**
	 * Updates the position of the selected shape using the coordinate text fields
	 */
	@FXML
	private void updateShapePosition() {
		if (selectedShape == null) {
			return;
		}

		Point ll = new Point(Double.parseDouble(lowerLeftPointXCoordinate.getText()),
				Double.parseDouble(lowerLeftPointYCoordinate.getText()), board);
		Point ur = new Point(Double.parseDouble(upperRigthPointXCoordinate.getText()),
				Double.parseDouble(upperRigthPointYCoordinate.getText()), board);

		selectedShape.getBoundingBox().setLowerLeftPoint(ll);
		selectedShape.getBoundingBox().setUpperRigthPoint(ur);

		board.redraw();
	}

	@FXML
	private void deleteSelectedShape() {
		board.removeShape(selectedShape);
		board.redraw();
	}
	
	@FXML
	private void saveToFile() throws IOException, ParserConfigurationException, TransformerException {
		FileChooser fc = new FileChooser();
		File file = fc.showSaveDialog(board.getScene().getWindow());
		if (file != null) {
			FileOutputStream out = new FileOutputStream(file);
			new SVGCreator().create(out, board);
		}
	}
	
	@FXML
	private void flipLine() {
		if (selectedShape instanceof Line) {
			Line line = (Line) selectedShape;
			line.setRotated(!line.isRotated());
			board.redraw();
		}
	}
	
	@FXML
	private void flipArrow() {
		if (selectedShape instanceof Arrow) {
			Arrow arrow = (Arrow) selectedShape;
			arrow.setArrowDirectionInverted(!arrow.isArrowDirectionInverted());
			board.redraw();
		}
	}
	
	@FXML
	private void zoomIn() {
		board.getScreen().zoom(1 + ZOOM_BUTTON_FACTOR);
		board.redraw();
	}

	
	@FXML
	private void zoomOut() {
		board.getScreen().zoom(1 - ZOOM_BUTTON_FACTOR);
		board.redraw();
	}
	
	@FXML
	private void handleZoomEvent(ZoomEvent event) {
		board.getScreen().zoom(Math.pow(event.getTotalZoomFactor(), ZOOM_GESTURE_EXPONENT));
		board.redraw();
	}
}
