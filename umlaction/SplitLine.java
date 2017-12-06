package umlaction;

import juml.*;
import umlobject.*;

import java.util.Vector;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.Parent;
import javafx.scene.shape.Line;

/*
 * Action class for splitting relationship lines with a pivot.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
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
      newSegment = new Segment(splitSegment.getStart(), pivot);
    }
    doInitialAction();
  }

  public void doAction() {
    splitSegment.disconnect();
    splitSegment.start = pivot;
    //splitSegment.connect();
    connectToPivots(splitSegment);
    int pos = relationship.segments.indexOf(splitSegment);
    relationship.pivots.add(pos, pivot);
    relationship.segments.add(pos, newSegment);
    relationship.group.getChildren().add(newSegment.getModel());
    relationship.group.getChildren().add(pivot.getModel());
    //newSegment.connect();
    connectToPivots(newSegment);
    if((Line) splitSegment.getModel() == relationship.startLine) {
      relationship.startLine = (Line) newSegment.getModel();
    }
    relationship.update();
  }

  public void undoAction() {
    relationship.pivots.remove(pivot);
    relationship.segments.remove(newSegment);
    if((Line) newSegment.getModel() == relationship.startLine) {
      relationship.startLine = (Line) splitSegment.getModel();
    }
    newSegment.disconnect();
    relationship.group.getChildren().remove(newSegment.getModel());
    relationship.group.getChildren().remove(pivot.getModel());
    splitSegment.disconnect();
    splitSegment.start = newSegment.getStart();
    //splitSegment.connect();
    connectToPivots(newSegment);
    relationship.update();
  }

 public void connectToPivots(Segment s) {
	    if (s.start instanceof Pivot) {
	    	s.start.getConnections().addElement(s);
	    }
	    if (s.stop instanceof Pivot) {
	    	s.stop.getConnections().addElement(s);
	    }
 }


  public void doInitialAction() {
    splitSegment.disconnect();
    splitSegment.start = pivot;
    //splitSegment.connect();
    connectToPivots(splitSegment);
    relationship.group.getChildren().add(newSegment.getModel());
    relationship.group.getChildren().add(pivot.getModel());
    //newSegment.connect();
    if (!(splitSegment.start instanceof Pivot)) {
    	Vector<UMLConnector> connections = splitSegment.start.getConnections();
    	for (int i=0; i<connections.size(); i++) {
    		if (connections.get(i).getModel().equals(splitSegment.getModel())) {
    			System.out.println("Connector removed");
    			break;
    		}
    	}
    }
    connectToPivots(newSegment);
    int pos = relationship.segments.indexOf(splitSegment);
    relationship.pivots.add(pos, pivot);
    relationship.segments.add(pos, newSegment);
    if((Line) splitSegment.getModel() == relationship.startLine) {
      relationship.startLine = (Line) newSegment.getModel();
    }
    relationship.update();


    class DragSource { double x, y; boolean ifDragged;}
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
			  }
		  }
		});

		// Moves UMLObject to new position it was dragged to.
		pivot.getModel().setOnMouseDragged(new EventHandler<MouseEvent>() {
		  @Override public void handle(MouseEvent mouseEvent) {
			  if (controller.MODE == Controller.Mode.SELECT && !Double.isNaN(dragSource.x) &&
          !Double.isNaN(dragSource.y)) {
				  if (pivot.getModel() instanceof Parent) {
					  pivot.move(pivot.getOriginX() + (mouseEvent.getX() - dragSource.x),
						  pivot.getOriginY() + (mouseEvent.getY() - dragSource.y));
				  } else {
					  pivot.move(mouseEvent.getX() + dragSource.x, mouseEvent.getY() + dragSource.y);
				  }
          dragSource.ifDragged = true;
				  pivot.update();
			  }
		  }
		});

		// Sets cursor back to default when mouse is released on the UMLObject.
		pivot.getModel().setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent mouseEvent) {
				if (controller.MODE == Controller.Mode.SELECT) {
          if (dragSource.ifDragged) {
            controller.UNDONE_ACTIONS.clear();
            controller.ACTIONS.push(
              new MoveUMLNode(pivot, originalPosition.x, originalPosition.y, false));
  				  pivot.getModel().getScene().setCursor(Cursor.HAND);
            dragSource.ifDragged = false;
          }
				}
			}
		});
  }
}
