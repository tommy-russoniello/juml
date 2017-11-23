package umlaction;

import juml.*;
import umlobject.*;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Cursor;


public class AddUMLNode extends UMLNodeAction {
  public AddUMLNode (UMLNode inNode, Controller inController) {
    if (inNode != null && inController != null) {
      controller = inController;
      node = inNode;
      model = node.getModel();
		  doInitialAction();
    }
  }

  public void doAction () {
    controller.pane.getChildren().add(model);
		controller.NODES.put(model, node);
  }

  public void undoAction () {
    controller.NODES.remove(model);
    controller.pane.getChildren().remove(model);
  }

  public void doInitialAction() {
		controller.pane.getChildren().add(model);
    controller.NODES.put(model, node);
		// Records mouse drag source coordinates across handlers.
		class DragSource { double x, y; }
		final DragSource dragSource = new DragSource();
    final DragSource originalPosition = new DragSource();
		// Records dragging coordinate information
		model.setOnMousePressed(new EventHandler<MouseEvent>() {
		  @Override public void handle(MouseEvent mouseEvent) {
			  // Record mouse click coordinates if UMLObject is a Parent.
			  if (controller.MODE == Controller.Mode.SELECT) {
          originalPosition.x = node.getOriginX();
          originalPosition.y = node.getOriginY();
				  if (model instanceof Parent) {
				    dragSource.x = mouseEvent.getX();
					  dragSource.y = mouseEvent.getY();
				  }
				  // Records delta of mouse click coordinates and UMLObject's origin if UMLObject is not
				  // * a Parent.
				  else {
					  dragSource.x = node.getOriginX() - mouseEvent.getX();
					  dragSource.y = node.getOriginY() - mouseEvent.getY();
				  }
  				model.getScene().setCursor(Cursor.MOVE);
  				System.out.println("begin moving Node");
			  }
		  }
		});

		// Moves UMLObject to new position it was dragged to.
		model.setOnMouseDragged(new EventHandler<MouseEvent>() {
		  @Override public void handle(MouseEvent mouseEvent) {
			  if (controller.MODE == Controller.Mode.SELECT) {
				  if (model instanceof Parent) {
					  node.move(node.getOriginX() + (mouseEvent.getX() - dragSource.x),
						node.getOriginY() + (mouseEvent.getY() - dragSource.y));
				  } else {
					  node.move(mouseEvent.getX() + dragSource.x, mouseEvent.getY() + dragSource.y);
				  }
				  node.update();
				  System.out.println("moved Node");
			  }
		  }
		});

		// Sets cursor back to default when mouse is released on the UMLObject.
		model.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent mouseEvent) {
				if (controller.MODE == Controller.Mode.SELECT) {
          controller.ACTIONS.push(
            new MoveUMLNode(node, originalPosition.x, originalPosition.y, false));
				  model.getScene().setCursor(Cursor.HAND);
				}
			}
		});

		// Sets cursor to HAND when mouse enters the UMLObject.
		model.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent mouseEvent) {
				if (controller.MODE == Controller.Mode.SELECT) {
					if (!mouseEvent.isPrimaryButtonDown()) {
						model.getScene().setCursor(Cursor.HAND);
					}
				}
			}
		});

		// Sets cursor back to default when mouse exits the UMLObject.
		model.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent mouseEvent) {
				if (controller.MODE == Controller.Mode.SELECT) {
					if (!mouseEvent.isPrimaryButtonDown()) {
						model.getScene().setCursor(Cursor.DEFAULT);
					}
				}
			}
		});
	}
}
