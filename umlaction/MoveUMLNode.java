package umlaction;

import juml.*;
import umlobject.*;

public class MoveUMLNode extends UMLNodeAction {
  double originalX, originalY, movedX, movedY;
  boolean moveAtFirst;
  UMLNodeController umlNodeController;

  public MoveUMLNode(UMLNode inNode, double inOriginalX, double inOriginalY,
    UMLNodeController inUMLNodeController) {
    this(inNode, inOriginalX, inOriginalY, inUMLNodeController, true);
  }

  public MoveUMLNode(UMLNode inNode, double inOriginalX, double inOriginalY) {
    this(inNode, inOriginalX, inOriginalY, null, true);
  }

  public MoveUMLNode(UMLNode inNode, double inOriginalX, double inOriginalY, boolean inMoveAtFirst) {
    this(inNode, inOriginalX, inOriginalY, null, inMoveAtFirst);
  }

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
