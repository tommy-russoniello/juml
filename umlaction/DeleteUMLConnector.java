package umlaction;

import juml.*;
import javafx.scene.Node;
import umlobject.*;
import javafx.scene.layout.Pane;

/*
 * Action class for deleting UMLConnectors.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class DeleteUMLConnector extends UMLConnectorAction {
	public DeleteUMLConnector(UMLConnector inConnector, Controller inController) {
		if (inConnector != null && inController != null) {
			controller = inController;
			connector = inConnector;
			model = connector.getModel();
			doAction();
		}
	}

	public void doAction() {
    controller.CONNECTORS.remove(model);
    controller.pane.getChildren().remove(model);
		connector.disconnect();
	}

	public void undoAction() {
    controller.pane.getChildren().add(model);
		controller.CONNECTORS.put(model, connector);
		connector.connect();
	}
}
