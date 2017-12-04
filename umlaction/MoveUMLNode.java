package umlaction;

import juml.*;
import umlobject.*;

public class MoveUMLNode extends UMLNodeAction {
  double originalX, originalY, movedX, movedY;
  boolean moveAtFirst;

  public MoveUMLNode(UMLNode inNode, double inOriginalX, double inOriginalY) {
    this(inNode, inOriginalX, inOriginalY, true);
  }

  public MoveUMLNode(UMLNode inNode, double inOriginalX, double inOriginalY, boolean inMoveAtFirst) {
    if (inNode != null) {
      node = inNode;
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
    } else {
      node.move(movedX, movedY);
    }
  }

  public void undoAction() {
    if (moveAtFirst) {
      node.move(movedX, movedY);
    } else {
      node.move(originalX, originalY);
    }
  }
}
