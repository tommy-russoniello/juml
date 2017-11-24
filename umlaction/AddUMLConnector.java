package umlaction;

import juml.*;
import javafx.scene.Node;
import umlobject.*;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;

public class AddUMLConnector extends UMLConnectorAction {
	public AddUMLConnector(UMLConnector inConnector, Controller inController) {
		if (inConnector != null && inController != null) {
			controller = inController;
			connector = inConnector;
			model = connector.getModel();
			doInitialAction();
		}
	}

	public void doAction() {
		controller.pane.getChildren().add(model);
		controller.CONNECTORS.put(model, connector);
		connector.connect();
	}

	public void undoAction() {
		controller.CONNECTORS.remove(model);
    controller.pane.getChildren().remove(model);
		connector.disconnect();
	}

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
	}
}
