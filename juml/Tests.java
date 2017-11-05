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

public class Tests {
	Pane pane;
	Controller juml;
	Point p1, p2, p3;
	ClassBox cb1, cb2, cb3;
	UMLConnector c1, c2, c3, c4, c5, c6;

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

	@Test
	public void addPoints(){
		juml.addObjects(p1, p2, p3);

		assertEquals(3, pane.getChildren().size());
		assertMap(juml.NODES, p1, p2, p3);
	}

	@Test
	public void addClassBoxes(){
		juml.addObjects(cb1, cb2, cb3);

		assertEquals(3, pane.getChildren().size());
		assertMap(juml.NODES, cb1, cb2, cb3);
	}

	@Test
	public void addConnectorsBetweenPoints() {
		juml.addObjects(p1, p2, p3);

		c1 = new UMLConnector(p1, p2);
		c2 = new UMLConnector(p2, p3);
		c3 = new UMLConnector(p1, p3);

		juml.addObjects(c1, c2, c3);

		assertEquals(6, pane.getChildren().size());
		assertMap(juml.CONNECTORS, c1, c2, c3);
		assertConnections(p1, c1, c3);
		assertConnections(p2, c1, c2);
		assertConnections(p3, c2, c3);
	}

	@Test
  public void addConnectorsBetweenClassBoxes() {
		juml.addObjects(cb1, cb2, cb3);

		c1 = new UMLConnector(cb1, cb2);
		c2 = new UMLConnector(cb2, cb3);
		c3 = new UMLConnector(cb1, cb3);

		juml.addObjects(c1, c2, c3);

		assertMap(juml.CONNECTORS, c1, c2, c3);

		assertEquals(6, pane.getChildren().size());
		assertConnections(cb1, c1, c3);
		assertConnections(cb2, c1, c2);
		assertConnections(cb3, c2, c3);
  }

	@Test
	public void addConnectorsBetweenPointsAndClassBoxes() {
		juml.addObjects(p1, p2, cb1, cb2);

		c1 = new UMLConnector (p1, p2);
		c2 = new UMLConnector (p1, cb1);
		c3 = new UMLConnector (p1, cb2);
		c4 = new UMLConnector (p2, cb1);
		c5 = new UMLConnector (p2, cb2);
		c6 = new UMLConnector (cb1, cb2);

		juml.addObjects(c1, c2, c3, c4, c5, c6);

		assertEquals(10, pane.getChildren().size());

		assertMap(juml.CONNECTORS, c1, c2, c3, c4, c5, c6);

		assertConnections(p1, c1, c2, c3);
		assertConnections(p2, c1, c4, c5);
		assertConnections(cb1, c2, c4, c6);
		assertConnections(cb2, c3, c5, c6);
	}

  @Test
  public void deletePoints(){
		juml.addObjects(p1, p2);

		assertEquals(2, pane.getChildren().size());
		assertMap(juml.NODES, p1, p2);

		juml.deleteObject(p1);

		assertEquals(1, pane.getChildren().size());
		assertNull(juml.NODES.get(p1.getModel()));
		assertMap(juml.NODES, p2);
  }

 	@Test
 	public void deleteClassBoxes(){
		juml.addObjects(cb1, cb2);

		assertEquals(2, pane.getChildren().size());
		assertMap(juml.NODES, cb1, cb2);

 		juml.deleteObject(cb1);

 		assertEquals(1, pane.getChildren().size());
		assertNull(juml.NODES.get(cb1.getModel()));
		assertMap(juml.NODES, cb2);

 	}

	@Test
	public void deleteConnectors() {
		juml.addObjects(p1, p2);

		c1 = new UMLConnector(p2, p1);
		juml.addObjects(c1);
		assertEquals(3, pane.getChildren().size());
		// test that each point has added one connection
		assertConnections(p1, c1);
		assertConnections(p2, c1);

		assertMap(juml.CONNECTORS, c1);

		juml.deleteObject(c1);

		assertEquals(2, pane.getChildren().size());
		// see if points removed connection
		assertConnections(p1);
		assertConnections(p2);

		assertNull(juml.CONNECTORS.get(c1.getModel()));
		assertMap(juml.CONNECTORS);
	}

	@Test
	public void deletePointsWithConnectors() {
		assertTrue(pane.getChildren().isEmpty());

		//add points to the pane/hashmap
		juml.addObjects(p1, p2, p3);

		c1 = new UMLConnector(p1, p2);
		c2 = new UMLConnector(p2, p3);

		juml.addObjects(c1, c2);

		assertMap(juml.CONNECTORS, c1, c2);
		assertEquals(5, pane.getChildren().size());
		assertConnections(p1, c1);
		assertConnections(p2, c1, c2);
		assertConnections(p3, c2);

		//this should delete the connector2 too
		juml.deleteObject(p2);

		// see if both point removed the connectors
		assertMap(juml.CONNECTORS);
		assertNull(juml.CONNECTORS.get(c1.getModel()));
		assertNull(juml.CONNECTORS.get(c2.getModel()));
		assertEquals(2, pane.getChildren().size());
		assertConnections(p1);
		assertConnections(p2);
		assertConnections(p3);
	}

	@Test
	public void getPoints() {
		//set up points and connectors
		juml.addObjects(p1, p2, p3);

		c1 = new UMLConnector(p1, p2);
		c2 = new UMLConnector(p2, p3);
		c3 = new UMLConnector(p1, p3);

		juml.addObjects(c1, c2, c3);

		assertEquals(p1, juml.getObject(p1.getModel()));
		assertEquals(p2, juml.getObject(p2.getModel()));
		assertEquals(p3, juml.getObject(p3.getModel()));
	}

	@Test
	public void getConnectors() {
		//set up points and connectors
		juml.addObjects(p1, p2, p3);

		c1 = new UMLConnector(p1, p2);
		c2 = new UMLConnector(p2, p3);
		c3 = new UMLConnector(p1, p3);

		juml.addObjects(c1, c2, c3);

		assertEquals(c1, juml.getObject(c1.getModel()));
		assertEquals(c2, juml.getObject(c2.getModel()));
		assertEquals(c3, juml.getObject(c3.getModel()));
	}

	@Test
	public void getClassBoxes() {
		//set up points and connectors
		juml.addObjects(cb1, cb2, cb3);

		c1 = new UMLConnector(cb1, cb2);
		c2 = new UMLConnector(cb2, cb3);
		c3 = new UMLConnector(cb1, cb3);

		juml.addObjects(c1, c2, c3);

		ObservableList<Node> children = ((VBox) cb2.getModel()).getChildren();

		for (Node child : children) {
			assertEquals(cb2, juml.getObject(child));
		}

		assertEquals(cb2, juml.getObject(cb2.getModel()));
	}

// ---------------------------------------------------------------------------------------------- \\

	@SafeVarargs
	public static <T> void assertMap (Map<Node, T> map, T... objects) {
		assertEquals(objects.length, map.size());
		for (T object : objects) {
			assertEquals(object, map.get(((UMLObject) object).getModel()));
		}
	}

	public static void assertConnections (UMLNode node, UMLConnector... actual) {
		Vector<UMLConnector> expected = node.getConnections();
		assertEquals(expected.size(), actual.length);
		for (UMLConnector connector : actual) {
			assertTrue(expected.contains(connector));
		}
	}
}
