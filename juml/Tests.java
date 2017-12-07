package juml;

import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import java.util.Vector;
import java.util.Arrays;
import java.util.Map;
import umlobject.*;
import static org.junit.Assert.*;
import javafx.collections.*;
import org.junit.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Tests.
 */
public class Tests {
	
	/** The pane. */
	Pane pane;
	
	/** The juml. */
	Controller juml;
	
	/** The p 3. */
	Point p1, p2, p3;
	
	/** The cb 3. */
	ClassBox cb1, cb2, cb3;
	
	/** The c 6. */
	UMLConnector c1, c2, c3, c4, c5, c6;

	/**
	 * Sets the up.
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

		cb1 = new ClassBox(4,5);
		cb2 = new ClassBox(5,6);
		cb3 = new ClassBox(6,7);
	}

	/**
	 * Adds the points.
	 */
	@Test
	public void addPoints(){
		juml.addObjects(p1, p2, p3);

		assertEquals(3, pane.getChildren().size());
		assertMap(juml.NODES, p1, p2, p3);
	}

	/**
	 * Adds the class boxes.
	 */
	@Test
	public void addClassBoxes(){
		juml.addObjects(cb1, cb2, cb3);

		assertEquals(3, pane.getChildren().size());
		assertMap(juml.NODES, cb1, cb2, cb3);
	}

	/**
	 * Adds the connectors between points.
	 */
	@Test
	public void addConnectorsBetweenPoints() {
		juml.addObjects(p1, p2, p3);

		c1 = new Association(p1, p2);
		c2 = new Association(p2, p3);
		c3 = new Association(p1, p3);

		juml.addObjects(c1, c2, c3);

		assertEquals(6, pane.getChildren().size());
		assertMap(juml.CONNECTORS, c1, c2, c3);
		assertConnections(p1, c1, c3);
		assertConnections(p2, c1, c2);
		assertConnections(p3, c2, c3);
	}

	/**
	 * Adds the connectors between class boxes.
	 */
	@Test
  public void addConnectorsBetweenClassBoxes() {
		juml.addObjects(cb1, cb2, cb3);

		c1 = new Association(cb1, cb2);
		c2 = new Association(cb2, cb3);
		c3 = new Association(cb1, cb3);

		juml.addObjects(c1, c2, c3);

		assertMap(juml.CONNECTORS, c1, c2, c3);

		assertEquals(6, pane.getChildren().size());
		assertConnections(cb1, c1, c3);
		assertConnections(cb2, c1, c2);
		assertConnections(cb3, c2, c3);
  }

	/**
	 * Adds the connectors between points and class boxes.
	 */
	@Test
	public void addConnectorsBetweenPointsAndClassBoxes() {
		juml.addObjects(p1, p2, cb1, cb2);

		c1 = new Association (p1, p2);
		c2 = new Association (p1, cb1);
		c3 = new Association (p1, cb2);
		c4 = new Association (p2, cb1);
		c5 = new Association (p2, cb2);
		c6 = new Association (cb1, cb2);

		juml.addObjects(c1, c2, c3, c4, c5, c6);

		assertEquals(10, pane.getChildren().size());

		assertMap(juml.CONNECTORS, c1, c2, c3, c4, c5, c6);

		assertConnections(p1, c1, c2, c3);
		assertConnections(p2, c1, c4, c5);
		assertConnections(cb1, c2, c4, c6);
		assertConnections(cb2, c3, c5, c6);
	}

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

 	}

	/**
	 * Delete connectors.
	 */
	@Test
	public void deleteConnectors() {
		juml.addObjects(p1, p2);

		c1 = new Association(p2, p1);
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
	}

	/**
	 * Delete points with connectors.
	 */
	@Test
	public void deletePointsWithConnectors() {
		assertTrue(pane.getChildren().isEmpty());

		//add points to the pane/hashmap
		juml.addObjects(p1, p2, p3);

		c1 = new Association(p1, p2);
		c2 = new Association(p2, p3);

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
	}

	/**
	 * Gets the points.
	 *
	 * @return the points
	 */
	@Test
	public void getPoints() {
		//set up points and connectors
		juml.addObjects(p1, p2, p3);

		c1 = new Association(p1, p2);
		c2 = new Association(p2, p3);
		c3 = new Association(p1, p3);

		juml.addObjects(c1, c2, c3);

		assertEquals(p1, juml.getObject(p1.getModel()));
		assertEquals(p2, juml.getObject(p2.getModel()));
		assertEquals(p3, juml.getObject(p3.getModel()));
	}

	/**
	 * Gets the connectors.
	 *
	 * @return the connectors
	 */
	@Test
	public void getConnectors() {
		//set up points and connectors
		juml.addObjects(p1, p2, p3);

		c1 = new Association(p1, p2);
		c2 = new Association(p2, p3);
		c3 = new Association(p1, p3);

		juml.addObjects(c1, c2, c3);

		assertEquals(c1, juml.getObject(c1.getModel()));
		assertEquals(c2, juml.getObject(c2.getModel()));
		assertEquals(c3, juml.getObject(c3.getModel()));
	}

	/**
	 * Gets the class boxes.
	 *
	 * @return the class boxes
	 */
	@Test
	public void getClassBoxes() {
		//set up points and connectors
		juml.addObjects(cb1, cb2, cb3);

		c1 = new Association(cb1, cb2);
		c2 = new Association(cb2, cb3);
		c3 = new Association(cb1, cb3);

		juml.addObjects(c1, c2, c3);

		ObservableList<Node> children = ((VBox) cb2.getModel()).getChildren();

		for (Node child : children) {
			assertEquals(cb2, juml.getObject(child));
		}

		assertEquals(cb2, juml.getObject(cb2.getModel()));
	}

// ---------------------------------------------------------------------------------------------- \\

	/**
 * Assert map.
 *
 * @param <T> the generic type
 * @param map the map
 * @param objects the objects
 */
@SafeVarargs
	public static <T> void assertMap (Map<Node, T> map, T... objects) {
		assertEquals(objects.length, map.size());
		for (T object : objects) {
			assertEquals(object, map.get(((UMLObject) object).getModel()));
		}
	}

	/**
	 * Assert connections.
	 *
	 * @param node the node
	 * @param actual the actual
	 */
	public static void assertConnections (UMLNode node, UMLConnector... actual) {
		Vector<UMLConnector> expected = node.getConnections();
		assertEquals(expected.size(), actual.length);
		for (UMLConnector connector : actual) {
			assertTrue(expected.contains(connector));
		}
	}
}
