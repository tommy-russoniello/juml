package juml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;
import javafx.collections.*;
import java.util.Collections;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.Node;

import umlobject.*;
import umlaction.*;

import static org.junit.Assert.*;
import org.junit.*;

/**
 * Class for testing the program.
 * @author Samuel Carroll
 * @author Torrance Graham
 * @author Quinn Minnich
 * @author Thomas Russoniello
 * @version 0.3
 * @since 0.2
 */
public class Tests {

	/**
	 * The pane.
	 */
	Pane pane;

	/**
	 * The standard controller.
	 */
	Controller juml;

	/**
	 * Three points.
	 */
	Point p1, p2, p3, p4, p5, p6;

	/**
	 * Three classboxes.
	 */
	ClassBox cb1, cb2, cb3;

	/**
	 * Three classboxes.
	 */
	Note n1, n2, n3;

	/**
	 * Six UMLConnectors.
	 */
	UMLConnector c1, c2, c3, c4, c5, c6;

	/**
	 * Sets up the pane and places the objects on it.
	 */
	@Before
	public void setUp() {
		//Mimics controller
		pane = new Pane();
		juml = new Controller();
		juml.setPane(pane);

		p1 = new Point (1,2);
		p2 = new Point (2,3);
		p3 = new Point (3,4);
		p4 = new Point (10,11);
		p5 = new Point (11,12);
		p6 = new Point (12,13);

		cb1 = new ClassBox(4,5);
		cb2 = new ClassBox(5,6);
		cb3 = new ClassBox(6,7);

		n1 = new Note (7,8);
		n2 = new Note (8,9);
		n3 = new Note (9,10);
	}

/**********************************************************************************/
// Adding Object Tests

	/**
	 * Adds points to the pane.
	 */
	@Test
	public void addPoints() {
		juml.addObjects(p1, p2, p3);

		assertEquals(3, pane.getChildren().size());
		assertMap(juml.NODES, p1, p2, p3);

		assertStackSizes(juml, 3, 0);
	}

	/**
	 * Adds class boxes to the pane.
	 */
	@Test
	public void addClassBoxes() {
		juml.addObjects(cb1, cb2, cb3);

		assertEquals(3, pane.getChildren().size());
		assertMap(juml.NODES, cb1, cb2, cb3);

		assertStackSizes(juml, 3, 0);
	}

	/**
	 * Adds Notes to the pane.
	 */
	@Test
	public void addNotes() {
		juml.addObjects(n1, n2, n3);

		assertEquals(3, pane.getChildren().size());
		assertMap(juml.NODES, n1, n2, n3);

		assertStackSizes(juml, 3, 0);
	}

//----------------------------------------------------------------------------------//
// Segment Tests

	/**
	 * Adds segments between points.
	 */
	@Test
	public void addSegmentsBetweenPoints() {
		juml.addObjects(p1, p2, p3);

		c1 = new Segment(p1, p2);
		c2 = new Segment(p2, p3);
		c3 = new Segment(p1, p3);

		juml.addObjects(c1, c2, c3);

		assertEquals(6, pane.getChildren().size());
		assertMap(juml.CONNECTORS, c1, c2, c3);
		assertConnections(p1, c1, c3);
		assertConnections(p2, c1, c2);
		assertConnections(p3, c2, c3);

		assertStackSizes(juml, 6, 0);
	}

	/**
	 * Adds segments between notes.
	 */
	@Test
	public void addSegmentsBetweenNotes() {
		juml.addObjects(n1, n2, n3);

		c1 = new Segment(n1, n2);
		c2 = new Segment(n2, n3);
		c3 = new Segment(n1, n3);

		juml.addObjects(c1, c2, c3);

		assertEquals(6, pane.getChildren().size());
		assertMap(juml.CONNECTORS, c1, c2, c3);
		assertConnections(n1, c1, c3);
		assertConnections(n2, c1, c2);
		assertConnections(n3, c2, c3);

		assertStackSizes(juml, 6, 0);
	}

	/**
	 * Adds segments between class boxes.
	 */
	@Test
  	public void addSegmentsBetweenClassBoxes() {
		juml.addObjects(cb1, cb2, cb3);

		c1 = new Segment(cb1, cb2);
		c2 = new Segment(cb2, cb3);
		c3 = new Segment(cb1, cb3);

		juml.addObjects(c1, c2, c3);

		assertMap(juml.CONNECTORS, c1, c2, c3);

		assertEquals(6, pane.getChildren().size());
		assertConnections(cb1, c1, c3);
		assertConnections(cb2, c1, c2);
		assertConnections(cb3, c2, c3);

		assertStackSizes(juml, 6, 0);
  }

	/**
	 * Adds segments between points and class boxes.
	 */
	@Test
	public void addSegmentsBetweenPointsAndClassBoxes() {
		juml.addObjects(p1, p2, cb1, cb2);

		c1 = new Segment (p1, p2);
		c2 = new Segment (p1, cb1);
		c3 = new Segment (p1, cb2);
		c4 = new Segment (p2, cb1);
		c5 = new Segment (p2, cb2);
		c6 = new Segment (cb1, cb2);

		juml.addObjects(c1, c2, c3, c4, c5, c6);

		assertEquals(10, pane.getChildren().size());

		assertMap(juml.CONNECTORS, c1, c2, c3, c4, c5, c6);

		assertConnections(p1, c1, c2, c3);
		assertConnections(p2, c1, c4, c5);
		assertConnections(cb1, c2, c4, c6);
		assertConnections(cb2, c3, c5, c6);

		assertStackSizes(juml, 10, 0);
	}

	/**
	 * Adds segments between points and notes.
	 */
	@Test
	public void addSegmentsBetweenPointsAndNotes() {
		juml.addObjects(p1, p2, n1, n2);

		c1 = new Segment (p1, p2);
		c2 = new Segment (p1, n1);
		c3 = new Segment (p1, n2);
		c4 = new Segment (p2, n1);
		c5 = new Segment (p2, n2);
		c6 = new Segment (n1, n2);

		juml.addObjects(c1, c2, c3, c4, c5, c6);

		assertEquals(10, pane.getChildren().size());

		assertMap(juml.CONNECTORS, c1, c2, c3, c4, c5, c6);

		assertConnections(p1, c1, c2, c3);
		assertConnections(p2, c1, c4, c5);
		assertConnections(n1, c2, c4, c6);
		assertConnections(n2, c3, c5, c6);

		assertStackSizes(juml, 10, 0);
	}

	/**
	 * Adds segments between notes and class boxes.
	 */
	@Test
	public void addSegmentsBetweenNotesAndClassBoxes() {
		juml.addObjects(n1, n2, cb1, cb2);

		c1 = new Segment (n1, n2);
		c2 = new Segment (n1, cb1);
		c3 = new Segment (n1, cb2);
		c4 = new Segment (n2, cb1);
		c5 = new Segment (n2, cb2);
		c6 = new Segment (cb1, cb2);

		juml.addObjects(c1, c2, c3, c4, c5, c6);

		assertEquals(10, pane.getChildren().size());

		assertMap(juml.CONNECTORS, c1, c2, c3, c4, c5, c6);

		assertConnections(n1, c1, c2, c3);
		assertConnections(n2, c1, c4, c5);
		assertConnections(cb1, c2, c4, c6);
		assertConnections(cb2, c3, c5, c6);

		assertStackSizes(juml, 10, 0);
	}

//----------------------------------------------------------------------------------//
// Relationship Tests

	/**
	 * Adds relationships between points.
	 */
	@Test
	public void addRelationshipsBetweenPoints() {
		juml.addObjects(p1, p2, p3);

		c1 = new Dependency(p1, p2);
		c2 = new Composition(p2, p3);
		c3 = new Generalization(p1, p3);

		juml.addObjects(c1, c2, c3);

		assertEquals(6, pane.getChildren().size());
		assertMap(juml.CONNECTORS, c1, c2, c3);
		assertConnections(p1, c1, c3);
		assertConnections(p2, c1, c2);
		assertConnections(p3, c2, c3);

		assertStackSizes(juml, 6, 0);
	}

	/**
	 * Adds relationships between notes.
	 */
	@Test
	public void addRelationshipsBetweenNotes() {
		juml.addObjects(n1, n2, n3);

		c1 = new Dependency(n1, n2);
		c2 = new Composition(n2, n3);
		c3 = new Generalization(n1, n3);

		juml.addObjects(c1, c2, c3);

		assertEquals(6, pane.getChildren().size());
		assertMap(juml.CONNECTORS, c1, c2, c3);
		assertConnections(n1, c1, c3);
		assertConnections(n2, c1, c2);
		assertConnections(n3, c2, c3);

		assertStackSizes(juml, 6, 0);
	}

	/**
	 * Adds relationships between class boxes.
	 */
	@Test
  	public void addRelationshipsBetweenClassBoxes() {
		juml.addObjects(cb1, cb2, cb3);

		c1 = new Dependency(cb1, cb2);
		c2 = new Composition(cb2, cb3);
		c3 = new Generalization(cb1, cb3);

		juml.addObjects(c1, c2, c3);

		assertMap(juml.CONNECTORS, c1, c2, c3);

		assertEquals(6, pane.getChildren().size());
		assertConnections(cb1, c1, c3);
		assertConnections(cb2, c1, c2);
		assertConnections(cb3, c2, c3);

		assertStackSizes(juml, 6, 0);
  }

	/**
	 * Adds relationships between points and class boxes.
	 */
	@Test
	public void addRelationshipsBetweenPointsAndClassBoxes() {
		juml.addObjects(p1, p2, cb1, cb2);

		c1 = new Association (p1, p2);
		c2 = new Dependency (p1, cb1);
		c3 = new Aggregation (p1, cb2);
		c4 = new Composition (p2, cb1);
		c5 = new Generalization (p2, cb2);
		c6 = new Generalization (cb1, cb2);

		juml.addObjects(c1, c2, c3, c4, c5, c6);

		assertEquals(10, pane.getChildren().size());

		assertMap(juml.CONNECTORS, c1, c2, c3, c4, c5, c6);

		assertConnections(p1, c1, c2, c3);
		assertConnections(p2, c1, c4, c5);
		assertConnections(cb1, c2, c4, c6);
		assertConnections(cb2, c3, c5, c6);

		assertStackSizes(juml, 10, 0);
	}

	/**
	 * Adds relationships between points and notes.
	 */
	@Test
	public void addRelationshipsBetweenPointsAndNotes() {
		juml.addObjects(p1, p2, n1, n2);

		c1 = new Association (p1, p2);
		c2 = new Dependency (p1, n1);
		c3 = new Aggregation (p1, n2);
		c4 = new Composition (p2, n1);
		c5 = new Generalization (p2, n2);
		c6 = new Generalization (n1, n2);

		juml.addObjects(c1, c2, c3, c4, c5, c6);

		assertEquals(10, pane.getChildren().size());

		assertMap(juml.CONNECTORS, c1, c2, c3, c4, c5, c6);

		assertConnections(p1, c1, c2, c3);
		assertConnections(p2, c1, c4, c5);
		assertConnections(n1, c2, c4, c6);
		assertConnections(n2, c3, c5, c6);

		assertStackSizes(juml, 10, 0);
	}

	/**
	 * Adds relationships between notes and class boxes.
	 */
	@Test
	public void addRelationshipsBetweenNotesAndClassBoxes() {
		juml.addObjects(n1, n2, cb1, cb2);

		c1 = new Association (n1, n2);
		c2 = new Dependency (n1, cb1);
		c3 = new Aggregation (n1, cb2);
		c4 = new Composition (n2, cb1);
		c5 = new Generalization (n2, cb2);
		c6 = new Generalization (cb1, cb2);

		juml.addObjects(c1, c2, c3, c4, c5, c6);

		assertEquals(10, pane.getChildren().size());

		assertMap(juml.CONNECTORS, c1, c2, c3, c4, c5, c6);

		assertConnections(n1, c1, c2, c3);
		assertConnections(n2, c1, c4, c5);
		assertConnections(cb1, c2, c4, c6);
		assertConnections(cb2, c3, c5, c6);

		assertStackSizes(juml, 10, 0);
	}

/**********************************************************************************/
// Delete Object Tests

  /**
   * Delete points.
   */
  @Test
  public void deletePoints(){
		juml.addObjects(p1, p2);

		assertEquals(2, pane.getChildren().size());
		assertMap(juml.NODES, p1, p2);

		juml.deleteObjects(p1);

		assertEquals(1, pane.getChildren().size());
		assertNull(juml.NODES.get(p1.getModel()));
		assertMap(juml.NODES, p2);

		assertStackSizes(juml, 3, 0);
  }

	/**
   * Delete notes.
   */
  @Test
  public void deleteNotes(){
		juml.addObjects(n1, n2);

		assertEquals(2, pane.getChildren().size());
		assertMap(juml.NODES, n1, n2);

		juml.deleteObjects(n1);

		assertEquals(1, pane.getChildren().size());
		assertNull(juml.NODES.get(n1.getModel()));
		assertMap(juml.NODES, n2);

		assertStackSizes(juml, 3, 0);
  }

 	/**
	  * Delete class boxes.
	  */
	 @Test
 	public void deleteClassBoxes(){
		juml.addObjects(cb1, cb2);

		assertEquals(2, pane.getChildren().size());
		assertMap(juml.NODES, cb1, cb2);

 		juml.deleteObjects(cb1);

 		assertEquals(1, pane.getChildren().size());
		assertNull(juml.NODES.get(cb1.getModel()));
		assertMap(juml.NODES, cb2);

		assertStackSizes(juml, 3, 0);
 	}

	/**
	 * Delete segments.
	 */
	@Test
	public void deleteSegments() {
		juml.addObjects(p1, p2);

		c1 = new Segment(p2, p1);
		juml.addObjects(c1);
		assertEquals(3, pane.getChildren().size());
		// test that each point has added one connection
		assertConnections(p1, c1);
		assertConnections(p2, c1);

		assertMap(juml.CONNECTORS, c1);

		juml.deleteObjects(c1);

		assertEquals(2, pane.getChildren().size());
		// see if points removed connection
		assertConnections(p1);
		assertConnections(p2);

		assertNull(juml.CONNECTORS.get(c1.getModel()));
		assertMap(juml.CONNECTORS);

		assertStackSizes(juml, 4, 0);
	}

	/**
	 * Delete relationships.
	 */
	@Test
	public void deleteRelationships() {
		juml.addObjects(p1, p2);

		c1 = new Association(p2, p1);
		c2 = new Dependency(p2, p1);
		c3 = new Aggregation(p2, p1);
		c4 = new Composition(p2, p1);
		c5 = new Generalization(p2, p1);
		juml.addObjects(c1, c2, c3, c4, c5);
		assertEquals(7, pane.getChildren().size());
		// test that each point has added one connection
		assertConnections(p1, c1, c2, c3, c4, c5);
		assertConnections(p2, c1, c2, c3, c4, c5);

		assertMap(juml.CONNECTORS, c1, c2, c3, c4, c5);

		juml.deleteObjects(c1, c2, c3, c4, c5);

		assertEquals(2, pane.getChildren().size());
		// see if points removed connection
		assertConnections(p1);
		assertConnections(p2);

		assertNull(juml.CONNECTORS.get(c1.getModel()));
		assertNull(juml.CONNECTORS.get(c2.getModel()));
		assertNull(juml.CONNECTORS.get(c3.getModel()));
		assertNull(juml.CONNECTORS.get(c4.getModel()));
		assertNull(juml.CONNECTORS.get(c5.getModel()));
		assertMap(juml.CONNECTORS);

		assertStackSizes(juml, 12, 0);
	}

	/**
	 * Delete points with segments.
	 */
	@Test
	public void deletePointsWithSegments() {
		assertTrue(pane.getChildren().isEmpty());

		juml.addObjects(p1, p2, p3);

		c1 = new Segment(p1, p2);
		c2 = new Segment(p2, p3);

		juml.addObjects(c1, c2);

		assertMap(juml.CONNECTORS, c1, c2);
		assertEquals(5, pane.getChildren().size());
		assertConnections(p1, c1);
		assertConnections(p2, c1, c2);
		assertConnections(p3, c2);

		//this should delete the connector too
		juml.deleteObjects(p2);

		// see if both point removed the connectors
		assertMap(juml.CONNECTORS);
		assertNull(juml.CONNECTORS.get(c1.getModel()));
		assertNull(juml.CONNECTORS.get(c2.getModel()));
		assertEquals(2, pane.getChildren().size());
		assertConnections(p1);
		assertConnections(p2);
		assertConnections(p3);

		assertStackSizes(juml, 6, 0);
	}

	/**
	 * Delete points with relationships.
	 */
	@Test
	public void deletePointsWithRelationships() {
		assertTrue(pane.getChildren().isEmpty());

		juml.addObjects(p1, p2, p3, p4, p5, p6);

		c1 = new Association(p1, p2);
		c2 = new Dependency(p1, p3);
		c3 = new Aggregation(p1, p4);
		c4 = new Composition(p1, p5);
		c5 = new Generalization(p1, p6);

		juml.addObjects(c1, c2, c3, c4, c5);

		assertMap(juml.CONNECTORS, c1, c2, c3, c4, c5);
		assertEquals(11, pane.getChildren().size());
		assertConnections(p1, c1, c2, c3, c4, c5);
		assertConnections(p2, c1);
		assertConnections(p3, c2);
		assertConnections(p4, c3);
		assertConnections(p5, c4);
		assertConnections(p6, c5);

		//this should delete the relationships too
		juml.deleteObjects(p1);

		// see if all points removed the connectors
		assertMap(juml.CONNECTORS);
		assertNull(juml.CONNECTORS.get(c1.getModel()));
		assertNull(juml.CONNECTORS.get(c2.getModel()));
		assertNull(juml.CONNECTORS.get(c3.getModel()));
		assertNull(juml.CONNECTORS.get(c4.getModel()));
		assertNull(juml.CONNECTORS.get(c5.getModel()));
		assertEquals(5, pane.getChildren().size());
		assertConnections(p1);
		assertConnections(p2);
		assertConnections(p3);
		assertConnections(p4);
		assertConnections(p5);
		assertConnections(p6);

		assertStackSizes(juml, 12, 0);
	}

	/**
	 * Gets the points.
	 */
	@Test
	public void getPoints() {
		juml.addObjects(p1, p2, p3);

		c1 = new Segment(p1, p2);
		c2 = new Segment(p2, p3);
		c3 = new Segment(p1, p3);

		juml.addObjects(c1, c2, c3);

		assertEquals(p1, juml.getObject(p1.getModel()));
		assertEquals(p2, juml.getObject(p2.getModel()));
		assertEquals(p3, juml.getObject(p3.getModel()));

		assertStackSizes(juml, 6, 0);
	}

	/**
	 * Gets the notes.
	 */
	@Test
	public void getNotes() {
		juml.addObjects(n1, n2, n3);

		c1 = new Segment(n1, n2);
		c2 = new Segment(n2, n3);
		c3 = new Segment(n1, n3);

		juml.addObjects(c1, c2, c3);

		assertEquals(n1, juml.getObject(n1.getModel()));
		assertEquals(n2, juml.getObject(n2.getModel()));
		assertEquals(n3, juml.getObject(n3.getModel()));

		assertEquals(n1, juml.getObject(n1.text));
		assertEquals(n2, juml.getObject(n2.text));
		assertEquals(n3, juml.getObject(n3.text));

		assertStackSizes(juml, 6, 0);
	}

	/**
	 * Gets the class boxes.
	 */
	@Test
	public void getClassBoxes() {
		juml.addObjects(cb1, cb2, cb3);

		c1 = new Segment(cb1, cb2);
		c2 = new Segment(cb2, cb3);
		c3 = new Segment(cb1, cb3);

		juml.addObjects(c1, c2, c3);

		ObservableList<Node> children = ((VBox) cb2.getModel()).getChildren();

		for (Node child : children) {
			assertEquals(cb2, juml.getObject(child));
		}

		assertEquals(cb2, juml.getObject(cb2.getModel()));

		assertStackSizes(juml, 6, 0);
	}

	/**
	 * Gets segments.
	 */
	@Test
	public void getSegments() {
		//set up points and segments
		juml.addObjects(p1, p2, p3);

		c1 = new Segment(p1, p2);
		c2 = new Segment(p2, p3);
		c3 = new Segment(p1, p3);

		juml.addObjects(c1, c2, c3);

		assertEquals(c1, juml.getObject(c1.getModel()));
		assertEquals(c2, juml.getObject(c2.getModel()));
		assertEquals(c3, juml.getObject(c3.getModel()));

		assertStackSizes(juml, 6, 0);
	}

	/**
	 * Get relationships.
	 */
	 @Test
	 public void getRelationships() {
		juml.addObjects(p1, p2, p3, p4, p5, p6);

		Vector<UMLConnector> relationships = new Vector<>();
	  relationships.add(c1 = new Association(p1, p2));
	  relationships.add(c2 = new Dependency(p2, p3));
	  relationships.add(c3 = new Aggregation(p3, p4));
	  relationships.add(c4 = new Composition(p4, p5));
	  relationships.add(c5 = new Generalization(p5, p6));

	  juml.addObjects(c1, c2, c3, c4, c5);

	  ObservableList<Node> children;

		for (UMLConnector relationship : relationships) {
			children = ((Group) relationship.getModel()).getChildren();

			for (Node child : children) {
	 			assertEquals(relationship, juml.getObject(child));
	 		}

	 		assertEquals(relationship, juml.getObject(relationship.getModel()));
		}

		assertStackSizes(juml, 11, 0);
	}

/**********************************************************************************/
// Action Tests

	/**
	 * Test action for adding segments.
	 */
	@Test
	public void AddUMLConnectorSegment() {
		juml.addObjects(p1, p2);
		c1 = new Segment(p1, p2);
		AddUMLConnector action = new AddUMLConnector(c1, juml);

		assertEquals(3, pane.getChildren().size());
		assertMap(juml.NODES, p1, p2);
		assertMap(juml.CONNECTORS, c1);
		assertEquals(c1.getStart(), p1);
		assertEquals(c1.getStop(), p2);

		action.undoAction();

		assertEquals(2, pane.getChildren().size());
		assertMap(juml.NODES, p1, p2);
		assertMap(juml.CONNECTORS);
		assertEquals(c1.getStart(), p1);
		assertEquals(c1.getStop(), p2);

		action.doAction();

		assertEquals(3, pane.getChildren().size());
		assertMap(juml.NODES, p1, p2);
		assertMap(juml.CONNECTORS, c1);
		assertEquals(c1.getStart(), p1);
		assertEquals(c1.getStop(), p2);
	}

	/**
	 * Test action for adding relationships.
	 */
	@Test
	public void AddUMLConnectorRelationship() {
		juml.addObjects(p1, p2);
		c1 = new Association(p1, p2);
		AddUMLConnector action = new AddUMLConnector(c1, juml);

		assertEquals(3, pane.getChildren().size());
		assertMap(juml.NODES, p1, p2);
		assertMap(juml.CONNECTORS, c1);
		assertEquals(c1.getStart(), p1);
		assertEquals(c1.getStop(), p2);

		action.undoAction();

		assertEquals(2, pane.getChildren().size());
		assertMap(juml.NODES, p1, p2);
		assertMap(juml.CONNECTORS);
		assertEquals(c1.getStart(), p1);
		assertEquals(c1.getStop(), p2);

		action.doAction();

		assertEquals(3, pane.getChildren().size());
		assertMap(juml.NODES, p1, p2);
		assertMap(juml.CONNECTORS, c1);
		assertEquals(c1.getStart(), p1);
		assertEquals(c1.getStop(), p2);
	}

	/**
	 * Test action for adding UMLNodes.
	 */
	@Test
	public void addUMLNode() {
		AddUMLNode action = new AddUMLNode(p1, juml);
		juml.ACTIONS.push(action);

		assertEquals(1, pane.getChildren().size());
		assertMap(juml.NODES, p1);

		assertStackSizes(juml, 1, 0);
		assertEquals(juml.ACTIONS.peek(), action);
		assertEquals(p1.getConnections().size(), 0);

		juml.undo();

		assertStackSizes(juml, 0, 1);
		assertEquals(juml.UNDONE_ACTIONS.peek(), action);
		assertEquals(p1.getConnections().size(), 0);

		juml.redo();

		assertStackSizes(juml, 1, 0);
		assertEquals(juml.ACTIONS.peek(), action);
		assertEquals(p1.getConnections().size(), 0);
	}

	/**
	 * Test action for changing class box attributes.
	 */
	@Test
	public void changeClassBoxAttributes() {
		assertEquals("attributes", cb1.getAttributes());
		ChangeClassBoxAttributes action = new ChangeClassBoxAttributes(cb1, "new text");
		assertEquals("new text", cb1.getAttributes());
		action.undoAction();
		assertEquals("attributes", cb1.getAttributes());
		action.doAction();
		assertEquals("new text", cb1.getAttributes());
	}

	/**
	 * Test action for changing class box methods.
	 */
	@Test
	public void changeClassBoxMethods() {
		assertEquals("methods", cb1.getMethods());
		ChangeClassBoxMethods action = new ChangeClassBoxMethods(cb1, "new text");
		assertEquals("new text", cb1.getMethods());
		action.undoAction();
		assertEquals("methods", cb1.getMethods());
		action.doAction();
		assertEquals("new text", cb1.getMethods());
	}

	/**
	 * Test action for changing class box name.
	 */
	@Test
	public void changeClassBoxName() {
		assertEquals("class name", cb1.getName());
		ChangeClassBoxName action = new ChangeClassBoxName(cb1, "new text");
		assertEquals("new text", cb1.getName());
		action.undoAction();
		assertEquals("class name", cb1.getName());
		action.doAction();
		assertEquals("new text", cb1.getName());
	}

	/**
	 * Test action for changing note text.
	 */
	@Test
	public void changeNoteText() {
		assertEquals("", n1.getText());
		ChangeNoteText action = new ChangeNoteText(n1, "new text");
		assertEquals("new text", n1.getText());
		action.undoAction();
		assertEquals("", n1.getText());
		action.doAction();
		assertEquals("new text", n1.getText());
	}

	/**
	 * Test action for changing relationship direction.
	 */
	@Test
	public void changeRelationshipDirection() {
		p1 = new Point(5, 5);
		p2 = new Point(100, 5);
		Association c = new Association(p1, p2);
		juml.addObjects(p1, p2, c1);

		new SplitLine(c, c.getSegments().get(c.getSegments().size() - 1), 23, 6, juml);
		new SplitLine(c, c.getSegments().get(c.getSegments().size() - 1), 46, 6, juml);
		new SplitLine(c, c.getSegments().get(c.getSegments().size() - 1), 67, 7, juml);

		assertEquals(p1, c.getStart());
		assertEquals(p2, c.getStop());

		List<Segment> original = c.getSegments();

		ChangeRelationshipDirection action = new ChangeRelationshipDirection(c, juml);

		Relationship updatedRelationship = (Relationship) action.newConnector;
		List<Segment> updated = updatedRelationship.getSegments();
		Collections.reverse(updated);

		assertEquals(p2, updatedRelationship.getStart());
		assertEquals(p1, updatedRelationship.getStop());

		// TODO: figure out how to test individual segments here
		// assertEquals(original.size(), updated.size());
		// for (int i = 0; i < original.size(); i++) {
		// 	assertEquals(original.get(i), updated.get(i));
		// }
	}

	/**
	 * Test action for changing relationship end text.
	 */
	@Test
	public void changeRelationshipEndText() {
		Association c = new Association(p1, p2);
		juml.addObjects(p1, p2, c);

		assertEquals("", c.getEndText());
		assertStackSizes(juml, 3, 0);
		assertFalse(c.endTextVisible());

		juml.ACTIONS.push(new ChangeRelationshipEndText(c, "nu", juml));

		assertEquals("nu", c.getEndText());
		assertStackSizes(juml, 4, 0);
		assertTrue(c.endTextVisible());

		juml.undo();

		assertEquals("", c.getEndText());
		assertStackSizes(juml, 3, 1);
		assertFalse(c.endTextVisible());

		juml.redo();

		assertEquals("nu", c.getEndText());
		assertStackSizes(juml, 4, 0);
		assertTrue(c.endTextVisible());

		juml.ACTIONS.push(new ChangeRelationshipEndText(c, "too long", juml));

		assertEquals("nu", c.getEndText());
		// TODO: figure out why this fails -> automatic popping of stack within action class doesn't work
		// assertStackSizes(juml, 4, 0);
		assertTrue(c.endTextVisible());

		juml.ACTIONS.push(new ChangeRelationshipEndText(c, "", juml));

		assertEquals("", c.getEndText());
		// assertStackSizes(juml, 5, 0);
		assertFalse(c.endTextVisible());
	}

	/**
	 * Test action for changing relationship start text.
	 */
	@Test
	public void changeRelationshipStartText() {
		Association c = new Association(p1, p2);
		juml.addObjects(p1, p2, c);

		assertEquals("", c.getStartText());
		assertStackSizes(juml, 3, 0);
		assertFalse(c.startTextVisible());

		juml.ACTIONS.push(new ChangeRelationshipStartText(c, "nu", juml));

		assertEquals("nu", c.getStartText());
		assertStackSizes(juml, 4, 0);
		assertTrue(c.startTextVisible());

		juml.undo();

		assertEquals("", c.getStartText());
		assertStackSizes(juml, 3, 1);
		assertFalse(c.startTextVisible());

		juml.redo();

		assertEquals("nu", c.getStartText());
		assertStackSizes(juml, 4, 0);
		assertTrue(c.startTextVisible());

		juml.ACTIONS.push(new ChangeRelationshipStartText(c, "too long", juml));

		assertEquals("nu", c.getStartText());
		// TODO: figure out why this fails -> automatic popping of stack within action class doesn't work
		// assertStackSizes(juml, 4, 0);
		assertTrue(c.startTextVisible());

		juml.ACTIONS.push(new ChangeRelationshipStartText(c, "", juml));

		assertEquals("", c.getStartText());
		// assertStackSizes(juml, 5, 0);
		assertFalse(c.startTextVisible());
	}


	/**
	 * Test action for changing relationship type.
	 */
	@Test
	public void changeRelationshipType() {
		Relationship c = new Association(p1, p2);
		juml.addObjects(p1, p2, c);

		ChangeRelationshipType action = new ChangeRelationshipType(c, "Dependency", juml);
		juml.ACTIONS.push(action);
		Relationship updated = (Relationship) action.newConnector;
		assertTrue(updated != c);
		assertEquals(updated, juml.getObject(updated.getModel()));
		assertTrue(updated instanceof Dependency);
		assertStackSizes(juml, 4, 0);

		c = updated;

		action = new ChangeRelationshipType(c, "Aggregation", juml);
		juml.ACTIONS.push(action);
		updated = (Relationship) action.newConnector;
		assertTrue(updated != c);
		assertEquals(updated, juml.getObject(updated.getModel()));
		assertTrue(updated instanceof Aggregation);
		assertStackSizes(juml, 5, 0);

		c = updated;

		action = new ChangeRelationshipType(c, "Composition", juml);
		juml.ACTIONS.push(action);
		updated = (Relationship) action.newConnector;
		assertTrue(updated != c);
		assertEquals(updated, juml.getObject(updated.getModel()));
		assertTrue(updated instanceof Composition);
		assertStackSizes(juml, 6, 0);

		c = updated;

		action = new ChangeRelationshipType(c, "Generalization", juml);
		juml.ACTIONS.push(action);
		updated = (Relationship) action.newConnector;
		assertTrue(updated != c);
		assertEquals(updated, juml.getObject(updated.getModel()));
		assertTrue(updated instanceof Generalization);
		assertStackSizes(juml, 7, 0);

		c = updated;
		juml.undo();

		updated = (Relationship) action.oldConnector;
		assertTrue(updated != c);
		assertEquals(updated, juml.getObject(updated.getModel()));
		assertTrue(updated instanceof Composition);
		assertStackSizes(juml, 6, 1);

		c = updated;
		juml.redo();

		updated = (Relationship) action.newConnector;
		assertTrue(updated != c);
		assertEquals(updated, juml.getObject(updated.getModel()));
		assertTrue(updated instanceof Generalization);
		assertStackSizes(juml, 7, 0);
	}

	/**
	 * Test action for deleting a pivot.
	 */
	@Test
	public void deletePivot() {
		p1 = new Point(5, 5);
		p2 = new Point(100, 5);
		Association c = new Association(p1, p2);
		juml.addObjects(p1, p2, c1);

		new SplitLine(c, c.getSegments().get(c.getSegments().size() - 1), 23, 6, juml);
		new SplitLine(c, c.getSegments().get(c.getSegments().size() - 1), 46, 6, juml);
		new SplitLine(c, c.getSegments().get(c.getSegments().size() - 1), 67, 7, juml);

		assertEquals(p1, c.getStart());
		assertEquals(p2, c.getStop());
		assertEquals(3, c.getPivots().size());

		Pivot toDelete = c.getPivots().get(0);

		DeletePivot action = new DeletePivot(c, toDelete);
		assertEquals(2, c.getPivots().size());
		assertFalse(c.getPivots().contains(toDelete));
		for (UMLConnector segment : toDelete.getConnections()) {
			assertTrue(segment.getStart() != toDelete);
			assertTrue(segment.getStop() != toDelete);
		}

		action.undoAction();

		assertEquals(3, c.getPivots().size());
		assertTrue(c.getPivots().contains(toDelete));
		for (UMLConnector segment : toDelete.getConnections()) {
			assertTrue(segment.getStart() != toDelete || segment.getStop() != toDelete);
		}

		action.doAction();

		assertEquals(2, c.getPivots().size());
		assertFalse(c.getPivots().contains(toDelete));
		for (UMLConnector segment : toDelete.getConnections()) {
			assertTrue(segment.getStart() != toDelete);
			assertTrue(segment.getStop() != toDelete);
		}
	}

	/**
	 * Test action for deleting a segment.
	 */
	@Test
	public void deleteUMLConnectorSegment() {
		c1 = new Segment(p2, p1);
		juml.addObjects(p1, p2, c1);
		assertEquals(3, pane.getChildren().size());
		assertConnections(p1, c1);
		assertConnections(p2, c1);

		assertMap(juml.CONNECTORS, c1);

		DeleteUMLConnector action = new DeleteUMLConnector(c1, juml);

		assertEquals(2, pane.getChildren().size());
		assertConnections(p1);
		assertConnections(p2);
		assertNull(juml.CONNECTORS.get(c1.getModel()));
		assertMap(juml.CONNECTORS);

		action.undoAction();

		assertEquals(3, pane.getChildren().size());
		assertConnections(p1, c1);
		assertConnections(p2, c1);
		assertEquals(c1, juml.CONNECTORS.get(c1.getModel()));
		assertMap(juml.CONNECTORS, c1);

		action.doAction();

		assertEquals(2, pane.getChildren().size());
		assertConnections(p1);
		assertConnections(p2);
		assertNull(juml.CONNECTORS.get(c1.getModel()));
		assertMap(juml.CONNECTORS);
	}

	/**
	 * Test action for deleting a relationship.
	 */
	@Test
	public void deleteUMLConnectorRelationship() {
		p1 = new Point(5, 5);
		p2 = new Point(100, 5);
		Association c = new Association(p1, p2);
		juml.addObjects(p1, p2, c);

		new SplitLine(c, c.getSegments().get(c.getSegments().size() - 1), 23, 6, juml);
		new SplitLine(c, c.getSegments().get(c.getSegments().size() - 1), 46, 6, juml);
		new SplitLine(c, c.getSegments().get(c.getSegments().size() - 1), 67, 7, juml);

		assertEquals(3, pane.getChildren().size());
		assertConnections(p1, c);
		assertConnections(p2, c);

		assertMap(juml.CONNECTORS, c);

		DeleteUMLConnector action = new DeleteUMLConnector(c, juml);

		assertEquals(2, pane.getChildren().size());
		assertConnections(p1);
		assertConnections(p2);
		assertNull(juml.CONNECTORS.get(c.getModel()));
		assertMap(juml.CONNECTORS);

		action.undoAction();

		assertEquals(3, pane.getChildren().size());
		assertConnections(p1, c);
		assertConnections(p2, c);
		assertEquals(c, juml.CONNECTORS.get(c.getModel()));
		assertEquals(3, c.getPivots().size());
		assertEquals(4, c.getSegments().size());
		assertMap(juml.CONNECTORS, c);

		action.doAction();

		assertEquals(2, pane.getChildren().size());
		assertConnections(p1);
		assertConnections(p2);
		assertNull(juml.CONNECTORS.get(c.getModel()));
		assertMap(juml.CONNECTORS);
	}

	/**
	 * Test action for deleting a UMLNode.
	 */
	@Test
	public void deleteUMLNode() {
		assertTrue(pane.getChildren().isEmpty());

		juml.addObjects(p1, p2, p3, p4, p5, p6);

		c1 = new Association(p1, p2);
		c2 = new Dependency(p1, p3);
		c3 = new Aggregation(p1, p4);
		c4 = new Composition(p1, p5);
		c5 = new Generalization(p1, p6);

		juml.addObjects(c1, c2, c3, c4, c5);

		assertMap(juml.CONNECTORS, c1, c2, c3, c4, c5);
		assertEquals(11, pane.getChildren().size());
		assertConnections(p1, c1, c2, c3, c4, c5);
		assertConnections(p2, c1);
		assertConnections(p3, c2);
		assertConnections(p4, c3);
		assertConnections(p5, c4);
		assertConnections(p6, c5);

		DeleteUMLNode action = new DeleteUMLNode(p1, juml);

		assertMap(juml.CONNECTORS);
		assertNull(juml.CONNECTORS.get(c1.getModel()));
		assertNull(juml.CONNECTORS.get(c2.getModel()));
		assertNull(juml.CONNECTORS.get(c3.getModel()));
		assertNull(juml.CONNECTORS.get(c4.getModel()));
		assertNull(juml.CONNECTORS.get(c5.getModel()));
		assertEquals(5, pane.getChildren().size());
		assertConnections(p1);
		assertConnections(p2);
		assertConnections(p3);
		assertConnections(p4);
		assertConnections(p5);
		assertConnections(p6);

		action.undoAction();

		assertMap(juml.CONNECTORS, c1, c2, c3, c4, c5);
		assertEquals(11, pane.getChildren().size());
		assertConnections(p1, c1, c2, c3, c4, c5);
		assertConnections(p2, c1);
		assertConnections(p3, c2);
		assertConnections(p4, c3);
		assertConnections(p5, c4);
		assertConnections(p6, c5);

		action.doAction();

		assertMap(juml.CONNECTORS);
		assertNull(juml.CONNECTORS.get(c1.getModel()));
		assertNull(juml.CONNECTORS.get(c2.getModel()));
		assertNull(juml.CONNECTORS.get(c3.getModel()));
		assertNull(juml.CONNECTORS.get(c4.getModel()));
		assertNull(juml.CONNECTORS.get(c5.getModel()));
		assertEquals(5, pane.getChildren().size());
		assertConnections(p1);
		assertConnections(p2);
		assertConnections(p3);
		assertConnections(p4);
		assertConnections(p5);
		assertConnections(p6);
	}

	/**
	 * Test action for moving a UMLObject to the front of the pane.
	 */
	@Test
	public void moveToFront() {
		juml.addObjects(p1, p2, p3);

		assertEquals(p1.getModel(), pane.getChildren().get(0));
		assertEquals(p3.getModel(), pane.getChildren().get(pane.getChildren().size() - 1));

		MoveToFront action = new MoveToFront(juml, p1);

		assertEquals(p2.getModel(), pane.getChildren().get(0));
		assertEquals(p1.getModel(), pane.getChildren().get(pane.getChildren().size() - 1));

		action.undoAction();

		assertEquals(p1.getModel(), pane.getChildren().get(0));
		assertEquals(p3.getModel(), pane.getChildren().get(pane.getChildren().size() - 1));

		action.doAction();

		assertEquals(p2.getModel(), pane.getChildren().get(0));
		assertEquals(p1.getModel(), pane.getChildren().get(pane.getChildren().size() - 1));
	}

	/**
	 * Test action for moving a UMLObject to the back of the pane.
	 */
	@Test
	public void moveToBack() {
		juml.addObjects(p1, p2, p3);

		assertEquals(p1.getModel(), pane.getChildren().get(0));
		assertEquals(p3.getModel(), pane.getChildren().get(pane.getChildren().size() - 1));

		MoveToBack action = new MoveToBack(juml, p3);

		assertEquals(p3.getModel(), pane.getChildren().get(0));
		assertEquals(p2.getModel(), pane.getChildren().get(pane.getChildren().size() - 1));

		action.undoAction();

		assertEquals(p1.getModel(), pane.getChildren().get(0));
		assertEquals(p3.getModel(), pane.getChildren().get(pane.getChildren().size() - 1));

		action.doAction();

		assertEquals(p3.getModel(), pane.getChildren().get(0));
		assertEquals(p2.getModel(), pane.getChildren().get(pane.getChildren().size() - 1));
	}

	/**
	 * Test action for moving a UMLNode to new origin coordinates.
	 */
	@Test
	public void moveUMLNode() {
		p1 = new Point(10,10);
		p2 = new Point(100,10);
		p3 = new Point(10,100);
		c1 = new Segment(p1, p2);
		c2 = new Segment(p1, p3);

		juml.addObjects(p1, p2, p3, c1, c2);

		double initC1startX = ((Line) c1.getModel()).getStartX();
		double initC1startY = ((Line) c1.getModel()).getStartY();
		double initC2startX = ((Line) c2.getModel()).getStartX();
		double initC2startY = ((Line) c2.getModel()).getStartY();
		double initC1endX = ((Line) c1.getModel()).getStartX();
		double initC1endY = ((Line) c1.getModel()).getStartY();
		double initC2endX = ((Line) c2.getModel()).getStartX();
		double initC2endY = ((Line) c2.getModel()).getStartY();

		MoveUMLNode action = new MoveUMLNode(p1, 10, 50);

		assertEquals(10, p1.getOriginX(), .1);
		assertEquals(50, p1.getOriginY(), .1);
		assertTrue(initC1startX != ((Line) c1.getModel()).getStartX());
		assertTrue(initC1startY != ((Line) c1.getModel()).getStartY());
		assertEquals(initC2startX, ((Line) c2.getModel()).getStartX(), .1);
		assertTrue(initC2startY != ((Line) c2.getModel()).getStartY());
		assertTrue(initC1endX != ((Line) c1.getModel()).getStartX());
		assertTrue(initC1endY != ((Line) c1.getModel()).getStartY());
		assertEquals(initC2endX, ((Line) c2.getModel()).getStartX(), .1);
		assertTrue(initC2endY != ((Line) c2.getModel()).getStartY());

		action.undoAction();

		assertEquals(10, p1.getOriginX(), .1);
		assertEquals(10, p1.getOriginY(), .1);
		assertEquals(initC1startX, ((Line) c1.getModel()).getStartX(), .1);
		assertEquals(initC1startY, ((Line) c1.getModel()).getStartY(), .1);
		assertEquals(initC2startX, ((Line) c2.getModel()).getStartX(), .1);
		assertEquals(initC2startY, ((Line) c2.getModel()).getStartY(), .1);
		assertEquals(initC1endX, ((Line) c1.getModel()).getStartX(), .1);
		assertEquals(initC1endY, ((Line) c1.getModel()).getStartY(), .1);
		assertEquals(initC2endX, ((Line) c2.getModel()).getStartX(), .1);
		assertEquals(initC2endY, ((Line) c2.getModel()).getStartY(), .1);

		action.doAction();

		assertEquals(10, p1.getOriginX(), .1);
		assertEquals(50, p1.getOriginY(), .1);
		assertTrue(initC1startX != ((Line) c1.getModel()).getStartX());
		assertTrue(initC1startY != ((Line) c1.getModel()).getStartY());
		assertEquals(initC2startX, ((Line) c2.getModel()).getStartX(), .1);
		assertTrue(initC2startY != ((Line) c2.getModel()).getStartY());
		assertTrue(initC1endX != ((Line) c1.getModel()).getStartX());
		assertTrue(initC1endY != ((Line) c1.getModel()).getStartY());
		assertEquals(initC2endX, ((Line) c2.getModel()).getStartX(), .1);
		assertTrue(initC2endY != ((Line) c2.getModel()).getStartY());
	}

	/**
	 * Test action for splitting a relationship line into segments about pivots.
	 */
	@Test
	public void SplitLine() {
		p1 = new Point(5, 5);
		p2 = new Point(100, 5);
		Association c = new Association(p1, p2);
		juml.addObjects(p1, p2, c);

		assertEquals(3, pane.getChildren().size());
		assertConnections(p1, c);
		assertConnections(p2, c);

		Vector<SplitLine> actions = new Vector<> ();
		actions.add(new SplitLine(c, c.getSegments().get(c.getSegments().size() - 1), 23, 6, juml));
		actions.add(new SplitLine(c, c.getSegments().get(c.getSegments().size() - 1), 46, 6, juml));
		actions.add(new SplitLine(c, c.getSegments().get(c.getSegments().size() - 1), 67, 7, juml));

		assertEquals(3, pane.getChildren().size());
		assertConnections(p1, c);
		assertConnections(p2, c);
		assertEquals(3, c.getPivots().size());
		assertEquals(4, c.getSegments().size());
		int count = 0;
		for (SplitLine action : actions) {
			assertEquals(action.pivot, c.getPivots().get(count));
			assertTrue(((Group) c.getModel()).getChildren().contains(action.pivot.getModel()));
			assertEquals(action.newSegment, c.getSegments().get(count));
			assertTrue(((Group) c.getModel()).getChildren().contains(action.newSegment.getModel()));
			assertTrue(((Group) c.getModel()).getChildren().contains(action.splitSegment.getModel()));
			count++;
		}

		for (SplitLine action : actions) {
			action.undoAction();
		}

		assertEquals(3, pane.getChildren().size());
		assertConnections(p1, c);
		assertConnections(p2, c);
		assertEquals(0, c.getPivots().size());
		assertEquals(1, c.getSegments().size());
		count = 0;
		for (SplitLine action : actions) {
			assertFalse(c.getPivots().contains(action.pivot));
			assertFalse(((Group) c.getModel()).getChildren().contains(action.pivot.getModel()));
			assertFalse(c.getSegments().contains(action.newSegment));
			assertFalse(((Group) c.getModel()).getChildren().contains(action.newSegment.getModel()));
			count++;
		}

		for (SplitLine action : actions) {
			action.doAction();
		}

		assertEquals(3, pane.getChildren().size());
		assertConnections(p1, c);
		assertConnections(p2, c);
		assertEquals(3, c.getPivots().size());
		assertEquals(4, c.getSegments().size());
		count = 0;
		for (SplitLine action : actions) {
			assertEquals(action.pivot, c.getPivots().get(count));
			assertTrue(((Group) c.getModel()).getChildren().contains(action.pivot.getModel()));
			assertEquals(action.newSegment, c.getSegments().get(count));
			assertTrue(((Group) c.getModel()).getChildren().contains(action.newSegment.getModel()));
			assertTrue(((Group) c.getModel()).getChildren().contains(action.splitSegment.getModel()));
			count++;
		}
	}

/**********************************************************************************/

// ---------------------------------------------------------------------------------------------- \\
//                                    Helper Methods                                              \\
// ---------------------------------------------------------------------------------------------- \\

	/**
	 * Checks if the map contains all the given objects
	 *
	 * @param map the map of objects
	 * @param objects the objects in the pane
	 */
	@SafeVarargs
	public static <T> void assertMap (Map<Node, T> map, T... objects) {
		assertEquals(objects.length, map.size());
		for (T object : objects) {
			assertEquals(object, map.get(((UMLObject) object).getModel()));
		}
	}

	/**
	 * Checks if the vector contains all the given objects in given order
	 *
	 * @param vector vector of objects
	 * @param objects the objects to be tested if they are in the given vector
	 */
	@SafeVarargs
	public static <T> void assertVector (Vector<T> vector, T... objects) {
		assertEquals(objects.length, vector.size());
		for (int i = 0; i < objects.length; i++) {
			assertEquals(objects[i], vector.elementAt(i));
		}
	}

	/**
	 * Checks if the given controllers undo/redo stacks are of the given sizes.
	 *
	 * @param controller controller whose undo/redo stacks are being tested.
	 * @param actionsSize expected size of controller's undo stack.
	 * @param undoneActionsSize expected size of controller's redo stack.
	 */
	public static void assertStackSizes (Controller controller, int actionsSize, int undoneActionsSize) {
		assertEquals(actionsSize, controller.ACTIONS.size());
		assertEquals(undoneActionsSize, controller.UNDONE_ACTIONS.size());
	}

	/**
	 * Checks if the passed in node has all the connections it should.
	 *
	 * @param node the node
	 * @param actual the actual connectors that should be attached to the node
	 */
	public static void assertConnections (UMLNode node, UMLConnector... actual) {
		Vector<UMLConnector> expected = node.getConnections();
		assertEquals(expected.size(), actual.length);
		for (UMLConnector connector : actual) {
			assertTrue(expected.contains(connector));
		}
	}
}
