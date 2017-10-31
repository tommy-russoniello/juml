package umlobject;

import java.util.Vector;

public class UMLNode extends UMLObject {

  public Vector<UMLConnector> connections = new Vector<>();

  public Vector<UMLConnector> getConnections() {
    return connections;
  }

  public void update() {
    for (UMLConnector c: connections) {
  		c.update();
  	}
  }

  public void delete() {
		Vector<UMLConnector> connectionCopy = (Vector<UMLConnector>) connections.clone();
		for (int i = 0; i < connectionCopy.size(); ++i) {
			connectionCopy.remove(i);
			System.out.println("Point has removed self from a connection");
			// because the current connection was deleted out from under us
		}
	}
}
