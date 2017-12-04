package umlobject;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;
import javafx.scene.Node;

/**
 * UML relationship representation.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.1
 */
public class UMLConnector extends UMLObject {

  /**
   * Two objects that this UMLConnector connects.
   */
  public UMLNode start, stop;


  /**
   * Save method. Stored as "delimiter startObjectIndex endObjectIndex"
   * @param allNodes List of all nodes currently in the scene. 
   * @postcondition generates a string with the necessary information for the object to rebuild itself.
   */
  public String saveAsString(Vector<UMLNode> allNodes) {
		Node startModel = start.getModel();
		Node endModel = stop.getModel();
		int startIndex = findIndex(allNodes, startModel);
		int endIndex = findIndex(allNodes, endModel);
		return "" + startIndex + " " + endIndex+" ";
	}

  /**
   * Takes the given array of nodes, and finds the index of a given model
   * @param allNodes List of all nodes currently in the scene.
   * @param model The particular node to search for in the list.
   * @postcondition returns the index of  the given node in the list
   */
	private int findIndex(Vector<UMLNode> allNodes, Node model) {
		for (int i = 0; i < allNodes.size(); i++) {
			if (allNodes.get(i).getModel().equals(model)) {
				return i;
			}
		}
		System.out.println("Error! could not find model");
		return -1;
	}


	
  
  /**
   * Return the UMLNode that the underlying Line model starts at.
   * @return UMLNode that the underlying Line model starts at.
   */
  public UMLNode getStart() {
    return start;
  }

  /**
   * Return the UMLNode that the underlying Line model stops at.
   * @return UMLNode that the underlying Line model stops at.
   */
  public UMLNode getStop() {
    return stop;
  }

  /**
   * Adds this to starting and stopping UMLNodes' list of UMLConnectors.
   * @postcondition This is added to starting and stopping UMLNodes' list of UMLConnectors.
   */
  public void connect() {
    start.connections.add(this);
    stop.connections.add(this);
  }

  /**
   * Removes this from starting and stopping UMLNodes' list of UMLConnectors.
   * @postcondition This is removed from starting and stopping UMLNodes' list of UMLConnectors.
   */
  public void disconnect() {
    start.connections.remove(this);
    stop.connections.remove(this);
  }
}
