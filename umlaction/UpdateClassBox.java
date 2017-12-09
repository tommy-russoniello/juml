package umlaction;

import juml.*;
import umlobject.*;

/**
 * Action class for updating all fields of a Classbox.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class UpdateClassBox extends UMLNodeAction {
  
  /** 
   * The name. 
   */
  ChangeClassBoxName name;
  
  /**
   * The attributes. 
   */
  ChangeClassBoxAttributes attributes;
  
  /** 
   * The methods. 
   */
  ChangeClassBoxMethods methods;
  
  /** 
   * The move. 
   */
  MoveUMLNode move;
  
  /** 
   * The class box controller. 
   */
  ClassBoxController classBoxController;

  /**
   * Instantiates a new update class box.
   *
   * @param classBox the class box
   * @param newName the new name
   * @param newAttributes the new attributes
   * @param newMethods the new methods
   * @param newX the new X
   * @param newY the new Y
   */
  public UpdateClassBox (ClassBox classBox, String newName, String newAttributes,
    String newMethods, double newX, double newY)
  {
    this(classBox, newName, newAttributes, newMethods, newX, newY, null);
  }

  /**
   * Instantiates a new update class box.
   *
   * @param classBox the class box
   * @param newName the new name
   * @param newAttributes the new attributes
   * @param newMethods the new methods
   * @param newX the new X
   * @param newY the new Y
   * @param inClassBoxController the in class box controller
   */
  public UpdateClassBox (ClassBox classBox, String newName, String newAttributes,
    String newMethods, double newX, double newY, ClassBoxController inClassBoxController)
  {
    classBoxController = inClassBoxController;
    name = new ChangeClassBoxName(classBox, newName, classBoxController);
    attributes = new ChangeClassBoxAttributes(classBox, newAttributes, classBoxController);
    methods = new ChangeClassBoxMethods(classBox, newMethods, classBoxController);
    move = new MoveUMLNode(classBox, newX, newY, classBoxController);
  }

  /**
   * @see umlaction.UMLAction#doAction()
   */
  public void doAction() {
    name.doAction();
    attributes.doAction();
    methods.doAction();
    move.doAction();
  }

  /**
   * @see umlaction.UMLAction#undoAction()
   */
  public void undoAction() {
    name.undoAction();
    attributes.undoAction();
    methods.undoAction();
    move.undoAction();
  }
}
