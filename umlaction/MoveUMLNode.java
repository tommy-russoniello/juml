package umlaction;

import juml.*;
import umlobject.*;

/**
 * Action class for moving a UMLNode.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class MoveUMLNode extends UMLNodeAction {
  
  /** 
   * The original x and y coordinates followed by the new x and y coordinates.
   */
  double originalX, originalY, movedX, movedY;
  
  /** 
   * If true, a move is done with original coordinates instead of new coordinates.
   */
  boolean moveAtFirst;
  
  /** 
   * The uml node controller. 
   */
  UMLNodeController umlNodeController;

  /**
   * Instantiates a new move UML node.
   *
   * @param inNode the in node
   * @param inOriginalX the original X coordinate
   * @param inOriginalY the original Y coordinate
   * @param inUMLNodeController the UML node controller
   */
  public MoveUMLNode(UMLNode inNode, double inOriginalX, double inOriginalY,
    UMLNodeController inUMLNodeController) {
    this(inNode, inOriginalX, inOriginalY, inUMLNodeController, true);
  }

  /**
   * Instantiates a new move UML node.
   *
   * @param inNode the in node
   * @param inOriginalX the original X coordinate
   * @param inOriginalY the original Y coordinate
   */
  public MoveUMLNode(UMLNode inNode, double inOriginalX, double inOriginalY) {
    this(inNode, inOriginalX, inOriginalY, null, true);
  }

  /**
   * Instantiates a new move UML node.
   *
   * @param inNode the in node
   * @param inOriginalX the original X coordinate
   * @param inOriginalY the original Y coordinate
   * @param inMoveAtFirst boolean representing whether the original or new coordinates should be used.
   */
  public MoveUMLNode(UMLNode inNode, double inOriginalX, double inOriginalY, boolean inMoveAtFirst) {
    this(inNode, inOriginalX, inOriginalY, null, inMoveAtFirst);
  }

  /**
   * Instantiates a new move UML node.
   *
   * @param inNode the in node
   * @param inOriginalX the original X coordinate
   * @param inOriginalY the original Y coordinate
   * @param inUMLNodeController the UML node controller
   * @param inMoveAtFirst boolean representing whether the original or new coordinates should be used.
   */
  public MoveUMLNode(UMLNode inNode, double inOriginalX, double inOriginalY, UMLNodeController inUMLNodeController, boolean inMoveAtFirst) {
    if (inNode != null) {
      node = inNode;
      umlNodeController = inUMLNodeController;
      originalX = inOriginalX;
      originalY = inOriginalY;
      movedX = node.getOriginX();
      movedY = node.getOriginY();
      if (moveAtFirst = inMoveAtFirst) {
        doAction();
      }
    }
  }

  /**
   * @see umlaction.UMLAction#doAction()
   */
  public void doAction() {
    if (moveAtFirst) {
      node.move(originalX, originalY);
      if (umlNodeController != null) {
        umlNodeController.setOriginCoordinatesText(originalX, originalY);
      }
    } else {
      node.move(movedX, movedY);
      if (umlNodeController != null) {
        umlNodeController.setOriginCoordinatesText(movedX, movedY);
      }
    }
  }

  /**
   * @see umlaction.UMLAction#undoAction()
   */
  public void undoAction() {
    if (moveAtFirst) {
      node.move(movedX, movedY);
      if (umlNodeController != null) {
        umlNodeController.setOriginCoordinatesText(movedX, movedY);
      }
    } else {
      node.move(originalX, originalY);
      if (umlNodeController != null) {
        umlNodeController.setOriginCoordinatesText(originalX, originalY);
      }
    }
  }
}
