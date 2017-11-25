package umlaction;

import juml.*;
import umlobject.*;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.Parent;

public class SplitLine extends UMLConnectorAction {
  Relationship relationship;
  Segment newSegment, splitSegment;
  Pivot pivot;

  public SplitLine(
    Relationship inConnector, Segment inSegment, double x, double y, Controller inController) {
    relationship = (Relationship) inConnector;
    controller = inController;
    splitSegment = inSegment;
    pivot = new Pivot(relationship, x, y);
    if (relationship instanceof Dependency) {
      newSegment = new Segment(splitSegment.getStart(), pivot, true);
    } else {
      System.out.println(splitSegment.getStart());
      newSegment = new Segment(splitSegment.getStart(), pivot);
    }
    relationship.getSegments().add(newSegment);
    doInitialAction();
  }

  public void doAction() {
    splitSegment.disconnect();
    splitSegment.start = pivot;
    splitSegment.connect();
    relationship.group.getChildren().add(newSegment.getModel());
    relationship.group.getChildren().add(pivot.getModel());
    newSegment.connect();
    relationship.update();
  }

  public void undoAction() {
    newSegment.disconnect();
    relationship.group.getChildren().remove(newSegment.getModel());
    relationship.group.getChildren().remove(pivot.getModel());
    splitSegment.disconnect();
    splitSegment.start = relationship.getStart();
    splitSegment.connect();
    relationship.update();
  }

  public void doInitialAction() {
    splitSegment.disconnect();
    splitSegment.start = pivot;
    splitSegment.connect();
    relationship.group.getChildren().add(newSegment.getModel());
    relationship.group.getChildren().add(pivot.getModel());
    newSegment.connect();
    relationship.update();


    class DragSource { double x, y; }
		final DragSource dragSource = new DragSource();
    final DragSource originalPosition = new DragSource();
		// Records dragging coordinate information
		pivot.getModel().setOnMousePressed(new EventHandler<MouseEvent>() {
		  @Override public void handle(MouseEvent mouseEvent) {
			  // Record mouse click coordinates if UMLObject is a Parent.
			  if (controller.MODE == Controller.Mode.SELECT) {
          originalPosition.x = pivot.getOriginX();
          originalPosition.y = pivot.getOriginY();
				  if (pivot.getModel() instanceof Parent) {
				    dragSource.x = mouseEvent.getX();
					  dragSource.y = mouseEvent.getY();
				  }
				  // Records delta of mouse click coordinates and UMLObject's origin if UMLObject is not
				  // * a Parent.
				  else {
					  dragSource.x = pivot.getOriginX() - mouseEvent.getX();
					  dragSource.y = pivot.getOriginY() - mouseEvent.getY();
				  }
  				pivot.getModel().getScene().setCursor(Cursor.MOVE);
  				System.out.println("begin moving pivot");
			  }
		  }
		});

		// Moves UMLObject to new position it was dragged to.
		pivot.getModel().setOnMouseDragged(new EventHandler<MouseEvent>() {
		  @Override public void handle(MouseEvent mouseEvent) {
			  if (controller.MODE == Controller.Mode.SELECT) {
				  if (pivot.getModel() instanceof Parent) {
					  pivot.move(pivot.getOriginX() + (mouseEvent.getX() - dragSource.x),
						  pivot.getOriginY() + (mouseEvent.getY() - dragSource.y));
				  } else {
					  pivot.move(mouseEvent.getX() + dragSource.x, mouseEvent.getY() + dragSource.y);
				  }
				  pivot.update();
				  System.out.println("moved pivot");
			  }
		  }
		});

		// Sets cursor back to default when mouse is released on the UMLObject.
		pivot.getModel().setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent mouseEvent) {
				if (controller.MODE == Controller.Mode.SELECT) {
          controller.ACTIONS.push(
            new MoveUMLNode(pivot, originalPosition.x, originalPosition.y, false));
				  pivot.getModel().getScene().setCursor(Cursor.HAND);
				}
			}
		});
  }
}
