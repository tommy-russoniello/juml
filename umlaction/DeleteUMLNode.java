package umlaction;

import juml.*;
import umlobject.*;
import java.util.Vector;
import java.util.Collections;

/**
 * Action class for deleting UMLNodes.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class DeleteUMLNode extends UMLNodeAction {
  
  /** 
   * A vector of connection actions. 
   */
  public Vector<DeleteUMLConnector> connectionActions;

  /**
   * Instantiates a new delete UML node.
   *
   * @param inNode the in node
   * @param inController the in controller
   */
  public DeleteUMLNode (UMLNode inNode, Controller inController) {
    if (inNode != null && inController != null) {
      controller = inController;
      node = inNode;
      model = node.getModel();
      connectionActions = new Vector<>();
		  doInitialAction();
    }
  }

  /**
   * @see umlaction.UMLAction#doAction()
   */
  public void doAction() {
    Collections.reverse(connectionActions);
    for (DeleteUMLConnector connectionAction : connectionActions) {
      connectionAction.doAction();
    }
    controller.NODES.remove(model);
    controller.pane.getChildren().remove(model);
  }

  /**
   * @see umlaction.UMLAction#undoAction()
   */
  public void undoAction() {
    controller.pane.getChildren().add(model);
		controller.NODES.put(model, node);
    Collections.reverse(connectionActions);
    for (DeleteUMLConnector connectionAction : connectionActions) {
      connectionAction.undoAction();
    }
  }

  /**
   * @see umlaction.UMLAction#doInitialAction()
   */
  public void doInitialAction() {
    while (!node.getConnections().isEmpty()) {
      connectionActions.add(
        new DeleteUMLConnector(node.getConnections().lastElement(), controller)
      );
    }
    controller.NODES.remove(model);
    controller.pane.getChildren().remove(model);
  }
}
