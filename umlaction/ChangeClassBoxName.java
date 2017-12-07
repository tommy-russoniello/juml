package umlaction;

import juml.*;
import umlobject.*;

/*
 * Action class for changing ClassBox name fields.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class ChangeClassBoxName extends UMLAction {
  ClassBoxController classBoxController;
  ClassBox classBox;
  String alt;

  public ChangeClassBoxName(ClassBox inClassBox, String newValue) {
    this(inClassBox, newValue, null);
  }

  public ChangeClassBoxName(ClassBox inClassBox, String newValue,
    ClassBoxController inClassBoxController) {
    classBoxController = inClassBoxController;
    classBox = inClassBox;
    alt = newValue;
    doAction();
  }

  public void doAction() {
    if (classBoxController != null) {
      classBoxController.classBoxName.setText(alt);
    }
    String temp = alt;
    alt = classBox.getName();
    classBox.setName(temp);
    classBox.trim();
  }

  public void undoAction() {
    if (classBoxController != null) {
      classBoxController.classBoxName.setText(alt);
    }
    String temp = alt;
    alt = classBox.getName();
    classBox.setName(temp);
    classBox.trim();
  }
}
