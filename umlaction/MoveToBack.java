package umlaction;

import umlobject.*;
import juml.*;

public class MoveToBack extends UMLAction {
  UMLObject [] objects;
  int [] indexes;

  public MoveToBack(Controller inController, UMLObject... inObjects) {
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

  public void doAction() {
    for (UMLObject object : objects) {
			if (controller.getPane().getChildren().contains(object.getModel())) {
				controller.getPane().getChildren().remove(object.getModel());
				controller.getPane().getChildren().add(0, object.getModel());
			}
		}
  }

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
