package umlaction;

import juml.*;
import umlobject.*;

/*
 * Action class for updating all fields of a Classbox.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class UpdateClassBox extends UMLNodeAction {
  ChangeClassBoxName name;
  ChangeClassBoxAttributes attributes;
  ChangeClassBoxMethods methods;
  MoveUMLNode move;
  ClassBoxController classBoxController;

  public UpdateClassBox (ClassBox classBox, String newName, String newAttributes,
    String newMethods, double newX, double newY)
  {
    this(classBox, newName, newAttributes, newMethods, newX, newY, null);
  }

  public UpdateClassBox (ClassBox classBox, String newName, String newAttributes,
    String newMethods, double newX, double newY, ClassBoxController inClassBoxController)
  {
    classBoxController = inClassBoxController;
    name = new ChangeClassBoxName(classBox, newName, classBoxController);
    attributes = new ChangeClassBoxAttributes(classBox, newAttributes, classBoxController);
    methods = new ChangeClassBoxMethods(classBox, newMethods, classBoxController);
    move = new MoveUMLNode(classBox, newX, newY, classBoxController);
  }

  public void doAction() {
    name.doAction();
    attributes.doAction();
    methods.doAction();
    move.doAction();
  }

  public void undoAction() {
    name.undoAction();
    attributes.undoAction();
    methods.undoAction();
    move.undoAction();
  }
}
