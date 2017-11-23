package umlaction;

import juml.*;
import umlobject.*;

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
