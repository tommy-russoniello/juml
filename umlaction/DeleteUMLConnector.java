package umlaction;

import juml.*;
import umlobject.*;

/**
 * Action class for deleting UMLConnectors.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class DeleteUMLConnector extends UMLConnectorAction {
	
	/**
	 * Instantiates a new delete UML connector.
	 *
	 * @param inConnector the in connector
	 * @param inController the in controller
	 */
	public DeleteUMLConnector(UMLConnector inConnector, Controller inController) {
		if (inConnector != null && inController != null) {
			controller = inController;
			connector = inConnector;
			model = connector.getModel();
			doAction();
		}
	}

	/**
	 * @see umlaction.UMLAction#doAction()
	 */
	public void doAction() {
    controller.CONNECTORS.remove(model);
    controller.pane.getChildren().remove(model);
		connector.disconnect();
	}

	/**
	 * @see umlaction.UMLAction#undoAction()
	 */
	public void undoAction() {
    controller.pane.getChildren().add(model);
		controller.CONNECTORS.put(model, connector);
		connector.connect();
	}
}
