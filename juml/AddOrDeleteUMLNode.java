package juml;
import umlobject.*;
import java.util.Vector;
import javafx.scene.Node;
import javafx.scene.layout.Pane;


public class AddOrDeleteUMLNode extends Controller {

	private Pane pane;
	private UMLNode object;
	private boolean isCreationObject;
	private Vector<UMLConnector> connections;
	private Vector<Controller> deletedConnections;
	
	
	public AddOrDeleteUMLNode(UMLNode n, boolean isCreator, Pane pane) {
		this(n, isCreator, true, pane);
	}

	
	@SuppressWarnings("unchecked")
	public AddOrDeleteUMLNode(UMLNode n, boolean isCreator, boolean addToStack, Pane pane) {
		super(addToStack);
		this.pane = pane; 
		if (pane == null) {
			System.out.println("No pane; faital error");
		} else {
			System.out.println("There is a pane");
		}
		object = n;
		connections = (Vector<UMLConnector>) object.getConnections().clone();
		//System.out.println("Delete object created. Number of connectors is: "+connections.size());
		deletedConnections = new Vector<Controller>();
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
		Node model = object.getModel();
		if (pane == null) {
			System.out.println("No pane; faital error");
			return;
		}
		if (NODES == null) {
			System.out.println("No NODES; faital error");
			return;
		}
		pane.getChildren().add(model);
		NODES.put(model, object);
		//this is for undoing if this is a delete object
		//System.out.println("Number of connections is: "+ deletedConnections.size());
		for (Controller c : deletedConnections) {
			c.undoAction();
			//System.out.println("Rebuilt a connector");
		}
	}
	
	private void delete() {
		deletedConnections.clear();
		//System.out.println("Number of origenal connections is: "+ connections.size());
		for (UMLConnector c : connections) {
			deletedConnections.addElement(new AddUMLConnector(c, false, false, pane));
			//System.out.println("Deleted a connector");
		}
		NODES.remove(object.getModel());
		pane.getChildren().remove(object.getModel());
	}
	
}
