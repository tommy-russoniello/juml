package umlaction;

import juml.*;
import umlobject.*;

public class ChangeClassBoxAttributes extends UMLAction {
  ClassBox classBox;
  String alt;

  public ChangeClassBoxAttributes(ClassBox inClassBox, String newValue) {
    classBox = inClassBox;
    alt = newValue;
    doAction();
  }

  public void doAction() {
    String temp = alt;
    alt = classBox.getAttributes();
    classBox.setAttributes(temp);
  }

  public void undoAction() {
    String temp = alt;
    alt = classBox.getAttributes();
    classBox.setAttributes(temp);
  }
}
