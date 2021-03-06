package umlaction;

import juml.*;
import umlobject.*;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;

/**
 * Action class for adding UMLConnectors.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class AddUMLConnector extends UMLConnectorAction {
	
	/**
	 * Instantiates a new adds the UML connector.
	 *
	 * @param inConnector the in connector
	 * @param inController the in controller
	 */
	public AddUMLConnector(UMLConnector inConnector, Controller inController) {
		if (inConnector != null && inController != null) {
			controller = inController;
			connector = inConnector;
			model = connector.getModel();
			doInitialAction();
		}
	}


	  /**
	   * @see umlaction.UMLAction#doAction()
	   */
	public void doAction() {
		controller.pane.getChildren().add(model);
		controller.CONNECTORS.put(model, connector);
		connector.connect();
	}

	  /**
	   * @see umlaction.UMLAction#undoAction()
	   */
	public void undoAction() {
		controller.CONNECTORS.remove(model);
		controller.pane.getChildren().remove(model);
		connector.disconnect();
	}

	  /**
	   * @see umlaction.UMLAction#doInitialAction()
	   */
	public void doInitialAction() {
		controller.pane.getChildren().add(model);
		controller.CONNECTORS.put(model, connector);
		connector.connect();

		// Sets cursor to HAND when mouse enters the UMLObject.
		model.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent mouseEvent) {
				if (controller.MODE == Controller.Mode.SELECT) {
					if (!mouseEvent.isPrimaryButtonDown()) {
						model.getScene().setCursor(Cursor.HAND);
					}
				}
			}
		});

		// Sets cursor back to default when mouse exits the UMLObject.
		model.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent mouseEvent) {
				if (controller.MODE == Controller.Mode.SELECT) {
					if (!mouseEvent.isPrimaryButtonDown()) {
						model.getScene().setCursor(Cursor.DEFAULT);
					}
				}
			}
		});

		if (connector instanceof Relationship) {
			Relationship relationship = (Relationship) connector;

		}
	}
}
