package umlaction;

import juml.*;
import umlobject.*;

/*
 * Action class for changing Classbox attributes fields.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class ChangeClassBoxAttributes extends UMLAction {
  ClassBoxController classBoxController;
  ClassBox classBox;
  String alt;

  public ChangeClassBoxAttributes(ClassBox inClassBox, String newValue) {
    this(inClassBox, newValue, null);
  }

  public ChangeClassBoxAttributes(ClassBox inClassBox, String newValue,
    ClassBoxController inClassBoxController) {
    classBoxController = inClassBoxController;
    classBox = inClassBox;
    alt = newValue;
    doAction();
  }

  public void doAction() {
    if (classBoxController != null) {
      classBoxController.classBoxAttributes.setText(alt);
    }
    String temp = alt;
    alt = classBox.getAttributes();
    classBox.setAttributes(temp);
  }

  public void undoAction() {
    if (classBoxController != null) {
      classBoxController.classBoxAttributes.setText(alt);
    }
    String temp = alt;
    alt = classBox.getAttributes();
    classBox.setAttributes(temp);
  }
}
