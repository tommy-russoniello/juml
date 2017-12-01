package umlaction;

import juml.*;
import umlobject.*;

/*
 * Action class for changing ClassBox methods fields.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class ChangeClassBoxMethods extends UMLAction {
  ClassBox classBox;
  String alt;

  public ChangeClassBoxMethods(ClassBox inClassBox, String newValue) {
    classBox = inClassBox;
    alt = newValue;
    doAction();
  }

  public void doAction() {
    String temp = alt;
    alt = classBox.getMethods();
    classBox.setMethods(temp);
  }

  public void undoAction() {
    String temp = alt;
    alt = classBox.getMethods();
    classBox.setMethods(temp);
  }
}
