package umlaction;

import java.util.Scanner;
import java.util.Vector;

import java.util.Collections;
import juml.Controller;
import umlobject.Aggregation;
import umlobject.Association;
import umlobject.Composition;
import umlobject.Dependency;
import umlobject.Generalization;
import umlobject.Relationship;
import umlobject.UMLConnector;
import umlobject.UMLNode;
import umlobject.UMLObject;

/*
 * Action object class for Changing the direction of a Relationship.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.3
 */
public class ChangeRelationshipDirection extends UMLAction {
	DeleteUMLConnector deleteOldLine;
	AddUMLConnector addNewLine;
	Controller controller;
	UMLObject oldConnector;
	UMLObject newConnector;


	public ChangeRelationshipDirection(Relationship original, Controller c) {
		controller = c;
		controller.deselectAll();
		oldConnector = original;
		// so that the variable is initialized, we will start by assuming it is an Aggregation
		RelationshipType thisRelationship = RelationshipType.AGGREGATION;
		if (original instanceof Aggregation) {
			thisRelationship = RelationshipType.AGGREGATION;
		} else if (original instanceof Association) {
			thisRelationship = RelationshipType.ASSOCIATION;
		} else if (original instanceof Composition) {
			thisRelationship = RelationshipType.COMPOSITION;
		} else if (original instanceof Dependency) {
			thisRelationship = RelationshipType.DEPENDENCY;
		} else if (original instanceof Generalization) {
			thisRelationship = RelationshipType.GENERALIZATION;
		}
		Vector<UMLNode> allNodes = new Vector<UMLNode>();
		allNodes.add(original.start);
		allNodes.add(original.stop);
		Collections.reverse(original.getPivots());
		String newRelationshipSaveString = ((UMLConnector) original).saveAsString(allNodes);
		newRelationshipSaveString += original.saveAsString();
		// reverse order of nodes so that the new connector will be reversed
		allNodes.clear();
		allNodes.add(original.stop);
		allNodes.add(original.start);
		deleteOldLine = new DeleteUMLConnector(original, c);
		UMLConnector newConnector = null;
		Scanner input = new Scanner(newRelationshipSaveString);
		if (thisRelationship == RelationshipType.AGGREGATION) {
			newConnector = new Aggregation(input, allNodes, c);
		} else if (thisRelationship == RelationshipType.ASSOCIATION) {
			newConnector = new Association(input, allNodes, c);
		} else if (thisRelationship == RelationshipType.COMPOSITION) {
			newConnector = new Composition(input, allNodes, c);
		} else if (thisRelationship == RelationshipType.DEPENDENCY) {
			newConnector = new Dependency(input, allNodes, c);
		} else if (thisRelationship == RelationshipType.GENERALIZATION) {
			newConnector = new Generalization(input, allNodes, c);
		}
		this.newConnector = newConnector;
		addNewLine = new AddUMLConnector(newConnector, c);
		controller.selectObject(newConnector);
	}

	public void doAction() {
		controller.deselectAll();
		deleteOldLine.doAction();
		addNewLine.doAction();
		controller.selectObject(newConnector);
	}

	public void undoAction() {
		controller.deselectAll();
		addNewLine.undoAction();
		deleteOldLine.undoAction();
		controller.selectObject(oldConnector);
	}
}
