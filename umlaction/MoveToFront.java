package umlaction;

import umlobject.*;
import juml.*;

/**
 * Action class for moving an object to the front of the pane.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class MoveToFront extends UMLAction {
  
  /** 
   * The objects. 
   */
  UMLObject [] objects;
  
  /** 
   * The indexes. 
   */
  int [] indexes;

  /**
   * Instantiates a new move to front.
   *
   * @param inController the in controller
   * @param inObjects the in objects
   */
  public MoveToFront(Controller inController, UMLObject... inObjects) {
    controller = inController;
    objects = inObjects;
    indexes = new int[objects.length];
    int counter = 0;
    for (UMLObject object : objects) {
      indexes[counter] = controller.getPane().getChildren().indexOf(object.getModel());
      counter++;
    }
    doAction();
  }

  /**
   * @see umlaction.UMLAction#doAction()
   */
  public void doAction() {
    for (UMLObject object : objects) {
			if (controller.getPane().getChildren().contains(object.getModel())) {
				controller.getPane().getChildren().remove(object.getModel());
				controller.getPane().getChildren().add(object.getModel());
			}
		}
  }

  /**
   * @see umlaction.UMLAction#undoAction()
   */
  public void undoAction() {
    int counter = 0;
    for (UMLObject object : objects) {
			if (controller.getPane().getChildren().contains(object.getModel())) {
				controller.getPane().getChildren().remove(object.getModel());
				controller.getPane().getChildren().add(indexes[counter], object.getModel());
				counter++;
			}
		}
  }
}
