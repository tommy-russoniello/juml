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
  
  /** 
   * The class box controller. 
   */
  ClassBoxController classBoxController;
  
  /** 
   * The class box. 
   */
  ClassBox classBox;
  
  /** The alternate text. */
  String alt;

  /**
   * Instantiates a new change class box methods.
   *
   * @param inClassBox the in class box
   * @param newValue the new value
   */
  public ChangeClassBoxMethods(ClassBox inClassBox, String newValue) {
    this(inClassBox, newValue, null);
  }

  /**
   * Instantiates a new change class box methods.
   *
   * @param inClassBox the in class box
   * @param newValue the new value
   * @param inClassBoxController the in class box controller
   */
  public ChangeClassBoxMethods(ClassBox inClassBox, String newValue,
    ClassBoxController inClassBoxController) {
    classBoxController = inClassBoxController;
    classBox = inClassBox;
    alt = newValue;
    doAction();
  }

  /**
   * @see umlaction.UMLAction#doAction()
   */
  public void doAction() {
    if (classBoxController != null) {
      classBoxController.classBoxMethods.setText(alt);
    }
    String temp = alt;
    alt = classBox.getMethods();
    classBox.setMethods(temp);
  }

  /**
   * @see umlaction.UMLAction#undoAction()
   */
  public void undoAction() {
    if (classBoxController != null) {
      classBoxController.classBoxMethods.setText(alt);
    }
    String temp = alt;
    alt = classBox.getMethods();
    classBox.setMethods(temp);
  }
}
