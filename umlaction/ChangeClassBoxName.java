package umlaction;

import juml.*;
import umlobject.*;

public class ChangeClassBoxName extends UMLAction {
  ClassBox classBox;
  String alt;

  public ChangeClassBoxName(ClassBox inClassBox, String newValue) {
    classBox = inClassBox;
    alt = newValue;
    doAction();
  }

  public void doAction() {
    String temp = alt;
    alt = classBox.getName();
    classBox.setName(temp);
  }

  public void undoAction() {
    String temp = alt;
    alt = classBox.getName();
    classBox.setName(temp);
  }
}
