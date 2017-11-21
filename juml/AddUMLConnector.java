package juml;
import javafx.scene.Node;
import umlobject.*;
import javafx.scene.layout.Pane;

public class AddUMLConnector extends Controller{
	
	private UMLConnector connector;
	private boolean isCreationObject;
	private Pane pane;
	
	
	public AddUMLConnector(UMLConnector c, boolean isCreator, Pane pane) {
		this (c, isCreator, true, pane);
	}
	
	public AddUMLConnector(UMLConnector c, boolean isCreator, boolean addToStack, Pane pane) {
		super(addToStack);
		this.pane = pane;
		connector = c;
		isCreationObject = isCreator;
		doAction();
	}

	public void doAction() {
		if (isCreationObject)
			create();
		else {
			delete();
		}
	}
	
	public void undoAction() {
		if (isCreationObject)
			delete();
		else {
			create();
		}
	}
	
	private void create	() {
		Node model = connector.getModel();
		//System.out.println("Create: Does pane contain the line: "+pane.getChildren().contains(connector.getModel()));
		pane.getChildren().add(model);
		CONNECTORS.put(model, connector);
		connector.getStart().connections.add(connector);
		connector.getStop().connections.add(connector);
	}
	
	private void delete() {
		CONNECTORS.remove(connector.getModel());
		connector.disconnect();
		pane.getChildren().remove(connector.getModel());
		//System.out.println("Delete: Does pane contain the line: "+pane.getChildren().contains(connector.getModel()));
	}
}
