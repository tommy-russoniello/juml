package umlaction;

import java.util.Scanner;
import java.util.Vector;

import juml.*;
import umlobject.*;

/**
 * Action class for changing relationship type.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class ChangeRelationshipType extends UMLAction {
	
	/** 
	 * The delete old line. 
	 */
	DeleteUMLConnector deleteOldLine;
	
	/** 
	 * The add new line. 
	 */
	AddUMLConnector addNewLine;
	
	/** 
	 * The controller. 
	 */
	Controller controller;
	
	/** 
	 * The old connector. 
	 */
	UMLObject oldConnector;
	
	/** 
	 * The new connector. 
	 */
	UMLObject newConnector;

	/**
	 * Instantiates a new change relationship type.
	 *
	 * @param original the original
	 * @param inNewRelationship the in new relationship
	 * @param c the c
	 */
	public ChangeRelationshipType(Relationship original, String inNewRelationship, Controller c) {
		inNewRelationship = inNewRelationship.toUpperCase();
    RelationshipType newRelationship = RelationshipType.valueOf(inNewRelationship);
		controller = c;
		controller.deselectAll();
		oldConnector = original;
		Vector<UMLNode> allNodes = new Vector<UMLNode>();
		allNodes.add(original.start);
		allNodes.add(original.stop);
		String newRelationshipSaveString = ((UMLConnector) original).saveAsString(allNodes);
		newRelationshipSaveString += original.saveAsString();
		deleteOldLine = new DeleteUMLConnector(original, c);
		UMLConnector newConnector = null;
		Scanner input = new Scanner(newRelationshipSaveString);
		if (newRelationship == RelationshipType.AGGREGATION) {
			newConnector = new Aggregation(input, allNodes, c);
		} else if (newRelationship == RelationshipType.ASSOCIATION) {
			newConnector = new Association(input, allNodes, c);
		} else if (newRelationship == RelationshipType.COMPOSITION) {
			newConnector = new Composition(input, allNodes, c);
		} else if (newRelationship == RelationshipType.DEPENDENCY) {
			newConnector = new Dependency(input, allNodes, c);
		} else if (newRelationship == RelationshipType.GENERALIZATION) {
			newConnector = new Generalization(input, allNodes, c);
		}
		addNewLine = new AddUMLConnector(newConnector, c);
		this.newConnector = newConnector;
		controller.selectObject(newConnector);
	}

	/**
	 * @see umlaction.UMLAction#doAction()
	 */
	public void doAction() {
		controller.deselectAll();
		deleteOldLine.doAction();
		addNewLine.doAction();
		controller.selectObject(newConnector);
	}

	/**
	 * @see umlaction.UMLAction#undoAction()
	 */
	public void undoAction() {
		controller.deselectAll();
		addNewLine.undoAction();
		deleteOldLine.undoAction();
		controller.selectObject(oldConnector);
	}
}
